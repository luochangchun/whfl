package org.marker.mushroom.controller;

import org.marker.mushroom.beans.ApplyForIncubator;
import org.marker.mushroom.beans.EnrolActivity;
import org.marker.mushroom.beans.MentorApplication;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.Womanwork;
import org.marker.mushroom.service.impl.ApplyForIncubatorService;
import org.marker.mushroom.service.impl.EnrolActivityService;
import org.marker.mushroom.service.impl.MentorApplicationService;
import org.marker.mushroom.service.impl.WomanworkService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 30 on 2017/11/24.
 */
@Controller
@RequestMapping("/front/neutron")
public class NeutronFrontController extends SupportController {

	@Autowired
	private ApplyForIncubatorService incubatorService;

	@Autowired
	private EnrolActivityService activityService;

	@Autowired
	private MentorApplicationService mentorService;

	@Autowired
	private WomanworkService neutronService;

	@RequestMapping(value = "/incubator", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage incubator(ApplyForIncubator incubator) throws Exception {
		return incubatorService.createApplyIncubator(incubator);
	}

	@RequestMapping(value = "/activity", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage activity(EnrolActivity activity) throws Exception {
		return activityService.createEnrolActivity(activity);
	}

	@RequestMapping(value = "/mentor", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage mentor(MentorApplication mentor) throws Exception {
		return mentorService.createMentorApplication(mentor);
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	@ResponseBody
	public ResultMessage join(Womanwork womanwork) throws Exception {
		return neutronService.createWomanwork(womanwork);
	}
}
