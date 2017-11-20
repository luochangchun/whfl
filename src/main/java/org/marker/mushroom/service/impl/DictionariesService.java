package org.marker.mushroom.service.impl;

import java.util.List;
import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 字典表业务处理
 *
 * @author dd
 * @version 1.0
 */
@Service(Services.DICTIONARIES)
public class DictionariesService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;



	public void convertDictionaries(final Map<String, Object> map, final String name, final String type)
	{
		final StringBuilder str = new StringBuilder();

		if (map != null)
		{
			final String sql = "select d.code,d.value from " + config.getPrefix() + "dictionaries d where d.name = ? and d.type= ? ";
			final List<Map<String, Object>> dictionaries = commonDao.queryForList(sql, name, type);

			final String tempStr = map.get(name) == null ? "" : map.get(name).toString();
			if (!tempStr.equals(""))
			{
				for (final String temp : tempStr.split(","))
				{
					for (final Map<String, Object> dictionarieMap : dictionaries)
					{
						final String code = dictionarieMap.get("code") == null ? "" : dictionarieMap.get("code").toString();
						if (temp.equals(code))
						{
							final String value = dictionarieMap.get("value") == null ? "" : dictionarieMap.get("value").toString();
							str.append(value).append(" ");
						}
					}
				}
			}
			map.put(name, str.toString());
		}
	}

}
