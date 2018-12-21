package com.d1m.wechat.model;
import java.util.List;

public class CustomRequestBody {

	private String touser;
	private String msgtype;
	private Media image;
	private Media voice;
	private Media mpnews;
	private Video video;
	private Music music;
	private List<Articles> articles;
	private Wxcard wxcard;

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

	public List<Articles> getArticles() {
		return articles;
	}

	public void setArticles(List<Articles> articles) {
		this.articles = articles;
	}

	public Wxcard getWxcard() {
		return wxcard;
	}

	public void setWxcard(Wxcard wxcard) {
		this.wxcard = wxcard;
	}

	@Override
	public String toString() {
		return "CustomRequestBody [touser=" + touser + ", msgtype=" + msgtype + ", image=" + image + ", voice=" + voice + ", mpnews=" + mpnews + ", video=" + video + ", music=" + music + ", articles=" + articles + ", wxcard=" + wxcard + "]";
	}

	public static class Builder {
		private String touser;
		private String msgtype;
		private Media image;
		private Media voice;
		private Media mpnews;
		private Video video;
		private Music music;
		private List<Articles> articles;
		private Wxcard wxcard;

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

		public Builder articles(List<Articles> articles) {
			this.articles = articles;
			return this;
		}

		public Builder wxcard(Wxcard wxcard) {
			this.wxcard = wxcard;
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
		this.articles = builder.articles;
		this.wxcard = builder.wxcard;
	}
}
