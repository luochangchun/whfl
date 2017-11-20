package org.marker.mushroom.beans;

import java.io.Serializable;

import org.marker.mushroom.dao.annotation.Entity;


@Entity("user_group")
public class UserGroup implements Serializable
{

	private static final long serialVersionUID = -6798123508979192619L;

	/** ID */
	private Integer id;
	/** 分组名称 */
	private String name;
	/* 作用域 */
	private int scope;
	private String description;


	public Integer getId()
	{
		return id;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public int getScope()
	{
		return scope;
	}

	public void setScope(final int scope)
	{
		this.scope = scope;
	}


}
