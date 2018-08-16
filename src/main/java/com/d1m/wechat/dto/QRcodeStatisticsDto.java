package com.d1m.wechat.dto;

public class QRcodeStatisticsDto {
	/**
	 * 名称
	 */
	private String name;//名称
	/**
	 * 场景
	 */
	private String scene;//场景
	/**
	 * 扫码人数
	 */
	private String Scaveng;//扫码人数
	/**
	 * 扫码后关注人数
	 */
	private String ScavengSubscribe ;//扫码后关注人数
	/**
	 * 关注后扫码人数
	 */
	private String SubscribeScaveng;//关注后扫码人数
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	public String getScaveng() {
		return Scaveng;
	}
	public void setScaveng(String scaveng) {
		Scaveng = scaveng;
	}
	public String getScavengSubscribe() {
		return ScavengSubscribe;
	}
	public void setScavengSubscribe(String scavengSubscribe) {
		ScavengSubscribe = scavengSubscribe;
	}
	public String getSubscribeScaveng() {
		return SubscribeScaveng;
	}
	public void setSubscribeScaveng(String subscribeScaveng) {
		SubscribeScaveng = subscribeScaveng;
	}
	
	
}
