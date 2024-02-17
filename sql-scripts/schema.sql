create table image
(
    image_id   bigserial,
    image_name varchar(255),
    image_url  varchar(255),
    primary key (image_id)
);

create table location
(
    location_id bigserial,
    locality    varchar(255),
    country     varchar(255),
    continent   varchar(255),
    primary key (location_id)
);

create table tour
(
    tour_id            bigserial,
    tour_name          varchar(255),
    location_id        bigint,
    recommended_months integer,
    description        varchar(1000),
    is_featured        boolean,
    booking_count      bigint,
    view_count         bigint,
    primary key (tour_id),
    constraint fkm2m7jcbrvh91t2ss2vgg93rt4
        foreign key (location_id) references location
);

create table booking
(
    booking_id   bigserial,
    tour_id      bigint,
    booking_date date,
    phone_number varchar(255),
    people_count integer,
    comment      varchar(300),
    primary key (booking_id),
    constraint fklc7bhi14w8e558dt15eofelm4
        foreign key (tour_id) references tour
);

create table review
(
    review_id   bigserial,
    tour_id     bigint,
    review_date date,
    author      varchar(255),
    image_id    bigint,
    body        varchar(500),
    primary key (review_id),
    constraint uk_c6cgb1awbhkdhsvgykdx8ikq3
        unique (image_id),
    constraint fk2bu91x77t5ea5nb14e39mqcqs
        foreign key (image_id) references image,
    constraint fk2yxuruefnrj0xan64vi2gg7ag
        foreign key (tour_id) references tour
);

create table tour_image_junction
(
    tour_id  bigint not null,
    image_id bigint not null,
    constraint uk_v7vkr0ifxepje70hckc47fy8
        unique (image_id),
    constraint fkjjux7qfltiv76vlngfwhgswuu
        foreign key (image_id) references image,
    constraint fkfjipm9hocj43ny4x0lv0xa3b6
        foreign key (tour_id) references tour
);

