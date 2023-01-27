

INSERT INTO persondb.users_roles (user_id, role_id) VALUES(
-- select user_id from users where username = 'admin' -> Hacemos una subconsulta. Trae el user_id del usuario admin y lo reemplazamos el 0 que viene por defecto
                                                           (select user_id from persondb.users where username = 'admin'),
-- select role_id from roles where role_id = 'admin' -> Hacemos una subconsulta. Trae el role_id del ROLE_ADMIN y lo reemplazamos el 0 que viene por defecto
                                                           (select role_id from persondb.roles where role_name = 'ROLE_ADMIN')
                                                           );



INSERT INTO persondb.users_roles (user_id, role_id) VALUES(
                                                              (select user_id from persondb.users where username = 'user.jwt'),
                                                              (select role_id from persondb.roles where role_name = 'ROLE_USER')
                                                          );