package dao.hibernate;

import model.Data;

import org.springframework.stereotype.Component;

@Component(value = "hibernateDataDao")
//@Component
public class DataDao extends BaseDao<Integer, Data>
{
}
