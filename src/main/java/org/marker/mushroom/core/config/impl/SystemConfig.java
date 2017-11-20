package org.marker.mushroom.core.config.impl;

import java.io.IOException;

import org.marker.mushroom.core.config.ConfigEngine;


/**
 * 系统配置类（对Properties进行了简单封装） 用于配置系统配置文件，提供读取和保存两种持久化操作 在系统StartListener监听器中进行配置文件地址的初始化
 * 
 * @author marker
 */
public final class SystemConfig extends ConfigEngine
{


	// 配置文件路径
	public static final String CONFIG_FILE_PATH = "/config/site/system.config";


	// 开发模式
	public static final String DEV_MODE = "dev_mode";

	// 主题路径
	public static final String THEME_PATH = "themes_path";

	// 是否开启动态HTML的GZIP
	public static final String GZIP = "gzip";

	// 是否启用站内统计
	public static final String STATISTICS = "statistics";

	// 是否启用代码压缩
	public static final String COMPRESS = "compress";

	// 默认语言
	public static final String DEFAULTLANG = "defaultlang";

	// 页面静态化
	public static final String STATIC_PAGE = "statichtml";

	// 主页地址
	public static final String HOME_PAGE = "index_page";

	//企业信息图片上传相对路径
	public static final String RELATIVE_PATH = "relative_path";

	public static final String MUSEUM_PATH = "museum_path";

	/**
	 * 默认构造方法
	 * 
	 * @throws IOException
	 */
	private SystemConfig()
	{
		super(CONFIG_FILE_PATH);
	}


	/**
	 * 这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证。
	 */
	private static SystemConfig instance;


	/**
	 * 获取系统配置实例
	 */
	public static SystemConfig getInstance()
	{
		if (null == instance)
			instance = new SystemConfig();
		return instance;
	}


	/**
	 * 配置文件中属性名称配置
	 */
	public interface Names
	{
		/** 关键字 */
		String KEYWORDS = "keywords";
		/** 描述信息 */
		String DESCRIPTION = "description";
	}


	/**
	 * 开发模式
	 * 
	 * @return
	 */
	public boolean isdevMode()
	{
		final String value = this.properties.get(DEV_MODE).toString();
		return Boolean.valueOf(value);
	}

	/**
	 * 是否启用统计
	 * 
	 * @return
	 */
	public boolean isStatistics()
	{
		final String value = this.properties.get(STATISTICS).toString();
		return Boolean.valueOf(value);
	}


	/**
	 * 是否启用代码压缩
	 * 
	 * @return
	 */
	public boolean isCompress()
	{
		final String value = this.properties.get(COMPRESS).toString();
		return Boolean.valueOf(value);
	}


	/**
	 * 获取默认语言
	 * 
	 * @return
	 */
	public String getDefaultLanguage()
	{
		return this.properties.getProperty(DEFAULTLANG, "zh-CN");
	}


	/**
	 * 是否启用页面静态化
	 * 
	 * @return
	 */
	public boolean isStaticPage()
	{
		final String value = this.properties.getProperty(STATIC_PAGE);
		return Boolean.valueOf(value);
	}


	/**
	 * 获取主页地址
	 * 
	 * @return
	 */
	public String getHomePage()
	{
		return this.properties.getProperty(HOME_PAGE);
	}


	/**
	 * 是否启用Gzip
	 * 
	 * @return
	 */
	public boolean isGzip()
	{
		final String value = this.properties.getProperty(GZIP);
		return Boolean.valueOf(value);

	}

	/**
	 * 获取企业信息图片上传相对路径
	 * 
	 * @return
	 */
	public String getRelativePath()
	{
		return this.properties.getProperty(RELATIVE_PATH);
	}

	/**
	 * 获取家风博物馆图片上传相对路径
	 */
	public String getMuseumPath()
	{
		return this.properties.getProperty(MUSEUM_PATH);
	}

}
