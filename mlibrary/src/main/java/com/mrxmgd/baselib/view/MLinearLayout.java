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
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MLinearLayout extends LinearLayout {

    public MLinearLayout(Context context) {
        super(context);
    }

    public MLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        insets.top = 0;
        return super.fitSystemWindows(insets);
    }
}