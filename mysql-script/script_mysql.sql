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
    foreign key (fk_doctor) references doctor(IDCode),
    foreign key (fk_patient) references patient(IDCode)
)engine=InnoDB;

create table delete_request(
	fk_medical int primary key,
    foreign key (fk_medical) references medical(code)
)engine=InnoDB;

create table move_request(
	fk_medical int primary key,
    new_date date,
    foreign key (fk_medical) references medical(code)
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
	select p.name, p.surname, m.date
    from medical m inner join patient p on m.fk_patient = p.IDCode
    where m.fk_doctor = doctor and m.medical_date = a_date;
end $$
delimiter ;


















