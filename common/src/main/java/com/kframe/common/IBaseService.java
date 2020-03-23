package com.kframe.common;

import org.springframework.data.domain.Page;

public interface IBaseService<T, ID> {

	
	Page<T> queryPage(PageInfo pageinfo, T t);
	
}
