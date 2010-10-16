package service.interf;

import java.util.List;

import model.test.Tb_user;

public interface ITb_userService {
	public Tb_user getById(int id);

	public void save(Tb_user model);

	public void update(Tb_user model);

	public void delete(Tb_user model);

	public void deleteById(int id);

	@SuppressWarnings("unchecked")
	public List getAll();
}
