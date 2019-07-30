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
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

@GlideModule
public class BaseGlideConfig extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context,builder);
//        int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;  // 取1/8最大内存作为最大缓存
//
//        // builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//        builder.setMemoryCache(new LruResourceCache(memorySize));
//
//        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
//                .setMemoryCacheScreens(2)
//                .build();
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
//        //修改bitmappool的大小为默认的1.2倍
//        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
//        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
//
//        File cacheDir = context.getExternalCacheDir();//指定的是数据的缓存地址
//        int diskCacheSize = 1024 * 1024 * 40;//最多可以缓存多少字节的数据 400M
//        //设置磁盘缓存大小
//        builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));

    }
}
