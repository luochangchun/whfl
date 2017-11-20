package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Course;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.ICourseDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperCourse;
import org.springframework.stereotype.Repository;

/**
 * Created by 30 on 2017/9/15.
 */
@Repository(DAO.COURSE)
public class CourseDaoImpl extends DaoEngine implements ICourseDao {

	@Override
	public Course findById(final int id) {
		String sql = "select * from " + dbConfig.getPrefix() + "course where id = ?";
		return jdbcTemplate.queryForObject(sql, new RowMapperCourse(), id);
	}
}
