package org.marker.mushroom.service.impl;

import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.ApplyForIncubator;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import love.cq.util.StringUtil;




/**
 *申请入驻处理
 * @author Administrator
 *
 */
@Service(Services.APPLYINCUBATOR)
public class ApplyForIncubatorService extends BaseService{
	
	@Autowired
	private ISupportDao commonDao;

	//申请操作
	@Transactional
	public ResultMessage createApplyIncubator(ApplyForIncubator applyForIncubator) {
		if(StringUtil.isBlank(applyForIncubator.getText())
				|| StringUtil.isBlank(applyForIncubator.getName())
				|| StringUtil.isBlank(applyForIncubator.getPhone())
				|| StringUtil.isBlank(applyForIncubator.getCompany())
				|| "".equals(applyForIncubator.getTeam()))
				return new ResultMessage("资料不完善，请填写完整信息！");
	
		if(commonDao.save(applyForIncubator)==false){
			return new ResultMessage("入驻失败，请重新入驻！");
		};
		return new ResultMessage();
	}


	//显示入驻孵化器
	public Page findByPage(int currentPageNo, int pageSize) {
		final String sql="select id,name,phone,company,team,date" +" from "+ config.getPrefix()+"applyincubator order by date desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	//删除操作
	public ResultMessage afterApplyForIncubatorDelete(String rid) {
		commonDao.deleteByIds(ApplyForIncubator.class, rid);
		return new ResultMessage();
	}

	//查看详情操作
	public Map<String,Object> tenancyDetail(int id) {
	    Map<String, Object> findById = commonDao.findById(ApplyForIncubator.class, id);
		return findById;
	}

	
	

	
	

	
}
