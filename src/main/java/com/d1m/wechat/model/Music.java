package com.d1m.wechat.model;

public class Music {
	private String title;
	private String description;
	private String musicurl;
	private String hqmusicurl;
	private String thumb_media_id;

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

	public String getMusicurl() {
		return musicurl;
	}

	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}

	public String getHqmusicurl() {
		return hqmusicurl;
	}

	public void setHqmusicurl(String hqmusicurl) {
		this.hqmusicurl = hqmusicurl;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	@Override
	public String toString() {
		return "Music [title=" + title + ", description=" + description + ", musicurl=" + musicurl + ", hqmusicurl=" + hqmusicurl + ", thumb_media_id=" + thumb_media_id + "]";
	}

	public static class Builder {
		private String title;
		private String description;
		private String musicurl;
		private String hqmusicurl;
		private String thumb_media_id;

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder musicurl(String musicurl) {
			this.musicurl = musicurl;
			return this;
		}

		public Builder hqmusicurl(String hqmusicurl) {
			this.hqmusicurl = hqmusicurl;
			return this;
		}

		public Builder thumb_media_id(String thumb_media_id) {
			this.thumb_media_id = thumb_media_id;
			return this;
		}

		public Music build() {
			return new Music(this);
		}
	}

	private Music(Builder builder) {
		this.title = builder.title;
		this.description = builder.description;
		this.musicurl = builder.musicurl;
		this.hqmusicurl = builder.hqmusicurl;
		this.thumb_media_id = builder.thumb_media_id;
	}

	public Music(String title, String description, String musicurl, String hqmusicurl, String thumb_media_id) {
		super();
		this.title = title;
		this.description = description;
		this.musicurl = musicurl;
		this.hqmusicurl = hqmusicurl;
		this.thumb_media_id = thumb_media_id;
	}

	public Music() {
		super();
	}
}
