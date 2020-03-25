package com.kframe.bean;

public class PageInfo<T> {

	private int page = 0;

	private int size = 10;

	private Sort asc;

	private Sort desc;

	private T bean;
	
	public T getBean() {
		return bean;
	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	public Sort getAsc() {
		return asc;
	}

	public void setAsc(Sort asc) {
		this.asc = asc;
	}

	public Sort getDesc() {
		return desc;
	}

	public void setDesc(Sort desc) {
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
	 * @return
	 */
	public org.springframework.data.domain.Sort fetchQuerySorts() {
		org.springframework.data.domain.Sort sort = null;
		if (asc != null)
			sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC,
					asc.fields);
		if (desc != null) {
			if (sort == null) {
				sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC,
						desc.fields);
				return sort;
			}
			return sort.and(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC,
					desc.fields));
		}
		return null;
	}

	/**
	 * 排序字段
	 * 
	 * @author fk
	 *
	 */
	public static class Sort {

		private String[] fields;


		public String[] getFields() {
			return fields;
		}

		public void setFields(String[] fields) {
			this.fields = fields;
		}
	}

}
