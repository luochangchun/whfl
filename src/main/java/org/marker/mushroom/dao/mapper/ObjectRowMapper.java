package org.marker.mushroom.dao.mapper;

import org.marker.mushroom.beans.Category;
import org.marker.mushroom.beans.Channel;
import org.marker.mushroom.beans.Course;
import org.marker.mushroom.beans.CourseReport;
import org.marker.mushroom.beans.Menu;
import org.marker.mushroom.beans.Model;
import org.marker.mushroom.beans.Module;
import org.marker.mushroom.beans.Permission;
import org.marker.mushroom.beans.Plugin;
import org.marker.mushroom.beans.User;
import org.marker.mushroom.beans.UserGroup;
import org.marker.mushroom.beans.UserObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 数据库查询结果集映射对象处理
 * 
 * 注意：这样做的目的是为了简化Dao实现的代码。而static final，这个是Spring3.2官方文档推荐使用的。
 * 
 * @author marker
 */
public final class ObjectRowMapper
{

	// 栏目RowMapper
	public static final class RowMapperChannel implements RowMapper<Channel>
	{
		public Channel mapRow(final ResultSet rs, final int arg1) throws SQLException
		{
			final Channel channel = new Channel();
			channel.setId(rs.getInt("id"));
			channel.setPid(rs.getLong("pid"));
			channel.setName(rs.getString("name"));
			channel.setTemplate(rs.getString("template"));
			channel.setUrl(rs.getString("url"));// URL地址
			channel.setRows(rs.getInt("rows"));// 分页条数目
			channel.setIcon(rs.getString("icon"));// 图标
			channel.setKeywords(rs.getString("keywords"));
			channel.setDescription(rs.getString("description"));
			channel.setRedirect(rs.getString("redirect"));// 重定向地址
			channel.setHide(rs.getShort("hide"));
			channel.setLangkey(rs.getString("langkey"));// 国际化
			channel.setCid(rs.getString("cid")); //分类id
			return channel;
		}
	}


	// 菜单RowMapper
	public static final class RowMapperMenu implements RowMapper<Menu>
	{
		public Menu mapRow(final ResultSet rs, final int arg1) throws SQLException
		{
			final Menu menu = new Menu();
			menu.setId(rs.getInt("id"));
			menu.setPid(rs.getInt("pid"));
			menu.setName(rs.getString("name"));
			menu.setIcon(rs.getString("icon"));
			menu.setSort(rs.getInt("sort"));
			menu.setUrl(rs.getString("url"));
			return menu;
		}
	}


	// 用户 RowMapper
	public static final class RowMapperUser implements RowMapper<User>
	{
		public User mapRow(final ResultSet rs, final int num) throws SQLException
		{
			final User user = new User();
			user.setId(rs.getInt("id"));
			user.setUuid(rs.getString("uuid"));
			user.setName(rs.getString("name"));
			user.setNickname(rs.getString("nickname"));
			user.setLogintime(rs.getDate("logintime"));
			user.setGid(Integer.valueOf(rs.getInt("gid")));// 分组ID
			user.setStatus(rs.getShort("status"));
			return user;
		}
	}


	// 插件 RowMapper
	public static final class RowMapperPlugin implements RowMapper<Plugin>
	{
		public Plugin mapRow(final ResultSet rs, final int num) throws SQLException
		{
			final Plugin plugin = new Plugin();
			plugin.setId(rs.getInt("id"));
			plugin.setName(rs.getString("name"));
			plugin.setUri(rs.getString("uri"));
			plugin.setMark(rs.getString("mark"));
			plugin.setStatus(rs.getInt("status"));
			plugin.setDescription(rs.getString("description"));
			return plugin;
		}
	}


	// 内容模型 RowMapper
	public static final class RowMapperModule implements RowMapper<Module>
	{
		public Module mapRow(final ResultSet rs, final int num) throws SQLException
		{
			final Module module = new Module();
			module.setId(rs.getLong("id"));
			module.setName(rs.getString("name"));
			module.setUri(rs.getString("uri"));
			module.setType(rs.getString("type"));
			module.setTemplate(rs.getString("template"));
			module.setVersion(rs.getInt("version"));
			module.setModule(rs.getString("module"));
			return module;
		}
	}

