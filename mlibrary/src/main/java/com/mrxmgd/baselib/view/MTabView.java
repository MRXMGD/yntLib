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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
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
public class MTabView extends LinearLayout {
    ImageView imageView;
    TextView textView;

    public MTabView(Context context) {
        super(context);
    }

    @SuppressLint("ResourceAsColor")
    public MTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.layout_tabview, null);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textView = (TextView) view.findViewById(R.id.textView);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MTabView);
        int leftIconSize = (int) typedArray.getDimension(R.styleable.MTabView_iconSize, getDefaultDPSize(context, 25));
        LayoutParams params = new LayoutParams(leftIconSize, leftIconSize);
        params.bottomMargin = (int) typedArray.getDimension(R.styleable.MTabView_margin, 0);
        imageView.setLayoutParams(params);
        imageView.setImageResource(typedArray.getResourceId(R.styleable.MTabView_tabIcon, R.drawable
                .img_default));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimensionPixelSize(R.styleable.MTabView_textSize, DensityUtils.sp2px(context, 12)));
        textView.setText(typedArray.getString(R.styleable.MTabView_tabText));
        textView.setTextColor(getResources().getColorStateList(typedArray.getResourceId(R.styleable.MTabView_textColor,R.drawable.selector_textcolor_tab)));
        addView(view);
        typedArray.recycle();
    }


    private int getDefaultDPSize(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }


    public MTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置选中改变样式
     *
     * @param selected
     */
    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        imageView.setSelected(selected);
        textView.setSelected(selected);
    }

}
