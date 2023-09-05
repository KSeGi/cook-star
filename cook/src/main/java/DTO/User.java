package DTO;

public class User {
	private int u_no;
	private String u_id;
	private String u_pw;
	private String u_name;
	private String u_nik;
	private String u_tel;
	private String first;
	private String second;
	private String third;
	private int u_grade;
	private String u_joindate;
	
	public int getU_no() {
		return u_no;
	}
	public void setU_no(int u_no) {
		this.u_no = u_no;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_pw() {
		return u_pw;
	}
	public void setU_pw(String u_pw) {
		this.u_pw = u_pw;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_nik() {
		return u_nik;
	}
	public void setU_nik(String u_nik) {
		this.u_nik = u_nik;
	}

	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getThird() {
		return third;
	}
	public void setThird(String third) {
		this.third = third;
		setU_tel(first+"-"+second+"-"+third);
	}
	public String getU_tel() {
		return u_tel;
	}
	public void setU_tel(String u_tel) {
		this.u_tel = u_tel;
	}
	public int getU_grade() {
		return u_grade;
	}
	public void setU_grade(int u_grade) {
		this.u_grade = u_grade;
	}
	public String getU_joindate() {
		return u_joindate;
	}
	public void setU_joindate(String u_joindate) {
		this.u_joindate = u_joindate;
	}

}
