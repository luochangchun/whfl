package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;


/**
 * 栏目实体
 * 
 * @author marker
 */
@Entity("channel")
public class Channel implements Serializable
{
	private static final long serialVersionUID = -7383542815506431998L;

	/** 自动生成ID */
	private int id = 0;
	/** 上级栏目ID */
	private long pid = 0;
	/** 图标 */
	private String icon;
	/** 栏目名称 */
	private String name;
	/** 语言键 */
	private String langkey;
	/** 栏目关键字 */
	private String keywords;
	/** 栏目描述 */
	private String description;
	/** URL地址 */
	private String url;
	/** 模板 */
	private String template;
	/** 排序 */
	private int sort;
	/** 条数 */
	private int rows;
	/** 是否隐藏 */
	private short hide;
	/** 重定向URL */
	private String redirect;
	/** 分类编号 **/
	private String cid;



	public int getId()
	{
		return id;
	}

	public void setId(final int id)
	{
		this.id = id;
	}

	public long getPid()
	{
		return pid;
	}

	public void setPid(final long pid)
	{
		this.pid = pid;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(final String icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getKeywords()
	{
		return keywords;
	}

	public void setKeywords(final String keywords)
	{
		this.keywords = keywords;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(final String url)
	{
		this.url = url;
	}

	public String getTemplate()
	{
		return template;
	}

	public void setTemplate(final String template)
	{
		this.template = template;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(final int sort)
	{
		this.sort = sort;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(final int rows)
	{
		this.rows = rows;
	}

	public short getHide()
	{
		return hide;
	}

	public void setHide(final short hide)
	{
		this.hide = hide;
	}

	public String getRedirect()
	{
		return redirect;
	}

	public void setRedirect(final String redirect)
	{
		this.redirect = redirect;
	}

	public String getLangkey()
	{
		return langkey;
	}

	public void setLangkey(final String langkey)
	{
		this.langkey = langkey;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(final String cid)
	{
		this.cid = cid;
	}
}
