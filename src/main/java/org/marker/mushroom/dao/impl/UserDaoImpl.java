package org.marker.mushroom.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.marker.mushroom.beans.User;
import org.marker.mushroom.beans.UserGroup;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IUserDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperUser;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperUserGroup;
import org.springframework.stereotype.Repository;


@Repository("userDao")
public class UserDaoImpl extends DaoEngine implements IUserDao
{


	/**
	 * 通过用户名和密码查询用户对象
	 */
	@Override
	public User queryByNameAndPass(final String name, final String pass)
	{
		final String prefix = dbConfig.getPrefix();
		final StringBuilder sql = new StringBuilder("select * from ");
		sql.append(prefix).append("user").append(" where name=? and pass=?");
		User user = null;
		try
		{
			user = this.jdbcTemplate.queryForObject(sql.toString(), new Object[]
			{ name, pass }, new RowMapperUser());
		}
		catch (final Exception e)
		{
		}

		return user;
	}


	@Override
	public boolean updateLoginTime(final Serializable id)
	{
		final String prefix = dbConfig.getPrefix();
		final StringBuilder sql = new StringBuilder("update ");
		sql.append(prefix).append("user ").append("set logintime=sysdate() where id=?");

		final int status = jdbcTemplate.update(sql.toString(), id);
		return status > 0 ? true : false;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.marker.mushroom.dao.IUserDao#findUserByName(java.lang.String)
	 */
	@Override
	public User findUserByName(final String userName)
	{
		final String prefix = dbConfig.getPrefix();
		final StringBuilder sql = new StringBuilder("select * from ");
		sql.append(prefix).append("user").append(" where name=?");
		User user = null;
		try
		{
			user = this.jdbcTemplate.queryForObject(sql.toString(), new Object[]
			{ userName }, new RowMapperUser());
		}
		catch (final Exception e)
		{
			logger.error("通过name查询用户失败!", e);
		}

		return user;
	}


	@Override
	public List<UserGroup> findGroup()
	{
		final StringBuilder sql = new StringBuilder("select * from ");
		sql.append(getPreFix()).append("user_group");
		return this.jdbcTemplate.query(sql.toString(), new RowMapperUserGroup());
	}


	// 统计用户组的用户数量
	@Override
	public int countUserByGroupId(final int groupId)
	{
		final StringBuilder sql = new StringBuilder("select count(*) from ");
		sql.append(getPreFix()).append("user u where u.gid=? ");

		return this.queryForObject(sql.toString(), Integer.class, new Object[]
		{ groupId });
	}

}
