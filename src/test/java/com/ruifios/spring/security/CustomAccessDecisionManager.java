package com.ruifios.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 常量
 * @author 陈华
 * @param <GrantedAuthority>
 *
 */
public class CustomAccessDecisionManager<GrantedAuthority> implements UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	//登陆验证时，通过username获取用户的所有权限信息，
    //并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User(username, "lcy", true, true, true, true, null); 
        log.debug("");
        return user;  
	}
	
}

