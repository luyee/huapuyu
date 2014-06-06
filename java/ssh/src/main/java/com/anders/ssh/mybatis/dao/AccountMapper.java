package com.anders.ssh.mybatis.dao;

import com.anders.ssh.mybatis.bo.Account;
import com.anders.ssh.mybatis.bo.AccountCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AccountMapper {
    @SelectProvider(type=AccountSqlProvider.class, method="countByCriteria")
    int countByCriteria(AccountCriteria example);

    @DeleteProvider(type=AccountSqlProvider.class, method="deleteByCriteria")
    int deleteByCriteria(AccountCriteria example);

    @Delete({
        "delete from tb_account",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into tb_account (id, enable, name)",
        "values (#{id,jdbcType=BIGINT}, #{enable,jdbcType=BIT}, #{name,jdbcType=VARCHAR})"
    })
    int insert(Account record);

    @InsertProvider(type=AccountSqlProvider.class, method="insertSelective")
    int insertSelective(Account record);

    @SelectProvider(type=AccountSqlProvider.class, method="selectByCriteria")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="enable", property="enable", jdbcType=JdbcType.BIT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    List<Account> selectByCriteria(AccountCriteria example);

    @Select({
        "select",
        "id, enable, name",
        "from tb_account",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="enable", property="enable", jdbcType=JdbcType.BIT),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    Account selectByPrimaryKey(Long id);

    @UpdateProvider(type=AccountSqlProvider.class, method="updateByCriteriaSelective")
    int updateByCriteriaSelective(@Param("record") Account record, @Param("example") AccountCriteria example);

    @UpdateProvider(type=AccountSqlProvider.class, method="updateByCriteria")
    int updateByCriteria(@Param("record") Account record, @Param("example") AccountCriteria example);

    @UpdateProvider(type=AccountSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Account record);

    @Update({
        "update tb_account",
        "set enable = #{enable,jdbcType=BIT},",
          "name = #{name,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Account record);
}