package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 学员
 * 
 * @author www.inxedu.com
 */
@Entity("edu_user")
public class Edu_User implements Serializable
{
	private static final long serialVersionUID = 1L;
	/** 学员ID */
	private int user_Id;
	/** 手机号 */
	private String mobile;
	/** 邮箱 */
	private String email;
	/** 密码 */
	private String password;
	/** 学员名 */
	private String user_Name;
	/** 显示名（昵称） */
	private String show_Name;
	/** 性别 1男 2女 */
	private int sex;
	/** 年龄 */
	private int age;
	/** 注册时间 */
	private Date create_Time;
	/** 状态 1正常 2冻结 */
	private int is_avalible;
	/** 用户头像 */
	private String pic_Img;
	/** 个人中心个性图片URL */
	private String banner_Url;
	/** 站内信未读消息数 */
	private int msg_Num;
	/** 系统自动消息未读消息数 */
	private int sys_Msg_Num;
	/** 上传统计系统消息时间 */
	private Date last_System_Time;
	/** AES加密密码 */
	private String aes_password;
	/** 积分 */
	private int points;

	private String uuid;

	public int getUser_Id()
	{
		return user_Id;
	}

	public void setUser_Id(final int user_Id)
	{
		this.user_Id = user_Id;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(final String mobile)
	{
		this.mobile = mobile;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public String getUser_Name()
	{
		return user_Name;
	}

	public void setUser_Name(final String user_Name)
	{
		this.user_Name = user_Name;
	}

	public String getShow_Name()
	{
		return show_Name;
	}

	public void setShow_Name(final String show_Name)
	{
		this.show_Name = show_Name;
	}

	public int getSex()
	{
		return sex;
	}

	public void setSex(final int sex)
	{
		this.sex = sex;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(final int age)
	{
		this.age = age;
	}

	public Date getCreate_Time()
	{
		return create_Time;
	}

	public void setCreate_Time(final Date create_Time)
	{
		this.create_Time = create_Time;
	}

	public int getIs_avalible()
	{
		return is_avalible;
	}

	public void setIs_avalible(final int is_avalible)
	{
		this.is_avalible = is_avalible;
	}

	public String getPic_Img()
	{
		return pic_Img;
	}

	public void setPic_Img(final String pic_Img)
	{
		this.pic_Img = pic_Img;
	}

	public String getBanner_Url()
	{
		return banner_Url;
	}

	public void setBanner_Url(final String banner_Url)
	{
		this.banner_Url = banner_Url;
	}

	public int getMsg_Num()
	{
		return msg_Num;
	}

	public void setMsg_Num(final int msg_Num)
	{
		this.msg_Num = msg_Num;
	}

	public int getSys_Msg_Num()
	{
		return sys_Msg_Num;
	}

	public void setSys_Msg_Num(final int sys_Msg_Num)
	{
		this.sys_Msg_Num = sys_Msg_Num;
	}

	public Date getLast_System_Time()
	{
		return last_System_Time;
	}

	public void setLast_System_Time(final Date last_System_Time)
	{
		this.last_System_Time = last_System_Time;
	}

	public String getAes_password()
	{
		return aes_password;
	}

	public void setAes_password(final String aes_password)
	{
		this.aes_password = aes_password;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(final int points)
	{
		this.points = points;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(final String uuid)
	{
		this.uuid = uuid;
	}
}
