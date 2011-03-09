INSERT INTO tb_user (id, name, pwd, enable) VALUES (1, 'zhuzhen', '1234', true);
INSERT INTO tb_user (id, name, pwd, enable) VALUES (2, 'guolili', '1234', true);

INSERT INTO tb_role (id, name, enable) VALUES (1, 'ROLE_USER', true);
INSERT INTO tb_role (id, name, enable) VALUES (2, 'ROLE_ADMIN', true);

INSERT INTO tb_user_role_relation (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role_relation (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role_relation (user_id, role_id) VALUES (2, 1);

INSERT INTO tb_authority (id, name, enable) VALUES (1, 'AUTH_A', true);
INSERT INTO tb_authority (id, name, enable) VALUES (2, 'AUTH_B', true);
INSERT INTO tb_authority (id, name, enable) VALUES (3, 'AUTH_C', true);

INSERT INTO tb_role_authority_relation (role_id, authority_id) VALUES (1, 1);
INSERT INTO tb_role_authority_relation (role_id, authority_id) VALUES (1, 2);
INSERT INTO tb_role_authority_relation (role_id, authority_id) VALUES (2, 3);

INSERT INTO tb_resource (id, type, value, enable) VALUES (1, 'URL', '/**', true);

INSERT INTO tb_authority_resource_relation (authority_id, resource_id) VALUES (1, 1);

COMMIT;