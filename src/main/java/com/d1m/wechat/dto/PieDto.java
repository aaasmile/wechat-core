package com.d1m.wechat.dto;

import java.util.List;

public class PieDto {

	/**sex,lang,area*/
	private String type;
	private List<PieBaseDto> list;
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the list
	 */
	public List<PieBaseDto> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<PieBaseDto> list) {
		this.list = list;
	}

	
	
}
