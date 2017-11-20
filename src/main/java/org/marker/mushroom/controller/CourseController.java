package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Course;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.ICourseDao;
import org.marker.mushroom.service.impl.CourseService;
import org.marker.mushroom.support.SupportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 课程管理
 *
 * Created by 30 on 2017/9/15.
 */
@Controller
@RequestMapping("/admin/course")
public class CourseController extends SupportController {

	@Autowired
	private ICourseDao courseDao;

	@Autowired
	private CourseService courseService;

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	public CourseController() {
		this.viewPath = "/admin/course/";
	}

	@RequestMapping("/view")
	public ModelAndView view() {
		return new ModelAndView(this.viewPath + "list");
	}

	/**
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(Page page) {
		page = courseService.find(page.getCurrentPageNo(), page.getPageSize());
		return page;
	}

	//添加碎片
	@RequestMapping("/add")
	public String add() {
		return this.viewPath + "add";
	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") final int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("course", commonDao.findById(Course.class, id));
		return view;
	}

	@ResponseBody
	@RequestMapping("/save")
	public Object save(final Course course) {
		course.setReported(0);
		course.setTime(new Date());
		course.setStatus(0);
		if (commonDao.save(course)) {
			return new ResultMessage(true, "添加成功!");
		} else {
			return new ResultMessage(false, "添加失败!");
		}
	}

	@ResponseBody
	@RequestMapping("/update")
	public Object update(Course course) {
		if (commonDao.update(course)) {
			return new ResultMessage(true, "更新成功!");
		} else {
			return new ResultMessage(true, "更新失败!");
		}
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("id") Integer id) {
		boolean status = commonDao.deleteByIds(Course.class, id + "");
		if (status) {
			return new ResultMessage(true, "删除成功!");
		} else {
			return new ResultMessage(false, "删除失败!");
		}
	}

	@RequestMapping("/report")
	public ModelAndView report(@RequestParam("id") final int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "/report/list");
		view.addObject("course", commonDao.findById(Course.class, id));
		view.addObject("reports", courseService.findReports(id));
		return view;
	}

	@RequestMapping(value = "/report/list", method = RequestMethod.GET)
	@ResponseBody
	public Object reportPage(Page page) {
		page = courseService.find(page.getCurrentPageNo(), page.getPageSize());
		return page;
	}

	@ResponseBody
	@RequestMapping("/report/ok")
	public Object reportOk(@RequestParam("id") Integer id) {
		return courseService.setCourseReported(id);
	}

	@ResponseBody
	@RequestMapping("/report/no")
	public Object reportNo(@RequestParam("id") Integer id) {
		return courseService.setCourseReportDeny(id);
	}
}
