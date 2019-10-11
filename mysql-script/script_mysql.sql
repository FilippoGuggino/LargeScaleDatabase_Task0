drop database clinic;
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




