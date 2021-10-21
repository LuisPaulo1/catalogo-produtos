create table product (
	id int not null auto_increment,	
	name varchar(80) not null,
	description text not null,
	price decimal(10,2) not null,	
	primary key (id)
) engine=InnoDB default charset=utf8;