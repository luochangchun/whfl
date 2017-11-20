package org.marker.mushroom.service.impl;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Page;
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
@Service(Services.ONLINECOURSE)
public class OnlineCourseService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;

	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = "select opl.*,a.title\n" + " from " + config.getPrefix() + "organization_points_log opl \n"
				+ " left join " + config.getPrefix() + "article a on opl.aid=a.id \n" + "left join " + config.getPrefix()
				+ "user u on a.userId=u.id \n" + " left join " + config.getPrefix() + "user_organization uo on u.uuid=uo.uuid \n"
				+ " left join " + config.getPrefix() + "organization o on uo.orguid=o.uuid\n" + "order by opl.time desc";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}


}
