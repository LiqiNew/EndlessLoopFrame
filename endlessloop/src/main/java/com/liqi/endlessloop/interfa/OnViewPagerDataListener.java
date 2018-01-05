package com.liqi.endlessloop.interfa;

import android.widget.ImageView;

import java.util.List;

/**
 * Created by LiQi on 2017/12/21.
 */

public interface OnViewPagerDataListener {
    void initAdapterListRefresh(List<ImageView> imageViews, int showPosition);

    void initAdapterListRefresh(List<ImageView> imageViews);

    void addAdapterListRefresh(List<ImageView> imageViews);

    void addAdapterListRefresh(ImageView imageView);
}
