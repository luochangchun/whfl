package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


@Entity("mechanism")
public class Mechanism implements Serializable
{


	private static final long serialVersionUID = 3490806663717585209L;
	/**
	 * ID
	 **/
	private Integer id;
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 成立时间
	 */
	private Date foundingTime;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 团队规模
	 */
	private String scale;
	/**
	 * 机构类别
	 */
	private Integer category;
	/**
	 * 入驻需求
	 */
	private String demand;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date time;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 审核人
	 */
	private Integer audit;
	/**
	 * 审核时间
	 */
	private Date auditTime;

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

	public Date getFoundingTime()
	{
		return foundingTime;
	}

	public void setFoundingTime(final Date foundingTime)
	{
		this.foundingTime = foundingTime;
	}

	public String getContacts()
	{
		return contacts;
	}

	public void setContacts(final String contacts)
	{
		this.contacts = contacts;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public String getScale()
	{
		return scale;
	}

	public void setScale(final String scale)
	{
		this.scale = scale;
	}

	public Integer getCategory()
	{
		return category;
	}

	public void setCategory(final Integer category)
	{
		this.category = category;
	}

	public String getDemand()
	{
		return demand;
	}

	public void setDemand(final String demand)
	{
		this.demand = demand;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(final Date createTime)
	{
		this.createTime = createTime;
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

	public Date getAuditTime()
	{
		return auditTime;
	}

	public void setAuditTime(final Date auditTime)
	{
		this.auditTime = auditTime;
	}
}
