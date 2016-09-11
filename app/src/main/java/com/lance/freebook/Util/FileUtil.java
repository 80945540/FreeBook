package com.lance.freebook.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.lance.freebook.MyApplication.MyApplication;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 *
 */
public class FileUtil {
	/**
	 * 开始消息提示常量
	 * */
	public static final int startDownloadMeg = 1;
	
	/**
	 * 更新消息提示常量
	 * */
	public static final int updateDownloadMeg = 2;
	
	/**
	 * 完成消息提示常量
	 * */
	public static final int endDownloadMeg = 3;
	
	/**
	 * 检验SDcard状态  
	 * @return boolean
	 */
	public static boolean checkSDCard()
	{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 保存文件文件到目录
	 * @param context
	 * @return  文件保存的目录
	 */
	public static String setMkdir(Context context)
	{
		String filePath;
		if(checkSDCard())
		{
			filePath = Environment.getExternalStorageDirectory()+ File.separator+"/youzytob/";
		}else{
			filePath = context.getCacheDir().getAbsolutePath()+ File.separator+"/youzytob/";
		}
		File file = new File(filePath);
		if(!file.exists())
		{
			boolean b = file.mkdirs();
			Log.e("file", "文件不存在  创建文件    "+b);
		}else{
			Log.e("file", "文件存在");
		}
		return filePath;
	}
	/**
	 * @return  创建缓存目录
	 */
	public static File getcacheDirectory()
	{
		File file = new File(MyApplication.getInstance().getApplicationContext().getExternalCacheDir(), "MyCache");
		if(!file.exists())
		{
			boolean b = file.mkdirs();
			Log.e("file", "文件不存在  创建文件    "+b);
		}else{
			Log.e("file", "文件存在");
		}
		return file;
	}
	/** 
	 * 得到文件的名称
	 * @return
	 * @throws IOException
	 */
	public static String getFileName(String url)
	{
		String name= null;
		try {
			name = url.substring(url.lastIndexOf("/")+1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
}
