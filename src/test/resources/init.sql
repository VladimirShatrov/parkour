create table companies
(
    id serial primary key,
    name_company varchar(50) not null
);

create table switches
(
    id                serial
        primary key,
    id_company        integer      not null
        references companies
            on delete cascade,
    title             varchar(100) not null,
    price             integer      not null,
    poeports          integer      not null,
    sfpports          integer      not null,
    ups               boolean      not null,
    controllable      boolean,
    is_there_a_switch boolean default true
);

create table switch_price_history
(
    id          serial
        primary key,
    id_switch   integer not null
        references switches
            on delete cascade,
    new_price   integer not null,
    change_date date default CURRENT_DATE
);


SELECT setval('companies_id_seq', (SELECT MAX(id) FROM companies));
SELECT setval('switches_id_seq', (SELECT MAX(id) FROM switches));
SELECT setval('switch_price_history_id_seq', (SELECT MAX(id) FROM switch_price_history));
