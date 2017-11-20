package org.marker.mushroom.dao.impl;

import java.util.List;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Permission;
import org.marker.mushroom.beans.UserObject;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IPermissionDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperPermission;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperUserObject;
import org.springframework.stereotype.Repository;


/**
 * 
 * @author marker
 *
 */
@Repository(DAO.PERMISSION)
public class PermissionDaoImpl extends DaoEngine implements IPermissionDao
{




	// 如果分组ID查询组内权限
	public List<Permission> findPermissionByGroupId(final int groupId)
	{
		final StringBuilder sql = new StringBuilder();
		sql.append("select gid,mid from ").append(getPreFix()).append("user_group_menu where gid=?");
		return this.jdbcTemplate.query(sql.toString(), new Object[]
		{ groupId }, new RowMapperPermission());

	}

	@Override
	public List<UserObject> findUserObjectByGroupId(final int groupId, final String type)
	{
		final StringBuilder sql = new StringBuilder();
		sql.append("select gid,oid,type from ").append(getPreFix()).append("user_group_object where gid=? and type= ? ");
		return this.jdbcTemplate.query(sql.toString(), new Object[]
		{ groupId, type }, new RowMapperUserObject());
	}


}
