package com.d1m.wechat.dto;

public class ReportQrcodeItemDto {

	private String date;
	/**已关注用户扫码数*/
//	private int attentionScanNum;
	private String qrCodeName;
	private String qrCodeType;
	/**扫描数*/
	private int scanNum;
	/**扫描关注用户数*/
	private int attentionByScan;
	private String scene;
	/**扫码用户数*/
	private int userByScan;
	private String qrCodeParentType;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
//	public int getAttentionScanNum() {
//		return attentionScanNum;
//	}
//	public void setAttentionScanNum(int attentionScanNum) {
//		this.attentionScanNum = attentionScanNum;
//	}
	public String getQrCodeName() {
		return qrCodeName;
	}
	public void setQrCodeName(String qrCodeName) {
		this.qrCodeName = qrCodeName;
	}
	public String getQrCodeType() {
		return qrCodeType;
	}
	public void setQrCodeType(String qrCodeType) {
		this.qrCodeType = qrCodeType;
	}
	public int getScanNum() {
		return scanNum;
	}
	public void setScanNum(int scanNum) {
		this.scanNum = scanNum;
	}
	public int getAttentionByScan() {
		return attentionByScan;
	}
	public void setAttentionByScan(int attentionByScan) {
		this.attentionByScan = attentionByScan;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	public int getUserByScan() {
		return userByScan;
	}
	public void setUserByScan(int userByScan) {
		this.userByScan = userByScan;
	}
	public String getQrCodeParentType() {
		return qrCodeParentType;
	}
	public void setQrCodeParentType(String qrCodeParentType) {
		this.qrCodeParentType = qrCodeParentType;
	}
	
}
