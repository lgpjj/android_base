/**
 * Copyright (C) 2017 南京思创信息技术有限公司
 * 版权所有
 * 类名　　       : XtxcUtil.java
 * 功能概要       : 协调巡查工具类
 * 创建日期       : 2017-2-21
 * 修改日期       : 2017-2-21
 */
package com.lgpgit.open.toolutils.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;

import com.strongit.nj.toolutils.common.Common;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

/**
 * 手机服务工具类
 *
 * @author lugp
 * @date 2019/01/31
 */
public class PhoneUtil {
	private static long lastClickTime;
	private final static int SPACE_TIME = 2000;

	public static void initLastClickTime() {
		lastClickTime = 0;
	}

	// 防止重复点击
	public synchronized static boolean isDoubleClick() {
		long currentTime = System.currentTimeMillis();
		boolean isClick2;
		if (currentTime - lastClickTime > SPACE_TIME) {
			isClick2 = false;
		} else {
			isClick2 = true;
		}
		lastClickTime = currentTime;
		return isClick2;
	}

	// 获取手机硬件唯一标识
	public static String getDeviceId(Context context) {
		StringBuilder deviceId = new StringBuilder();
		// 渠道标志
		// deviceId.append("a");
		try {

			// IMEI（imei）
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = tm.getDeviceId();
			if (!Common.stringIsNull(imei)) {
				deviceId.append("imei");
				deviceId.append(imei);
				return deviceId.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceId.toString();
	}

	//获取手机sim卡唯一标识
	public static String getTelephoneSIMId(Context context){
		TelephonyManager telephony = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		Boolean simStype = false;
		int simState = telephony.getSimState();
		if (simState == TelephonyManager.SIM_STATE_READY) {
			simStype = true;
		}
		telephony.getSimSerialNumber();
		if (simStype) {
            Class<?> telephonyClass;
			Object result = null;
			try {
				telephonyClass = Class.forName(telephony.getClass().getName());
				Method m = telephonyClass.getMethod("getSubscriberId", new Class[]{int.class});
				result = m.invoke(telephony, 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if ("000000000000000".equals(telephony.getSubscriberId())) {
				return null;
			} else {
				String sim = result != null ? "," + result : "";
				if (telephony.getSubscriberId().equals(result)) {
					sim = telephony.getSubscriberId();
				} else {
					sim = telephony.getSubscriberId() + sim;
				}
				return sim;
			}
		} else {
			return null;
		}
	}

	// 获取时间上午下午
	public static String getTime() {
		long time = System.currentTimeMillis();
		final Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(time);

		int apm = mCalendar.get(Calendar.AM_PM);
		if (apm == 0) {
			return "上午好！";
		} else {
			return "下午好！";
		}

	}

	/**
	 * 把list转成字符串
	 *
	 * @param list
	 *            待转的list
	 * @param s
	 *            字符串拼接符
	 * @return
	 */
	public static String join(List<String> list, String s) {
		String strResult = "";
		for(int i=0;i<list.size();i++){
			   strResult += list.get(i);
			}
			strResult = strResult.substring(0,strResult.length());//去掉最后多出来的逗号。
		return strResult;

	}

	public static boolean ExistSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	public static long getSDFreeSize(){
		//取得SD卡文件路径
		File path = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(path.getPath());
		//获取单个数据块的大小(Byte)
		long blockSize = sf.getBlockSize();
		//空闲的数据块的数量
		long freeBlocks = sf.getAvailableBlocks();
		//返回SD卡空闲大小
		return freeBlocks * blockSize;  //单位Byte
		//return (freeBlocks * blockSize)/1024;   //单位KB
//		return (freeBlocks * blockSize)/1024 /1024; //单位MB
	}

}
