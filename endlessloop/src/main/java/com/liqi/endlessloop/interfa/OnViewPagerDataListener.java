package com.liqi.endlessloop.interfa;

import android.widget.ImageView;

import java.util.List;

/**数据装配接口
 * Created by LiQi on 2017/12/21.
 */

public interface OnViewPagerDataListener {
    /**
     *数据赋值。并指定当前第几个界面展示
     * @param imageViews ImageView数据集合
     * @param showPosition 当前第几个界面展示
     */
    void initAdapterListRefresh(List<ImageView> imageViews, int showPosition);

    /**
     *数据赋值。
     * @param imageViews ImageView数据集合
     */
    void initAdapterListRefresh(List<ImageView> imageViews);

    /**
     * 数据添加
     * @param imageViews ImageView数据集合
     */
    void addAdapterListRefresh(List<ImageView> imageViews);

    /**
     * 数据添加
     * @param imageView ImageView数据
     */
    void addAdapterListRefresh(ImageView imageView);
}
