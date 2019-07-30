/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.util.List;
import java.util.Stack;

/*
 *
 *   Activity管理类
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */
public class AppManager {
    static Stack<Activity> activityStack = new Stack<Activity>();
    static AppManager instance;


    /**
     * 获取单例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 获取activity的个数
     */
    public int getActivityCount() {
        return activityStack.size();
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.push(activity);
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 移除当前Activity（堆栈中最后一个压入的）
     */
    public void popActivity() {
        activityStack.pop();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定类名的Activity
     */
    public void removeActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
                    .ACTIVITY_SERVICE);
            activityManager.restartPackage(context.getPackageName());
            System.exit(0);
            int pid = android.os.Process.myPid();
            android.os.Process.killProcess(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断应用是否在前台运行
     */
    public boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }

        return false;
    }

    /**
     * 判断应用是否在运行
     */
    public boolean isRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(context.getPackageName()) || info.baseActivity.getPackageName().equals(context.getPackageName())) {
                isAppRunning = true;
                break;
            }
        }

        return isAppRunning;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名
     * @return
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 判断进程是否运行
     *
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
            }
        }

        return isRunning;
    }

    /**
     * 判断app是否安装
     *
     * @param context
     * @param packageName 待检测app的包名
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 重启应用
     *
     * @param context
     */
    public static void restartApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }

    /**
     * 安装application
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file, String provider) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, provider, file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 启动第三方应用
     *
     * @param context
     * @param packageName 第三方应用包名
     */
    public static void openOtherApp(Context context, String packageName) {
        // 判断是否安装过App，否则去市场下载
        if (isAppInstalled(context, packageName)) {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            goToMarket(context, packageName);
        }
    }

    /**
     * 去市场下载页面
     *
     * @param context
     * @param packageName
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
        }
    }
}
