package org.marker.mushroom.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Offline_course;
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
@Service(Services.OFFLINECOURSE)
public class OfflineCourseService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;

	@Autowired
	private UserOrganizationService userOrganizationService;

	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = "select oc.*,o.name as orgName,u.name as userName " + "from " + config.getPrefix() + "offline_course oc "
				+ "left join " + config.getPrefix() + "organization o on oc.orguid=o.uuid " + "left join " + config.getPrefix()
				+ "user u on oc.uuid=u.uuid " + "order by oc.time desc";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	public boolean addCourse(final String ids, final String courseName, final Integer courseIntegral, final Integer userId)
	{
		boolean flag = false;
		final List<Offline_course> ocList = new ArrayList<>();
		final List<Map<String, Object>> list = userOrganizationService.findById(ids);

		for (final Map<String, Object> map : list)
		{

			final Offline_course offline_course = new Offline_course();
			try
			{
				offline_course.setName(new String(courseName.getBytes("ISO-8859-1"), "utf-8"));
			}
			catch (final UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			;
			offline_course.setUuid(map.get("uuid") == null ? "" : map.get("uuid").toString());
			offline_course.setOrgUid(map.get("orguid") == null ? "" : map.get("orguid").toString());
			offline_course.setIntegral(courseIntegral);
			offline_course.setUserId(userId);
			offline_course.setTime(new Date());
			offline_course.setIntegralTime(new Date());

			ocList.add(offline_course);
		}


		String sql = "insert into " + config.getPrefix()
				+ "offline_course(name,uuid,orgUid,integral,integralTime,time,userId) values(?,?,?,?,?,?,?)";

		if (saveAll(sql, ocList))
		{
			sql = "update " + config.getPrefix() + "user_organization set offlinePoint=offlinePoint+? where uuid=? and orgUid=?";
			flag = batchUpdate(sql, ocList);
		}
		return flag;
	}

	private boolean saveAll(final String sql, final List<Offline_course> ocList)
	{
		boolean flag = true;
		final List<Object[]> objectList = new ArrayList<>();

		for (final Offline_course offline_course : ocList)
		{
			objectList.add(new Object[]
			{ offline_course.getName(), offline_course.getUuid(), offline_course.getOrgUid(), offline_course.getIntegral(),
					offline_course.getIntegralTime(), offline_course.getTime(), offline_course.getUserId() });
		}
		if (!objectList.isEmpty())
		{
			final int[] results = commonDao.batchUpdate(sql, objectList);
			for (final int i : results)
			{
				if (i < 0)
				{
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

	private boolean batchUpdate(final String sql, final List<Offline_course> ocList)
	{
		boolean flag = true;
		final List<Object[]> objectList = new ArrayList<>();

		for (final Offline_course offline_course : ocList)
		{
			objectList.add(new Object[]
			{ offline_course.getIntegral(), offline_course.getUuid(), offline_course.getOrgUid() });
		}
		if (!objectList.isEmpty())
		{
			final int[] results = commonDao.batchUpdate(sql, objectList);

			for (final int i : results)
			{
				if (i < 0)
				{
					flag = false;
					break;
				}
			}
		}
		return flag;
	}

}
