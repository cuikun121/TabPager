package com.yhy.tabpager.pager;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhy.tpg.pager.TpgFragment;
import com.yhy.tpg.dispatch.DispatchLoading;

import java.util.Random;

/**
 * @author 颜洪毅
 * @dateTime 2016/12/22 21:46
 * @copyRight SilentWolf
 */
public class APager extends TpgFragment {
    private static final DispatchLoading.STATE[] STATES = {DispatchLoading.STATE.ERROR,
            DispatchLoading.STATE.EMPTY, DispatchLoading.STATE.SUCCESS};

    @Override
    protected View getSuccessView() {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setText("A页面加载成功");
        tv.setTextColor(Color.RED);
        tv.setTextSize(32);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }

    /**
     * 由于该页面是第一页，所以需要重写该方法，并返回true
     *
     * @return
     */
    @Override
    public boolean shouldLoadDataAtFirst() {
        return true;
    }

    @Override
    protected void initData() {
        final Random random = new Random();
        new Thread() {
            @Override
            public void run() {
                //模拟网络加载延迟
                SystemClock.sleep(1000);

                //数据加载结束后，需要手动刷新页面状态
                refresh(STATES[random.nextInt(STATES.length)]);
            }
        }.start();
    }
}
