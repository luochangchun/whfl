package org.marker.mushroom.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.marker.mushroom.utils.DateStyle;
import org.marker.mushroom.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 分类业务层处理
 *
 * @author dd
 * @version 1.0
 */
@Service(Services.APPLICATIONSITE)
public class ApplicationSiteService extends BaseService {

	@Autowired
	private ISupportDao commonDao;

	public String findInUse() {
		Date now = new Date();
		final int currYear = DateUtils.getYear(now);
		final int currMonth = DateUtils.getMonth(now);

		int preYear = currYear;
		int nextYear = currYear;
		int preMonth = currMonth - 1;
		int nextMonth = currMonth + 1;
		if (currMonth == 12) {
			nextYear = nextYear + 1;
			nextMonth = 1;
		}
		if (currMonth == 1) {
			preYear = preYear - 1;
			preMonth = 12;
		}
		int currDayNum = DateUtils.getDaysOfMonth(currYear + "-" + currMonth);
		int preDayNum = DateUtils.getDaysOfMonth(preYear + "-" + preMonth);
		int nextDayNum = DateUtils.getDaysOfMonth(nextYear + "-" + nextMonth);
		String start = String.format("%04d-%02d-01 00:00:00", preYear, preMonth);
		String end = String.format("%04d-%02d-%02d 23:59:59", nextYear, nextMonth, nextDayNum);
		Date startDate = DateUtils.stringToDate(start, "yyyy-MM-dd HH:mm:ss");
		Date endDate = DateUtils.stringToDate(end, "yyyy-MM-dd HH:mm:ss");
		Map<String, Integer> pre = new HashMap<>();
		pre.put("year", preYear);
		pre.put("month", preMonth);
		pre.put("days", preDayNum);

		Map<String, Integer> curr = new HashMap<>();
		curr.put("year", currYear);
		curr.put("month", currMonth);
		curr.put("days", currDayNum);

		Map<String, Integer> next = new HashMap<>();
		next.put("year", nextYear);
		next.put("month", nextMonth);
		next.put("days", nextDayNum);

		final String sql = "select app.id as id, app.site as site, app.makeDate as makeDate,"
			+ " app.moment as moment, app.status as status, d.value as siteValue"
			+ " from mr_application_site app left join mr_dictionaries d"
			+ " on app.site=d.code and d.name='site' and d.type='application_site'"
			+ " where app.status <> 1 and app.makeDate between ? and ? order by app.makeDate asc";
		List list = commonDao.queryForList(sql, startDate, endDate);

		Map<String, Object> result = new HashMap<>();
		result.put("data", list);
		result.put("start", DateUtils.getDate(start));
		result.put("end", DateUtils.getDate(end));
		result.put("pre", pre);
		result.put("curr", curr);
		result.put("next", next);
		return JSONObject.toJSONString(result);
	}

	@Deprecated
	public List<Map<String, Object>> find() {
		final String sql = "select app.*,d.value as siteValue from mr_application_site app "
			+ "left join mr_dictionaries d on app.site=d.code and d.name='site' and d.type='application_site' where app.status "
			+ "<> 1 order by app.time desc";
		return commonDao.queryForList(sql);
	}

