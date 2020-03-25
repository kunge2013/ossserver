package com.kframe.bean;

import java.util.List;

import com.kframe.bean.PageInfo.SortField;

/**
 * 分页对象
 * 
 * @author fk
 * @param <T>
 */
public class PageData<T> {

	private int page = 0;

	private int size = 10;

	private SortField asc;

	private SortField desc;

	private List<T> rows;

	private long total;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public SortField getAsc() {
		return asc;
	}

	public void setAsc(SortField asc) {
		this.asc = asc;
	}

	public SortField getDesc() {
		return desc;
	}

	public void setDesc(SortField desc) {
		this.desc = desc;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * 分页查询结果
	 * 
	 * @param <T>
	 * @param pageInfo
	 * @param rows
	 * @param total
	 * @return
	 */
	public static <T> PageData<T> from(PageInfo<T> pageInfo, List<T> rows, long total) {
		PageData<T> pageData = new PageData<T>();
		pageData.setAsc(pageInfo.getAsc());
		pageData.setDesc(pageInfo.getDesc());
		pageData.setPage(pageInfo.getPage());
		pageData.setSize(pageInfo.getSize());
		pageData.setRows(rows);
		pageData.setTotal(total);
		return pageData;
	}

}
