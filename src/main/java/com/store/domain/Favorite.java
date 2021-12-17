package com.store.domain;

import java.util.Date;

public class Favorite {
	private Long fid;
	private Long pid;//商品ID
	private String productName;//商品名称
	private Long uid;
	private Date favDate;
	public Long getFid() {
		return fid;
	}
	public void setFid(Long fid) {
		this.fid = fid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Date getFavDate() {
		return favDate;
	}
	public void setFavDate(Date favDate) {
		this.favDate = favDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
}
