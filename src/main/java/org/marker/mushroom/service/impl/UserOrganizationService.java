package org.marker.mushroom.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.*;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.marker.mushroom.utils.EncryptUtil;
import org.marker.mushroom.utils.GeneratePass;
import org.marker.mushroom.utils.GenerateUUID;
import org.marker.security.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户组织层处理
 * 
 * @author dd
 * @version 1.0
 */
@Service(Services.USERORGANIZATION)
@Transactional
public class UserOrganizationService extends BaseService
{

	@Autowired
	private ISupportDao commonDao;

	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = "select uo.*,o.name as orgName,u.name " + "from " + config.getPrefix() + "user_organization uo "
				+ "left join " + config.getPrefix() + "organization o on uo.orgUid=o.uuid " + "left join " + config.getPrefix()
				+ "user u on uo.uuid=u.uuid ";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	public List<Map<String, Object>> findById(final String ids)
	{
		final String sql = "select uo.* from " + config.getPrefix() + "user_organization uo where uo.id in (" + ids + ") ";

		return commonDao.queryForList(sql);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean createUser(final UserOrganization userOrganization)
	{
		final User user = new User();
		user.setUuid(GenerateUUID.getUUID());
		user.setName(userOrganization.getAccount());
		try
		{
			user.setPass(GeneratePass.encode(userOrganization.getPassword()));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		user.setCreatetime(new Date());
		if (userOrganization.getSettled() == 1)
		{
			user.setGid(120);
		}
		else if (userOrganization.getSettled() == 2)
		{
			user.setGid(149);
		}

		user.setStatus(0);

		final Organization organization = new Organization();

		organization.setUuid(GenerateUUID.getUUID());
		organization.setName(userOrganization.getName());
		if (userOrganization.getSettled() == 1)
		{
			organization.setCid(175);
		}
		else if (userOrganization.getSettled() == 2)
		{
			organization.setCid(227);
		}
		organization.setAuthor1(userOrganization.getAuthor1());
		organization.setPhone1(userOrganization.getPhone1());
		organization.setQq1(userOrganization.getQq1());
		organization.setAuthor2(userOrganization.getAuthor2());
		organization.setPhone2(userOrganization.getPhone2());
		organization.setQq2(userOrganization.getQq2());
		organization.setType(userOrganization.getType());

		if (organization.getType() == 3)
		{
			organization.setRegPlace(userOrganization.getRegPlace());
			organization.setFoundingTime(userOrganization.getFoundingTime());
			organization.setRegisterNum(userOrganization.getRegisterNum());
			organization.setIssAuthority(userOrganization.getIssAuthority());
			organization.setAddress(userOrganization.getAddress());
		}
		organization.setSettled(userOrganization.getSettled());
		organization.setKingdom(userOrganization.getKingdom());
		organization.setDemand(userOrganization.getDemand());
		organization.setPlan(userOrganization.getPlan());
		organization.setDescription(userOrganization.getDescription());
		organization.setTime(new Date());
		organization.setCreateTime(new Date());
		organization.setStatus(0);

		commonDao.save(organization);


		final User_Organization user_organization = new User_Organization();
		user_organization.setUuid(user.getUuid());
		user_organization.setOrguid(organization.getUuid());
		user_organization.setStatus(0);

		final Edu_User edu_user = new Edu_User();
		edu_user.setMobile(userOrganization.getPhone1());
		edu_user.setUser_Name(userOrganization.getAccount());
		edu_user.setPassword(MD5.getMD5Code(userOrganization.getPassword()));
		edu_user.setAes_password(EncryptUtil.getInstance().AESencode(userOrganization.getPassword(), EncryptUtil.KEY));
		edu_user.setShow_Name(edu_user.getUser_Name());
		edu_user.setCreate_Time(new Date());
		edu_user.setIs_avalible(0);
		edu_user.setMsg_Num(0);
		edu_user.setSys_Msg_Num(0);
		edu_user.setLast_System_Time(new Date());
		edu_user.setUuid(user.getUuid());

		commonDao.save(edu_user, "");

		return commonDao.saveAll(user, user_organization);

	}

	public boolean checkExists(final String userName)
	{
		final String sql = "select u.* from " + config.getPrefix() + "user u where u.name=? ";
		try
		{
			commonDao.queryForMap(sql, userName);
		}
		catch (final EmptyResultDataAccessException ex)
		{
			return true;
		}
		return false;
	}

	public boolean updateEduUserPass(final String pass, final String uuid)
	{
		final String sql = "update edu_user set password = ?, aes_password = ?,last_system_time = ? where uuid = ?";
		final String password = MD5.getMD5Code(pass);
		final String aesPassword = EncryptUtil.getInstance().AESencode(pass, EncryptUtil.KEY);

		return commonDao.update(sql, password, aesPassword, new Date(), uuid);
	}
}
