package com.ctrl.android.property.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.beanu.arad.utils.MessageUtils;
import com.ctrl.android.property.R;
import com.ctrl.android.property.base.Constant;
import com.ctrl.android.property.eric.entity.ApkInfo;

import java.io.File;

public class UpdateUtil {

	/**
	 * 获取当前应用的版本信息
	 * Created by apple on 2015/05/20.
	 * @param context
	 * @return
	 */
	public static PackageInfo getVersion(Context context) {
		PackageInfo packInfo = null;
		try {
			PackageManager packageManager = context.getPackageManager();
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (Exception e) {

		}
		return packInfo;
	}

	/**
	 *
	 * @param activity
	 * @param version
	 * @param showToast //是否显示版本提示信息
	 * @param isToMain  //是否在不升级的情况下跳转到首页，否则结束当前activity
	 * @return
	 */
	public static boolean checkVersionByCode(final Activity activity, final ApkInfo version, boolean showToast,final boolean isToMain) {
		int localVersionCode = getVersion(activity).versionCode;
		if(version==null){return false;}
		Log.d("demo", "老版本号: " + localVersionCode + " || 新版本号: " + version.getVersionCode());
		if (localVersionCode < Integer.parseInt(version.getVersionCode() == null || version.getVersionCode().equals("") ? "0" : version.getVersionCode())) { //
			new AlertDialog.Builder(activity).setTitle("版本升级")
					.setMessage(version.getRemark())
					.setPositiveButton("立即更新", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 开启更新服务UpdateService
							// 这里为了把update更好模块化，可以传一些updateService依赖的值
							// 如布局ID，资源ID，动态获取的标题,这里以app_name为例
							Intent updateIntent = new Intent(activity,
									UpdateService.class);
							updateIntent.putExtra("title", activity
									.getResources()
									.getString(R.string.app_name));
							updateIntent.putExtra("downloadUrl", version.getApkUrl());
							Log.d("demo", "apk url:" + version.getApkUrl());
							activity.startService(updateIntent);
							MessageUtils.showLongToast(activity, "开始下载……");

						}
					}).setNegativeButton("稍后更新", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).show();

			return true;
		} else {
			File updateFile = new File(Constant.SD_DOWNLOAD_PATH, activity
					.getResources().getString(R.string.app_name) + ".apk");
			if (updateFile.exists()) {
				updateFile.delete();
			}
			if (showToast){
				MessageUtils.showLongToast(activity, "当前版本为最新版");
				return false;
			}
		}
		return false;
	}
}
