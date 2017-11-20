package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 文章对象
 * 
 * @author marker
 */
@Entity("volunteer_team")
public class Volunteer_Team implements Serializable
{
	private static final long serialVersionUID = 898878983682273933L;
	/** 志愿者团队ID **/
	private Integer id;
	/** 志愿者团队姓名 **/
	private String name;
	/** 志愿者团队代表人身份证 **/
	private String idCard;
	/** 志愿者团队人数 **/
	private String number;
	/** 志愿者团队爱好 **/
	private String like;
	/** 志愿者团队联系方式 **/
	private String phone;
	/**
	 * 区域
	 */
	private Integer region;
	/** 志愿者团队慝服务方向 **/
	private String description;
	/** 志愿者团队申请时间 **/
	private Date applyTime;
	/** 志愿者团队修改时间 **/
	private Date time;
	/** 团队状态 **/
	private Integer status;
	/** 团队审核人 **/
	private Integer audit;

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

	public String getIdCard()
	{
		return idCard;
	}

	public void setIdCard(final String idCard)
	{
		this.idCard = idCard;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(final String number)
	{
		this.number = number;
	}

	public String getLike()
	{
		return like;
	}

	public void setLike(final String like)
	{
		this.like = like;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public Integer getRegion()
	{
		return region;
	}

	public void setRegion(final Integer region)
	{
		this.region = region;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public Date getApplyTime()
	{
		return applyTime;
	}

	public void setApplyTime(final Date applyTime)
	{
		this.applyTime = applyTime;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(final Integer status)
	{
		this.status = status;
	}

	public Integer getAudit()
	{
		return audit;
	}

	public void setAudit(final Integer audit)
	{
		this.audit = audit;
	}
}
