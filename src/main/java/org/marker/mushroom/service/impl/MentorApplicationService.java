package org.marker.mushroom.service.impl;

import love.cq.util.StringUtil;
import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.MentorApplication;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 导师申请表单
 *
 * @author Administrator
 */
@Service(Services.MENTORAPPLICATION)
public class MentorApplicationService extends BaseService {

	@Autowired
	private ISupportDao commonDao;

	@Transactional
	public ResultMessage createMentorApplication(MentorApplication mentorApplication) {
		String str = mentorApplication.getPhone();
		if (!Pattern.compile("^[0-9]{8,11}$").matcher(str).matches()) {
			return new ResultMessage(false, "号码格式不正确！请重新输入！");
		}
		String str1 = mentorApplication.getOfficePhone();
		if (!Pattern.compile("^[0-9]{8,11}$").matcher(str1).matches()) {
			return new ResultMessage(false, "号码格式不正确！请重新输入！");
		}

		if (StringUtil.isBlank(mentorApplication.getName())
			|| StringUtil.isBlank(mentorApplication.getGender())
			|| StringUtil.isBlank(mentorApplication.getBirthday())
			|| StringUtil.isBlank(mentorApplication.getUniversity())
			|| StringUtil.isBlank(mentorApplication.getEducationalBackground())
			|| StringUtil.isBlank(mentorApplication.getMajors())
			|| StringUtil.isBlank(mentorApplication.getUnit())
			|| StringUtil.isBlank(mentorApplication.getDuty())
			|| StringUtil.isBlank(mentorApplication.getIndustry())
			|| StringUtil.isBlank(mentorApplication.getPhotographs())
			|| StringUtil.isBlank(mentorApplication.getResume()))
			return new ResultMessage("资料不完善，请填写完整信息！");

		mentorApplication.setTime(new Date());
		if (commonDao.save(mentorApplication)) {
			return new ResultMessage(true, "申请成功！");
		} else {
			return new ResultMessage(false, "申请失败，请重新申请！");
		}
	}

	//后台展示
	public Page findByPage(int currentPageNo, int pageSize) {
		final String sql = "select id,name,gender,officePhone,mailorqq,courses,time" + " from " + config.getPrefix()
			+ "mentorapplication order by time desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	//删除操作
	public boolean MentorApplicationDelete(String rid) {
		return commonDao.deleteByIds(MentorApplication.class, rid);
	}

	//查看详情操作
	public Map<String, Object> tenancyDetail(int id) {
		return commonDao.findById(MentorApplication.class, id);
	}

}
