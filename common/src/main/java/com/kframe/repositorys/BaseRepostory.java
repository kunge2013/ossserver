package com.kframe.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
/**
 * 可扩展  dao
 * @author fk
 *
 * @param <T>
 * @param <ID>
 */

import com.kframe.entity.Base;
@NoRepositoryBean
public interface BaseRepostory<T , ID> extends JpaRepository<T, ID> {
	
    Page<T> findAll(Specification<T> spec, Pageable pageable);
}
