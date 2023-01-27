CREATE TABLE IF NOT EXISTS persondb.users_roles (
                                      id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                      user_id int4 NOT NULL,
                                      role_id int4 NOT NULL,
                                      CONSTRAINT user_roles_pk PRIMARY KEY (id),
                                      CONSTRAINT user_roles_fk FOREIGN KEY (user_id) REFERENCES persondb.users(user_id),
                                      CONSTRAINT user_roles_fk_1 FOREIGN KEY (role_id) REFERENCES persondb.roles(role_id)
);