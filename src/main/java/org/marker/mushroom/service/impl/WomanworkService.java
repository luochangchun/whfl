package org.marker.mushroom.service.impl;

import love.cq.util.StringUtil;
import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.Womanwork;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 女创登记表
 *
 * @author Administrator
 */
@Service(Services.WOMANWORK)
public class WomanworkService extends BaseService {

	@Autowired
	private ISupportDao commonDao;

	//申请操作
	@Transactional
	public ResultMessage createWomanwork(Womanwork womanwork) {
		String str = womanwork.getOfficePhone();
		if (!Pattern.compile("^[0-9]{8,11}$").matcher(str).matches()) {
			return new ResultMessage(false, "号码格式不正确！请重新输入！");
		}
		String str1 = womanwork.getMovePhone();
		if (!Pattern.compile("^[0-9]{8,11}$").matcher(str1).matches()) {
			return new ResultMessage(false, "号码格式不正确！请重新输入！");
		}

		if (StringUtil.isBlank(womanwork.getName())
			|| StringUtil.isBlank(womanwork.getGender())
			|| StringUtil.isBlank(womanwork.getBackground())
			|| StringUtil.isBlank(womanwork.getMail())
			|| StringUtil.isBlank(womanwork.getResume()))
			return new ResultMessage("资料不完善，请填写完整信息！");

		womanwork.setTime(new Date());
		if (commonDao.save(womanwork)) {
			return new ResultMessage(true, "申请成功！");
		} else {
			return new ResultMessage(false, "申请失败，请重新申请！");
		}
	}

	//后台展示
	public Object findByPage(int currentPageNo, int pageSize) {
		final String sql =
			"select id,name,gender,officePhone,mail,home,time" + " from " + config.getPrefix() + "womanwork order by time desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	//删除操作
	public boolean WomanworkDelete(String rid) {
		boolean deleteByIds = commonDao.deleteByIds(Womanwork.class, rid);
		return deleteByIds;

	}

	//点击详情
	public Map<String, Object> tenancyDetail(int id) {
		Map<String, Object> findById = commonDao.findById(Womanwork.class, id);
		return findById;
	}

}
