package org.marker.mushroom.dao;

import org.marker.mushroom.beans.CourseReport;

/**
 * Created by 30 on 2017/9/18.
 */
public interface ICourseReportDao extends ISupportDao {
	CourseReport findById(int id);
}
