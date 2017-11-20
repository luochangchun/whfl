package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Donation;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IDonationDao;
import org.springframework.stereotype.Repository;


@Repository(DAO.DONATION)
public class DonationDaoImpl extends DaoEngine implements IDonationDao
{

	@Override
	public boolean update(final Donation donation)
	{
		//		final String prefix = dbConfig.getPrefix();//获取数据库表前缀
		//
		//		final StringBuilder sql = new StringBuilder();
		//		sql.append("update ").append(prefix).append("article").append(
		//				" set cid=?, title=?,keywords=?,description=?,author=?, content=?,status=?,source=?,icon=?,time=?,reject=? where id=?");
		//
		//		final int status = jdbcTemplate.update(sql.toString(), a.getCid(), a.getTitle(), a.getKeywords(), a.getDescription(),
		//				a.getAuthor(), a.getContent(), a.getStatus(), a.getSource(), a.getIcon(), a.getTime(), a.getReject(), a.getId());
		//
		//		return status > 0 ? true : false;
		return false;
	}


}
