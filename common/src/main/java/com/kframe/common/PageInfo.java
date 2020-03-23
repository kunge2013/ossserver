package com.kframe.common;

public class PageInfo {

	private int page = 1;

	private int size = 10;

	private Sort asc;

	private Sort desc;

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

		public static final String ASC_SORT = "ASC";

		public static final String DESC_SORT = "DESC";

		private String[] fields;

		private String direction = "asc";

		public boolean isAsc() {
			return ASC_SORT.equalsIgnoreCase(direction);
		}

		public String[] getFields() {
			return fields;
		}

		public void setFields(String[] fields) {
			this.fields = fields;
		}

		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}

	}

}
