package com.ruifios.server;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.MethodParameter;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ruifios.annotation.FormParam;

/**
 * 自定义表单解析器
 * eg: public String user(@FormParam("base.") BaseSales base, @FormParam("record.") SalesRecord record)  
 * @author ch
 * http://jinnianshilongnian.iteye.com/blog/1717180
 * 获取权限   ReflectionUtils.makeAccessible(getBridgedMethod());
 * 获取泛型
 * Type genType = getClass().getGenericSuperclass();   
 * Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
 * entityClass =  (Class)params[0];
 *  cn.cybertron.nutz.ext.mvc.adaptor.ExtendPairAdaptor
 *  org.springframework.web.method.annotation.RequestParamMethodArgumentResolver
 *  org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver
 */
public class FormModelMethodArgumentResolver /*extends AbstractNamedValueMethodArgumentResolver*/ implements HandlerMethodArgumentResolver  
{
	
	public static final TypeDescriptor STRING_TYPE_DESCRIPTOR = TypeDescriptor.valueOf(String.class);
	
	public static final Pattern pattern = Pattern.compile("(?<=\\[)(\\S+)(?=\\])");  
	
	public FormModelMethodArgumentResolver() {
		//super(true);
	}

	public FormModelMethodArgumentResolver(boolean useDefaultResolution) {
		//super(useDefaultResolution);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		FormParam formParam = (FormParam) parameter.getParameterAnnotation(FormParam.class);
		if (formParam != null) {
			return true;
		}

		return false;
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		/*
		HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest(HttpServletRequest.class);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) WebUtils.getNativeRequest(servletRequest, MultipartHttpServletRequest.class);
		if (MultipartFile.class == parameter.getParameterType()) {
			Object arg = multipartRequest.getFile("base");
		}
		*/
		
		//FormParam formParam = (FormParam) parameter.getParameterAnnotation(FormParam.class);
		String prefix = getPrefix(parameter);// 对象前缀
		Map<String, String> parameterMap = getParameterMap(webRequest, prefix);
		Class<?> paramType = parameter.getParameterType();
		WebDataBinder binder = binderFactory.createBinder(webRequest, null, prefix);  
		
		Object arg = null;
		if(Map.class.isAssignableFrom(paramType)){// Map解析
			Set<String> keys = getkey(parameterMap.keySet());
			Type[] types = getGenericSuperclass(parameter);
			Class<?> k = (Class<?>)types[0];
			Class<?> t = (Class<?>)types[1];
			Map<Object, Object> map = new HashMap<Object, Object>(keys.size());
			for(String key: keys){
				Object karg = binder.convertIfNecessary(key, k, parameter);
				String mapprefix = prefix + "["+key+"]";
				Object targ = createBean(t, parameter, parameterMap, mapprefix, binder);
				map.put(karg, targ);
			}
			
			arg = map;
		}else if ((Collection.class == paramType) || (List.class.isAssignableFrom(paramType))) {// List解析
			Set<String> keys = getkey(parameterMap.keySet());
			Type[] types = getGenericSuperclass(parameter);
			Class<?> k = (Class<?>)types[0];
			List<Object> list = new ArrayList<>(keys.size());
			for(String key: keys){
				String mapprefix = prefix + "["+key+"]";
				Object karg = createBean(k, parameter, parameterMap, mapprefix, binder);
				list.add(karg);
			}
			
			arg = list;
		}else{
			arg = createBean(paramType, parameter, parameterMap, prefix, binder); 
		}

		return binder.convertIfNecessary(arg, paramType, parameter);
	}
	
	public Object createBean(Class<?> paramType, MethodParameter parameter, Map<String, String> properties, String prefix, WebDataBinder binder){
		//Class<?> paramType = parameter.getParameterType();
		 
		try {
			Field[] fields = paramType.getDeclaredFields();  
			Object target = paramType.newInstance();  
			for(Field field:fields){   
				if(!isUsedFiled(field)){
					field.setAccessible(true);  
			    	String fieldName   = field.getName();  
			    	Class<?> fieldType = field.getType();  
			    	Object arg = null;
			    	if(isPrimitive(fieldType)){
			    		arg = binder.convertIfNecessary(properties.get(prefix+"."+fieldName), fieldType, field);
			    	}else{
			    		String tempkey = prefix + "." + fieldName;
			    		arg = createBean(fieldType, parameter, properties, tempkey, binder);
			    	}
			           
			    	field.set(target, arg);  
				}
				 
			}
			return target;
		} catch (Exception e) {
			return null;
		}  
	}
	
	public Object getMap(Map<String, Object> parameters, Class<?> clazz){
		Map<String, Object> obj = new HashMap<String, Object>();
		
		return obj;
	}
	
	/**
	 * 判断是否为静态变量或最终变量
	 * @param field
	 * @return
	 */
	public static boolean isUsedFiled(Field field) { 
        try { 
        	if(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())){
        		return true;
        	}
        	
        	return false; 
        } catch (Exception e) { 
            return false; 
        } 
    } 

	/**
	 * 判断是否为简单类型
	 * @param fieldType
	 * @return
	 */
	public static boolean isPrimitive(Class<?> fieldType) { 
        try { 
        	if(fieldType.isPrimitive()){
	    		Class<?> type = (Class<?>) fieldType.getField("TYPE").get(null);
	    		return type.isPrimitive();
        	}
        	return true;
        } catch (Exception e) { 
            return false; 
        } 
    } 
	
	/**
	 * 获取对象前缀
	 * 	1、获取 FormParam 注解的名称
	 *  2、获取 参数名称
	 * 
	 * ClassUtils.getShortNameAsProperty(targetType); // 获取类名称首字母小写 
	 */ 
	protected String getPrefix(MethodParameter parameter){
		FormParam formParam = (FormParam) parameter.getParameterAnnotation(FormParam.class);
		String prefix = formParam.value();
		if(StringUtils.isEmpty(prefix)){
			prefix = parameter.getParameterName(); 
		}
		if(prefix != "" && !prefix.endsWith("\\.")){
			prefix = prefix.replace(".", "");
		}
		
		return prefix;
	}
	
	/**
	 * 获取和前缀匹配参数
	 * @param webRequest
	 * @param prefix
	 * @return
	 */
	protected Map<String, String> getParameterMap(NativeWebRequest webRequest, String prefix) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		Map<String, String[]> parameterMap = webRequest.getParameterMap();
		
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			if (entry.getKey().startsWith(prefix)) {
				String value = "";
				if(entry.getValue() != null){
					value = entry.getValue()[0];
				}
				result.put(entry.getKey(), value);
			}
		}
		return result;
	}
 
	/**
	 * 获取泛型类型
	 * @param clazz
	 * @return
	 */
	protected Type[] getGenericSuperclass(MethodParameter parameter){
		Type genType = parameter.getGenericParameterType();   
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		return params;
	}
	
	/**
	 * 获取mapkey
	 * @param set
	 * @return
	 */
	protected Set<String> getkey(Set<String> set) {
		Set<String> keys = new HashSet<String>();
		for(String key: set){
			Matcher matcher = pattern.matcher(key);
			while(matcher.find()){
				String m = matcher.group(1);
				keys.add(m);
				break;
	        }
		}
		return keys;
	}

	/*
	@Override
	@Override
	protected Object resolveName(String paramString, MethodParameter paramMethodParameter,
			NativeWebRequest paramNativeWebRequest) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void handleMissingValue(String paramString, MethodParameter paramMethodParameter)
			throws ServletException {
		// TODO Auto-generated method stub
		
	} 
	*/
}
