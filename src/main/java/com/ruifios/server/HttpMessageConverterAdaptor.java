package com.ruifios.server;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.MultiValueMap;

/**
 * HTTP 消息转换适配器
 * @author ch
 *
 */
public class HttpMessageConverterAdaptor extends FormHttpMessageConverter {
	
	public HttpMessageConverterAdaptor() {
		super();
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType){
		return super.canRead(clazz, mediaType);
	}

	@Override
	public  boolean canWrite(Class<?> clazz, MediaType mediaType){
		return super.canWrite(clazz, mediaType);
	}

	@Override
	public  List<MediaType> getSupportedMediaTypes(){
		List<MediaType> list = super.getSupportedMediaTypes();
		
		return list;
	}

	@Override
	public  MultiValueMap<String, String> read(Class<? extends MultiValueMap<String, ?>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException{
		MultiValueMap<String, String> map = super.read(clazz, inputMessage);
		
		return map;
	}

	@Override
	public  void write(MultiValueMap<String, ?> map, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException{
		super.write(map, contentType, outputMessage);
	}
	
}