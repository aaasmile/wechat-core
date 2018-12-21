package com.d1m.wechat.model;

import java.util.List;

public class News {

	private List<Articles> articles;

	public List<Articles> getArticles() {
		return articles;
	}

	public void setArticles(List<Articles> articles) {
		this.articles = articles;
	}

	public News(List<Articles> articles) {
		super();
		this.articles = articles;
	}

	public News() {
		super();
	}

	public static class Builder {
		private List<Articles> articles;

		public Builder articles(List<Articles> articles) {
			this.articles = articles;
			return this;
		}

		public News build() {
			return new News(this);
		}
	}

	private News(Builder builder) {
		this.articles = builder.articles;
	}
}
