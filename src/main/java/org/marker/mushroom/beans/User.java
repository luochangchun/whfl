package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 用户对象
 * 
 * @author marker
 */
@Entity("user")
public class User implements Serializable
{

	private static final long serialVersionUID = -3931877820780528915L;

	/** 自动生成ID */
	private int id;
	private String uuid;
	/** 分组ID */
	private Integer gid;
	private String nickname;
	private String name;
	private String pass;
	private Date createtime;
	private Date logintime;
	private int status;
	private String description;

	private List<Role> roles;

	public int getId()
	{
		return id;
	}

	public void setId(final int id)
	{
		this.id = id;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(final String uuid)
	{
		this.uuid = uuid;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(final String nickname)
	{
		this.nickname = nickname;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getPass()
	{
		return pass;
	}

	public void setPass(final String pass)
	{
		this.pass = pass;
	}

	public Date getCreatetime()
	{
		return createtime;
	}

	public void setCreatetime(final Date createtime)
	{
		this.createtime = createtime;
	}

	public Date getLogintime()
	{
		return logintime;
	}

	public void setLogintime(final Date logintime)
	{
		this.logintime = logintime;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(final int status)
	{
		this.status = status;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	/**
	 * @return the roles
	 */
	public List<Role> getRoles()
	{
		return roles;
	}

	/**
	 * @param roles
	 *           the roles to set
	 */
	public void setRoles(final List<Role> roles)
	{
		this.roles = roles;
	}

	public Integer getGid()
	{
		return gid;
	}

	public void setGid(final Integer gid)
	{
		this.gid = gid;
	}










}
