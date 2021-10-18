create table user_detail(
    id  bigserial not null,
    email varchar(255),
    name varchar(255),
    password varchar(255),
    primary key (id)
);

CREATE TABLE task(
    id bigserial not null,
    name varchar(50) NOT NULL,
    description varchar(200),
    created_date date not null,
    last_date date not null,
    completed_date date,
    complete boolean default false,
    assigned_by bigserial,
    assigned_to bigserial,
    primary key (id),
    foreign key(assigned_by) references user_detail(id),
    foreign key(assigned_to) references user_detail(id)
 );