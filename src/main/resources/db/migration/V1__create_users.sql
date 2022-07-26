CREATE TABLE users (
	id bigserial NOT NULL,
	email varchar(255) NULL,
	"password" varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);