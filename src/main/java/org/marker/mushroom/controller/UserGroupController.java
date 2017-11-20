package org.marker.mushroom.controller;

import java.util.ArrayList;
import java.util.List;

import org.marker.mushroom.beans.Permission;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.UserGroup;
import org.marker.mushroom.beans.UserObject;
import org.marker.mushroom.dao.IPermissionDao;
import org.marker.mushroom.dao.IUserDao;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



/**
 * 用户组管理
 * 
 * @author marker
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/group")
public class UserGroupController extends SupportController
{

	// 权限
	@Autowired
	private IPermissionDao permissionDao;

	@Autowired
	private IUserDao userDao;


	public UserGroupController()
	{
		this.viewPath = "/admin/group/";
	}



	/** 添加用户 */
	@RequestMapping("/add")
	public ModelAndView add()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		return view;
	}



	/** 保存组 */
	@ResponseBody
	@RequestMapping("/save")
	public Object save(final UserGroup group)
	{
		if (commonDao.save(group))
		{
			return new ResultMessage(true, "添加成功!");
		}
		else
		{
			return new ResultMessage(false, "保存失败!");
		}
	}

	/** 删除组 */
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("id") final Integer id)
	{
		if (id == 1)
		{// 如果找到管理员组
			return new ResultMessage(false, "管理员组为内置，不能删除!");
		}

		// 检查用户是否有使用此组，如果有，不能删除。
		final int count = userDao.countUserByGroupId(id);
		if (count > 0)
		{
			return new ResultMessage(false, "用户组有用户存在，不能删除!");
		}

		dao.update("delete from mr_user_group_menu where gid =" + id);


		final boolean status = commonDao.deleteByIds(UserGroup.class, id + "");
		if (status)
		{
			return new ResultMessage(true, "删除成功!");
		}
		else
		{
			return new ResultMessage(false, "删除失败!");
		}
	}



	/** 保存用户 */
	@ResponseBody
	@RequestMapping("/update")
	public Object update(final UserGroup group, @RequestParam("id") final int id)
	{
		group.setId(id);// 不能注入 
		if (commonDao.update(group))
		{
			return new ResultMessage(true, "更新成功!");
		}
		else
		{
			return new ResultMessage(false, "更新失败!");
		}
	}


	/** 编辑组 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") final long id)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("group", commonDao.findById(UserGroup.class, id));
		return view;
	}

	/**
	 * 用户组列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		final StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(getPrefix()).append("user_group");
		view.addObject("data", commonDao.queryForList(sql.toString()));
		return view;
	}


	/**
	 * 赋权
	 */
	@ResponseBody
	@RequestMapping(value = "/granting", method = RequestMethod.POST)
	public Object granting(@RequestParam(value = "mid", defaultValue = "") final String mid,
			@RequestParam(value = "cat", defaultValue = "") final String cat,
			@RequestParam(value = "btn", defaultValue = "") final String btn, @RequestParam(value = "gid") final int gid)
	{

		// 在更新前，先清空以前权限，再更新最新权限。
		dao.update("delete from " + getPrefix() + "user_group_menu where gid=" + gid);

		String sql = "insert into " + dbConfig.getPrefix() + "user_group_menu(gid,mid) values(?,?)";

		if (!StringUtils.isEmpty(mid))
		{
			batchUpdate(sql, gid, mid);
		}

		dao.update("delete from " + getPrefix() + "user_group_object where gid=" + gid);

		sql = "insert into " + dbConfig.getPrefix() + "user_group_object(gid,oid,type) value(?,?,'button')";

		if (!StringUtils.isEmpty(btn))
		{
			batchUpdate(sql, gid, btn);
		}

		sql = "insert into " + dbConfig.getPrefix() + "user_group_object(gid,oid,type) value(?,?,'category')";
		if (!StringUtils.isEmpty(cat))
		{
			batchUpdate(sql, gid, cat);
		}

		return new ResultMessage(true, "更新权限成功!");
	}

	/**
	 * 赋权操作
	 */
	@RequestMapping("/grant")
	public ModelAndView grant(@RequestParam("id") final int groupId)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "grant");
		final StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(getPrefix()).append("user_menu order by sort");
		view.addObject("menus", commonDao.queryForList(sql.toString()));
		view.addObject("gid", groupId);

		sql.delete(0, sql.length()); //清空sql
		sql.append("select * from ").append(getPrefix()).append("category ");
		view.addObject("category", commonDao.queryForList(sql.toString()));

		sql.delete(0, sql.length());//清空sql
		sql.append("select * from ").append(getPrefix()).append("user_button ");
		view.addObject("button", commonDao.queryForList(sql.toString()));
		// 这里要把原来的权限查询出来
		final List<Permission> permissions = permissionDao.findPermissionByGroupId(groupId);
		view.addObject("permissions", permissions);


		final List<UserObject> objectButton = permissionDao.findUserObjectByGroupId(groupId, "button");
		view.addObject("objectButton", objectButton);

		final List<UserObject> objectCategory = permissionDao.findUserObjectByGroupId(groupId, "category");
		view.addObject("objectCategory", objectCategory);

		return view;
	}

	private void batchUpdate(final String sql, final int gid, final String objstr)
	{
		final List<Object[]> objectList = new ArrayList<>();

		final String[] objs = objstr.split(",");
		for (final String objid : objs)
		{

			objectList.add(new Object[]
			{ gid, objid });

		}
		if (!objectList.isEmpty())
		{
			dao.batchUpdate(sql, objectList);
		}
	}

}
