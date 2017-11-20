package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 碎片管理
 * 
 * @author marker
 */
@Entity("offline_course")
public class Offline_course implements Serializable
{
	private static final long serialVersionUID = 2634458608596868816L;

	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 线下课程名称
	 */
	private String name;
	/**
	 * 用户uuid
	 */
	private String uuid;
	/**
	 * 组织uuid
	 */
	private String orgUid;
	/**
	 * 积分
	 */
	private Integer integral;
	/**
	 * 积分录入时间
	 */
	private Date integralTime;
	/**
	 * 创建时间
	 */
	private Date time;
	/**
	 * 操作用户id
	 */
	private Integer userId;

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

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(final String uuid)
	{
		this.uuid = uuid;
	}

	public String getOrgUid()
	{
		return orgUid;
	}

	public void setOrgUid(final String orgUid)
	{
		this.orgUid = orgUid;
	}

	public Integer getIntegral()
	{
		return integral;
	}

	public void setIntegral(final Integer integral)
	{
		this.integral = integral;
	}

	public Date getIntegralTime()
	{
		return integralTime;
	}

	public void setIntegralTime(final Date integralTime)
	{
		this.integralTime = integralTime;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(final Integer userId)
	{
		this.userId = userId;
	}
}
