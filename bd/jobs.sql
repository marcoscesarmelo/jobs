drop database jobs;
create database jobs;
use jobs;

create table job (
	partner_id int not null primary key,
	title varchar(100) not null,
	category_id int not null,
	expires_at datetime not null,
	status int not null
) ENGINE=INNODB;

create table user (
	id int not null primary key auto_increment,
	username varchar(10) not null,
	password varchar(10) not null
) ENGINE=INNODB;

insert into user values(default, 'root', 'root');

commit;
