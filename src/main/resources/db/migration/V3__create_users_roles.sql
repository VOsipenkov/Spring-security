create table users_roles (
  user_id bigserial not null,
  role_id bigserial not null,
  foreign key (user_id) references users(id),
  foreign key (role_id) references roles(id)
);