package com.d1m.wechat.pamametermodel;

public class BusinessListModel {
	Integer pageNum = 1;
	Integer pageSize = 10;
	String sortName;
	String sortDir = "desc";

	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the sortName
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * @param sortName
	 *            the sortName to set
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	/**
	 * @return the sortDir
	 */
	public String getSortDir() {
		return sortDir;
	}

	/**
	 * @param sortDir
	 *            the sortDir to set
	 */
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

}
