package com.kframe.repositorys;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kframe.entity.VerifyCode;
/**
 * 验证码保存接口
 * @author fk
 *
 */
@Repository
public interface VerifyCodeRepository extends JpaRepository<VerifyCode, Serializable> {

	@Query(" select t from VerifyCode t where t.code = :code and expiretime <= :expiretime")
	public List<VerifyCode> queryVerifyCodes(@Param(value = "code") String code, @Param(value = "expiretime") long expiretime);
	
	@Query(" select count(t) from VerifyCode t where t.code = :code and expiretime <= :expiretime")
	public int countVerifyCodes(@Param(value = "code") String code, @Param(value = "expiretime") long expiretime);
	
	@Query(" delete from VerifyCode t where t.code = :code and expiretime <= :expiretime")
	public int removeVerifyCodes(@Param(value = "code") String code, @Param(value = "expiretime") long expiretime);
	
	/**
	 * 校验验证码是否过期
	 * @param code
	 * @param expiretime
	 * @return
	 */
	 default public boolean exitsCode(String code, long expiretime) {
		 boolean result = countVerifyCodes(code, expiretime) > 0;
		 if (result) removeVerifyCodes(code, expiretime); //删除已经验证的验证码
		return result;
	}
}
