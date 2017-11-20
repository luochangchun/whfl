package org.marker.mushroom.beans;

import java.io.Serializable;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 活动对象
 * 
 * @author dd
 */
@Entity("user_Organization")
public class User_Organization implements Serializable
{
	private static final long serialVersionUID = 1919636756936490415L;
	/** 用户id **/
	private Integer id;
	/** 用户id **/
	private String uuid;
	/** 组织id **/
	private String orguid;
	/**
	 * 联系人
	 **/
	private String author;
	/**
	 * 联系方式
	 */
	private String phone;
	/** 状态 **/
	private Integer status;

	public Integer getId()
	{
		return id;
	}

	public void setId(final Integer id)
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

	public String getOrguid()
	{
		return orguid;
	}

	public void setOrguid(final String orguid)
	{
		this.orguid = orguid;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(final String author)
	{
		this.author = author;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(final Integer status)
	{
		this.status = status;
	}
}
