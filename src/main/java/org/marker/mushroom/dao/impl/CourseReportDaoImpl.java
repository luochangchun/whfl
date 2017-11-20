package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.CourseReport;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.ICourseReportDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperCourseReport;
import org.springframework.stereotype.Repository;

/**
 * Created by 30 on 2017/9/18.
 */
@Repository(DAO.COURSE_REPORT)
public class CourseReportDaoImpl extends DaoEngine implements ICourseReportDao {

	@Override
	public CourseReport findById(final int id) {
		String sql = "select * from " + dbConfig.getPrefix() + "course_report where id = ?";
		return jdbcTemplate.queryForObject(sql, new RowMapperCourseReport(), id);
	}
}
