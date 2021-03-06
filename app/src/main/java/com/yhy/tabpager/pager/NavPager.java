package com.yhy.tabpager.pager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.tabpager.R;
import com.yhy.tabpager.pager.factory.PagerFactory;
import com.yhy.tabpager.utils.ToastUtils;
import com.yhy.tabnav.adapter.TpgAdapter;
import com.yhy.tabnav.handler.ResultHandler;
import com.yhy.tabnav.listener.OnPageChangedListener;
import com.yhy.tabnav.pager.TpgFragment;
import com.yhy.tabnav.widget.TpgView;

public class NavPager extends TpgFragment {
    private static final String[] TABS = {"菜单A", "菜单B", "菜单C"};
    private ResultHandler mResultHandler;
    private TpgView tpgView;
    private PagersAdapter mAdapter;

    @Override
    protected View getSuccessView(LayoutInflater inflater, ViewGroup container) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.activity_tpg, null);
        tpgView = (TpgView) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public boolean shouldLoadDataAtFirst() {
        Bundle args = getArguments();
        boolean firstPage = args.getBoolean("firstPage");
        return firstPage;
    }

    @Override
    protected void initData(ResultHandler handler) {
        mResultHandler = handler;

        //mAdapter = new PagersAdapter(getFragmentManager());
        /*
        //这里需要用getChildFragmentManager()
        getChildFragmentManager()是fragment中的方法, 返回的是管理当前fragment内部子fragments的manager.
        getFragmentManager()在activity和fragment中都有.
        在activity中, 如果用的是v4 support库, 方法应该用getSupportFragmentManager(),
        返回的是管理activity中fragments的manager.
        在fragment中, 还叫getFragmentManager(), 返回的是把自己加进来的那个manager.

        也即, 如果fragment在activity中, fragment.getFragmentManager()得到的是activity中管理fragments的那个manager.
        如果fragment是嵌套在另一个fragment中, fragment.getFragmentManager()
        得到的是它的parent的getChildFragmentManager().

        总结就是: getFragmentManager()是本级别管理者, getChildFragmentManager()是下一级别管理者.
        这实际上是一个树形管理结构.
        */
        mAdapter = new PagersAdapter(getChildFragmentManager());
        tpgView.setAdapter(mAdapter);

        handler.sendSuccessHandler();
    }

    @Override
    protected void initListener() {
        tpgView.setOnPageChangedListener(new OnPageChangedListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ToastUtils.shortToast("position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class PagersAdapter extends TpgAdapter {

        public PagersAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Override
        public TpgFragment getPager(int position) {
            Bundle args = new Bundle();
            args.putBoolean("firstPage", position == 0);
            TpgFragment fragment = new NavContentPager();
            fragment.setArguments(args);
            return fragment;
        }
    }
}