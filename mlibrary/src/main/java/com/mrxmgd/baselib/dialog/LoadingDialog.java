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

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.progressindicator.AVLoadingIndicatorView;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.mrxmgd.baselib.R;

/*
 *
 *   加载对话框
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */
public class LoadingDialog extends Dialog {
    View view;
    AVLoadingIndicatorView loadingIndicatorView;
    TextView textView;
    Context context;
    boolean cancleable = true, touchoutsideCancle;

    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
        this.context = context;
        Init();
    }

    public LoadingDialog(Context context, Boolean cancleable, boolean touchoutsideCancle) {
        super(context, R.style.loadingDialogStyle);
        this.context = context;
        this.cancleable = cancleable;
        this.touchoutsideCancle = touchoutsideCancle;
        Init();
    }

    private void Init() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_loading, null);
        loadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.loadingView);
        textView = (TextView) view.findViewById(R.id.textView);
        loadingIndicatorView.setIndicatorId(ProgressStyle.LineSpinFadeLoader);
        setContentView(view);
        setCancelable(cancleable);
        setCanceledOnTouchOutside(touchoutsideCancle);
    }

    public void setTips(String str) {
        textView.setText(str);
    }

    public void setStyle(int progressStyle) {
        loadingIndicatorView.setIndicatorId(progressStyle);
    }

}
