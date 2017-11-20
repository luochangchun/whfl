package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * Created by dongbin on 2017/5/8.
 */
@Entity("donation")
public class Donation implements Serializable
{
	private int id;
	private String name;
	private String phone;
	private String address;
	private String description;
	private String imgSrc;
	private int method;
	private Date time;

	public int getId()
	{
		return id;
	}

	public void setId(final int id)
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

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(final String address)
	{
		this.address = address;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}


	public int getMethod()
	{
		return method;
	}

	public void setMethod(final int method)
	{
		this.method = method;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public String getImgSrc()
	{
		return imgSrc;
	}

	public void setImgSrc(final String imgSrc)
	{
		this.imgSrc = imgSrc;
	}
}
