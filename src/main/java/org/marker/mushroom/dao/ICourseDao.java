package org.marker.mushroom.dao;

import org.marker.mushroom.beans.Course;

/**
 *
 * Created by 30 on 2017/9/15.
 */
public interface ICourseDao extends ISupportDao {

	Course findById(int id);
}
