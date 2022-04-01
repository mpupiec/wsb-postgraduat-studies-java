drop table if exists flights;
create table flights(
    id int primary key auto_increment,
    description varchar(200) not null,
    done bit
)