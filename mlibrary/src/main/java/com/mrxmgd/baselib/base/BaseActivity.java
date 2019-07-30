/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.base;
/*
 *
 *
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.mrxmgd.baselib.R;
import com.mrxmgd.baselib.databinding.ActivityBaseBinding;
import com.mrxmgd.baselib.dialog.LoadingDialog;
import com.mrxmgd.baselib.manager.AppManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {
    protected Context mContext;
    protected LoadingDialog mLoadingDialog;
    protected ActivityBaseBinding baseDataBinding;
    protected ViewDataBinding childDataBinding;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSteepStatusBar();
        baseDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        baseDataBinding.setShowTitleBar(true);
        setChildView(setLayout());
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                mContext = BaseActivity.this;
                mLoadingDialog = new LoadingDialog(BaseActivity.this);
                Init(savedInstanceState);
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                displayMetrics.scaledDensity = displayMetrics.density;
                // 设置竖屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                AppManager.getAppManager().addActivity(BaseActivity.this);
                return false; //false 表示只监听一次IDLE事件,之后就不会再执行这个函数了.
            }
        });
    }

    /**
     * 设置内容布局
     * 必须设置，不然为空
     *
     * @param layoutResID
     */

    protected void setChildView(int layoutResID) {
        if (layoutResID != 0) {
            childDataBinding = DataBindingUtil.inflate(getLayoutInflater(), layoutResID, null, false);
            baseDataBinding.layoutContainer.addView(childDataBinding.getRoot(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    /**
     * 设置子布局
     *
     * @return
     */
    protected abstract int setLayout();

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    protected abstract void Init(Bundle savedInstanceState);


    /**
     * 浅色状态栏
     */
    protected void setLightStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * 深色状态栏
     */
    protected void clearLightStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 沉浸状态栏
     */
    protected void setSteepStatusBar() {
        Window window = getWindow();
        //4.4版本及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //5.0版本及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }


    /**
     * 设置状态栏颜色
     *
     * @param colorResId
     */
    protected void setStatusBarColor(int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorResId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置底部导航栏颜色
     *
     * @param colorResId
     */
    protected void setNavigationBarColor(int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setNavigationBarColor(colorResId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        setTitle(getString(resId));
    }


    /**
     * 设置标题
     *
     * @param str
     */
    public void setTitle(String str) {
        baseDataBinding.setTitle(str);
    }

    /**
     * 设置状态栏左边图片
     *
     * @param resId
     */
    protected void setLeftImage(int resId) {
        baseDataBinding.titleBarIvLeft.setVisibility(View.VISIBLE);
        baseDataBinding.titleBarIvLeft.setImageResource(resId);
    }

    /**
     * 设置状态栏右边图片
     *
     * @param resId
     */
    protected void setRightImage(int resId) {
        baseDataBinding.titleBarIvRight.setVisibility(View.VISIBLE);
        baseDataBinding.titleBarIvRight.setImageResource(resId);
    }

    /**
     * 设置状态栏左边文字
     *
     * @param text
     */
    protected void setLeftText(String text) {
        baseDataBinding.titleBarTvLeft.setVisibility(View.VISIBLE);
        baseDataBinding.titleBarTvLeft.setText(text);
    }

    /**
     * 设置状态栏右边文字
     *
     * @param text
     */
    protected void setRightText(String text) {
        baseDataBinding.titleBarTvRight.setVisibility(View.VISIBLE);
        baseDataBinding.titleBarTvRight.setText(text);
    }

    /**
     * 显示toast
     *
     * @param resId
     */
    protected void showToast(int resId) {
        showToast(getString(resId));
    }

    /**
     * 显示toast
     *
     * @param str
     */
    protected void showToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置屏幕常亮
     */
    protected void setKeepScreenOn() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.titleBar_iv_left) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().removeActivity(this);
        mContext = null;
        mLoadingDialog.dismiss();
        mLoadingDialog = null;
        baseDataBinding = null;
        childDataBinding = null;
        super.onDestroy();
    }
}
