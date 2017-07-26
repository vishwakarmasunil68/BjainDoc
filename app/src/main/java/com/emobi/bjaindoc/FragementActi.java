package com.emobi.bjaindoc;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.EditText;

public class FragementActi extends FragmentActivity {
	ViewPager Tab;
    TabPagerAdapter TabAdapter;
	Button savebtn;
	ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_tabs);
        
        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());
//		savebtn=(Button)findViewById(R.id.savebtn) ;
        Tab = (ViewPager)findViewById(R.id.viewpager);

		/*savebtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"button",Toast.LENGTH_LONG);
				addMotorAPi();
			}
		})*/;
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                       
                    	actionBar = getActionBar();
                    	actionBar.setSelectedNavigationItem(position);                    }
                });
        Tab.setAdapter(TabAdapter);
        
        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){

			@Override
			public void onTabReselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			 public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

	            Tab.setCurrentItem(tab.getPosition());
	        }

			@Override
			public void onTabUnselected(ActionBar.Tab tab,
					FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}};
			//Add New Tab
			actionBar.addTab(actionBar.newTab().setText("Chat").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("Medication").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("Prescription").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("Doctornotes").setTabListener(tabListener));
			actionBar.addTab(actionBar.newTab().setText("Updatelist").setTabListener(tabListener));

    }
	public String ValidateEdit(EditText edit){
		if(edit.getText().toString().equals(null)||edit.getText().toString().equals("")){
			return "";
		}
		else{
			return edit.getText().toString().trim();
		}
	}

    
}
