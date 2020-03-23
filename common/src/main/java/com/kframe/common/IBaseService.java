package com.kframe.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;

public interface IBaseService<T, ID> extends InitializingBean {

	
	default  void init() {};
	
	@Override
	default void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		init();
	}
	
	
	Page<T> queryPage(PageInfo pageinfo, T t);
}
