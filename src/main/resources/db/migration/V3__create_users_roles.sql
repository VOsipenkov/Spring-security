create table users_roles (
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES springsecurity.users(id),
	CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES springsecurity.roles(id)
);