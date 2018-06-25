package com.steward.model.book;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3970114381674560511L;

	private String id;
	
	private String title;
	
	private String author;
	
	private String url;
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date adddate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}
	
	
	
}
