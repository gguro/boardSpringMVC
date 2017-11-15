package com.bug.dto;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotBlank;

public class BoardVO {
	/*
	 * @NotBlank("NotBlank.board.name") 형식이 정석이지만
	 * @NotBlank 하여도 스프링에서 message/messages.properties 에 정의된 값으로 에러메세지를 보여준다
	 * message/messages.properties 에 정의할때 형식에 맞게 넣어주어야 한다(주의)
	 */
	
	private int num;
//	@NotBlank("NotBlank.board.name")
	@NotBlank
	private String name;
	private String email;
//	@NotBlank("NotBlank.board.pass")
	@NotBlank
	private String pass;
//	@NotBlank("NotBlank.board.title")
	@NotBlank
	private String title;
	private String content;
	private int readcount;
	private Timestamp writedate;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public Timestamp getWritedate() {
		return writedate;
	}

	public void setWritedate(Timestamp writedate) {
		this.writedate = writedate;
	}

	@Override
	public String toString() {
		return "BoardVO [num=" + num + ", name=" + name + ", email=" + email + ", pass=" + pass + ", title=" + title
				+ ", content=" + content + ", readcount=" + readcount + ", writedate=" + writedate + "]";
	}

}
