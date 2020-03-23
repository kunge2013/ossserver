package com.kframe.common;

import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.kframe.entity.Base;
import com.kframe.repositorys.BaseRepostory;

@Transactional
public abstract class BaseService<T, ID> implements IBaseService<T, ID> {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected  BaseRepostory<T , ID> repository;

	public BaseService(BaseRepostory repository) {
		this.repository = repository;
	}
	
	@Resource
	protected EntityManager entityManager;

	public Page<T> queryPage(PageInfo pageinfo, T t) {
		Pageable pageable = null;
		if (pageinfo.fetchQuerySorts() == null) {
			pageable = PageRequest.of(pageinfo.getPage(), pageinfo.getSize());
		} else {
			pageable = PageRequest.of(pageinfo.getPage(), pageinfo.getSize(), pageinfo.fetchQuerySorts());
		}
		if (repository == null) {
			LOGGER.error("repostory not init ...");
			throw new UnsupportedOperationException("repostory not init ...");
		}
		return repository.findAll(createSpecification(t), pageable);
	}

	protected Specification<T> createSpecification(T t) {
		return null;
	}

	public <F> Page<F> pageBySql(F f) {
		return null;
	}
	
	 /**
     * 给hql参数设置值
     * @param query 查询
     * @param params 参数
     */
	protected void setParameters(Query query,Map<String,Object> params){
        for(Map.Entry<String,Object> entry:params.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }
    }
}
