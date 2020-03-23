package com.kframe.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kframe.common.BaseService;
import com.kframe.common.RetResult;
import com.kframe.entity.UserInfo;

@Service
@Transactional
public class UserService extends BaseService<UserInfo, Long> implements IUserService {

//	@Resource
//	private UserRepository userRepository;
	
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
//	public void init() {
//		super.repostory = this.repostory;
//	}
	
	@Override
	protected Specification<UserInfo> createSpecification(UserInfo userinfo) {
		Specification<UserInfo> specification = new Specification<UserInfo>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -1151214153523166032L;

			@Override
			public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				 List<Predicate> predicates = new ArrayList<>();
				 String username = userinfo.getUsername();
				 if ( username != null && !username.isEmpty()) {
					 Predicate likeName = criteriaBuilder.like(root.get("username").as(String.class), username + "%");
					 predicates.add(likeName);
				 }
				 return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
			
		};
		return specification;
	}
}
