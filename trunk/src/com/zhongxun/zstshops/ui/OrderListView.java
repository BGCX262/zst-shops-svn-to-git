package com.zhongxun.zstshops.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhongxun.zstshops.R;
import com.zhongxun.zstshops.adapter.OrderListAdapter;
import com.zhongxun.zstshops.http.ConnectCallBack;
import com.zhongxun.zstshops.http.HttpConnect;
import com.zhongxun.zstshops.http.Urls;
import com.zhongxun.zstshops.model.OrderInfo;
import com.zhongxun.zstshops.model.OrderListResponse;
import com.zhongxun.zstshops.utils.Constant;
import com.zhongxun.zstshops.utils.CyptoUtils;
import com.zhongxun.zstshops.utils.JsonUtils;
import com.zhongxun.zstshops.utils.MyLog;
import com.zhongxun.zstshops.view.CustomToast;
import com.zhongxun.zstshops.view.PullAndLoadListView;
import com.zhongxun.zstshops.view.PullAndLoadListView.OnLoadMoreListener;
import com.zhongxun.zstshops.view.PullToRefreshListView.OnRefreshListener;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * 订单列表视图
 * @author Administrator
 *
 */
public class OrderListView extends LinearLayout {
    
	public OrderListView(Context context) {
		super(context);
		this.mcontext =context;
         init(context);
         
	}
	
