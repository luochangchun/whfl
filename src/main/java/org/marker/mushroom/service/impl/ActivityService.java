package org.marker.mushroom.service.impl;

import java.util.List;
import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 分类业务层处理
 * 
 * @author dd
 * @version 1.0
 */
@Service(Services.ACTIVITY)
public class ActivityService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;


	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = " select m.*,m1.title from " + config.getPrefix() + "activity m left join " + config.getPrefix()
				+ "article m1 on m.articleId=m1.id order by m.time desc ";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	public List<Map<String, Object>> find()
	{
		final String sql = " select m1.title,m.name,m.idCard,m.phone,m.applyTime from " + config.getPrefix()
				+ "activity m left join " + config.getPrefix() + "article m1 on m.articleId=m1.id order by m.time desc ";

		return commonDao.queryForList(sql);
	}
}
