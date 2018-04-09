package com.d1m.wechat.customservice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CustomerServiceRepresentative
 *
 * @author f0rb on 2017-03-16.
 */
public class CustomerServiceRepresentative {

    private Integer id;

    private String photoUrl;

    private String nickname;

    private String account;

    private AtomicInteger currentCustomerCount = new AtomicInteger(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public AtomicInteger getCurrentCustomerCount() {
		return currentCustomerCount;
	}

	public void setCurrentCustomerCount(AtomicInteger currentCustomerCount) {
		this.currentCustomerCount = currentCustomerCount;
	}
    
}
