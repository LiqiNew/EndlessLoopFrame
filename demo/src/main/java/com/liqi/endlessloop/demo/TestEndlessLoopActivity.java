package com.liqi.endlessloop.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.liqi.endlessloop.CycleViewPager;
import com.liqi.endlessloop.interfa.OnCycleViewPagerListener;
import com.liqi.endlessloop.interfa.OnImageCycleViewListener;
import com.liqi.endlessloop.interfa.OnViewPagerDataListener;

import java.util.ArrayList;
import java.util.List;

/**轮播测试用例
 * Created by LiQi on 2017/12/21.
 */

public class TestEndlessLoopActivity extends AppCompatActivity implements OnImageCycleViewListener {
    private CycleViewPager mCycleViewPager;
    private OnViewPagerDataListener mOpenWheel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_endless_loop_activity);
        List<ImageView> imageViews=new ArrayList<>();
        imageViews.add(getImageView(R.drawable.test_iamge01));
        imageViews.add(getImageView(R.drawable.test_iamge02));
        imageViews.add(getImageView(R.drawable.test_iamge03));
        mCycleViewPager=(CycleViewPager) getSupportFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager);
        final OnCycleViewPagerListener viewPagerListener = mCycleViewPager.setCycle(true)
                .setTime(2)
               // .setIndicatorTopCenter()
               //.leftRightDisplayOffset(50,50)
                .setBackgroundColorId(android.R.color.white)
                .startLoadData(imageViews, this);
        //开启轮播
        findViewById(R.id.test_endless_loop_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOpenWheel = viewPagerListener.openWheel();
            }
        });
        //关闭轮播
        findViewById(R.id.test_endless_loop_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerListener.closeWheel(true);
            }
        });
        //添加展示数据
        findViewById(R.id.test_endless_loop_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=mOpenWheel){
                    mOpenWheel.addAdapterListRefresh(getImageView(R.drawable.test_iamge04));
                }
            }
        });
        //替换展示数据，并展示第三个图片
        findViewById(R.id.test_endless_loop_04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=mOpenWheel) {
                    List<ImageView> imageViews = new ArrayList<>();
                    imageViews.add(getImageView(R.drawable.test_iamge04));
                    imageViews.add(getImageView(R.drawable.test_iamge03));
                    imageViews.add(getImageView(R.drawable.test_iamge02));
                    imageViews.add(getImageView(R.drawable.test_iamge01));
                    mOpenWheel.initAdapterListRefresh(imageViews,2);
                }
            }
        });
    }
    /**
     * 获取对应的imageView控件
     *
     * @param imageId
     * @return
     */
    private ImageView getImageView(int imageId) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageId);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setTag(imageId);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    @Override
    public void onImageClick(View imageView) {
        Toast.makeText(this,"图片点击了+"+imageView,Toast.LENGTH_SHORT).show();
    }
}
