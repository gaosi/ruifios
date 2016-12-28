package com.hawkeye.auth.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hawkeye.entity.auth.SecUser;

/**
 * 用户CURD操作
 */
public interface UserDao extends PagingAndSortingRepository<SecUser, String> {
	
	@Modifying
	@Transactional
	@Query("from User u where u.name = ?1")
	public List<SecUser> queryUserByName(String name);
	
}
