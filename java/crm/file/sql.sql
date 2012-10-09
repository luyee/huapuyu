#用户具有的角色
SELECT 
a.id, a.user_name, a.password, a.name, a.email, a.enabled,
c.id, c.name, c.role, c.enabled, 
e.id, e.name, e.url, e.enabled
FROM tb_user a, rlt_user_to_role b, tb_role c, rlt_role_to_url d, tb_url e 
WHERE a.id = b.user_id AND b.role_id = c.id AND c.id = d.role_id AND d.url_id = e.id;

#用户所属用户组具有的角色
SELECT 
a.id, a.user_name, a.password, a.name, a.email, a.enabled,
c.id, c.name, c.enabled,
e.id, e.name, e.role, e.enabled, 
g.id, g.name, g.url, g.enabled
FROM tb_user a, rlt_user_to_user_group b, tb_user_group c, rlt_user_group_to_role d, tb_role e, rlt_role_to_url f, tb_url g 
WHERE a.id = b.user_id AND b.user_group_id = c.id AND c.id = d.user_group_id AND d.role_id = e.id AND e.id = f.role_id AND f.url_id = g.id;