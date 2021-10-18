create table task_backup (
    id  bigserial not null,
    created_date date,
    description varchar(255),
    last_date date,
    name varchar(255),
    operation_type varchar(255),
    task_id int8,
    primary key (id)
);