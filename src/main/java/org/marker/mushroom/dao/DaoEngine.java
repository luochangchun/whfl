package org.marker.mushroom.dao;

import org.apache.poi.ss.formula.functions.T;
import org.marker.mushroom.alias.LOG;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.dao.annotation.Entity;
import org.marker.mushroom.utils.SQLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 数据库操作对象引擎 实现：基于Spring的JDBCTemplate开发的通用对象。 目的：简化数据库代码 最新采用slf4j日志接口
 * 
 * @author marker
 * @date 2014-08-10
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public abstract class DaoEngine implements ISupportDao
{

	/** 日志记录器 */
	protected Logger logger = LoggerFactory.getLogger(LOG.DAOENGINE);

	/** 数据库配置 */
	protected static final DataBaseConfig dbConfig = DataBaseConfig.getInstance();



	/** 自动注入jdbc模板操作 */
	@Autowired
	protected JdbcTemplate jdbcTemplate;


	/** 基本信息缓存 */
	private static final EntityInfoCache cache = new EntityInfoCache();





	/**
	 * 泛型构造方法目的是为了简化实现
	 * 
	 * @param t
	 */
	public DaoEngine()
	{
	}


	/*
	 * ================================================================== 数据查询接口实现
	 * ==================================================================
	 */

	// 查询单个对象实现
	@Override
	public Map<String, Object> queryForMap(final String sql, final Object... args)
	{
		logger(sql);
		return jdbcTemplate.queryForMap(sql, args);
	}


	// 查询对象集合实现
	@Override
	public List<Map<String, Object>> queryForList(final String sql, final Object... args)
	{
		logger(sql);
		return jdbcTemplate.queryForList(sql, args);
	}

	// 查询对象 
	@Override
	public <T> T queryForObject(final String sql, final Class<T> clzz, final Object... args)
	{
		logger(sql);
		return jdbcTemplate.queryForObject(sql, clzz, args);
	}





	/*
	 * ================================================================== 数据更新接口实现
	 * ==================================================================
	 */

	// 批量更新
	@Override
	public int[] batchUpdate(final String sql, final List<Object[]> batchArgs)
	{
		logger(sql);
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	// 更新数据
	@Override
	public boolean update(final String sql, final Object... args)
	{
		logger(sql);
		return jdbcTemplate.update(sql, args) > 0 ? true : false;
	}





	/*
	 * ================================================================== 数据删除接口实现
	 * ==================================================================
	 */

	// 批量删除
	@Override
	public boolean deleteByIds(final Class<?> clzz, final String ids)
	{
		final String prefix = dbConfig.getPrefix();// 表前缀

		MapConfig map = null;
		try
		{
			map = cache.getMapConfig(clzz);
		}
		catch (final Exception e1)
		{
			e1.printStackTrace();
		}



		final StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(prefix).append(map.getTableName()).append(" where ").append(map.getPrimaryKey())
				.append(" in(").append(ids).append(")");
		logger(sql.toString());
		return jdbcTemplate.update(sql.toString()) > 0 ? true : false;
	}




	// 批量删除
	@Override
	public boolean deleteByIds(final String tableName, final String key, final String ids)
	{
		final StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(tableName).append(" where ").append(key).append(" in(").append(ids).append(")");
		logger(sql.toString());
		return jdbcTemplate.update(sql.toString()) > 0 ? true : false;
	}



	/*
	 * ========================================= 根据ID查询 =========================================
	 */
	@Override
	public Map<String, Object> findById(final Class<?> clzz, final Serializable id)
	{
		final String prefix = dbConfig.getPrefix();// 表前缀 
		final String tableName = clzz.getAnnotation(Entity.class).value();
		final String primaryKey = clzz.getAnnotation(Entity.class).key();
		final StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(prefix).append(tableName).append(" where ").append(primaryKey).append("=?");
		return queryForMapNoException(sql.toString(), id);
	}

	// 无异常查询
	private Map<String, Object> queryForMapNoException(final String sql, final Object... args)
	{
		try
		{
			logger(sql);
			return jdbcTemplate.queryForMap(sql, args);
		}
		catch (final Exception e)
		{
		}
		return new HashMap<String, Object>();
	}


	// 分页查询
	@Override
	public Page findByPage(final int currentPageNo, final int pageSize, String sql, final Object... args)
	{

		final int beginPos = sql.toLowerCase().indexOf("from");
		final String hql4Count = "select count(*) " + sql.substring(beginPos);
		sql += " limit " + (currentPageNo - 1) * pageSize + "," + pageSize;

		// 获取总条数
		final int totalRows = jdbcTemplate.queryForObject(hql4Count, Integer.class, args);

		final List<Map<String, Object>> data = queryForList(sql, args);

		final Page page = new Page(currentPageNo, totalRows, pageSize);
		page.setData(data);// 获取数据集合 
		return page;
	}

	// 保存对象  
	public boolean save(final Object entity)
	{
		return save(entity, dbConfig.getPrefix());
	}

	/**
	 * 可以传入前缀
	 * 
	 * @param entity
	 * @param prefix
	 * @return
	 */
	public boolean save(final Object entity, final String prefix)
	{
		final Class<?> clzz = entity.getClass();

		MapConfig map = null;
		try
		{
			map = cache.getMapConfig(clzz);
		}
		catch (final Exception e1)
		{
			e1.printStackTrace();
		}

		// 构造SQL字符串
		final StringBuilder sql = new StringBuilder("insert into ");
		sql.append(prefix).append(map.getTableName()).append("(");
		final StringBuilder val = new StringBuilder(" values(");
		final Field[] fields = clzz.getDeclaredFields();
		final int length = fields.length;
		final List<Object> list = new ArrayList<Object>(length);
		for (int i = 0; i < length; i++)
		{
			final Field field = fields[i];
			final String fieldName = field.getName();
			if (fieldName.equals(map.getPrimaryKey()))
			{// 如果是主键
				continue;
			}
			if ("serialVersionUID".equals(fieldName))
			{
				continue;
			}
			final String methodName = "get"
					+ fieldName.replaceFirst(fieldName.charAt(0) + "", (char) (fieldName.charAt(0) - 32) + "");

			Method me = null;
			try
			{
				me = clzz.getMethod(methodName);
			}
			catch (final SecurityException e)
			{
				e.printStackTrace();
			}
			catch (final NoSuchMethodException e)
			{
				e.printStackTrace();
			}

			Object returnObject = null;
			try
			{
				returnObject = me.invoke(entity);
			}
			catch (final IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (final IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (final InvocationTargetException e)
			{
				e.printStackTrace();
			} //
			if (returnObject != null)
			{// 如果返回值为null
				sql.append("`" + fieldName + "`");
				val.append("?");
				list.add(returnObject);
				sql.append(",");
				val.append(",");
			}
		}

		final StringBuilder sql2 = new StringBuilder(sql.substring(0, sql.length() - 1));
		final StringBuilder val2 = new StringBuilder(val.substring(0, val.length() - 1));


		sql2.append(")").append(val2).append(")");
		final KeyHolder holder = new GeneratedKeyHolder();


		this.jdbcTemplate.update(new PreparedStatementCreator()
		{

			@Override
			public PreparedStatement createPreparedStatement(final Connection con) throws SQLException
			{
				final PreparedStatement ps = con.prepareStatement(sql2.toString());
				final Object[] params = list.toArray();
				for (int i = 0; i < params.length; i++)
				{
					ps.setObject(i + 1, params[i]);
				}
				return ps;
			}
		}, holder);



		try
		{
			final Method me = clzz.getMethod("setId", Integer.class);
			me.invoke(entity, holder.getKey().intValue());
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return true;
	}

	public boolean saveAll(final Object... entitys)
	{
		for (final Object entity : entitys)
		{
			save(entity);
		}
		return true;
	}

	// 更新数据
	public boolean update(final Object entity)
	{
		final String prefix = dbConfig.getPrefix();
		final Class<?> clzz = entity.getClass();
		final Entity tableInfo = clzz.getAnnotation(Entity.class);
		final String tableName = tableInfo.value();
		final String primaryKey = tableInfo.key();

		StringBuffer sql = new StringBuffer();
		sql.append("update `").append(prefix).append(tableName).append("` set ");
		List<Object> list = null;
		int id = 0;
		try
		{
			id = (Integer) clzz.getMethod("getId").invoke(entity);
			final Field[] fields = clzz.getDeclaredFields();
			final int length = fields.length;
			list = new ArrayList<Object>(length);
			for (int i = 0; i < length; i++)
			{
				final Field field = fields[i];
				final String fieldName = field.getName();
				if (fieldName.equals(primaryKey))
				{// 如果是主键
					continue;
				}
				if ("serialVersionUID".equals(fieldName))
				{
					continue;
				}
				final String methodName = "get"
						+ fieldName.replaceFirst(fieldName.charAt(0) + "", (char) (fieldName.charAt(0) - 32) + "");
				final Method me = clzz.getMethod(methodName);
				final Object returnObject = me.invoke(entity);
				if (returnObject != null)
				{// 如果返回值为null
					sql.append("`" + fieldName + "`").append("=?");
					list.add(returnObject);
					sql.append(",");
				}
			}
			sql = new StringBuffer(sql.substring(0, sql.length() - 1));
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		list.add(id);
		sql.append(" where `").append(primaryKey).append("`=?");



		return update(sql.toString(), list.toArray());

	}

	// 查询某页数据
	@Override
	public List<Map<String, Object>> queryFotList(final int currentPageNo, final int pageSize, String sql, final Object... args)
	{
		sql += " limit " + (currentPageNo - 1) * pageSize + "," + pageSize;
		logger(sql);
		return jdbcTemplate.queryForList(sql, args);
	}


	/**
	 * 获取数据库前缀
	 * 
	 * @return
	 */
	protected String getPreFix()
	{
		return dbConfig.getPrefix();
	}


	/**
	 * 日志打印方法 如果是数据库开发模式，方可打印日志
	 * 
	 * @param info
	 */
	protected void logger(String info, final Object... params)
	{
		if (dbConfig.isDebug())
		{// debug模式输入SQL语句
			info = SQLUtil.format(info);
			logger.info(info, params);
		}
	}

	@Override
	public void execute(final String sql)
	{
		jdbcTemplate.execute(sql);
	}

}
