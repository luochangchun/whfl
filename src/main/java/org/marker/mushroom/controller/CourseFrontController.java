package org.marker.mushroom.controller;

import org.marker.mushroom.beans.CourseReport;
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

/**
 * Created by 30 on 2017/9/18.
 */
@Controller
@RequestMapping("/front/course")
public class CourseFrontController extends SupportController {
	private static final Logger logger = LoggerFactory.getLogger(CourseFrontController.class);

	@Autowired
	private CourseService courseService;

	public CourseFrontController() {
	}

	@RequestMapping("/detail")
	@ResponseBody
	public Object detail(@RequestParam("courseid") Integer id) {
		return courseService.findCourse(id);
	}

	/**
	 * @param courseReport
	 * @return
	 */
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	@ResponseBody
	public Object report(final CourseReport courseReport) {
		return courseService.saveReport(courseReport);
	}
}
