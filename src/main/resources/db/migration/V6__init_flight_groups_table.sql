create table flight_groups
(
    id int primary key auto_increment,
    description varchar(200) not null,
    done bit,
    departure_airport varchar(300) null,
    arrival_airport varchar(300) null
);
alter table flights add column flight_groups_id int null;
alter table flights add foreign key (flight_groups_id) references flight_groups(id);