INSERT INTO tb_user (id, user_name, name, pwd, enable) VALUES (1, 'zhuzhen', 'zhuzhen', '1234', true);
INSERT INTO tb_user (id, user_name, name, pwd, enable) VALUES (2, 'guolili', 'guolili', '1234', true);

INSERT INTO tb_role (id, name, enable) VALUES (1, 'ADMIN', true);
INSERT INTO tb_role (id, name, enable) VALUES (2, 'USER', true);
INSERT INTO tb_role (id, name, enable) VALUES (999, 'RESOURCE', true);

INSERT INTO tb_user_role_relation (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role_relation (user_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_role_relation (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_user_group (id, name, enable) VALUES (1, 'ADMIN', true);
INSERT INTO tb_user_group (id, name, enable) VALUES (2, 'USER', true);

INSERT INTO tb_user_user_group_relation (user_id, user_group_id) VALUES (1, 1);
INSERT INTO tb_user_user_group_relation (user_id, user_group_id) VALUES (2, 2);

INSERT INTO tb_user_group_role_relation (user_group_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_group_role_relation (user_group_id, role_id) VALUES (1, 2);
INSERT INTO tb_user_group_role_relation (user_group_id, role_id) VALUES (2, 2);

INSERT INTO tb_resource (id, type, url, name, enable) VALUES (1, 'JSP', '/index.jsp', 'index page', true);
INSERT INTO tb_resource (id, type, url, name, enable) VALUES (2, 'JSP', '/main.jsp', 'main page', true);

INSERT INTO tb_role_resource_relation (role_id, resource_id) VALUES (1, 1);
INSERT INTO tb_role_resource_relation (role_id, resource_id) VALUES (2, 2);
INSERT INTO tb_role_resource_relation (role_id, resource_id) VALUES (999, 1);
INSERT INTO tb_role_resource_relation (role_id, resource_id) VALUES (999, 2);

COMMIT;