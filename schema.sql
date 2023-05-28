drop database if exists media;

create database media;

use media;

create table photos (
   photo_id int auto_increment,
   photo mediumblob,
   content_type varchar(128) not null,

   primary key(photo_id)
);

grant all privileges on media.* to 'fred'@'%';