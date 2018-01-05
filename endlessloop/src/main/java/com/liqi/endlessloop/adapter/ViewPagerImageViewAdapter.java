package com.liqi.endlessloop.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liqi.endlessloop.interfa.OnImageCycleViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面适配器 返回对应的view
 * Created by LiQi on 2017/12/21.
 */

public class ViewPagerImageViewAdapter extends PagerAdapter {
    private List<ImageView> mImageViews;
    private OnImageCycleViewListener mOnImageCycleViewListener;

    private ViewPagerImageViewAdapter() {

    }

    public ViewPagerImageViewAdapter(@NonNull List<ImageView> imageViews) {
        if (null == mImageViews) {
            mImageViews = new ArrayList<>();
        }
        if (null != imageViews && !imageViews.isEmpty()) {
            mImageViews.addAll(imageViews);
        }
    }

    public List<ImageView> getImageViews() {
        return mImageViews;
    }

    public void initImageViews(@NonNull List<ImageView> imageViews) {
        if (!mImageViews.isEmpty()) {
            mImageViews.clear();
        }

        if (null != imageViews && !imageViews.isEmpty()) {
            mImageViews.addAll(imageViews);
        }
    }

    public void addImageViews(@NonNull List<ImageView> imageViews) {
        if (null != imageViews && !imageViews.isEmpty()) {
            mImageViews.addAll(imageViews);
        }
    }
    public void addImageView(@NonNull ImageView imageView) {
        if (null != imageView) {
            mImageViews.add(imageView);
        }
    }
    public void setOnImageCycleViewListener(OnImageCycleViewListener onImageCycleViewListener) {
        mOnImageCycleViewListener = onImageCycleViewListener;
    }

    @Override
    public int getCount() {
        return mImageViews.size();
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
    public View instantiateItem(ViewGroup container, final int position) {
        ImageView v = mImageViews.get(position);
        if (mOnImageCycleViewListener != null) {
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != mOnImageCycleViewListener) {
                        mOnImageCycleViewListener.onImageClick(v);
                    }
                }
            });
        }
        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
