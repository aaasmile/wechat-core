package com.d1m.wechat.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Template {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 微信模板ID
	 */
	@Column(name = "template_id")
	private String templateId;

	/**
	 * 模板标题
	 */
	private String title;

	/**
	 * 模板所属行业的一级行业
	 */
	@Column(name = "primary_industry")
	private String primaryIndustry;

	/**
	 * 模板所属行业的二级行业
	 */
	@Column(name = "deputy_industry")
	private String deputyIndustry;

	/**
	 * 模板参数
	 */
	private String parameters;

	/**
	 * 公众号ID
	 */
	@Column(name = "wechat_id")
	private Integer wechatId;

	/**
	 * 模板使用状态（0-停用，1-使用）
	 */
	private Byte status;

	/**
	 * 模板内容
	 */
	private String content;

	/**
	 * 模板示例
	 */
	private String example;

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取微信模板ID
	 *
	 * @return template_id - 微信模板ID
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * 设置微信模板ID
	 *
	 * @param templateId
	 *            微信模板ID
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId == null ? null : templateId.trim();
	}

	/**
	 * 获取模板标题
	 *
	 * @return title - 模板标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置模板标题
	 *
	 * @param title
	 *            模板标题
	 */
	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	/**
	 * 获取模板所属行业的一级行业
	 *
	 * @return primary_industry - 模板所属行业的一级行业
	 */
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}

	/**
	 * 设置模板所属行业的一级行业
	 *
	 * @param primaryIndustry
	 *            模板所属行业的一级行业
	 */
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry == null ? null : primaryIndustry
				.trim();
	}

	/**
	 * 获取模板所属行业的二级行业
	 *
	 * @return deputy_industry - 模板所属行业的二级行业
	 */
	public String getDeputyIndustry() {
		return deputyIndustry;
	}

	/**
	 * 设置模板所属行业的二级行业
	 *
	 * @param deputyIndustry
	 *            模板所属行业的二级行业
	 */
	public void setDeputyIndustry(String deputyIndustry) {
		this.deputyIndustry = deputyIndustry == null ? null : deputyIndustry
				.trim();
	}

	/**
	 * 获取模板参数
	 *
	 * @return parameters - 模板参数
	 */
	public String getParameters() {
		return parameters;
	}

	/**
	 * 设置模板参数
	 *
	 * @param parameters
	 *            模板参数
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters == null ? null : parameters.trim();
	}

	/**
	 * 获取公众号ID
	 *
	 * @return wechat_id - 公众号ID
	 */
	public Integer getWechatId() {
		return wechatId;
	}

	/**
	 * 设置公众号ID
	 *
	 * @param wechatId
	 *            公众号ID
	 */
	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	/**
	 * 获取模板使用状态（0-停用，1-使用）
	 *
	 * @return status - 模板使用状态（0-停用，1-使用）
	 */
	public Byte getStatus() {
		return status;
	}

	/**
	 * 设置模板使用状态（0-停用，1-使用）
	 *
	 * @param status
	 *            模板使用状态（0-停用，1-使用）
	 */
	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
	 * 获取模板内容
	 *
	 * @return content - 模板内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置模板内容
	 *
	 * @param content
	 *            模板内容
	 */
	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	/**
	 * 获取模板示例
	 *
	 * @return example - 模板示例
	 */
	public String getExample() {
		return example;
	}

	/**
	 * 设置模板示例
	 *
	 * @param example
	 *            模板示例
	 */
	public void setExample(String example) {
		this.example = example;
	}

}