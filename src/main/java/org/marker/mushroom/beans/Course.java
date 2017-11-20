package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 社工学苑课程发布
 * Created by 30 on 2017/9/15.
 */
@Entity("course")
public class Course implements Serializable {

	private Integer id;
	private String name;
	private String address;
	private String content;
	private Integer total;
	private Integer reported;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	private Integer status;
	private Date time;
	private String auditUser;
	private Date auditTime;
	/** 特邀嘉宾 */
	private String guest;
	/** 嘉宾简介 */
	private String brief;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Integer getReported() {
		return reported;
	}

	public void setReported(Integer reported) {
		this.reported = reported;
	}

	public String getGuest() {
		return guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
}
