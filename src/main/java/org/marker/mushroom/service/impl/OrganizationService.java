package org.marker.mushroom.service.impl;

import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 社会组织列表
 * 
 * @author marker
 * @version 1.0
 */
@Service(Services.ORGANIZATION)
public class OrganizationService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;


	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = " select u.id as uid,u.name as account,u.status as userStatus,o.*,d1.value as settledValue,d2.value as typeValue "
				+ "from " + config.getPrefix() + "organization o left join " + config.getPrefix()
				+ "dictionaries d1 on o.settled = d1.code and d1.name='settled' and d1.type='organization' left join "
				+ config.getPrefix() + "dictionaries d2 on o.type = d2.code and d2.name='type' and d2.type='organization' left join "
				+ config.getPrefix() + "user_organization  uo on o.uuid=uo.orguid left join " + config.getPrefix()
				+ "user u on u.uuid=uo.uuid " + " order by o.time desc ";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	public Map<String, Object> find(final Integer id)
	{
		final String sql = " select u.name as account,u.status as userStatus, o.*,d1.value as settledValue,d2.value as typeValue "
				+ "from " + config.getPrefix() + "organization o left join " + config.getPrefix()
				+ "dictionaries d1 on o.settled = d1.code and d1.name='settled' and d1.type='organization' left join "
				+ config.getPrefix() + "dictionaries d2 on o.type = d2.code and d2.name='type' and d2.type='organization' left join "
				+ config.getPrefix() + "user_organization  uo on o.uuid=uo.orguid left join " + config.getPrefix()
				+ "user u on u.uuid=uo.uuid " + " where o.id = ? ";

		return commonDao.queryForMap(sql, id);
	}

}
