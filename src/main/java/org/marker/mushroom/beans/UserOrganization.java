package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户对象
 * 
 * @author marker
 */
@Entity("userOrganization")
public class UserOrganization implements Serializable
{

	private static final long serialVersionUID = -3931877820780528915L;

	/**
	 * 组织名称
	 */
	private String name;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 拟入驻类型
	 */
	private Integer settled;
	/**
	 * 联系人1
	 */
	private String author1;
	/**
	 * 联系方式1
	 */
	private String phone1;
	/**
	 * QQ1
	 */
	private String qq1;
	/**
	 * 联系人2
	 */
	private String author2;
	/**
	 * 联系方式2
	 */
	private String phone2;
	/**
	 * QQ2
	 */
	private String qq2;
	/**
	 * 组织类型
	 */
	private Integer type;
	/**
	 * 组织注册地
	 */
	private String regPlace;
	/**
	 * 成立时间
	 */
	private Date foundingTime;
	/**
	 * 注册号
	 */
	private String registerNum;
	/**
	 * 发证机关
	 */
	private String issAuthority;
	/**
	 * address
	 */
	private String address;
	/**
	 * 服务领域
	 */
	private String kingdom;
	/**
	 * 入驻需求
	 */
	private String demand;
	/**
	 * 拟定合作发展计划
	 */
	private String plan;
	/**
	 * 组织简介
	 */
	private String description;

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(final String account)
	{
		this.account = account;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public Integer getSettled()
	{
		return settled;
	}

	public void setSettled(final Integer settled)
	{
		this.settled = settled;
	}

	public String getAuthor1()
	{
		return author1;
	}

	public void setAuthor1(final String author1)
	{
		this.author1 = author1;
	}

	public String getPhone1()
	{
		return phone1;
	}

	public void setPhone1(final String phone1)
	{
		this.phone1 = phone1;
	}

	public String getQq1() {
		return qq1;
	}

	public void setQq1(String qq1) {
		this.qq1 = qq1;
	}

	public String getAuthor2()
	{
		return author2;
	}

	public void setAuthor2(final String author2)
	{
		this.author2 = author2;
	}

	public String getPhone2()
	{
		return phone2;
	}

	public void setPhone2(final String phone2)
	{
		this.phone2 = phone2;
	}

	public String getQq2() {
		return qq2;
	}

	public void setQq2(String qq2) {
		this.qq2 = qq2;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(final Integer type)
	{
		this.type = type;
	}

	public String getRegPlace()
	{
		return regPlace;
	}

	public void setRegPlace(final String regPlace)
	{
		this.regPlace = regPlace;
	}

	public Date getFoundingTime()
	{
		return foundingTime;
	}

	public void setFoundingTime(final Date foundingTime)
	{
		this.foundingTime = foundingTime;
	}

	public String getRegisterNum()
	{
		return registerNum;
	}

	public void setRegisterNum(final String registerNum)
	{
		this.registerNum = registerNum;
	}

	public String getIssAuthority()
	{
		return issAuthority;
	}

	public void setIssAuthority(final String issAuthority)
	{
		this.issAuthority = issAuthority;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(final String address)
	{
		this.address = address;
	}

	public String getKingdom()
	{
		return kingdom;
	}

	public void setKingdom(final String kingdom)
	{
		this.kingdom = kingdom;
	}

	public String getDemand()
	{
		return demand;
	}

	public void setDemand(final String demand)
	{
		this.demand = demand;
	}

	public String getPlan()
	{
		return plan;
	}

	public void setPlan(final String plan)
	{
		this.plan = plan;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
}
