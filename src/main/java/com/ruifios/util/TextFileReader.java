package com.ruifios.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文本文件操作封装
 */
public final class TextFileReader
{
	/**
	 * 获取字符串的BufferedReader,如果程序返回不是null,那么需要调用本程序的closeBufferedReader方法关闭BufferedReader
	 * @param text 字符串文本
	 * @return BufferedReader,如果产生异常,那么返回null(此时不需要显示关闭BufferedReader)
	 *         如果返回非null,那么需要调用本程序的closeBufferedReader方法关闭BufferedReader
	 * @throws IOException
	 */
	public static final BufferedReader getBufferedReader(String text) throws IOException
	{
		if(text!=null)
		{
			StringReader sr = new StringReader(text);
			BufferedReader br = new BufferedReader(sr);			
			return br;
		}
		return null;
		
		
	}
	
	
	/**
	 * 根据指定的编码获取文件的BufferedReader,如果程序返回不是null,那么需要调用本程序的closeBufferedReader方法关闭BufferedReader
	 * 
	 * @param file
	 *            需要读取的文件
	 * @param charset
	 *            指定的字符编码
	 * @return BufferedReader,如果产生异常,那么返回null(此时不需要显示关闭BufferedReader)
	 *         如果返回非null,那么需要调用本程序的closeBufferedReader方法关闭BufferedReader
	 * @throws IOException
	 */
	public static final BufferedReader getBufferedReader(File file,
			String charset) throws IOException
	{
		if(file.exists())
		{
			BufferedReader br = null;
			try
			{
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
				return br;
			}
			catch(FileNotFoundException e)
			{
				br = null;
				throw e;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * 安全关闭BufferedReader
	 * 
	 * @param reader
	 *            BufferedReader
	 */
	public static void closeBufferedReader(BufferedReader reader)
	{
		if(reader != null)
		{
			try
			{
				reader.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用指定的编码方式读取文件,转为字符串 本方法是一次将文件的内容读取到内存,因此不适合读取过大的文件
	 * 
	 * @param file
	 *            读取的文件路径
	 * @param charset
	 *            按照此编码读取文件,例如"utf-8"
	 * @return 字符串形式的文本文件 如果文件不存在则返回null
	 */
	public static final String readAsCharset(File file, String charset)
	{
		byte[] data = readAsBinary(file);
		if(data != null)
		{
			try
			{
				return new String(data, charset);
			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static final List<String> readLinesAsCharset(File file, String charset)
	{
		String content = readAsCharset(file, charset);
		
		StringReader reader = new StringReader(content);
		
		BufferedReader br = new BufferedReader(reader);
		
		List<String> lineList = new ArrayList<String>();
		
		String s = null;
		try
		{
			while((s=br.readLine())!=null)
			{
				lineList.add(s);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				br.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}			
			
			try
			{
				reader.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return lineList;
	}

	/**
	 * 将文本文件以字节的形式读取出来 本方法是一次将文件的内容读取到内存,因此不适合读取过大的文件
	 * 
	 * @param file
	 *            要读取的文件
	 * @return 字节形式的文件内容,可以按照需要的编码转换 如果文件不存在则返回null
	 */
	public static final byte[] readAsBinary(File file)
	{
		if(file.exists())
		{
			long fileLength = file.length();
			byte[] buffer = new byte[(int)fileLength];
			FileInputStream fr = null;
			BufferedInputStream br = null;
			try
			{
				fr = new FileInputStream(file);
				br = new BufferedInputStream(fr);
				br.read(buffer);
				return buffer;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(br != null)
				{
					try
					{
						br.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
				if(fr != null)
				{
					try
					{
						fr.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}
