package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 活动对象
 * 
 * @author dd
 */
@Entity("activity")
public class Activity implements Serializable
{
	private static final long serialVersionUID = -8246056434724900100L;
	/** 活动人员ID **/
	private Integer id;
	/** 活动人员姓名 **/
	private String name;
	/** 活动人员身份证号 **/
	private String idCard;
	/** 活动人员联系方式 **/
	private String phone;
	/** 文章id **/
	private Integer articleId;
	/** 活动人员申请时间 **/
	private Date applyTime;
	/** 活动人员修改时间 **/
	private Date time;
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

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public Integer getArticleId()
	{
		return articleId;
	}

	public void setArticleId(final Integer articleId)
	{
		this.articleId = articleId;
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
}
