package com.lgpgit.open.toolutils.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * activity页面工具类
 *
 * @author lugp
 * @date 2019/01/31
 */
public class ActivityUtils {
	
	// 进度提示框
    protected static ProgressDialog progressDialog;
    
    /**
     * 显示Toast形式的提示信息(短时间)
     * @param message
     */
    public static void showShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

	/**
	 * 显示Toast形式的提示信息(长时间)
	 *
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, String message) {
    	Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示加载框
	 * @param context
	 */
	public static void showProgressDialog(Context context, String title, String content) {
		progressDialog = ProgressDialog.show(context, title, content, true, false);
		progressDialog.show();
	}
	/**
	 * 登录加载框
	 * @param context
	 */
	public static void showProgressDialogLogin(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "正在登录中。。。", true, false);
		progressDialog.show();
	}
	/**
	 * 提交加载框
	 * @param context
	 */
	public static void showProgressDialogSubmit(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "正在提交中。。。", true, false);
		progressDialog.show();
	}
	/**
	 * 保存加载框
	 * @param context
	 */
	public static void showProgressDialogSave(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "正在保存中。。。", true, false);
		progressDialog.show();
	}
	/**
	 * 退出加载框
	 * @param context
	 */
	public static void showProgressDialogExit(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "正在退出中。。。", true, false);
		progressDialog.show();
	}
	/**
	 * 上传加载框
	 * @param context
	 */
	public static void showProgressDialogUpload(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "正在上传中。。。", true, false);
		progressDialog.show();
	}
	/**
	 * 检查更新加载框
	 * @param context
	 */
	public static void showProgressDialogUpdate(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "检查更新中。。。", true, false);
		progressDialog.show();
	}
	/**
	 * 签收加载框
	 * @param context
	 */
	public static void showProgressDialogSign(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "正在签收中。。。", true, false);
		progressDialog.show();
	}
	
	/**
	 * 显示加载框
	 * @param context
	 */
	public static void showProgressUoload(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "视频上传中。。。", true, false);
		progressDialog.show();
	}
	
	// update by wangming 20170504 start
	/**
	 * 显示视频加载框
	 * @param context
	 */
	public static void showProgressLoadVideo(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "视频加载中。。。", true, false);
		progressDialog.show();
	}
	// update by wangming 20170504 end
	/**
	 * 数据更新
	 * @param context
	 */
	public static void showProgressUpdateData(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "数据更新中。。。", true, false);
		progressDialog.show();
	}
	/**
	 * 数据初始化
	 * @param context
	 */
	public static void showProgressUpdateCsh(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "数据初始化中。。。", true, false);
		progressDialog.show();
	}
	/**
	 * 加载图片等待框
	 * @param context
	 */
	public static void showProgressLoadPicture(Context context) {
		progressDialog = ProgressDialog.show(context, "提示", "图片正在加载中。。。", true, false);
		progressDialog.show();
	}
	/**
     * 关闭加载框
     */
    public static void dismissProgressDialog() {
    	if (progressDialog != null) {
    		progressDialog.dismiss();
    	}
    }

	/**
	 * 判断是否有加载框
	 *
	 * @return
	 */
	public static boolean isShowingDialog(){
    	if(progressDialog != null && progressDialog.isShowing()){
    		return true;
    	}
		return false;
    }

}
