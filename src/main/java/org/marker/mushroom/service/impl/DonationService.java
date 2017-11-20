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
 * @author marker
 * @version 1.0
 */
@Service(Services.DONATION)
public class DonationService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;


	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = " select * from " + config.getPrefix() + "donation m order by m.time desc ";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	public List<Map<String, Object>> find()
	{
		final String sql = " select m.name,m.phone,m.address,m.description,d.value,m.time from " + config.getPrefix()
				+ "donation m left join " + config.getPrefix()
				+ "dictionaries d on m.method=d.code and d.name='method' and d.type='donation' order by m.time desc ";

		return commonDao.queryForList(sql);
	}

}
