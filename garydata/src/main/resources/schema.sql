drop table if exists Candy_Delivery;
drop table if exists Candy;
create table if not exists Candy (id bigint not null, name nvarchar(255), price decimal(12,4), primary key (id));
create table if not exists Candy_Delivery (candyId bigint not null, deliveryId bigint not null);