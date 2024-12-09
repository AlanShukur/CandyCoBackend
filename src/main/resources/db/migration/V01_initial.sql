create table if not exists customer (
    id serial primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(20) not null
    );

create table if not exists product (
    id serial primary key,
    name varchar(100) not null,
    description text,
    price int not null,
    status varchar(20) not null,
    quantity int not null
    );

create table if not exists orders (
     id serial primary key,
     customer_id int not null,
     shipping_address text not null,
     total_price int not null,
     shipped boolean default false,
     shipping_charge int not null,
     foreign key (customer_id) references customer (id) on delete cascade
    );

create table if not exists order_items (
    id serial primary key,
    order_id int not null,
    product_id int not null,
    quantity int not null,
    foreign key (order_id) references orders (id) on delete cascade,
    foreign key (product_id) references product (id) on delete cascade
    );

create table if not exists customer_address (
    id serial primary key,
    customer_id int not null,
    street varchar(255) not null,
    city varchar(100) not null,
    state varchar(100),
    postal_code varchar(20) not null,
    country varchar(100) not null,
    foreign key (customer_id) references customer (id) on delete cascade
    );

create sequence customer_seq;
create sequence product_seq;
create sequence orders_seq;
create sequence order_items_seq;
create sequence customer_address_seq;