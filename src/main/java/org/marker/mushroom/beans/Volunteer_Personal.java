package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 文章对象
 * 
 * @author marker
 */
@Entity("volunteer_personal")
public class Volunteer_Personal implements Serializable
{
	private static final long serialVersionUID = 6847642003152596212L;

	/** 志愿者ID **/
	private Integer id;
	/** 志愿者姓名 **/
	private String name;
	/** 志愿者身份证号 **/
	private String idCard;
	/** 志愿者文化程度 **/
	private String culture;
	/** 志愿者爱好 **/
	private String like;
	/** 志愿者联系方式 **/
	private String phone;
	/**
	 * 区域
	 */
	private Integer region;
	/** 志愿者慝服务方向 **/
	private String description;
	/** 志愿者申请时间 **/
	private Date applyTime;
	/** 志愿者修改时间 **/
	private Date time;
	/** 状态 **/
	private Integer status;
	/** 审核人 **/
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

	public String getCulture()
	{
		return culture;
	}

	public void setCulture(final String culture)
	{
		this.culture = culture;
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

	public Date getApplyTime()
	{
		return applyTime;
	}

	public void setApplyTime(final Date applyTime)
	{
		this.applyTime = applyTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
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
