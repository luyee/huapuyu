package service.interf;

import java.util.List;

import model.Role;

public interface RoleService
{
	public Role getById(Integer id);

	public void save(Role entity);

	public void update(Role entity);

	public void delete(Role entity);

	public void deleteById(Integer id);

	public List<Role> getAll();
}