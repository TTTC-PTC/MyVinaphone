package com.vnp.myvinaphone.activity;


import com.meetme.android.horizontallistview.HorizontalListView;
import com.vnp.myvinaphone.MyApplication;
import com.vnp.myvinaphone.R;
import com.vnp.myvinaphone.R.id;
import com.vnp.myvinaphone.R.layout;
import com.vnp.myvinaphone.R.menu;
import com.vnp.myvinaphone.fragment.SuperAwesomeCardFragment;
import com.vnp.myvinaphone.util.PagerSlidingTabStrip;
import com.vnp.myvinaphone.util.PagerSlidingTabStrip.IconTabProvider;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;


public class MyVNPActivity extends ActionBarActivity {

	
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myvnp_activity);
        getActionBar().hide();
        
    	 
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        
//		if (pager.getAdapter() instanceof IconTabProvider) {
//			addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
//		} else {
//			addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
//		}
        
        
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		
		tabs.setViewPager(pager);
		tabs.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				System.out.println("onPageSelected:"+arg0+":"+tabs.getChildAt(arg0));
					
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		
	
    }

    
    
    
    public class MyPagerAdapter extends FragmentPagerAdapter implements IconTabProvider {
    	final int PAGE_COUNT = 4;
		private final String[] TITLES = { "TIỆN ÍCH", "DỊCH VỤ", "TIN TỨC", "TRA CỨU"};

	
		private final int[] ICONS = { R.drawable.icon_thongtincanhan, R.drawable.icon_thongtincanhan, 
		        R.drawable.icon_thongtincanhan,R.drawable.icon_thongtincanhan};
		
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}
		
		@Override
		public int getPageIconResId(int position) {
		    // TODO Auto-generated method stub
		    return ICONS[position];
		 }

		@Override
		public Fragment getItem(int position) {
			return SuperAwesomeCardFragment.newInstance(position);
		}

		@Override
		public String getPageContentDescription(int position) {
			// TODO Auto-generated method stub
			 return TITLES[position];
		}

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_vinaphone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
