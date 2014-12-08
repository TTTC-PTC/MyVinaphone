package com.vnp.myvinaphone.adapter;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vnp.myvinaphone.MyApplication;
import com.vnp.myvinaphone.R;
import com.vnp.myvinaphone.bean.VinaphoneService;

public class ServiceAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<VinaphoneService> movieItems;
	ImageLoader imageLoader  = MyApplication.getInstance().getImageLoader();

	public ServiceAdapter(Activity activity, List<VinaphoneService> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;
	}

	@Override
	public int getCount() {
		return movieItems.size();
	}

	@Override
	public Object getItem(int location) {
		return movieItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.service_layout, null);

		if (imageLoader == null)
			imageLoader = MyApplication.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.service_image);
		TextView title = (TextView) convertView.findViewById(R.id.service_name);
		
		// getting movie data for the row
		VinaphoneService m = movieItems.get(position);

		// thumbnail image
		thumbNail.setImageUrl(m.getServiceImageUrl(), imageLoader);
		
		// title
		title.setText(m.getServiceName());
		
		
		return convertView;
	}

}