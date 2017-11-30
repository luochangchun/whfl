package org.marker.mushroom.service.impl;

import love.cq.util.StringUtil;
import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.ApplyForIncubator;
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
 * 申请入驻处理
 *
 * @author Administrator
 */
@Service(Services.APPLYINCUBATOR)
public class ApplyForIncubatorService extends BaseService {

	@Autowired
	private ISupportDao commonDao;

	//申请操作
	@Transactional
	public ResultMessage createApplyIncubator(ApplyForIncubator applyForIncubator) {
		String str = applyForIncubator.getPhone();
		if (!Pattern.compile("^[0-9]{8,11}$").matcher(str).matches()) {
			return new ResultMessage(false, "号码格式不正确！请重新输入！");
		}

		if (StringUtil.isBlank(applyForIncubator.getText())
			|| StringUtil.isBlank(applyForIncubator.getName())
			|| StringUtil.isBlank(applyForIncubator.getCompany())
			|| applyForIncubator.getTeam() <= 0)
			return new ResultMessage("资料不完善，请填写完整信息！");

		applyForIncubator.setDate(new Date());
		if (commonDao.save(applyForIncubator)) {
			return new ResultMessage(true, "入驻成功！");
		} else {
			return new ResultMessage(false, "入驻失败，请重新入驻！");
		}
	}

	//显示入驻孵化器
	public Page findByPage(int currentPageNo, int pageSize) {
		final String sql =
			"select id,name,phone,company,team,date" + " from " + config.getPrefix() + "applyincubator order by date desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	//删除操作
	public boolean afterApplyForIncubatorDelete(String rid) {
		boolean deleteByIds = commonDao.deleteByIds(ApplyForIncubator.class, rid);
		return deleteByIds;
	}

	//查看详情操作
	public Map<String, Object> tenancyDetail(int id) {
		Map<String, Object> findById = commonDao.findById(ApplyForIncubator.class, id);
		return findById;
	}

}
