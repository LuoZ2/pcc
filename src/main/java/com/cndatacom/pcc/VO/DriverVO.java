package com.cndatacom.pcc.VO;

import java.util.Date;

public class DriverVO {

	private String drId;
	private String drName;
	private String drTel;
	private String drLicense;
	private String drCarid;
	private String drCartype;
	private String drAddr;
	private String drPassword;
	private Integer drStatus;
	private Integer drNum;
	private Date created;
	private String userToken;
	public String getDrId() {
		return drId;
	}
	public void setDrId(String drId) {
		this.drId = drId;
	}
	public String getDrName() {
		return drName;
	}
	public void setDrName(String drName) {
		this.drName = drName;
	}
	public String getDrTel() {
		return drTel;
	}
	public void setDrTel(String drTel) {
		this.drTel = drTel;
	}
	public String getDrLicense() {
		return drLicense;
	}
	public void setDrLicense(String drLicense) {
		this.drLicense = drLicense;
	}
	public String getDrCarid() {
		return drCarid;
	}
	public void setDrCarid(String drCarid) {
		this.drCarid = drCarid;
	}
	public String getDrCartype() {
		return drCartype;
	}
	public void setDrCartype(String drCartype) {
		this.drCartype = drCartype;
	}
	public String getDrAddr() {
		return drAddr;
	}
	public void setDrAddr(String drAddr) {
		this.drAddr = drAddr;
	}
	public String getDrPassword() {
		return drPassword;
	}
	public void setDrPassword(String drPassword) {
		this.drPassword = drPassword;
	}
	public Integer getDrStatus() {
		return drStatus;
	}
	public void setDrStatus(Integer drStatus) {
		this.drStatus = drStatus;
	}
	public Integer getDrNum() {
		return drNum;
	}
	public void setDrNum(Integer drNum) {
		this.drNum = drNum;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
}
