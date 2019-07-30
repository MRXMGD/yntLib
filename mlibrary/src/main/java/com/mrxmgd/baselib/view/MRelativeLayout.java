/*
 *
 *
 *        Copyright (c) 2019. mrxmgd.com
 *
 *        @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.mrxmgd.baselib.util.ScreenUtils;

public class MRelativeLayout extends RelativeLayout {

    public MRelativeLayout(Context context) {
        super(context);
        Init();
    }

    public MRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Init();
    }

    public MRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Init();
    }

    public void Init() {
        setPadding(0, ScreenUtils.getStatusHeight(getContext()), 0, 0);
    }
}