package com.ruifios.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

/**
 * 常量
 * @author 陈华
 * @param <GrantedAuthority>
 *
 */
public class CustomInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private AntPathMatcher urlMatcher = new AntPathMatcher(); 
	
    private static Map<String, List<ConfigAttribute>> resourceMap = null;
     
    //tomcat启动时实例化一次
    public CustomInvocationSecurityMetadataSource() {
        loadResourceDefine();  
    } 
    
    //tomcat开启时加载一次，加载所有url和权限（或角色）的对应关系
    private void loadResourceDefine() {
        resourceMap = new HashMap<String, List<ConfigAttribute>>(); 
        List<ConfigAttribute> atts = new ArrayList<ConfigAttribute>(); 
        ConfigAttribute ca = new SecurityConfig("ROLE_USER");
        atts.add(ca); 
        resourceMap.put("/index.jsp", atts);  
        List<ConfigAttribute> attsno =new ArrayList<ConfigAttribute>();
        ConfigAttribute cano = new SecurityConfig("ROLE_NO");
        attsno.add(cano);
        resourceMap.put("/http://blog.csdn.net/u012367513/article/details/other.jsp", attsno);   
    }  
    
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		log.debug("");
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// 将参数转为url    
        String url = ((FilterInvocation)object).getRequestUrl();   
        Iterator<String>ite = resourceMap.keySet().iterator(); 
        while (ite.hasNext()) {         
            String resURL = ite.next();  
            if (urlMatcher.match(resURL, url)) { 
            	return resourceMap.get(resURL);         
            }       
        } 
        return null;    
	}

	@Override
	public boolean supports(Class<?> paramClass) {
		return true;
	}
	
}

