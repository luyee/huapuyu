package dao.jpa;

import java.util.List;

import model.Tb_depart;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.interf.ITb_departDao;

@Transactional
public class Tb_departDao extends JpaDaoSupport implements ITb_departDao
{
	@Override
	public void delete(Tb_depart model)
	{
		getJpaTemplate().remove(model);
	}

	@Override
	public void deleteById(int id)
	{
		getJpaTemplate().remove(getJpaTemplate().getReference(Tb_depart.class, id));
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Tb_depart get(int id)
	{
		return getJpaTemplate().find(Tb_depart.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List getAll()
	{
		return getJpaTemplate().find("select depart from Tb_depart depart");
	}

	@Override
	public void save(Tb_depart model)
	{
		getJpaTemplate().persist(model);
	}

	@Override
	public void update(Tb_depart model)
	{
		getJpaTemplate().merge(model);
	}
}
