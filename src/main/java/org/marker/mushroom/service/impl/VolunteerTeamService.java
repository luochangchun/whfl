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
@Service(Services.VOLUNTEERTEAM)
public class VolunteerTeamService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;


	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = " select m.*,d.value from " + config.getPrefix() + "volunteer_team m left join " + config.getPrefix()
				+ "dictionaries d on m.region=d.id and d.name='region' and d.type='volunteer' order by m.time desc ";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}


	public List<Map<String, Object>> find()
	{
		final String sql = " select m.name,m.idCard,m.number,m.phone,m.like,d.value,m.applyTime,m.description from "
				+ config.getPrefix() + "volunteer_team m left join " + config.getPrefix()
				+ "dictionaries d on m.region=d.id and d.name='region' and d.type='volunteer' order by m.time desc ";

		return commonDao.queryForList(sql);
	}
}
