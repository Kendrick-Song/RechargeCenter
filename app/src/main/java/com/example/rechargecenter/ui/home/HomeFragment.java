package com.example.rechargecenter.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.rechargecenter.R;
import com.example.rechargecenter.RechargeHfFragment;
import com.example.rechargecenter.RechargeLlFragment;
import com.example.rechargecenter.RechargeSpFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //设置标题
        TextView title_tv = Objects.requireNonNull(getActivity()).findViewById(R.id.title_tv);
        title_tv.setText("充值中心");
        mTabLayout = root.findViewById(R.id.recharge_tab);
        mViewPager = root.findViewById(R.id.recharge_pager);
        initTab();
        return root;
    }


    private void initTab() {
        titles.clear();
        fragments.clear();
        titles.add("充话费");
        titles.add("充流量");
        titles.add("充视频会员");
        fragments.add(new RechargeHfFragment());
        fragments.add(new RechargeLlFragment());
        fragments.add(new RechargeSpFragment());
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
    }
}