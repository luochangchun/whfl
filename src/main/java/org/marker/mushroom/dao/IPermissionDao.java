package org.marker.mushroom.dao;

import java.util.List;

import org.marker.mushroom.beans.Permission;
import org.marker.mushroom.beans.UserObject;


/**
 * 权限管理接口
 * 
 * @author Administrator
 *
 */
public interface IPermissionDao extends ISupportDao
{

	List<Permission> findPermissionByGroupId(int groupId);

	List<UserObject> findUserObjectByGroupId(int groupId, String type);

}
