package com.lance.freebook.MVP.Home.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.util.ArrayList;


public class FragmentController {

	private int containerId;
	private FragmentManager fm;
	private ArrayList<Fragment> fragments;
	
	private static FragmentController controller;

	public static FragmentController getInstance(FragmentActivity activity, int containerId) {
		if (controller == null) {
			controller = new FragmentController(activity, containerId);
		}
		return controller;
	}
	
	public static void onDestroy() {
		controller = null;
	}

	private FragmentController(FragmentActivity activity, int containerId) {
		this.containerId = containerId;
		fm = activity.getSupportFragmentManager();
		initFragment();
	}

	private void initFragment() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new HomeRecommendFragment());
		fragments.add(new HomeStackFragment());
		fragments.add(new HomeSearchFragment());
		fragments.add(new HomeDownloadFragment());
		
		FragmentTransaction ft = fm.beginTransaction();
		for(Fragment fragment : fragments) {
			ft.add(containerId, fragment);
		}
//		ft.commit();
		ft.commitAllowingStateLoss();
	}

	public void showFragment(int position) {
		hideFragments();
		Fragment fragment = fragments.get(position);
		FragmentTransaction ft = fm.beginTransaction();
		ft.show(fragment);
//		ft.commit();
		ft.commitAllowingStateLoss();
	}
	
	public void hideFragments() {
		FragmentTransaction ft = fm.beginTransaction();
		for(Fragment fragment : fragments) {
			if(fragment != null) {
				ft.hide(fragment);
			}
		}
//		ft.commit();
		ft.commitAllowingStateLoss();
	}
	
	public Fragment getFragment(int position) {
		return fragments.get(position);
	}
}