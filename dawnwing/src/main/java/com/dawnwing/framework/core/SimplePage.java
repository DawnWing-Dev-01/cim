package com.dawnwing.framework.core;

import java.util.List;

/**
 * @author wxl
 * 
 * @param <T>
 */
public class SimplePage<T> {

	/**
	 * 下一页
	 */
	private int nextPage;

	/**
	 * 当前页
	 */
	private int nowPage;

	/**
	 * 每页个个数
	 */
	private int pageSize;

	/**
	 * 总记录数
	 */
	private int totalNum;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 记录集合
	 */
	private List<T> results;

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	/**
	 * 获取下一页
	 */
	public void next() {
		nextPage = nowPage + 1;
		totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum
				/ pageSize + 1;
	}
}
