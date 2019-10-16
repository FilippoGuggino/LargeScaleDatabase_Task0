drop database clinic;
create database clinic;

use clinic;
create table patient(
	IDCode int primary key auto_increment,
    name varchar(50),
    surname varchar(50),
    unique(name,surname)
)engine=InnoDB;

create table doctor(
	IDCode int primary key auto_increment,
    name varchar(50),
    surname varchar(50),
    unique(name,surname)
)engine=InnoDB;

create table employee(
	IDCode int primary key auto_increment,
    name varchar(50),
    surname varchar(50),
    unique(name,surname)
)engine=InnoDB;

create table medical(
	code int primary key auto_increment,
    fk_doctor int,
    fk_patient int,
    medical_date date,
    approved bool default false,  /*so another table for create requests isn't needed*/
    unique (fk_doctor, fk_patient, medical_date),
    foreign key (fk_doctor) references doctor(IDCode) on delete cascade,
    foreign key (fk_patient) references patient(IDCode) on delete cascade
)engine=InnoDB;

create table delete_request(
	fk_medical int primary key,
    foreign key (fk_medical) references medical(code) on delete cascade
)engine=InnoDB;

create table move_request(
	fk_medical int primary key,
    new_date date,
    foreign key (fk_medical) references medical(code) on delete cascade
)engine=InnoDB;

insert into doctor values (1,"Francesco", "Toccaferro"), (2,"Leonardo", "Cecchelli"), (3,"Filippo", "Guggino");
insert into patient values (1,"Carlo", "Vallati"), (2,"Giuseppe", "Lettieri"), (3,"Giuseppe", "Anastasi");
insert into employee values (1,"Vittoria", "Dattilo"), (2,"Emilio", "Paolini"), (3,"Zaccaria", "Essaid");
insert into medical (fk_doctor,fk_patient,medical_date)values (2,1,'2019-10-25'),(1,2,'2019-10-23'),(3,3,'2019-10-21'),(3,1,'2019-10-22');


drop procedure if exists new_medical_request;
delimiter $$
create procedure new_medical_request(patient int, doctor int, med_date date)
begin
	insert into medical (fk_doctor,fk_patient,medical_date)values(doctor,patient,med_date);
end $$
delimiter ;

drop procedure if exists new_delete_request; /*selects 0 if medical didn't exist, selects 1 if everything was ok*/
delimiter $$
create procedure new_delete_request(patient int, doctor int, med_date date)
begin
	DECLARE med_code int;
    select code into med_code
	from medical
    where fk_patient = patient and fk_doctor = doctor and medical_date = med_date;
    if med_code is null
    then
		select 0;
	else
		begin
			replace into delete_request values(med_code);
            select 1;
		end;
	end if;
end $$
delimiter ;

drop procedure if exists new_move_request; /*selects 0 if medical didn't exist, selects 1 if everything was ok*/
delimiter $$
create procedure new_move_request(patient int, doctor int, med_date date, new_date date)
begin
	DECLARE med_code int;
    select code into med_code
	from medical
    where fk_patient = patient and fk_doctor = doctor and medical_date = med_date;
    if med_code is null
    then
		select 0;
	else
		begin
			replace into move_request values(med_code, new_date);
            select 1;
		end;
	end if;
end $$
delimiter ;

drop procedure if exists get_code; /*stampa il codice di un utente specificato*/
delimiter $$
create procedure get_code(r_name varchar(50), r_surname varchar(50), role varchar(10))  #roles = {"patient","doctor","employee"}
begin
	DECLARE ret int DEFAULT 0;
	if role='patient'
    then
		select IDCode into ret
        from patient
        where name = r_name and surname = r_surname;
	elseif role='doctor'
    then
		select IDCode into ret
        from doctor
        where name = r_name and surname = r_surname;
	elseif role='employee'
    then
		select IDCode into ret
        from employee
        where name = r_name and surname = r_surname;
	end if;
    select ret;
end $$
delimiter ;

drop procedure if exists handle_medical_request;
delimiter $$
create procedure handle_medical_request(patient int, doctor int, med_date date, result int) /*result=1 to approve, result=0 to reject */
/*selects 1 in case of success, 0 if request was already approved or didn't exist*/
begin
	DECLARE med_code int;
    select code into med_code
	from medical
    where fk_patient = patient and fk_doctor = doctor and medical_date = med_date and approved=false;
    if med_code IS NULL
    then select 0;
    else
		begin
			if result=1
			then
				update medical
				set approved=true
				where code=med_code;
			else 
				delete from medical
				where code = med_code;
			end if;
			select 1;
        end;
	end if;
end $$
delimiter ;

