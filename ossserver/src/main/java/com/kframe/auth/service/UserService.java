package com.kframe.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kframe.bean.PageData;
import com.kframe.bean.PageInfo;
import com.kframe.common.BaseService;
import com.kframe.common.RetResult;
import com.kframe.entity.Role;
import com.kframe.entity.UserInfo;
import com.kframe.repositorys.UserRepository;

@Service
@Transactional
public class UserService extends BaseService<UserInfo, Long> implements IUserService {

	@Resource
	private UserRepository repository;

	public UserService(UserRepository repository) {
		super(repository);
	}
	
	@Override
	public RetResult<UserInfo> saveUser(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return RetResult.success(repository.save(userInfo));
	}

	@Override
	public void remove(UserInfo userInfo) {
		// TODO Auto-generated method stub
		repository.delete(userInfo);
	}


//	@Override
//	protected Specification<UserInfo> createSpecification(UserInfo userinfo) {
//		Specification<UserInfo> specification = new Specification<UserInfo>() {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = -1151214153523166032L;
//
//			@Override
//			public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//				List<Predicate> predicates = new ArrayList<>();
//				String username = userinfo.getUsername();
//				if (username != null && !username.isEmpty()) {
//					Predicate likeName = criteriaBuilder.like(root.get("username").as(String.class), username + "%");
//					predicates.add(likeName);
//				}
//				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//			}
//
//		};
//		return specification;
//	}

	private Page<Role> pageRole(PageInfo pageInfo, Role role) {
		StringBuilder countSelectSql = new StringBuilder();
		countSelectSql.append("select count(*) from Role po where 1=1 ");

		StringBuilder selectSql = new StringBuilder();
		selectSql.append("from Role po where 1=1 ");
		Map<String, Object> params = new HashMap<>();
		String name = role.getName();
		StringBuilder whereSql = new StringBuilder();
		String countSql = new StringBuilder().append(countSelectSql).append(whereSql).toString();
		Query countQuery = this.entityManager.createQuery(countSql, Long.class);

		if (name != null && !name.isEmpty()) {
			whereSql.append(" and name like :name% ");
			params.put("name", name);
		}

		setParameters(countQuery, params);
		Long count = (Long) countQuery.getSingleResult();

		String querySql = new StringBuilder().append(selectSql).append(whereSql).toString();
		Query query = this.entityManager.createQuery(querySql, Role.class);
		this.setParameters(query, params);
		if (pageInfo != null) { // 分页
			query.setFirstResult(pageInfo.getPage() * pageInfo.getSize());
			query.setMaxResults(pageInfo.getSize());
		}
		List<Role> roleList = query.getResultList();
		if (pageInfo != null) {
			Pageable pageable = PageRequest.of(pageInfo.getPage(), pageInfo.getSize());
			Page<Role> rolePage = new PageImpl<Role>(roleList, pageable, count);
			return rolePage;
		}
		// 不分页
		return new PageImpl<Role>(roleList);
	}


}
