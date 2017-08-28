package com.d1m.wechat.dto;

public class ReportMessageItemDto {
	
	private String date;
	private int text;
	private int image;
	private int voice;
	private int video;
	private int imagetext;
	private int sendTimes;
	private String time;
	private int shortvideo;
	private int location;
	private int link;
	private int music;
	private int scan;
	private int click;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getText() {
		return text;
	}
	public void setText(int text) {
		this.text = text;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getVoice() {
		return voice;
	}
	public void setVoice(int voice) {
		this.voice = voice;
	}
	public int getVideo() {
		return video;
	}
	public void setVideo(int video) {
		this.video = video;
	}
	public int getImagetext() {
		return imagetext;
	}
	public void setImagetext(int imagetext) {
		this.imagetext = imagetext;
	}
	public int getSendTimes() {
		return sendTimes;
	}
	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getShortvideo() {
		return shortvideo;
	}
	public int getLocation() {
		return location;
	}
	public int getLink() {
		return link;
	}
	public int getMusic() {
		return music;
	}
	public void setShortvideo(int shortvideo) {
		this.shortvideo = shortvideo;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public void setLink(int link) {
		this.link = link;
	}
	public void setMusic(int music) {
		this.music = music;
	}
	public int getScan() {
		return scan;
	}
	public int getClick() {
		return click;
	}
	public void setScan(int scan) {
		this.scan = scan;
	}
	public void setClick(int click) {
		this.click = click;
	}
	
}
