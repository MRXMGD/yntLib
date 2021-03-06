/*
 *
 *
 *    Copyright (c) 2018. mrxmgd.com
 *
 *     @author MRXMGD <mrxmgd@gmail.com>
 *
 *
 */

package com.mrxmgd.baselib.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.mrxmgd.baselib.R;
import com.mrxmgd.baselib.base.BaseActivity;
import com.mrxmgd.baselib.databinding.ActivityImagePreviewBinding;
import com.mrxmgd.baselib.util.FileUtils;

import java.io.File;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class ImagePreviewActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    MyAdapter mAdapter;
    List<Object> mList;
    Transformation transformation;
    int position = 0;
    public static boolean showIndex = true;
    public static boolean isLocalImg = false;
    public static int pleaceHolder = R.drawable.img_default;
    public static int error = R.drawable.img_error;
    ActivityImagePreviewBinding imagePreviewBinding;


    @Override
    protected int setLayout() {
        setStatusBarColor(Color.BLACK);
        baseDataBinding.setShowTitleBar(false);
        return R.layout.activity_image_preview;
    }

    @Override
    protected void Init(Bundle savedInstanceState) {
        imagePreviewBinding = (ActivityImagePreviewBinding) childDataBinding;
        if (getIntent().hasExtra("list"))
            mList = (List<Object>) getIntent().getSerializableExtra("list");
        if (getIntent().hasExtra("position"))
            position = getIntent().getIntExtra("position", 0);
        if (getIntent().hasExtra("url")) {
            mList = new ArrayList<>();
            mList.add(getIntent().getStringExtra("url"));
        }
        imagePreviewBinding.setShowIndex(showIndex);
        // 设置图片变换，不管图片多大设置宽度全屏
        transformation = new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap source, int outWidth, int outHeight) {
                int targetWidth = getWindowManager().getDefaultDisplay().getWidth();
                if (source.getWidth() == 0) {
                    return source;
                }
                double aspectRatio = (double) source.getHeight() / (double) source
                        .getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight != 0 && targetWidth != 0) {
                    return Bitmap.createScaledBitmap(source, targetWidth,
                            targetHeight, false);
                } else {
                    return source;
                }
            }

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

            }
        };
        mAdapter = new MyAdapter();
        imagePreviewBinding.viewPager.setAdapter(mAdapter);
        imagePreviewBinding.setIndex(1 + "/" + mAdapter.getCount());
        imagePreviewBinding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imagePreviewBinding.setIndex((position + 1) + "/" + mAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imagePreviewBinding.viewPager.setCurrentItem(position);
        imagePreviewBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveImage(((BitmapDrawable) ((PhotoView) mAdapter.getPrimaryItem()).getDrawable()).getBitmap());
                } catch (Exception e) {
                    Toast.makeText(ImagePreviewActivity.this, "保存失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (mList != null && mList.size() > 0) {
                return mList.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(ImagePreviewActivity.this);
            photoView.enable();
            if (isLocalImg) {
                Glide.with(ImagePreviewActivity.this).load(Uri.fromFile(new File(mList.get(position).toString()))).error(error)
                        .placeholder(pleaceHolder).transform(transformation).into(photoView);
            } else {
                Glide.with(ImagePreviewActivity.this).load(mList.get(position).toString()).error(error)
                        .placeholder(pleaceHolder).transform(transformation).into(photoView);
            }
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    try {
                        saveImage(((BitmapDrawable) ((PhotoView) v).getDrawable()).getBitmap());
                    } catch (Exception e) {
                        Toast.makeText(ImagePreviewActivity.this, "保存失败" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
            });
            container.addView(photoView);
            return photoView;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        private View mCurrentView;

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            mCurrentView = (View) object;
        }

        public View getPrimaryItem() {
            return mCurrentView;
        }
    }

    private void saveImage(Bitmap bitmap) {
        if (EasyPermissions.hasPermissions(ImagePreviewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            if (FileUtils.saveBitmap(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), "/" + System.currentTimeMillis() + ".png")) {
                Toast.makeText(ImagePreviewActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                MediaScannerConnection.scanFile(ImagePreviewActivity.this, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, null);
            } else
                Toast.makeText(ImagePreviewActivity.this, "保存失败", Toast.LENGTH_LONG).show();
        } else {
            EasyPermissions.requestPermissions(ImagePreviewActivity.this, "需要储存权限", 10001, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    public static void startActivity(Context context, List<Object> list, int position) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("list", (Serializable) list);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
}
