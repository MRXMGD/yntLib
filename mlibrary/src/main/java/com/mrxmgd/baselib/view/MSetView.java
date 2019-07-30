/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrxmgd.baselib.R;
import com.mrxmgd.baselib.util.DensityUtils;


/*
 *
 *   自定义的tabview
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */
public class MSetView extends LinearLayout {
    LinearLayout layout;
    ImageView ivLeft, ivRight;
    TextView tvLeft, tvRight;

    public MSetView(Context context) {
        super(context);
    }

    public MSetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.layout_setview, null);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        ivLeft = (ImageView) view.findViewById(R.id.iv_left);
        ivRight = (ImageView) view.findViewById(R.id.iv_right);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvRight = (TextView) view.findViewById(R.id.tv_right);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MSetView);

        ivLeft.setImageResource(typedArray.getResourceId(R.styleable.MSetView_leftIcon, R.drawable
                .img_default));
        int leftIconSize = (int) typedArray.getDimension(R.styleable.MSetView_leftIconSize, getDefaultDPSize(context, 22));
        LayoutParams params = new LayoutParams(leftIconSize, leftIconSize);
        params.rightMargin = (int) typedArray.getDimension(R.styleable.MSetView_leftIconMargin, getDefaultDPSize(context, 10));
        ivLeft.setLayoutParams(params);

        int rightIconSize = (int) typedArray.getDimension(R.styleable.MSetView_rightIconSize, getDefaultDPSize(context, 12));
        ivRight.setLayoutParams(new LayoutParams(rightIconSize, rightIconSize));
        ivRight.setImageResource(typedArray.getResourceId(R.styleable.MSetView_rightIcon, R.drawable
                .ic_more));
        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.MSetView_leftTextSize, DensityUtils.sp2px(context, 14)));
        tvLeft.setText(typedArray.getString(R.styleable.MSetView_leftText));
        tvLeft.setTextColor(typedArray.getColor(R.styleable.MSetView_leftTextColor,getResources().getColor(R.color.colorText)));

        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.MSetView_rightTextSize, DensityUtils.sp2px(context, 12)));
        tvRight.setText(typedArray.getString(R.styleable.MSetView_rightText));
        tvRight.setTextColor(typedArray.getColor(R.styleable.MSetView_rightTextColor,getResources().getColor(R.color.colorTextLight)));
        layout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        
        addView(view);
        typedArray.recycle();
    }

    private int getDefaultDPSize(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }


    public MSetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLeftIcon(int resId) {
        ivLeft.setImageResource(resId);
    }

    public void setRightIcon(int resId) {
        ivRight.setImageResource(resId);
    }

    public void setLeftText(String text) {
        tvLeft.setText(text);
    }

    public void setRightText(String text) {
        tvRight.setText(text);
    }

}
