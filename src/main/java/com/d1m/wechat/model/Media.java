package com.d1m.wechat.model;

public class Media {

	private String media_id;

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	@Override
	public String toString() {
		return "Media [media_id=" + media_id + "]";
	}

	public static class Builder {
		private String media_id;

		public Builder media_id(String media_id) {
			this.media_id = media_id;
			return this;
		}

		public Media build() {
			return new Media(this);
		}
	}

	private Media(Builder builder) {
		this.media_id = builder.media_id;
	}

	public Media(String media_id) {
		super();
		this.media_id = media_id;
	}

	public Media() {
		super();
	}
}