	public Page find(final int currentPageNo, final int pageSize) {
		final String sql = "select app.*,d.value as siteValue from mr_application_site app "
			+ "left join mr_dictionaries d on app.site=d.code and d.name='site' and d.type='application_site' order by app"
			+ ".makeDate desc,app.site asc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	@Deprecated
	public String init(final List<Map<String, Object>> list) {

		final Map<String, List<Map<String, Object>>> init = new HashMap<>();
		final List<Map<String, Object>> list1 = new ArrayList<>();
		init.put("data", list1);

		final Map<String, Object> preData = new HashMap<>();
		final Map<String, Object> preMorning = new HashMap<>();
		final Map<String, Object> preAfternoon = new HashMap<>();
		final Map<String, Object> preEvening = new HashMap<>();
		final List<Map<String, Object>> preDaysList = new ArrayList<>();
		final List<Map<String, Object>> preMomentList = new ArrayList<>();
		final List<Map<String, Object>> preMorningList = new ArrayList<>();
		final List<Map<String, Object>> preAfternoonList = new ArrayList<>();
		final List<Map<String, Object>> preEveningList = new ArrayList<>();

		list1.add(preData);
		preMomentList.add(preMorning);
		preMomentList.add(preAfternoon);
		preMomentList.add(preEvening);
		preMorning.put("morning", preMorningList);
		preAfternoon.put("afternoon", preAfternoonList);
		preEvening.put("evening", preEveningList);

		final Map<String, Object> currData = new HashMap<>();
		final Map<String, Object> currMorning = new HashMap<>();
		final Map<String, Object> currAfternoon = new HashMap<>();
		final Map<String, Object> currEvening = new HashMap<>();
		final List<Map<String, Object>> currDaysList = new ArrayList<>();
		final List<Map<String, Object>> currMomentList = new ArrayList<>();
		final List<Map<String, Object>> currMorningList = new ArrayList<>();
		final List<Map<String, Object>> currAfternoonList = new ArrayList<>();
		final List<Map<String, Object>> currEveningList = new ArrayList<>();

		list1.add(currData);
		currMomentList.add(currMorning);
		currMomentList.add(currAfternoon);
		currMomentList.add(currEvening);
		currMorning.put("morning", currMorningList);
		currAfternoon.put("afternoon", currAfternoonList);
		currEvening.put("evening", currEveningList);

		final Map<String, Object> nextData = new HashMap<>();
		final Map<String, Object> nextMorning = new HashMap<>();
		final Map<String, Object> nextAfternoon = new HashMap<>();
		final Map<String, Object> nextEvening = new HashMap<>();
		final List<Map<String, Object>> nextDaysList = new ArrayList<>();
		final List<Map<String, Object>> nextMomentList = new ArrayList<>();
		final List<Map<String, Object>> nextMorningList = new ArrayList<>();
		final List<Map<String, Object>> nextAfternoonList = new ArrayList<>();
		final List<Map<String, Object>> nextEveningList = new ArrayList<>();

		list1.add(nextData);
		nextMomentList.add(nextMorning);
		nextMomentList.add(nextAfternoon);
		nextMomentList.add(nextEvening);
		nextMorning.put("morning", nextMorningList);
		nextAfternoon.put("afternoon", nextAfternoonList);
		nextEvening.put("evening", nextEveningList);

		//当前年份
		final int currYear = DateUtils.getYear(new Date());
		//当前月份
		final int currMonth = DateUtils.getMonth(new Date());
		//当前天数
		final int currDayNum = DateUtils.getDaysOfMonth(new Date());

		int preYear = currYear;
		int nextYear = currYear;
		int preMonth = currMonth - 1;
		int nextMonth = currMonth + 1;

		if (currMonth == 12) {
			nextYear = nextYear + 1;
			nextMonth = 1;

		}
		if (currMonth == 1) {
			preYear = preYear - 1;
			preMonth = 12;
		}

		final int preDayNum = DateUtils.getDaysOfMonth(preYear + "-" + preMonth);
		final int nextDayNum = DateUtils.getDaysOfMonth(nextYear + "-" + nextMonth);

		preData.put("year", preYear);
		preData.put("month", preMonth);
		preData.put("day_num", preDayNum);
		preData.put("cont_id", 0);
		preData.put("days", preDaysList);

		currData.put("year", currYear);
		currData.put("month", currMonth);
		currData.put("day_num", currDayNum);
		currData.put("cont_id", 1);
		currData.put("days", currDaysList);

		nextData.put("year", nextYear);
		nextData.put("month", nextMonth);
		nextData.put("day_num", nextDayNum);
		nextData.put("cont_id", 2);
		nextData.put("days", nextDaysList);

		if (list != null && list.size() > 0) {
			toJsonDays(preData, list);
			toJsonDays(currData, list);
			toJsonDays(nextData, list);
		}
		return JSONObject.toJSONString(init);
	}

	@Deprecated
	private void toJsonDays(final Map<String, Object> data, final List<Map<String, Object>> list) {


		final int year = Integer.parseInt(data.get("year").toString());
		final int month = Integer.parseInt(data.get("month").toString());
		final int dayNum = Integer.parseInt(data.get("day_num").toString());

		//		final List<Map<String, Object>> cont = (List) ((Map) data.get("days")).get("cont");
		final List<Map<String, Object>> days = (List) data.get("days");


		for (int i = 1; i < dayNum + 1; i++) {
			final List<Map<String, Object>> morningList = new ArrayList<>();
			final List<Map<String, Object>> afternoonList = new ArrayList<>();
			final List<Map<String, Object>> eveningList = new ArrayList<>();
			final List<Map<String, Object>> cont = new ArrayList<>();
			final Map<String, Object> day = new HashMap<>();
			final Map<String, Object> morning = new HashMap<>();
			final Map<String, Object> afternoon = new HashMap<>();
			final Map<String, Object> evening = new HashMap<>();

			for (final Map<String, Object> map : list) {
				final Date makeDate = DateUtils.stringToDate(map.get("makeDate").toString(), DateStyle.YYYY_MM_DD);
				final int makeYear = DateUtils.getYear(makeDate);
				final int makeMonth = DateUtils.getMonth(makeDate);
				final int makeDay = DateUtils.getDay(makeDate);
				if (year == makeYear && month == makeMonth) {
					if (makeDay == i) {
						if (!day.containsKey("day")) {
							morning.put("morning", morningList);
							afternoon.put("afternoon", afternoonList);
							evening.put("evening", eveningList);
							cont.add(morning);
							cont.add(afternoon);
							cont.add(evening);

							day.put("day", makeDay);
							day.put("cont", cont);
						}

						final Map<String, Object> site = new HashMap<>();
						site.put("id", map.get("site").toString());
						site.put("value", map.get("siteValue").toString());
						site.put("status_num", map.get("status").toString());

						if (map.containsValue("morning")) {
							morningList.add(site);
						}
						if (map.containsValue("afternoon")) {
							afternoonList.add(site);
						}
						if (map.containsValue("evening")) {
							eveningList.add(site);
						}
					}
				}
			}
			if (!day.isEmpty()) {
				days.add(day);
			}
		}
	}
}
