package com.d1m.wechat.model;

public class Articles {

	private String title;
	private String description;
	private String url;
	private String picurl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	@Override
	public String toString() {
		return "Articles [title=" + title + ", description=" + description + ", url=" + url + ", picurl=" + picurl + "]";
	}

	public static class Builder {
		private String title;
		private String description;
		private String url;
		private String picurl;

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public Builder picurl(String picurl) {
			this.picurl = picurl;
			return this;
		}

		public Articles build() {
			return new Articles(this);
		}
	}

	private Articles(Builder builder) {
		this.title = builder.title;
		this.description = builder.description;
		this.url = builder.url;
		this.picurl = builder.picurl;
	}

	public Articles(String title, String description, String url, String picurl) {
		super();
		this.title = title;
		this.description = description;
		this.url = url;
		this.picurl = picurl;
	}

	public Articles() {
		super();
	}
}
