package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;

/**
 * 导师申请表
 * @author Administrator
 *
 */
@Entity("mentorapplication")
public class MentorApplication implements Serializable{

	//id
	private Integer id;
	//名字
	private String name;
	//性别
	private String gender;
	//出生日期
	private String birthday;
	//毕业院校
	private String university;
	//专业
	private String majors;
	//学历
	private String educationalBackground;
	//办公电话
	private String officePhone;
	//手机
	private String phone;
	//邮箱或者qq
	private String mailorqq;
	//单位名称
	private String unit;
	//职务
	private String duty;
	//行业
	private String industry;
	//居住地
	private String address;
	//创业就业指导课程
	private String courses;
	//公司详细介绍
	private String introduce;
	//个人介绍
	private String resume;
	//照片
	private String photographs;
	//登记时间
	private Date time;
	
	
	public String getEducationalBackground() {
		return educationalBackground;
	}
	public void setEducationalBackground(String educationalBackground) {
		this.educationalBackground = educationalBackground;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getMajors() {
		return majors;
	}
	public void setMajors(String majors) {
		this.majors = majors;
	}
	
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMailorqq() {
		return mailorqq;
	}
	public void setMailorqq(String mailorqq) {
		this.mailorqq = mailorqq;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCourses() {
		return courses;
	}
	public void setCourses(String courses) {
		this.courses = courses;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getPhotographs() {
		return photographs;
	}
	public void setPhotographs(String photographs) {
		this.photographs = photographs;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
}
