
create database clinic;

use clinic;
create table patient(
	IDCode int primary key auto_increment,
    name varchar(50),
    surname varchar(50)
)engine=InnoDB;

create table doctor(
	IDCode int primary key auto_increment,
    name varchar(50),
    surname varchar(50),
    salary int
)engine=InnoDB;

create table employee(
	IDCode int primary key auto_increment,
    name varchar(50),
    surname varchar(50),
    salary int
)engine=InnoDB;

create table medical(
	code int primary key auto_increment,
    fk_doctor int,
    fk_patient int,
    medical_date date,
    approved bool default false, # so another table for create requests isn't needed
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

insert into doctor values (1,"Francesco", "Toccaferro", 1200), (2,"Leonardo", "Cecchelli", 2000), (3,"Filippo", "Guggino", 2100);
insert into patient values (1,"Carlo", "Vallati"), (2,"Giuseppe", "Lettieri"), (3,"Giuseppe", "Anastasi");
insert into employee values (1,"Vittoria", "Dattilo", 1500), (2,"Emilio", "Paolini", 1000), (3,"Zaccaria", "Essaid", 100);
insert into medical (fk_doctor,fk_patient,medical_date)values (2,1,'2019-10-25'),(1,2,'2019-10-23'),(3,3,'2019-10-21'),(3,1,'2019-10-22');




