package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;





/**
 * 文章对象
 * 
 * @author marker
 */
@Entity("article")
public class Article implements Serializable
{
	private static final long serialVersionUID = -2456959238880328330L;

	/** 文章ID */
	private Integer id;
	/** 所属栏目ID */
	private Integer cid;
	/** 图标 */
	private String icon;
	/** 标题 */
	private String title;
	/** 关键字 */
	private String keywords;
	/** 描述 */
	private String description;
	/** 作者 */
	private String author;
	/** 浏览量 */
	private int views;
	/** 内容 */
	private String content;
	/** 发布时间 */
	private Date time;

	/* 发布状态：0草稿 1发布 */
	private int status;

	/** 引用地址 */
	private String source;

	/** 内容类型 */
	private int type;

	/** 原始内容 */
	private String orginal;

	/** 创建时间 **/
	private Date createTime;

	/** 驳回理由 **/
	private String reject;

	/** 文章置顶标识 **/
	private Integer top;

	private String userId;

	private Integer auditId;

	private Date auditTime;

	public String getOrginal()
	{
		return orginal;
	}

	public void setOrginal(final String orginal)
	{
		this.orginal = orginal;
	}

	public int getType()
	{
		return type;
	}

	public void setType(final int type)
	{
		this.type = type;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public Integer getCid()
	{
		return cid;
	}

	public void setCid(final Integer cid)
	{
		this.cid = cid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(final String author)
	{
		this.author = author;
	}

	public long getViews()
	{
		return views;
	}

	public void setViews(final int views)
	{
		this.views = views;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(final String content)
	{
		this.content = content;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public String getKeywords()
	{
		return keywords;
	}

	public void setKeywords(final String keywords)
	{
		this.keywords = keywords;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(final int status)
	{
		this.status = status;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(final String source)
	{
		this.source = source;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(final String icon)
	{
		this.icon = icon;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(final Date createTime)
	{
		this.createTime = createTime;
	}

	public String getReject()
	{
		return reject;
	}

	public void setReject(final String reject)
	{
		this.reject = reject;
	}

	public Integer getTop()
	{
		return top;
	}

	public void setTop(final Integer top)
	{
		this.top = top;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(final String userId)
	{
		this.userId = userId;
	}

	public Integer getAuditId()
	{
		return auditId;
	}

	public void setAuditId(final Integer auditId)
	{
		this.auditId = auditId;
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