	public OrderListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mcontext =context;
		init(context);

	}
    private int currentPage=1;
	private Button myshop_member,other_member;
	private Spinner spinner_year,spinner_month;
	private Context mcontext;
	private TextView total_count,total_price;
	private int currentYear,currentMonth;
	private PullAndLoadListView listView;
	private String years[]={"2013","2014","2015","2016","2017"};
	private String months[]={"一","二","三","四","五","六","七","八","九","十","十一","十二",};
   
	private String yearStr,monthStr;//年月的查询条件
	
	/*查询会员的条件*/
	private int TYPE_ALL=-1;//所有会员
	private int TYPE_MYSHOP=1;//本店会员
	private int TYPE_OTHERSHOP=0;//其他店会员
	private int TYPE=-1;
	
	private List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
	
	private boolean flag =false;
	private void init(Context context)
    {   
    	
    	Calendar cal=Calendar.getInstance();    
    	currentYear=cal.get(Calendar.YEAR);    yearStr =currentYear+"";
    	currentMonth=cal.get(Calendar.MONTH)+1;  monthStr=currentMonth+"";
         
    	
    	
    	View view  = View.inflate(context, R.layout.orderlist, this);
    	myshop_member = (Button) view.findViewById(R.id.myshop_member);
    	other_member = (Button) view.findViewById(R.id.other_member);
    	spinner_year = (Spinner) view.findViewById(R.id.spinner_year);
    	spinner_month = (Spinner) view.findViewById(R.id.spinner_month);
    	other_member = (Button) view.findViewById(R.id.other_member);
    	total_count = (TextView) view.findViewById(R.id.total_count);
    	total_price = (TextView) view.findViewById(R.id.total_price);
    	listView = (PullAndLoadListView) view.findViewById(R.id.listview);
    	
    	
    	initButton();
    	initSpinner();
    	initListView();
    	
    	
    	if(!flag)
    	getDataTask(false, yearStr, monthStr, TYPE+"",false);
    	
    	
    }
    
	int tipcount=0;//提示次数
	
	private void initListView()
	{
		listView.setOnRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				currentPage=1;
				tipcount=0;
				// Do work to refresh the list here.
				getDataTask(true, yearStr, monthStr, TYPE+"",true);
			}
		});
		
		
		listView.setOnLoadMoreListener(new OnLoadMoreListener() {
		
			@Override
			public void onLoadMore() {
				if(currentPage>totalPage)
				{   
                   listView.onLoadMoreComplete();
                   if(tipcount<1)
                   {
			        CustomToast.TransBgToast(mcontext, "没有更多数据");//提示没有数据
			        tipcount++;
                   }
					return;
				}
				getDataTask(false, yearStr, monthStr, TYPE+"",false);
				
			}
		});
	}
    
    private void initButton(){
    	myshop_member.setSelected(true);
    	other_member.setSelected(true);
    	myshop_member.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				changeButtonState(myshop_member);
			}
		});
    	
    	other_member.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				changeButtonState(other_member);
			}
		});
    	
    }
	
    
    private void changeButtonState(Button btn){
    	btn.setSelected(btn.isSelected()==true?false:true);
    	if(myshop_member.isSelected()&&other_member.isSelected())
    	{
    		TYPE = TYPE_ALL;
    		currentPage=1;
    		getDataTask(false, yearStr, monthStr, TYPE+"",true);
    	}
    	if(myshop_member.isSelected()&&!other_member.isSelected())
    	{
    		TYPE = TYPE_MYSHOP;
    		currentPage=1;
    		getDataTask(false, yearStr, monthStr, TYPE+"",true);
    	}
    	if(!myshop_member.isSelected()&&other_member.isSelected())
    	{
    		TYPE = TYPE_OTHERSHOP;
    		currentPage=1;
    		getDataTask(false, yearStr, monthStr, TYPE+"",true);
    	}
    	if(!myshop_member.isSelected()&&!other_member.isSelected()){
    		System.out.println("00");
    		//清空数据
        	if(adapter!=null){
        		adapter.clearDatas();
        	}
    	}
    }
    
    
    private void initSpinner(){
    	 
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(mcontext,android.R.layout.simple_spinner_item,years);
    	//设置下拉列表的风格  
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
    	spinner_year.setAdapter(adapter);
    	spinner_year.setOnItemSelectedListener(new SpinnerYearSelectedListener());
    	int i=0;
    	for(;i<years.length;i++)
    	{
    		if(years[i].equals(currentYear+"")){
    			spinner_year.setSelection(i, true);//设置被选中的项
    			break;
    		}
  
    	}
    	
    	if(i>=years.length){
    		spinner_year.setSelection(0, true);//设置被选中的项
    	}
    	
    	ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(mcontext,android.R.layout.simple_spinner_item,months);
    	//设置下拉列表的风格  
    	adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
    	spinner_month.setAdapter(adapterMonth);
    	spinner_month.setOnItemSelectedListener(new SpinnerMonthSelectedListener());
    	for(int j =0;j<months.length;j++)
    	{
    		if(months[j].equals(getValueofCurrenMonth(currentMonth))){
    			spinner_month.setSelection(j, true);//设置被选中的项
    			break;
    		}
  
    	}
    }
    
    class SpinnerYearSelectedListener implements OnItemSelectedListener{  
  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
        	MyLog.e("选择年", years[arg2]);
        	yearStr = years[arg2];
        	currentPage=1;
        	if(flag)
        	getDataTask(false, yearStr, monthStr, TYPE+"",true);
        	
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }  
    
    
    class SpinnerMonthSelectedListener implements OnItemSelectedListener{  
    	  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
           	MyLog.e("选择月", months[arg2]);
           	monthStr = months[arg2];
           	
           	currentPage=1;
           	if(flag)
           	getDataTask(false, yearStr, monthStr, TYPE+"",true);
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
    
    
    private String getValueofCurrenMonth(int val)
    {
    	if(val==1){
    		return "一";
    	}
    	if(val==2){
    		return "二";
    	}
    	if(val==3){
    		return "三";
    	}
    	if(val==4){
    		return "四";
    	}
    	if(val==5){
    		return "五";
    	}
    	if(val==6){
    		return "六";
    	}
    	if(val==7){
    		return "七";
    	}
    	if(val==8){
    		return "八";
    	}
    	if(val==9){
    		return "九";
    	}
    	if(val==10){
    		return "十";
    	}
    	if(val==11){
    		return "十一";
    	}
    	if(val==12){
    		return "十二";
    	}
    	return "一";
    }
    
    private int getintValueofCurrenMonth(String val)
    {
    	if(val.equals("一")){
    		return 1;
    	}
    	if(val.equals("二")){
    		return 2;
    	}
    	if(val.equals("三")){
    		return 3;
    	}
    	if(val.equals("四")){
    		return 4;
    	}
    	if(val.equals("五")){
    		return 5;
    	}
    	if(val.equals("六")){
    		return 6;
    	}
    	if(val.equals("七")){
    		return 7;
    	}
    	if(val.equals("八")){
    		return 8;
    	}
    	if(val.equals("九")){
    		return 9;
    	}
    	if(val.equals("十")){
    		return 10;
    	}
    	if(val.equals("十一")){
    		return 11;
    	}
    	if(val.equals("十二")){
    		return 12;
    	}
    	return currentMonth;
    }
    
    
    private OrderListAdapter adapter;
    
    /**
     * 根据条件查询订单
     * @param down  ture执行的是下拉动作
     * @param year   年
     * @param month  月
     * @param type   会员类型
     */
    private void getDataTask(final boolean down,String year,String month,String type,final boolean isClear)
    {   
    	month = getintValueofCurrenMonth(month)+"";
    	MyLog.e("OrderListView", "down-"+down+" year-"+year+" month-"+month+" type-"+type);
		Map params = new HashMap();
		params.put("S", CyptoUtils.encode(Urls.IV, Constant.LOGINDATA.getShopID()+""));
		params.put("SID", Constant.LOGINDATA.getShopID()+"");
		params.put("PageIndex", currentPage+"");
		params.put("IsMember", TYPE+"");
		params.put("PageSize", Urls.PAGE_SIZE);
		params.put("Year", yearStr);
		params.put("Month", month);
		
		HttpConnect connect = new HttpConnect();
		connect.setCallBack(new ConnectCallBack() {
			
			@Override
			public void onSuccess(String data) {
				PerformanceActivity.hideloading();
				
				currentPage++;
				flag=true;
				MyLog.e("orderlistview", data+"");
				
				if(down)
				{
					listView.onRefreshComplete();
				}else
				{
					listView.onLoadMoreComplete();
				}
				if(adapter!=null&&isClear)
				{
					adapter.clearDatas();//清空数据
				}
				
				if(!TextUtils.isEmpty(data))
				{
					OrderListResponse res  = JsonUtils.fromJson(data, OrderListResponse.class);
					computeTotalPage(res.getTotal());//计算页数
					
					if(res.getRows()!= null&& res.getRows().size()>0)
					{   
						
						if(adapter==null){
							adapter = new OrderListAdapter(mcontext, res.getRows());
							listView.setAdapter(adapter);
						}else
						{
							adapter.addDatas(res.getRows());
						}
						
					}
					else
					{
						CustomToast.TransBgToast(mcontext, "没有记录");
					}
				}
				else
				{
					CustomToast.TransBgToast(mcontext, "获取数据失败");
				}
			}
			
			@Override
			public void onPrepare() {
				PerformanceActivity.showloading();
			}
			
			@Override
			public void onFialure(String failMsg) 
			{   
				CustomToast.TransBgToast(mcontext, "获取数据失败");
				PerformanceActivity.hideloading();
				if(down)
				{
					listView.onRefreshComplete();
					System.out.println("onRefreshComplete---");
				}else
				{
					listView.onLoadMoreComplete();
					System.out.println("onLoadMoreComplete---");
				}
			}
		} );
		connect.post(params, Urls.URL_QUERY_ORDER_LIST);
    	
    }
    
    private int totalPage=1;
    private void  computeTotalPage(int total){
    	if((total%10)==0)
    	{
    		totalPage = total/10;
    	}else
    	{
    		totalPage = total/10+1;
    	}
    }
    
    
    private void getOrderListPrice()
    {
		Map params = new HashMap();
		params.put("S", CyptoUtils.encode(Urls.IV, Constant.LOGINDATA.getShopID()+""));
		params.put("SID", Constant.LOGINDATA.getShopID()+"");
		params.put("IsMember", TYPE+"");
		params.put("Year", yearStr);
		params.put("Month", getintValueofCurrenMonth(monthStr)+"");
		
		HttpConnect connect = new HttpConnect();
		connect.setCallBack(new ConnectCallBack() {

			@Override
			public void onPrepare() {
				
			}

			@Override
			public void onSuccess(String data) {
				if(!TextUtils.isEmpty(data))
				{
					
				}
				else
				{
					
				}
			}

			@Override
			public void onFialure(String failMsg) {
              CustomToast.TransBgToast(mcontext, "查询订单价格失败");
				
			}
			
		});
		
		connect.post(params, Urls.URL_QUERY_ORDER_LIST_PRICE);
    }

}
