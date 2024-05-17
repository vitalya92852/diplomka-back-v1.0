create table users (
     id serial8 not null ,
     username varchar not null ,
     password varchar not null ,
     primary key (id)
);



create table student(
    id serial8 not null primary key ,
    name varchar not null ,
    lastname varchar not null ,
    surname varchar not null ,
    group_id int4 not null ,
    user_id int4 not null ,
    course int4 not null ,
    foreign key (user_id) references users(id),
    foreign key (group_id) references groupname(id)
);

insert into student(name, lastname, surname, group_id, user_id, course)
values ('Vitaliy','Vitaliy','Vitaliy',7,2,2);



create table teacher(
    id  serial8 not null primary key ,
    name varchar not null ,
    lastname varchar not null ,
    surname varchar not null ,
    group_id int4 not null ,
    user_id int4 not null ,
    foreign key (group_id) references groupname(id),
    foreign key (user_id) references users(id)

);


insert into teacher (name,lastname,surname,group_id,user_id)
values ('Anata','Anata','Anata',7,3);

create table groupname(
    id serial8 not null primary key ,
    name varchar not null
);

insert into groupname(name)
values ('20-05'),
       ('20-06'),
       ('20-07');


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

create table group_subject_teacher(
    id serial8 not null primary key ,
    group_id int4 not null ,
    subject_id int4 not null ,
    teacher_id int4 not null ,
    foreign key (group_id) references groupname(id),
    foreign key (subject_id) references subject(id),
    foreign key (teacher_id) references teacher(id)
);

insert into group_subject_teacher(group_id, subject_id, teacher_id)
values (7,1,1);

create table status(
    id serial8 not null primary key ,
    name varchar not null
);

insert into status(name)
values ('В ожидании'),
       ('Одобрено'),
       ('Отклонено');

create table request_grade(
    id serial8 not null primary key ,
    grade int4 not null ,
    student_id int4 not null ,
    status_id int4 not null ,
    group_subject_teacher_id int4 not null ,
    foreign key (group_subject_teacher_id) references  group_subject_teacher(id),
    foreign key (student_id) references student(id),
    foreign key (status_id) references status(id)

);
-- Надо надо надо надо надо надо надо надо надо / пример хороший  с джоином

-- SELECT r.*
-- FROM request_grade r
--          JOIN group_subject_teacher gst ON r.group_subject_teacher_id = gst.id
-- WHERE gst.teacher_id = :teacherId;
--
-- SELECT rg.id
-- FROM request_grade rg
--          JOIN group_subject_teacher gst ON rg.group_subject_teacher_id = gst.id
-- WHERE gst.teacher_id = :teacherId;


create table aim(
    id serial8 not null primary key ,
    name varchar not null

);



insert into aim(name)
values ('Front-end'),
       ('Back-end'),
       ('Full-stack'),
       ('Data-science'),
       ('Machine Learning');

create table aim_subject_student(
    id serial8 not null primary key ,
    aim_id int4 not null ,
    subject_id int4 not null ,
    student_id int4 not null ,
    foreign key (student_id) references student(id),
    foreign key (aim_id) references aim(id),
    foreign key (subject_id) references subject(id)
);

insert into aim_subject_student(aim_id, subject_id,student_id)
values (1,2,1),
       (1,3,1);


create table student_aim_status(
    id serial8 not null primary key ,
    student_id int4 not null ,
    aim_id int4 not null ,
    status_id int4 not null ,
    foreign key (status_id) references status(id),
    foreign key (student_id) references student (id),
    foreign key (aim_id) references aim(id)
);

create table student_resume(
    id serial8 not null primary key ,
    student_id int4 not null ,
    name varchar not null ,
    path varchar not null ,
    foreign key (student_id) references student(id)
);

create table student_aim_name_info(
    id serial8 not null primary key ,
    student_id int4 not null ,
    aim_id int4 not null ,
    name varchar not null ,
    info varchar not null ,
    foreign key (student_id) references student(id),
    foreign key (aim_id) references aim(id)
);

/* Саня */

create table employer_resume(
    id serial8 not null primary key ,
    employer_id int4 not null ,
    company_name varchar not null ,
    category_name varchar not null ,
    company_status varchar not null ,
    company_city varchar not null ,
    salary int4 not null ,
    additional_info varchar not null ,
    update_date timestamp not null ,
    experience_lvl_id int4 not null ,
    education_lvl_id int4 not null ,
    foreign key (experience_lvl_id) references experience_lvl(id),
    foreign key (education_lvl_id) references experience_lvl(id),
    foreign key (employer_id) references employer(id)
);

create table experience_lvl(
    id serial8 not null primary key ,
    name varchar not null
);

insert into experience_lvl (name)
values ('Нет опыта'),
       ('Не требуется'),
       ('от 1 года до 3 лет'),
       ('от 3 лет до 6 лет'),
       ('от 6 лет');

create table education_lvl(
    id serial8 not null primary key ,
    name varchar not null
);

insert into education_lvl(name)
values ('Не трубется или не указано'),
       ('Среднее проффесиональное'),
       ('Высшее');

create table employer (
    id  serial8 not null primary key ,
    name varchar not null ,
    lastname varchar not null ,
    surname varchar not null ,
    user_id int4 not null ,
    foreign key (user_id) references users(id)
);

create table response_resume(
    id serial8 not null primary key ,
    user_id int4 not null ,
    employer_resume_id int4 not null ,
    student_resume_id int4 not null ,
    foreign key (student_resume_id) references student_resume(id),
    foreign key (user_id) references users(id),
    foreign key (employer_resume_id) references employer_resume(id)
);

create table favorites(
    id serial8 not null primary key ,
    user_id int4 not null ,
    employer_resume_id int4 not null ,
    foreign key (user_id) references users(id),
    foreign key (employer_resume_id) references employer_resume(id)
)











