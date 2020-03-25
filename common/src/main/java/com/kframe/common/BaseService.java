package com.kframe.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.kframe.bean.PageData;
import com.kframe.bean.PageInfo;
import com.kframe.repositorys.BaseRepostory;

@Transactional
public abstract class BaseService<T, ID> implements IBaseService<T, ID> {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected  final BaseRepostory<T, ID> repository;

	public BaseService( final BaseRepostory repository ) {
		this.repository = repository;
	}

	@Resource
	protected EntityManager entityManager;

	public  PageData<T> queryPage( PageInfo<T> pageinfo ) {
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
		Page<T> page = repository.findAll(createSpecification(pageinfo.getBean()), pageable);
		return PageData.from(pageinfo, page.getContent(), page.getTotalElements());
	}

	protected Specification<T> createSpecification( T t ) {
		Specification<T> specification = new Specification<T>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1151214153523166032L;

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};
		return specification;
	}

	public <F> Page<F> pageBySql( F f ) {
		return null;
	}

	/**
	 * 给hql参数设置值
	 * 
	 * @param query  查询
	 * @param params 参数
	 */
	protected void setParameters( Query query, Map<String, Object> params ) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
}
