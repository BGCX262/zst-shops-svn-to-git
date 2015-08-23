package com.zhongxun.zstshops.adapter;

import java.util.List;

import com.zhongxun.zstshops.R;
import com.zhongxun.zstshops.model.OrderInfo;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {
	private List<OrderInfo> infos;
	private Context context;
	public OrderListAdapter(Context context,List<OrderInfo> infos){
		this.infos=infos;
		this.context=context;
	}
	@Override
	public int getCount() {
       if(infos==null) return 0;
		return infos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		ViewHolder holder =null;
		if(convertView==null)
		{
			convertView= View.inflate(context, R.layout.order_list_item, null);
			holder = new ViewHolder();
			holder.order_code = (TextView) convertView.findViewById(R.id.order_code);
			holder.order_price = (TextView) convertView.findViewById(R.id.order_price);
			holder.order_time = (TextView) convertView.findViewById(R.id.order_time);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(pos%2==0)
		{
			holder.layout.setBackgroundColor(context.getResources().getColor(R.color.order_list_item_bg));
		}
		else
		{
			holder.layout.setBackgroundColor(Color.WHITE);
		}
		
		holder.order_code.setText(infos.get(pos).getOrderID());
		holder.order_price.setText(infos.get(pos).getPrice());
		holder.order_time.setText(infos.get(pos).getAddTime());
		
		return convertView;
	}

	public static class ViewHolder
	{
		public TextView order_code;
		public TextView order_price;
		public TextView order_time;
		private LinearLayout layout;
	}
	
	
	public void addDatas(List<OrderInfo> infos){
		this.infos.addAll(infos);
		this.notifyDataSetChanged();
		
	}
	
	
	public void clearDatas(){
		if(infos!=null){
			infos.clear();
			this.notifyDataSetChanged();
		}
	}

}
