package com.kframe.common;

import com.kframe.bean.PageData;
import com.kframe.bean.PageInfo;

public interface IBaseService<T, ID> {

	
	PageData<T> queryPage(PageInfo<T> pageinfo);
	
}
