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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrxmgd.baselib.R;
import com.mrxmgd.baselib.recyclerview.adaper.BaseQuickRecycleAdapter;
import com.mrxmgd.baselib.recyclerview.divider.RecycleViewDivider;
import com.mrxmgd.baselib.recyclerview.viewholder.BaseViewHolder;
import com.mrxmgd.baselib.util.DensityUtils;
import com.mrxmgd.baselib.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 底部列表dailog
 *
 * @author MRXMGD <mrxmgd@gmail.com>
 */
public abstract class BottomListDialog<T> {
    Dialog dialog;
    Context context;
    LayoutInflater inflater;
    View view;
    LinearLayout layout;
    TextView textView, tvCancle, tvEnter;
    RecyclerView recyclerView;
    BaseQuickRecycleAdapter<T> mAdapter;
    Window window;
    WindowManager.LayoutParams layoutParams;
    int selectPosition = -1;
    int request = -1;
    List<T> selectList;
    List<T> data;

    public BottomListDialog(Context context, final Boolean isMulti) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.layout_dialog_list_bottom, null);
        textView = (TextView) view.findViewById(R.id.textView);
        tvCancle = (TextView) view.findViewById(R.id.tv_cancle);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        tvEnter = (TextView) view.findViewById(R.id.tv_enter);
        if (isMulti) {
            layout.setVisibility(View.VISIBLE);
            selectList = new ArrayList<>();
        } else {
            layout.setVisibility(View.GONE);
        }
        recyclerView = view.findViewById(R.id.recyclerView);
        selectList = new ArrayList<>();
        data=new ArrayList<>();
        mAdapter = new BaseQuickRecycleAdapter<T>(R.layout.layout_item_dialog, data) {
            @Override
            protected void convert(BaseViewHolder holder, T item, int position) {
                TextView textView = holder.getView(R.id.textView);
                ImageView imageView = holder.getView(R.id.imageView);
                T bean = (T) data.get(position);
                textView.setText(bean.toString());
                if (isMulti) {
                    imageView.setSelected(selectList.contains(bean));
                } else {
                    imageView.setSelected(selectPosition == position);
                }
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new RecycleViewDivider(context, RecycleViewDivider
                .ORIENTATION_VERTICAL, DensityUtils.dip2px(context, 0.5f), context.getResources()
                .getColor
                        (R.color.colorDivider)));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickRecycleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMulti) {
                    T bean = (T) data.get(position);
                    if (selectList.contains(bean)) {
                        selectList.remove(bean);
                    } else {
                        selectList.add(bean);
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    selectPosition = position;
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    onItemSelect(data.get(position), position, request);
                }
            }
        });


    }

    /**
     * 显示Dialog
     *
     * @return
     */
    public Dialog showDialog(String title, ArrayList<T> mList, int selectedPosition, List<T> selectedList, int requestId) {
        selectPosition = selectedPosition;
        request = requestId;
        if (selectedList != null) {
            selectList.clear();
            selectList.addAll(selectedList);
        }
        textView.setText(title);
        data.clear();
        data.addAll(mList);
        mAdapter.notifyDataSetChanged();
        if (dialog == null) {
            Builder buidler = new Builder(context, R.style.bottomDialogStyle);
            dialog = buidler.create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            window = dialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            layoutParams = window.getAttributes();
            layoutParams.width = ScreenUtils.getScreenWidth(context);
            dialog.setContentView(view);
        } else {
            dialog.show();
        }
        if (mList.size() > 5) {
            layoutParams.height = ScreenUtils.getScreenHeight(context) / 2;
        } else {
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        window.setAttributes(layoutParams);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onMultiItemSelect(selectList, request);
            }
        });
        return dialog;
    }

    public abstract void onItemSelect(T bean, int position, int requestId);

    public abstract void onMultiItemSelect(List<T> selectList, int requestId);
}
