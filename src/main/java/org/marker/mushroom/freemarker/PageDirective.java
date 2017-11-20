package org.marker.mushroom.freemarker;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.core.WebParam;
import org.marker.mushroom.ext.model.ContentModel;
import org.marker.mushroom.ext.model.ContentModelContext;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.service.impl.CategoryService;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;


/**
 * 分页指令
 * 
 * @author marker
 * @version 1.0
 */
public class PageDirective implements TemplateDirectiveModel
{

	private final ContentModelContext cmc;
	private final CategoryService categoryService;

	public PageDirective()
	{
		cmc = ContentModelContext.getInstance();
		categoryService = SpringContextHolder.getBean(Services.CATEGORY);
	}

	@Override
	public void execute(final Environment env, @SuppressWarnings("rawtypes") final Map params, final TemplateModel[] loopVars,
			final TemplateDirectiveBody body) throws TemplateException, IOException
	{

		String modelType = "";// 内容模型

		String order = "";

		String cids = "";

		int pageSize = 10;

		String whereSql = "";

		final WebParam param = WebParam.get();

		for (final Object entrySetObject : params.entrySet())
		{
			final Map.Entry mapEntry = (Map.Entry) entrySetObject;

			final String key = mapEntry.getKey() == null ? "" : mapEntry.getKey().toString();
			final String value = mapEntry.getValue() == null ? "" : mapEntry.getValue().toString();

			if (!StringUtils.isEmpty(key))
			{
				if (key.toLowerCase().equals("model"))
				{
					modelType = value;
				}
				else if (key.toLowerCase().equals("size"))
				{
					try
					{
						pageSize = Integer.parseInt(value);// 页面大小
					}
					catch (final Exception e)
					{
						throw new TemplateException("@page size is number e.g: size=\"10\" ", env);
					}
				}
				else if (key.toLowerCase().equals("order"))
				{
					order = value;
				}
				else if (key.toLowerCase().equals("cid"))
				{
					try
					{
						final int cid = Integer.parseInt(value); //内容Id
						cids = categoryService.findChildIds(cid);
						whereSql += " and A.cid in (" + cids + ") ";
					}
					catch (final Exception e)
					{
						throw new TemplateException("@page cid is number e.g: cid=\"12\" ", env);
					}
				}
				else //只要不属于以上四种默认类型，就都当where条件使用
				{
					whereSql += " and A." + key + " = '" + value + "'";
				}
			}

		}

		param.modelType = modelType;

		param.pageSize = pageSize;

		param.extendSql = whereSql;

		param.orderSql = StringUtils.isEmpty(order) ? "" : " order by " + order;

		final ContentModel cm = cmc.get(modelType);
			final Page page = cm.doPage(param);

		env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(page));
		body.render(env.getOut());
	}

}
