package com.kframe.bean;

import static org.springframework.data.domain.Sort.*;

import org.springframework.data.domain.Sort;
/**
 * 分页查询条件
 * 
 * @author fk
 *
 * @param <T>
 */
public class PageInfo<T> {

	private int page = 0;

	private int size = 10;

	private SortField asc;

	private SortField desc;

	private T bean;

	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
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

	/**
	 * 获取排序字段
	 * 
	 * @return
	 */
	public Sort fetchQuerySorts() {
		Sort sort = null;
		if (asc != null) sort = by(Direction.ASC, asc.fields);
		if (desc != null) {
			if (sort == null) return by(Direction.DESC, desc.fields);
			return sort.and(by(Direction.DESC, desc.fields));
		}
		return null;
	}

	/**
	 * 排序字段
	 * 
	 * @author fk
	 *
	 */
	public static class SortField {

		private String[] fields;

		public String[] getFields() {
			return fields;
		}

		public void setFields(String[] fields) {
			this.fields = fields;
		}
	}

}
