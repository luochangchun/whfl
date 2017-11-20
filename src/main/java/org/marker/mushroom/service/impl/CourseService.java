package org.marker.mushroom.service.impl;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Course;
import org.marker.mushroom.beans.CourseReport;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.ICourseDao;
import org.marker.mushroom.dao.ICourseReportDao;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by 30 on 2017/9/15.
 */
@Service(Services.COURSE)
public class CourseService extends BaseService {

	@Autowired
	private ISupportDao commonDao;

	@Autowired
	private ICourseReportDao reportDao;

	@Autowired
	private ICourseDao courseDao;

	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = "select c.*\n" + " from " + config.getPrefix() + "course c\n order by c.time desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	public List findReports(final int courseId) {
		final String sql = "select * from " + config.getPrefix() + "course_report"
			+ " where courseId = " + courseId + " order by time desc";
		return commonDao.queryForList(sql);
	}

	public CourseReport findReport(final int id) {
		return reportDao.findById(id);
	}

	public Course findCourse(final int id) {
		return courseDao.findById(id);
	}

	/**
	 * 新增课程报名
	 * @param report
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object saveReport(CourseReport report) {
		Course course = courseDao.findById(report.getCourseId());
		if (course == null) return new ResultMessage(false, "报名失败！");
		else if (course.getStatus() == 1) return new ResultMessage(false, "该课程报名已截止！");
		else if (course.getReported() >= course.getTotal()) return new ResultMessage(false, "该课程报名人数已满！");

		report.setTime(new Date());
		report.setAccepted(0);

		int reported = course.getReported() + 1;
		course.setReported(reported);
		if (reported == course.getTotal()) course.setStatus(1);
		if (commonDao.save(report) && commonDao.update(course)) {
			return new ResultMessage(true, "报名成功!");
		} else {
			return new ResultMessage(false, "报名失败!");
		}
	}

	/**
	 * 课程报名审核通过
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object setCourseReported(final int id) {
		CourseReport cp = reportDao.findById(id);
		if (cp == null || cp.getAccepted() < 0)
			return new ResultMessage(false, "审核失败！");

		cp.setAccepted(1);
		if(commonDao.update(cp)) {
			return new ResultMessage(true, "审核成功！");
		} else {
			return new ResultMessage(false, "审核失败！");
		}
	}

	/**
	 * 课程报名审核驳回
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object setCourseReportDeny(final int id) {
		CourseReport cp = reportDao.findById(id);
		if (cp == null || cp.getAccepted() > 0) return new ResultMessage(false, "驳回失败！");

		Course course = courseDao.findById(cp.getCourseId());
		if (course == null) return new ResultMessage(false, "驳回失败！");

		int report = course.getReported();
		if (report == course.getTotal() && course.getStatus() == 1)
			course.setStatus(0);
		course.setReported(report-1);

		cp.setAccepted(-1);
		if(commonDao.update(cp) && commonDao.update(course)) {
			return new ResultMessage(true, "驳回成功！");
		} else {
			return new ResultMessage(false, "驳回失败!");
		}
	}
}
