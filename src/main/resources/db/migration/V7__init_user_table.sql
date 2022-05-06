create table users
(
    id int primary key auto_increment,
    login varchar(200) not null,
    pas varchar(200) not null
);
create table user_details
(
    id int primary key auto_increment,
    first_name varchar(200) not null,
    last_name varchar(200) not null,
    user_id int not null,
    foreign key (user_id) references users (id)
);
alter table flight_groups add column user_id int null;
alter table flight_groups add foreign key (user_id) references flights(id);