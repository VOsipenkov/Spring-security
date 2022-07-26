create table authorities(
username varchar(250) not null,
authority varchar(250) not null,
foreign key (username) references users(username),
unique (username, authority)
);