package org.marker.mushroom.beans;

import java.io.Serializable;

import org.marker.mushroom.dao.annotation.Entity;


@Entity("user_object")
public class UserObject implements Serializable
{

	private static final long serialVersionUID = -6798123508979192619L;

	/** ID */
	private int gid;
	/** type类型对应的id **/
	private int oid;
	/* 类型 */
	private String type;


	public int getGid()
	{
		return gid;
	}

	public void setGid(final int gid)
	{
		this.gid = gid;
	}

	public int getOid()
	{
		return oid;
	}

	public void setOid(final int oid)
	{
		this.oid = oid;
	}

	public String getType()
	{
		return type;
	}

	public void setType(final String type)
	{
		this.type = type;
	}
}
