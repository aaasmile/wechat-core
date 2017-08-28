package com.d1m.wechat.dto;

import java.util.List;

public class QrcodeTypeDto {
	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	private QrcodeTypeDto parent;

	private List<QrcodeTypeDto> children;

	public List<QrcodeTypeDto> getChildren() {
		return children;
	}

	/**
	 * 获取主键ID
	 *
	 * @return id - 主键ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 获取名称
	 *
	 * @return name - 名称
	 */
	public String getName() {
		return name;
	}

	public QrcodeTypeDto getParent() {
		return parent;
	}

	public void setChildren(List<QrcodeTypeDto> children) {
		this.children = children;
	}

	/**
	 * 设置主键ID
	 *
	 * @param id
	 *            主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 设置名称
	 *
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public void setParent(QrcodeTypeDto parent) {
		this.parent = parent;
	}

}
