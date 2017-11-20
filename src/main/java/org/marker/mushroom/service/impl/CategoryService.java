package org.marker.mushroom.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Category;
import org.marker.mushroom.dao.ICategoryDao;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 分类业务层处理
 * 
 * @author marker
 * @version 1.0
 */
@Service(Services.CATEGORY)
public class CategoryService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;

	@Autowired
	private ICategoryDao categoryDao;

	/**
	 * 查询所有分类信息
	 * 
	 * @return List<Map<String, Object>>
	 */
	public List<Category> list()
	{
		return categoryDao.list();
	}

	/**
	 * 查询模型的分类信息
	 * 
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> list(final String modelType)
	{
		final String sql = "select * from " + config.getPrefix() + "category where model=?";
		return commonDao.queryForList(sql, modelType);
	}



	// 通过分类ID查询模型类型
	public String findModelById(final Serializable id)
	{
		final String sql = "select model from " + config.getPrefix() + "category where id=?";
		return commonDao.queryForObject(sql, String.class, id);
	}



	/**
	 * 判断分类节点是否有子节点
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasChild(final Serializable id)
	{
		final String sql = "select count(id) from " + config.getPrefix() + "category where pid=?";
		final int a = commonDao.queryForObject(sql, Integer.class, id);
		return a > 0 ? true : false;
	}




	/**
	 * 查询子Id集合字符串 方便使用where语句
	 * 
	 * @param cid
	 * @return
	 */
	public String findChildIds(final int cid)
	{
		final List<Category> list = categoryDao.list();
		final StringBuilder sb = new StringBuilder();
		sb.append("" + cid);
		fetch(cid, sb, list);
		return sb.toString();
	}



	// 抓取数据
	private void fetch(final int cid, final StringBuilder sb, final List<Category> list)
	{
		for (final Category c : list)
		{
			if (c.getPid() == cid)
			{
				sb.append(",").append(c.getId());
				fetch(c.getId(), sb, list);
			}
		}
	}

	public List<Map<String, Object>> findMapCategoryById(final int pid)
	{
		final String sql = "select m1.id as id,m1.name as name,m1.alias as en, count(m2.id) as value from " + config.getPrefix()
				+ "category m1 left join " + config.getPrefix()
				+ "article m2 on (m1.id=m2.cid and m2.status=1) where 1=1 and m1.pid = ? group by m1.id,m1.name,m1.alias";

		return commonDao.queryForList(sql, pid);
	}




}
