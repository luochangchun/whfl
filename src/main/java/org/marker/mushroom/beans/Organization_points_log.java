package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 碎片管理
 * 
 * @author marker
 */
@Entity("organization_points_log")
public class Organization_points_log implements Serializable
{

	private static final long serialVersionUID = -3738360997172770758L;

	private Integer id;
	/**
	 * 文章id
	 */
	private Integer aid;
	/**
	 * 用户ip
	 */
	private String ip;
	/**
	 * 创建时间
	 */
	private Date time;
	/**
	 * 积分
	 */
	private Integer points;


	public Integer getId()
	{
		return id;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public Integer getAid()
	{
		return aid;
	}

	public void setAid(final Integer aid)
	{
		this.aid = aid;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(final String ip)
	{
		this.ip = ip;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public Integer getPoints()
	{
		return points;
	}

	public void setPoints(final Integer points)
	{
		this.points = points;
	}
}
