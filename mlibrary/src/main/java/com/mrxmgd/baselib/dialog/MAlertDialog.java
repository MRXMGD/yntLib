/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.dialog;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mrxmgd.baselib.R;


/**
 * 自定义的alertdialog
 *
 * @author MRXMGD <mrxmgd@gmail.com>
 */
public abstract class MAlertDialog {
    Dialog dialog;
    Context context;
    LayoutInflater inflater;
    View view;
    TextView tvMessage;
    TextView tvCancle, tvEnter;
    View divider;

    public MAlertDialog(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.layout_dialog_alert, null);
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        tvCancle = (TextView) view.findViewById(R.id.tv_cancle);
        tvEnter = (TextView) view.findViewById(R.id.tv_enter);
        divider = (View) view.findViewById(R.id.divider);
    }

    /**
     * 显示Dialog
     *
     * @param message       提示信息
     * @param cancel        关闭按钮文字
     * @param enter         确定按钮文字
     * @param showCancleBtn 是否显示关闭按钮
     * @return
     */
    public Dialog showDialog(String message, String cancel, String enter, boolean
            showCancleBtn, final int requestId) {
        if (message != null && message.length() > 0)
            tvMessage.setText(message);
        if (cancel != null && cancel.length() > 0)
            tvCancle.setText(cancel);
        if (enter != null && enter.length() > 0)
            tvEnter.setText(enter);
        if (showCancleBtn) {
            tvCancle.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        } else {
            tvCancle.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }
        if (dialog == null) {
            Builder buidler = new Builder(context,R.style.alertDialogStyle);
            dialog = buidler.create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(view);
        } else {
            dialog.show();
        }
        tvCancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancleClick(requestId);
            }
        });
        tvEnter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onEnterClick(requestId);
            }
        });
        return dialog;
    }

    protected abstract void onEnterClick(int requestId);

    protected void onCancleClick(int requestId) {
        dialog.cancel();
    }

}