	// 权限 RowMapper
	public static final class RowMapperPermission implements RowMapper<Permission>
	{
		public Permission mapRow(final ResultSet rs, final int rowNum) throws SQLException
		{
			final Permission permission = new Permission();
			permission.setGid(rs.getInt("gid"));
			permission.setMid(rs.getInt("mid"));
			return permission;
		}

	}

	public static final class RowMapperUserObject implements RowMapper<UserObject>
	{
		public UserObject mapRow(final ResultSet rs, final int rowNum) throws SQLException
		{
			final UserObject object = new UserObject();
			object.setGid(rs.getInt("gid"));
			object.setOid(rs.getInt("oid"));
			object.setType(rs.getString("type"));
			return object;
		}

	}

	// 用户分组 RowMapper
	public static final class RowMapperUserGroup implements RowMapper<UserGroup>
	{
		public UserGroup mapRow(final ResultSet rs, final int rowNum) throws SQLException
		{
			final UserGroup group = new UserGroup();
			group.setId(rs.getInt("id"));
			group.setName(rs.getString("name"));
			group.setScope(rs.getInt("scope"));
			group.setDescription(rs.getString("description"));
			return group;
		}

	}


	// 内容模型rowmapper
	public static final class RowMapperModel implements RowMapper<Model>
	{

		public Model mapRow(final ResultSet rs, final int arg1) throws SQLException
		{
			final Model model = new Model();

			model.setId(rs.getInt("id"));
			model.setName(rs.getString("name"));
			model.setIcon(rs.getString("icon"));
			model.setVersion(rs.getString("version"));
			model.setTemplate(rs.getString("template"));
			model.setType(rs.getString("type"));
			model.setAuthor(rs.getString("author"));
			model.setTime(rs.getTimestamp("time"));
			model.setModule(rs.getString("module"));

			return model;
		}

	}




	// 分类(Category)
	public static final class RowMapperCategory implements RowMapper<Category>
	{

		public Category mapRow(final ResultSet rs, final int arg1) throws SQLException
		{
			final Category categroy = new Category();
			categroy.setId(rs.getInt("id"));
			categroy.setName(rs.getString("name"));
			categroy.setPid(rs.getInt("pid"));
			categroy.setRoot(rs.getInt("root"));
			categroy.setAlias(rs.getString("alias"));
			categroy.setSort(rs.getInt("sort"));
			categroy.setDescription(rs.getString("description"));
			categroy.setType(rs.getString("type"));
			categroy.setModel(rs.getString("model"));
			return categroy;
		}

	}

	// 分类(Course)
	public static final class RowMapperCourse implements RowMapper<Course> {

		public Course mapRow(final ResultSet rs, final int arg1) throws SQLException {
			final Course course = new Course();
			course.setId(rs.getInt("id"));
			course.setName(rs.getString("name"));
			course.setAddress(rs.getString("address"));
			course.setContent(rs.getString("content"));
			course.setStartTime(rs.getDate("startTime"));
			course.setStatus(rs.getInt("status"));
			course.setTotal(rs.getInt("total"));
			course.setReported(rs.getInt("reported"));
			course.setTime(rs.getTimestamp("time"));
			course.setAuditUser(rs.getString("auditUser"));
			course.setAuditTime(rs.getTimestamp("auditTime"));
			course.setGuest(rs.getString("guest"));
			course.setBrief(rs.getString("brief"));
			return course;
		}
	}

	// 分类(CourseReport)
	public static final class RowMapperCourseReport implements RowMapper<CourseReport> {

		public CourseReport mapRow(final ResultSet rs, final int arg1) throws SQLException {
			final CourseReport report = new CourseReport();
			report.setId(rs.getInt("id"));
			report.setName(rs.getString("name"));
			report.setOrganization(rs.getString("organization"));
			report.setPhone(rs.getString("phone"));
			report.setRemark(rs.getString("remark"));
			report.setQq(rs.getString("qq"));
			report.setAccepted(rs.getInt("accepted"));
			report.setCourseId(rs.getInt("courseId"));
			report.setTime(rs.getTimestamp("time"));
			return report;
		}
	}
}
