package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;


@Entity("guestbook")
public class GuestBook implements Serializable
{
	private static final long serialVersionUID = -4292951929182132458L;

	private int id;
	private String content;
	private String ip;
	private String nickname;
	private Date time;
	private String reply;
	private String replyTime;
	private String replyUser;
	private String title;
	private String status;
	private String reject;
	private String auditUser;
	private String auditTime;
	private int cid;

	public String getContent()
	{
		return content;
	}

	public void setContent(final String content)
	{
		this.content = content;
	}

	public int getId()
	{
		return id;
	}

	public void setId(final int id)
	{
		this.id = id;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(final String ip)
	{
		this.ip = ip;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(final String nickname)
	{
		this.nickname = nickname;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public String getReply()
	{
		return reply;
	}

	public void setReply(final String reply)
	{
		this.reply = reply;
	}

	public String getReplyUser()
	{
		return replyUser;
	}

	public void setReplyUser(final String replyUser)
	{
		this.replyUser = replyUser;
	}

	public String getReplyTime()
	{
		return replyTime;
	}

	public void setReplyTime(final String replyTime)
	{
		this.replyTime = replyTime;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(final String status)
	{
		this.status = status;
	}

	public String getReject()
	{
		return reject;
	}

	public void setReject(final String reject)
	{
		this.reject = reject;
	}

	public String getAuditUser()
	{
		return auditUser;
	}

	public void setAuditUser(final String auditUser)
	{
		this.auditUser = auditUser;
	}

	public String getAuditTime()
	{
		return auditTime;
	}

	public void setAuditTime(final String auditTime)
	{
		this.auditTime = auditTime;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}
}
