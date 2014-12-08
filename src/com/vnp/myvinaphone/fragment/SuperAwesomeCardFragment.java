/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vnp.myvinaphone.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.meetme.android.horizontallistview.HorizontalListView;
import com.vnp.myvinaphone.MyApplication;
import com.vnp.myvinaphone.R;
import com.vnp.myvinaphone.R.id;
import com.vnp.myvinaphone.R.layout;
import com.vnp.myvinaphone.adapter.CustomArrayAdapter;
import com.vnp.myvinaphone.adapter.ServiceAdapter;
import com.vnp.myvinaphone.bean.CustomData;
import com.vnp.myvinaphone.bean.VinaphoneService;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

public class SuperAwesomeCardFragment extends Fragment {
	private static final String url = "http://www.json-generator.com/api/json/get/cjTujPbXOq";
	
	private HorizontalListView mHlvUsingServiceList;
	private HorizontalListView mHlvNewServiceList;
	private HorizontalListView mHlvCommonServiceList;
	private HorizontalListView mHlvOtherServiceList;
	
	private List<VinaphoneService> serviceList = new ArrayList<VinaphoneService>();
//	private List<VinaphoneService> serviceList = new ArrayList<VinaphoneService>();
//	private List<VinaphoneService> serviceList = new ArrayList<VinaphoneService>();
//	private List<VinaphoneService> serviceList = new ArrayList<VinaphoneService>();
//	private ListView listView;
	private ServiceAdapter adapter;

	private static final String ARG_POSITION = "position";

	private int position;

	public static SuperAwesomeCardFragment newInstance(int position) {
		SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int layout = 0;
		View vService;
		// LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);
		//
		// Activity a=getActivity();
		// FrameLayout fl = new FrameLayout(a);
		//
		// a.setContentView(R.layout.utilites_layout);

		/*
		 * fl.setLayoutParams(params);
		 * 
		 * final int margin = (int)
		 * TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
		 * getResources() .getDisplayMetrics());
		 * 
		 * TextView v = new TextView(getActivity()); params.setMargins(margin,
		 * margin, margin, margin); v.setLayoutParams(params);
		 * v.setLayoutParams(params); v.setGravity(Gravity.CENTER); //
		 * v.setBackgroundResource(R.drawable.background_card);
		 * v.setText("CARD " + (position + 1));
		 * 
		 * fl.addView(v);
		 */

		switch (position) {
		case 0:
			layout = R.layout.tab_utilites_layout;
			vService = inflater.inflate(layout, container, false);
			break;

		case 1:
			layout = R.layout.tab_services_layout;
			vService = inflater.inflate(layout, container, false);

			mHlvUsingServiceList = (HorizontalListView) vService.findViewById(R.id.hlvUsingServiceList);
			mHlvNewServiceList = (HorizontalListView) vService.findViewById(R.id.hlvNewServiceList);
			mHlvCommonServiceList = (HorizontalListView) vService.findViewById(R.id.hlvCommonServiceList);
			mHlvOtherServiceList = (HorizontalListView) vService.findViewById(R.id.hlvOtherServiceList);
			serviceContructor();
			break;

		case 2:
			layout = R.layout.tab_news_layout;
			vService = inflater.inflate(layout, container, false);
			break;

		case 3:
			layout = R.layout.tab_search_layout;
			vService = inflater.inflate(layout, container, false);
			break;

		default:
			layout = R.layout.tab_utilites_layout;
			vService = inflater.inflate(layout, container, false);
			break;
		}

		return vService;
	}

	private void serviceContructor() {
		
		adapter = new ServiceAdapter(getActivity(), serviceList);
		mHlvUsingServiceList.setAdapter(adapter);
		mHlvNewServiceList.setAdapter(adapter);
		mHlvCommonServiceList.setAdapter(adapter);
		mHlvOtherServiceList.setAdapter(adapter);
	
		JsonArrayRequest movieReq = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
//						hidePDialog();

						// Parsing json
						for (int i = 0; i < response.length(); i++) {
							try {

								JSONObject obj = response.getJSONObject(i);
								VinaphoneService vinaphoneService = new VinaphoneService();
								vinaphoneService.setServiceName(obj.getString("title"));
								vinaphoneService.setServiceImageUrl(obj.getString("image"));
								
								serviceList.add(vinaphoneService);

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						// notifying list adapter about data changes
						// so that it renders the list view with updated data
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
//						VolleyLog.d(TAG, "Error: " + error.getMessage());
						error.printStackTrace();
//						hidePDialog();

					}
				});

		// Adding request to request queue
		MyApplication.getInstance().addToRequestQueue(movieReq);
	}




}