package org.marker.mushroom.service.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.MentorApplication;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import love.cq.util.StringUtil;

/**
 * 导师申请表单
 * @author Administrator
 *
 */
@Service(Services.MENTORAPPLICATION)
public class MentorApplicationService extends BaseService{

	@Autowired
	private ISupportDao commonDao;

	//申请操作
	public ResultMessage createMentorApplication(MentorApplication mentorApplication) {
		
		 Pattern p = null;  
	     Matcher m = null;  
	     boolean b = false; 
	     String str=mentorApplication.getPhone();
	     p = Pattern.compile("^[0-9]{8,11}$"); // 验证手机号8到11位 
	     m = p.matcher(str);  
	     b = m.matches(); 
	     if(b==false){
	    	 return new ResultMessage(false, "号码格式不正确！请重新输入！");
	     }
	     Pattern p1 = null;  
	     Matcher m1 = null;  
	     boolean b1 = false; 
	     String str1=mentorApplication.getOfficePhone();
	     p1 = Pattern.compile("^[0-9]{8,11}$"); // 验证手机号8到11位 
	     m1 = p1.matcher(str1);  
	     b1 = m1.matches(); 
	     if(b1==false){
	    	 return new ResultMessage(false, "号码格式不正确！请重新输入！");
	     }
		
		if(StringUtil.isBlank(mentorApplication.getName())
				|| StringUtil.isBlank(mentorApplication.getGender())
				|| StringUtil.isBlank(mentorApplication.getBirthday())
				|| StringUtil.isBlank(mentorApplication.getUniversity())
				|| StringUtil.isBlank(mentorApplication.getEducationalBackground())
				|| StringUtil.isBlank(mentorApplication.getMajors())
				|| StringUtil.isBlank(mentorApplication.getMailorqq())
				|| StringUtil.isBlank(mentorApplication.getUnit())
				|| StringUtil.isBlank(mentorApplication.getDuty())
				|| StringUtil.isBlank(mentorApplication.getIndustry())
				|| StringUtil.isBlank(mentorApplication.getAddress())
				|| StringUtil.isBlank(mentorApplication.getCourses())
				|| StringUtil.isBlank(mentorApplication.getIntroduce())
				|| StringUtil.isBlank(mentorApplication.getPhotographs())
				|| StringUtil.isBlank(mentorApplication.getResume()))
				return new ResultMessage("资料不完善，请填写完整信息！");
	
		if(commonDao.save(mentorApplication)==false){
			return new ResultMessage(false, "申请失败，请重新申请！");
		}else{
			return new ResultMessage(true, "申请成功！");
		}
	}

	//后台展示
	public Page findByPage(int currentPageNo, int pageSize) {
		final String sql="select id,name,gender,officePhone,mailorqq,courses,time" +" from "+ config.getPrefix()+"mentorapplication order by time desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	//删除操作
	public boolean MentorApplicationDelete(String rid) {
		boolean deleteByIds = commonDao.deleteByIds(MentorApplication.class, rid);
		return deleteByIds;
		
	}

	//查看详情操作
	public Map<String, Object> tenancyDetail(int id) {
		 Map<String, Object> findById = commonDao.findById(MentorApplication.class, id);
			return findById;
	}
	
	
	
}
