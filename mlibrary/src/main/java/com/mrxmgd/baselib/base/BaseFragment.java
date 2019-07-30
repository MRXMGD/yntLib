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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.mrxmgd.baselib.dialog.LoadingDialog;

/*
 *
 *
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    public Context mContext;
    public LoadingDialog mLoadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getBaseContext();
        mLoadingDialog = new LoadingDialog(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return setLayout(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Init(savedInstanceState);
    }


    /**
     * 设置子布局
     *
     * @return
     */
    protected abstract View setLayout(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState);

    /**
     * 初始化
     *
     * @param savedInstanceState
     */
    protected abstract void Init(Bundle savedInstanceState);

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
        intent.setClass(mContext, clz);
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
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mLoadingDialog.dismiss();
        mLoadingDialog = null;
        super.onDestroy();
    }
}
