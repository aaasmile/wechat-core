package com.d1m.wechat.model;

public class Wxcard {

	private String card_id;

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	@Override
	public String toString() {
		return "Wxcard [card_id=" + card_id + "]";
	}

	public static class Builder {
		private String card_id;

		public Builder card_id(String card_id) {
			this.card_id = card_id;
			return this;
		}

		public Wxcard build() {
			return new Wxcard(this);
		}
	}

	private Wxcard(Builder builder) {
		this.card_id = builder.card_id;
	}
}
