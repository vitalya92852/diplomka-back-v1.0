create table users (
     id serial8 not null ,
     name varchar not null ,
     age int4 not null ,
     gender varchar not null ,
     IndividualIdentificationNumber int8 not null ,
     username varchar not null ,
     password varchar not null ,
     primary key (id)
);

insert into users(name, age, gender, IndividualIdentificationNumber,username,password)
values ('Павел',21,'male',12345,'pavel123','pavel123'),
       ('Наталья',21,'female',12346,'natasha123','natasha123'),
       ('Зуза',20,'female',12347,'zuza123','zuza123'),
       ('Саня',20,'male',12348,'sanya123','sanya123');

drop table if exists subject cascade ;

create table subject(
            id serial8 not null ,
            name varchar not null,
            primary key (id)
);

insert into subject (name)
values ('Java'),
       ('Python'),
       ('C++'),
       ('Pascal'),
       ('Английский');


create table grade(
    id serial8 not null ,
    users_id int8 not null ,
    subject_id int8 not null ,
    grade int4 not null ,
    semester_id int4 not null ,
    week int4 not null ,
    type_of_grade_id int4 not null ,
    primary key (id),
    foreign key (users_id) references users(id),
    foreign key (subject_id) references subject(id),
    foreign key (semester_id) references semester(id),
    foreign key (type_of_grade_id) references type_of_grade(id)
);

drop table if exists grade;

insert into grade (users_id, subject_id, grade,semester_id,week,type_of_grade_id)
values (2,1,50,1,1,2),
       (2,1,30,1,2,2),
       (2,1,70,1,3,2),
       (2,1,55,1,4,2),
       (2,1,100,1,5,2),
       (2,1,10,1,6,2);

create table type_of_grade(
    id serial8 not null ,
    name varchar not null ,
    primary key (id)
);
insert into type_of_grade(name)
values ('Practise'),
       ('Academ');

create table roles(
    id serial8 not null ,
    name varchar not null,
    primary key (id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');
drop table if exists roles;

create table users_roles(
    id serial8 primary key,
    users_id int8 not null ,
    roles_id int8 not null ,
    foreign key (users_id) references users (id),
    foreign key (roles_id) references roles (id)
);

insert into users_roles(users_id,roles_id)
values (2,2),
        (1,1);

drop table if exists subject;

select users.username,grade.grade
from users
INNER JOIN grade ON users.id = grade.users_id;

create table semester(
    id serial8 not null ,
    count int4 not null ,
    primary key (id)
);
drop table if exists semester cascade;

insert into semester(count)
values (1),
       (2),
       (3);


