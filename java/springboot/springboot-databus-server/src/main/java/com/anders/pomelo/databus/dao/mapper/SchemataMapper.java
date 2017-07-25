package com.anders.pomelo.databus.dao.mapper;

import java.util.List;

import com.anders.pomelo.databus.dao.bo.Schemata;

public interface SchemataMapper {
  
    List<Schemata> selectAll();
}