drop procedure if exists handle_delete_request;
delimiter $$
create procedure handle_delete_request(patient int, doctor int, med_date date, result int) /*result=1 to approve, result=0 to reject */
/*selects 1 in case of success, 0 if request  or medical didn't exist*/
begin
	DECLARE med_code int;
    DECLARE consistency int;
    select code into med_code
	from medical
    where fk_patient = patient and fk_doctor = doctor and medical_date = med_date;
     if med_code IS NULL
    then select 0;
    else
		begin
			select count(*) into consistency
            from delete_request
            where fk_medical = med_code;
			if (result=1 and consistency = 1)
			then
				begin
					delete from medical
					where code = med_code;
                    select 1;
				end;
			elseif (result <> 1 and consistency = 1)
            then
				begin
					delete from delete_request
					where fk_medical = med_code;
                    select 1;
				end;
			else
				select 0;
			end if;
        end;
	end if;
end $$
delimiter ;

drop procedure if exists handle_move_request;
delimiter $$
create procedure handle_move_request(patient int, doctor int, med_date date, result int) /*result=1 to approve, result=0 to reject */
/*selects 1 in case of success, 0 if request  or medical didn't exist*/
begin
	DECLARE med_code int;
    DECLARE update_date date default null;
    select code into med_code
	from medical
    where fk_patient = patient and fk_doctor = doctor and medical_date = med_date;
     if med_code IS NULL
    then select 0;
    else
		begin
			select new_date into update_date
            from move_request
            where fk_medical = med_code;
			if (result=1 and update_date is not null)
			then
				begin
					update medical
					set medical_date = update_date
                    where code= med_code;
                    
                    delete from move_request
                    where fk_medical = med_code;
                    select 1;
				end;
			elseif (result <> 1 and update_date is not null)
            then
				begin
					delete from move_request
                    where fk_medical = med_code;
                    select 1;
				end;
			else
				select 0;
			end if;
        end;
	end if;
end $$
delimiter ;

/*used by employees to see pending requests*/
drop procedure if exists get_new_requests;
delimiter $$
create procedure get_new_requests()
begin
	select d.name, d.surname, d.IDCode, p.name, p.surname, p.IDCode, m.date
    from medical m inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode
    where approved=false;
end $$
delimiter ;

/*used by employees to see pending requests*/
drop procedure if exists get_delete_requests;
delimiter $$
create procedure get_delete_requests()
begin
	select d.name, d.surname, d.IDCode, p.name, p.surname, p.IDCode, m.date
    from (delete_request dr inner join medical m  on dr.fk_medical = m.code)
				inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode;
end $$
delimiter ;

/*used by employees to see pending requests*/
drop procedure if exists get_move_requests;
delimiter $$
create procedure get_move_requests()
begin
	select d.name, d.surname, d.IDCode, p.name, p.surname, p.IDCode, m.date
    from (move_request dr inner join medical m  on dr.fk_medical = m.code)
				inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode;
end $$
delimiter ;

/*used by patients to get their schedule*/
drop procedure if exists get_personal_schedule;
delimiter $$
create procedure get_personal_schedule(patient int)
begin
	select d.name, d.surname, m.date
    from medical m inner join doctor d on m.fk_doctor = d.IDCode 
    where m.fk_patient = patient;
end $$
delimiter ;

/*used by doctors to get their agenda*/
drop procedure if exists get_personal_agenda;
delimiter $$
create procedure get_personal_agenda(doctor int, a_date date)
begin
	select d.name, d.surname, p.name, p.surname, m.medical_date
    from medical m inner join patient p on m.fk_patient = p.IDCode inner join doctor d on m.fk_doctor = d.IDCode 
    where m.fk_doctor = doctor and m.medical_date = a_date;
end $$
delimiter ;

/*used by employees to get a schedule by a paramether*/
drop procedure if exists get_day_schedule;
delimiter $$
create procedure get_day_schedule(by_date date)
/*set integer params to 0 if you don't want to select by that paramehter.
set by_date  = '' if you don't want to select by date*/
begin
	select d.name, d.surname, p.name, p.surname
    from medical m inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode
    where m.medical_date = by_date;
end $$
delimiter ;

/*used by employees to get a schedule by a paramether*/
drop procedure if exists get_doctor_schedule;
delimiter $$
create procedure get_doctor_schedule(by_doctor int)
/*set integer params to 0 if you don't want to select by that paramehter.
set by_date  = '' if you don't want to select by date*/
begin
	select p.name, p.surname, m.date
    from medical m inner join doctor d on m.fk_doctor = d.IDCode inner join patient p on m.fk_patient = p.IDCode
    where m.fk_doctor = by_doctor;
end $$
delimiter ;
















