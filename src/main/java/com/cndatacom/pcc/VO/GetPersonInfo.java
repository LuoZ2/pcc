package com.cndatacom.pcc.VO;

import com.cndatacom.pcc.domain.Student;

public class GetPersonInfo {

	/**
	 * 学生信息
	 */
	private Student student;
	/**
	 * 是否为本人
	 */
	private boolean is_me;
	/**
	 * 是否有权限查看信息
	 */
	private boolean is_contact;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public boolean isIs_me() {
		return is_me;
	}
	public void setIs_me(boolean is_me) {
		this.is_me = is_me;
	}
	public boolean isIs_contact() {
		return is_contact;
	}
	public void setIs_contact(boolean is_contact) {
		this.is_contact = is_contact;
	}
	
}
