package com.anders.ssh.dao.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.anders.ssh.bo.xml.Area;
import com.anders.ssh.dao.Dao;

@Component("mybatisAreaDao")
public class AreaDao implements Dao<Long, Area> {

	@Resource
	private AreaMapper areaMapper;

	@Override
	public void delete(Area area) {
		Assert.notNull(area);
	}

	@Override
	public void deleteById(Long id) {
		Assert.notNull(id);
		areaMapper.deleteById(id);
	}

	@Override
	public List<Area> getAll() {
		return null;
	}

	@Override
	public Area getById(Long id) {
		Assert.notNull(id);
		return areaMapper.getById(id);
	}

	@Override
	public void save(Area area) {
		Assert.notNull(area);
		areaMapper.save(area);
	}

	@Override
	public void saveOrUpdate(Area area) {
		Assert.notNull(area);
	}

	@Override
	public void update(Area area) {
		Assert.notNull(area);
		areaMapper.update(area);
	}

}
