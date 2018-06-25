package com.steward.commons;

import java.util.List;

/**
 * 分页
 * @author mastercheng
 *
 * @param <T>
 */
public class Page<T> {
	
	/**
	 * 总条目数
	 */
	private Integer totalRecords;
	
	/**
	 * 总页数
	 */
	private Integer totalPages;
	
	/**
	 * 每页显示多少
	 */
	private Integer pageSize;
	
	/**
	 * 当前页
	 */
	private Integer currentPage;
	
	/**
	 * 当前页的数据
	 */
	private List<T> records;
	
	/**
	 * 是否有下一页
	 */
	private Boolean hasNext;
	
	/**
	 * 是否有上一页
	 */
	private Boolean hasPrev;

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public Boolean getHasPrev() {
		return hasPrev;
	}

	public void setHasPrev(Boolean hasPrev) {
		this.hasPrev = hasPrev;
	}

	public Page(Integer totalRecords, Integer pageSize, Integer currentPage, List<T> records) {
		super();
		this.totalRecords = totalRecords;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
		this.records = records;
		this.totalPages = totalRecords%pageSize>0?totalRecords/pageSize+1:totalRecords/pageSize;
		if (this.totalPages==currentPage) {
			this.hasNext = false;
			if (totalPages == 1 || totalPages == 0) {
				this.hasPrev = false;
			}else{
				this.hasPrev = true;
			}
		}else if (currentPage < totalPages) {
			this.hasNext = true;
			if (currentPage == 1) {
				this.hasPrev = false;
			}else{
				this.hasPrev = true;
			}
		}else if (totalPages == 1) {
			this.hasNext = false;
			this.hasPrev = false;
		}
	}

	public Page() {
		super();
	}
	

}
