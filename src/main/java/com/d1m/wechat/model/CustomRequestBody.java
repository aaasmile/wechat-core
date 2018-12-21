package com.d1m.wechat.model;

public class CustomRequestBody {

	private String touser;
	private String msgtype;
	private Media image;
	private Media voice;
	private Media mpnews;
	private Video video;
	private Music music;
	private Wxcard wxcard;
	private News news;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Media getImage() {
		return image;
	}

	public void setImage(Media image) {
		this.image = image;
	}

	public Media getVoice() {
		return voice;
	}

	public void setVoice(Media voice) {
		this.voice = voice;
	}

	public Media getMpnews() {
		return mpnews;
	}

	public void setMpnews(Media mpnews) {
		this.mpnews = mpnews;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public Wxcard getWxcard() {
		return wxcard;
	}

	public void setWxcard(Wxcard wxcard) {
		this.wxcard = wxcard;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public CustomRequestBody(String touser, String msgtype, Media image, Media voice, Media mpnews, Video video, Music music, Wxcard wxcard, News news) {
		super();
		this.touser = touser;
		this.msgtype = msgtype;
		this.image = image;
		this.voice = voice;
		this.mpnews = mpnews;
		this.video = video;
		this.music = music;
		this.wxcard = wxcard;
		this.news = news;
	}

	public CustomRequestBody() {
		super();
	}

	public static class Builder {
		private String touser;
		private String msgtype;
		private Media image;
		private Media voice;
		private Media mpnews;
		private Video video;
		private Music music;
		private Wxcard wxcard;
		private News news;

		public Builder touser(String touser) {
			this.touser = touser;
			return this;
		}

		public Builder msgtype(String msgtype) {
			this.msgtype = msgtype;
			return this;
		}

		public Builder image(Media image) {
			this.image = image;
			return this;
		}

		public Builder voice(Media voice) {
			this.voice = voice;
			return this;
		}

		public Builder mpnews(Media mpnews) {
			this.mpnews = mpnews;
			return this;
		}

		public Builder video(Video video) {
			this.video = video;
			return this;
		}

		public Builder music(Music music) {
			this.music = music;
			return this;
		}

		public Builder wxcard(Wxcard wxcard) {
			this.wxcard = wxcard;
			return this;
		}

		public Builder news(News news) {
			this.news = news;
			return this;
		}

		public CustomRequestBody build() {
			return new CustomRequestBody(this);
		}
	}

	public CustomRequestBody(Builder builder) {
		this.touser = builder.touser;
		this.msgtype = builder.msgtype;
		this.image = builder.image;
		this.voice = builder.voice;
		this.mpnews = builder.mpnews;
		this.video = builder.video;
		this.music = builder.music;
		this.wxcard = builder.wxcard;
		this.news = builder.news;
	}
}
