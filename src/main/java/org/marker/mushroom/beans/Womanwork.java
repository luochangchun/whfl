package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;
/**
 * 女性创业者登记表
 * @author Administrator
 *
 */
@Entity("womanwork")
public class Womanwork implements Serializable{


	//id
	private Integer id;
	//姓名
	private String name;
	//性别
	private String gender;
	//出生年月
	private String birth;
	//学历
	private String background;
	//专业
	private String majors;
	//党派
	private String party;
	//单位和职务
	private String unitandduty;
	//企业所属行业
	private String industry;
	//企业所在地
	private String home;
	//办公电话
	private String officePhone;
	//地址和邮编
	private String addressandpostcode;
	//移动电话
	private String movePhone;
	//电子邮箱
	private String mail;
	//企业基本情况
	private String introduce;
	//简历
	private String resume;
	//登记时间
	private Date time;
	
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
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getMajors() {
		return majors;
	}
	public void setMajors(String majors) {
		this.majors = majors;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public String getUnitandduty() {
		return unitandduty;
	}
	public void setUnitandduty(String unitandduty) {
		this.unitandduty = unitandduty;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getAddressandpostcode() {
		return addressandpostcode;
	}
	public void setAddressandpostcode(String addressandpostcode) {
		this.addressandpostcode = addressandpostcode;
	}
	public String getMovePhone() {
		return movePhone;
	}
	public void setMovePhone(String movePhone) {
		this.movePhone = movePhone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
