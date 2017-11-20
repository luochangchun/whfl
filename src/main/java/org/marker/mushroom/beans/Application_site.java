package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


@Entity("application_site")
public class Application_site implements Serializable
{

	private static final long serialVersionUID = 997098299954544499L;

	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 活动室
	 */
	private Integer site;
	/**
	 * 预约日期
	 */
	private Date makeDate;
	/**
	 * 天数区间
	 */
	private String moment;
	/**
	 * 申请单位
	 */
	private String company;
	/**
	 * 申请人
	 */
	private String name;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 活动主题
	 */
	private String theme;
	/**
	 * 活动内容
	 */
	private String content;
	/**
	 * 补充需求
	 */
	private String demand;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 日期
	 */
	private Date time;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 审核人
	 */
	private Integer audit;
	/**
	 * 审核日期
	 */
	private Date auditDate;
	/**
	 * 用户
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

	public Integer getSite()
	{
		return site;
	}

	public void setSite(final Integer site)
	{
		this.site = site;
	}

	public Date getMakeDate()
	{
		return makeDate;
	}

	public void setMakeDate(final Date makeDate)
	{
		this.makeDate = makeDate;
	}

	public String getMoment()
	{
		return moment;
	}

	public void setMoment(final String moment)
	{
		this.moment = moment;
	}

	public String getCompany()
	{
		return company;
	}

	public void setCompany(final String company)
	{
		this.company = company;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public String getTheme()
	{
		return theme;
	}

	public void setTheme(final String theme)
	{
		this.theme = theme;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(final String content)
	{
		this.content = content;
	}

	public String getDemand()
	{
		return demand;
	}

	public void setDemand(final String demand)
	{
		this.demand = demand;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(final Integer status)
	{
		this.status = status;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(final Date createTime)
	{
		this.createTime = createTime;
	}

	public Integer getAudit()
	{
		return audit;
	}

	public void setAudit(final Integer audit)
	{
		this.audit = audit;
	}

	public Date getAuditDate()
	{
		return auditDate;
	}

	public void setAuditDate(final Date auditDate)
	{
		this.auditDate = auditDate;
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

