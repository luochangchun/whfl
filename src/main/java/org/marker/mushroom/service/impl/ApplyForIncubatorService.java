package org.marker.mushroom.service.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		
		 Pattern p = null;  
	     Matcher m = null;  
	     boolean b = false; 
	     String str=applyForIncubator.getPhone();
	     p = Pattern.compile("^[0-9]{8,11}$"); // 验证手机号8到11位 
	     m = p.matcher(str);  
	     b = m.matches(); 
	     if(b==false){
	    	 return new ResultMessage(false, "号码格式不正确！请重新输入！");
	     }
	     
		if(StringUtil.isBlank(applyForIncubator.getText())
				|| StringUtil.isBlank(applyForIncubator.getName())
				|| StringUtil.isBlank(applyForIncubator.getCompany())
				|| "".equals(applyForIncubator.getTeam()))
				return new ResultMessage("资料不完善，请填写完整信息！");
	
		if(commonDao.save(applyForIncubator)==false){
			return new ResultMessage(false,"入驻失败，请重新入驻！");
		}else{
			return new ResultMessage(true, "入驻成功！");
		}
		
	}


	//显示入驻孵化器
	public Page findByPage(int currentPageNo, int pageSize) {
		final String sql="select id,name,phone,company,team,date" +" from "+ config.getPrefix()+"applyincubator order by date desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	//删除操作
	public boolean afterApplyForIncubatorDelete(String rid) {
		boolean deleteByIds = commonDao.deleteByIds(ApplyForIncubator.class, rid);
		return deleteByIds;
	}

	//查看详情操作
	public Map<String,Object> tenancyDetail(int id) {
	    Map<String, Object> findById = commonDao.findById(ApplyForIncubator.class, id);
		return findById;
	}

	
	

	
	

	
}
