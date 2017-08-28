package com.d1m.wechat.controller.file;

public class Upload {

	private String fileName;

	private String newFileName;

	private String absolutePath;

	private String accessPath;

	public String getAbsolutePath() {
		return absolutePath;
	}

	public String getAccessPath() {
		return accessPath;
	}

	public String getFileName() {
		return fileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public void setAccessPath(String accessPath) {
		this.accessPath = accessPath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

}
