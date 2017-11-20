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
 * @author dd
 * @version 1.0
 */
@Service(Services.MECHANISM)
public class MechanismService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;


	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = " select \n"
				+ "\tm.*,d1.style as style, d1.value as scaleValue,d2.style as style ,d2.value as categroyValue,d3.style as style,d3.value as statusValue\n"
				+ "from \n" + "\tmr_mechanism m \n"
				+ "left join mr_dictionaries d1 on m.scale=d1.code and d1.name='scale' and d1.type='mechanism'\n"
				+ "left join mr_dictionaries d2 on m.category=d2.code and d2.name='category' and d2.type='mechanism'\n"
				+ "left join mr_dictionaries d3 on m.status=d3.code and d3.name='status' and d3.type='mechanism'\n  order by m.time desc ";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

}
