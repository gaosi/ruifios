package com.ruifios.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 常量
 * @author 陈华
 *
 */
public class CustomUserDetailsService implements UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		log.debug("");
		return null;
	}
	
}

