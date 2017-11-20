package org.marker.mushroom.ext.tag.impl;

import org.marker.mushroom.alias.Core;
import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.exception.SystemException;
import org.marker.mushroom.ext.tag.MatchRule;
import org.marker.mushroom.ext.tag.Taglib;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.template.MyCMSTemplate;
import org.marker.mushroom.template.tags.res.ObjectDataSourceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 遍历集合标签 说明：采用解析结构生成SQL语句进行进一步的查询，最后使用MVC模式传递数据 格式： <code>
 *  <!-- {list:var=(channel) items=(channels) table=(channel) pid=(0) page=(1) limit=(10)} --> 
 *		<a href="${channel.url}">${channel.name}</a>
 *	<!-- {/list} -->
 * </code> 2013-01-18: 调整了输出HTML代码的格式，增加可读性 2013-01-20 根据item参数来存取SQLDataEngqin
 * 
 * @author marker
 * @date 2013-01-17
 */
public class ListTagImpl extends Taglib
{

	/** 默认构造 */
	public ListTagImpl()
	{
		final Map<String, Object> config = new HashMap<String, Object>();
		config.put("name", "遍历数据");
		config.put("author", "marker");
		config.put("doc", "doc/5.md");
		config.put("description", "系统内置");
		this.configure(config);


		this.put(
				"\\s*<!--\\s*\\{(\\w+):list\\s*table\\=\\((\\w+)\\)\\s*[\\w+\\=\\('?[a-zA-Z0-9\\s,\\s]+'?\\)\\s*]*\\}\\s*-->[\\x20]*\\n?",
				"<#list $2 as $1>\n", 1);
		this.put("\\s*<!--\\s*?\\{/list\\}\\s*?-->[\\x20]*\\n?", "</#list>\n", 0);
	}




	/**
	 * 处理拥有数据的标签
	 * 
	 * @throws SystemException
	 * @throws Exception
	 */
	public void doDataReplace(final MatchRule mr) throws SystemException
	{
		final MyCMSTemplate cmstemplate = SpringContextHolder.getBean(Core.ENGINE_TEMPLATE);
		final Matcher m = mr.getRegex().matcher(content);
		while (m.find())
		{
			//处理提取数据.进行持久化操作
			final String text = m.group();
			//解析数据库相关字段表名等。
			final int sql_start = text.indexOf(":") + 1;
			final int sql_end = text.lastIndexOf("}");
			final String text2 = text.substring(sql_start, sql_end);
			final Pattern p_a = Pattern.compile("\\w+\\=\\('?[a-zA-Z0-9\\s,\\s]+'?"); // 将给定的正则表达式编译到模式中
			final Matcher m_a = p_a.matcher(text2);


			final int left_start = text.indexOf("{") + 1;
			//创建数据引擎
			final ObjectDataSourceImpl data = new ObjectDataSourceImpl();

			final String var = text.substring(left_start, sql_start - 1);

			data.setVar(var);

			String whereTemp = "";//必须初始化""
			while (m_a.find())
			{
				final String[] field_kv = m_a.group().split("\\=\\(");//拆分数据格式 ："var=(xxxx"

				if (field_kv[1].indexOf("$") == 0)
				{
					final String str = field_kv[1].substring(1, field_kv[1].length());

					final String[] attributeArray = str.split("_");

					Object o = ActionContext.getReq().getAttribute(attributeArray[0]);

					if (attributeArray.length != 0)
					{
						for (int i = 1; i < attributeArray.length; i++)
						{
							try
							{
								final String firstLetter = attributeArray[i].substring(0, 1).toUpperCase();
								final String getter = "get" + firstLetter + attributeArray[i].substring(1);
								o = o.getClass().getMethod(getter, new Class[] {}).invoke(o, new Object[] {});
							}
							catch (final Exception e)
							{
								throw new SystemException("遍历集合标签中" + str + "变量未定义");
							}
						}

						field_kv[1] = o.toString();
					}
				}

				if ("table".equals(field_kv[0]))
				{
					data.setTableName(field_kv[1]);
					continue;
				}
				else if ("var".equals(field_kv[0]))
				{//遍历内部对象变量名称，参考forEach标签
					data.setVar(field_kv[1]);
					continue;
				}
				else if ("limit".equals(field_kv[0]))
				{//数据量限制
					data.setLimit(Integer.parseInt(field_kv[1]));
					continue;
				}
				else if ("page".equals(field_kv[0]))
				{//查询页码 
					data.setPage(Integer.parseInt(field_kv[1]));
					continue;
				}
				else if ("order".equals(field_kv[0]))
				{//排序支持
					data.setOrder(field_kv[1]);
				}
				else
				{// Where条件
					whereTemp += field_kv[0] + "=" + field_kv[1] + ",";
				}
			}
			data.setItems("mrcms_" + UUID.randomUUID().toString().replaceAll("-", ""));
			final String re = "<#list " + data.getItems() + " as " + data.getVar() + ">";

			content = content.replace(text, re);//替换采用UUID生成必须的
			data.setWhere(whereTemp);//设置条件

			cmstemplate.put(data);
			System.out.println(data.getQueryString());
		}
	}

}
