package org.marker.mushroom.core.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.marker.mushroom.core.IChip;
import org.marker.mushroom.core.SystemStatic;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.dao.ISupportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 碎片
 */
@Service(SystemStatic.SYSTEM_CMS_CHIP)
public class ChipContext implements IChip
{

	private static final Log log = LogFactory.getLog(ChipContext.class);

	@Autowired
	ISupportDao commonDao;

	private final boolean isSyn = false;

	private HashMap<String, String> data;



	public synchronized void syn()
	{
		if (!isSyn)
		{
			final String prefix = DataBaseConfig.getInstance().getPrefix();
			final List<Map<String, Object>> list = commonDao.queryForList("select * from " + prefix + "chip");
			data = new HashMap<String, String>();
			for (final Map<String, Object> o : list)
			{
				final String mark = o.get("mark").toString();
				data.put(mark, o.get("content").toString());
			}
			log.info("syn chip data ");
			//			isSyn = true; //将此代码注释掉，可以将碎片同步功能打开。
		}
	}

	public Object getVector()
	{
		syn();
		return data;
	}
}


