package org.marker.mushroom.service.impl;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.ApplyForIncubator;
import org.marker.mushroom.beans.EnrolActivity;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import love.cq.util.StringUtil;

/**
 * 申请活动
 * @author Administrator
 *
 */
@Service(Services.ENROLACTIVITY)
public class EnrolActivityService extends BaseService{

	
	@Autowired
	private ISupportDao commonDao;

	//申请操作
	@Transactional
	public ResultMessage createEnrolActivity(EnrolActivity enrolActivity) {
		
		if(StringUtil.isBlank(enrolActivity.getName())
				|| StringUtil.isBlank(enrolActivity.getPhone())
				|| StringUtil.isBlank(enrolActivity.getAppellation()))
				return new ResultMessage("资料不完善，请填写完整信息！");
	
		if(commonDao.save(enrolActivity)==false){
			return new ResultMessage(false, "申请失败，请重新申请！");
		};
		return new ResultMessage();
	}

	//后台展示
	public Page findByPage(int currentPageNo, int pageSize) {
		final String sql="select id,name,phone,appellation,time" +" from "+ config.getPrefix()+"enrolactivity order by time desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	//删除操作
	public ResultMessage EnrolActivityDelete(String rid) {
		commonDao.deleteByIds(EnrolActivity.class, rid);
		return new ResultMessage();
	}
	
	
	
	
}
