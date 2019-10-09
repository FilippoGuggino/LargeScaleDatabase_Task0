create database clinica;

use clinica;
create table paziente(
    name varchar(50),
    codfisc varchar(50) primary key
)engine=InnoDB;

create table dottore(
    nome varchar(50),
    codfisc varchar(50) primary key,
    stipendio int
)engine=InnoDB;

create table visita(
    tipo varchar(50),
    data date,
    fk_dottore varchar(50),
    fk_paziente varchar(50),
    primary key (fk_dottore, fk_paziente, data),
    foreign key (fk_dottore) references dottore(codfisc),
    foreign key (fk_paziente) references paziente(codfisc)
)engine=InnoDB;

INSERT INTO `paziente` VALUES ('filippo','bellino'),('leonardo','ciaccio'),('leonardo','ciccettino'),('zaccaria','fruzza');
INSERT INTO `clinica`.`dottore` (`codfisc`, `stipendio`, `nome`) VALUES ('bellino', '1000', 'toccaferro');
