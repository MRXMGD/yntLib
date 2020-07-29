/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.recyclerview;
/*
 *
 *
 *   @author MRXMGD <mrxmgd@gmail.com>
 *
 */

import android.databinding.BindingAdapter;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.mrxmgd.baselib.R;

public class LRecyclerViewUtils {

    @BindingAdapter("recyclerViewStyle")
    public static void setStyle(LRecyclerView recyclerView, int progressStyle) {
        recyclerView.setRefreshProgressStyle(progressStyle);
        recyclerView.setHeaderViewColor(R.color.colorPrimary, R.color.colorText, android.R
                .color.white);
        recyclerView.setLoadingMoreProgressStyle(progressStyle);
        recyclerView.setFooterViewColor(R.color.colorPrimary, R.color.colorText, android.R
                .color.white);
        recyclerView.setFooterViewHint(recyclerView.getContext().getResources().getString(R.string.loadingText),
                recyclerView.getContext().getResources().getString(R.string.noMoreText), recyclerView.getContext().getResources()
                        .getString(R.string
                                .noNetWorkText));

    }
}
