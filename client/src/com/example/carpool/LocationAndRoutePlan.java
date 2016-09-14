package com.example.carpool;



import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.carsharing.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



	/**
	 * 此demo用来展示如何进行驾车、步行�?公交路线搜索并在地图使用RouteOverlay、TransitOverlay绘制
	 * 同时展示如何进行节点浏览并弹出泡�?
	 */
	public class LocationAndRoutePlan extends Activity implements BaiduMap.OnMapClickListener,
	        OnGetRoutePlanResultListener {
	    //浏览路线节点相关
	    Button mBtnPre = null;//上一个节�?
	    Button mBtnNext = null;//下一个节�?
	    int nodeIndex = -1;//节点索引,供浏览节点时使用
	    RouteLine route = null;
	    OverlayManager routeOverlay = null;
	    boolean useDefaultIcon = false;
	    private TextView popupText = null;//泡泡view

	    //地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
	    //如果不处理touch事件，则无需继承，直接使用MapView即可
	    MapView mMapView = null;    // 地图View
	    BaiduMap mBaidumap = null;
	    //搜索相关
	    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使�?
	    
	    
	    
	    
	    
		// 定位相关
		LocationClient mLocClient;
		public MyLocationListenner myListener ;
		private LocationMode mCurrentMode;
		BitmapDescriptor mCurrentMarker;

//		BaiduMap mBaiduMap;

		// UI相关
		Button requestLocButton;
		boolean isFirstLoc = true;// 是否首次定位
	    
	    
		
		
		
	    
	    

	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        SDKInitializer.initialize(getApplicationContext());  
	        setContentView(R.layout.activity_locationandrouteplan);
	        CharSequence titleLable = "路线规划功能";
	        setTitle(titleLable);
	        myListener = new MyLocationListenner();
	        
	        
	        
	        requestLocButton = (Button) findViewById(R.id.location_button);//定位button
			mCurrentMode = LocationMode.NORMAL;//设为normal模式

			OnClickListener btnClickListener = new OnClickListener() {
				public void onClick(View v) {
					mLocClient.start();//点了�?��定位
					isFirstLoc = true;//设为真才能重复定�?
					
					
				}
			};
			requestLocButton.setOnClickListener(btnClickListener);//设置监听
			
			

			
			
			
	        //初始化地�?
	        mMapView = (MapView) findViewById(R.id.map);
	        mBaidumap = mMapView.getMap();
	        mBtnPre = (Button) findViewById(R.id.pre);
	        mBtnNext = (Button) findViewById(R.id.next);
	        mBtnPre.setVisibility(View.INVISIBLE);
	        mBtnNext.setVisibility(View.INVISIBLE);
	        mBaidumap.setMyLocationEnabled(true);//启动定位图层才能显示出小蓝点
	        //地图点击事件处理
	        mBaidumap.setOnMapClickListener(this);
	        // 初始化搜索模块，注册事件监听
	        mSearch = RoutePlanSearch.newInstance();
	        mSearch.setOnGetRoutePlanResultListener(this);
			
	     // 定位初始�?
	     		mLocClient = new LocationClient(this);
	     		mLocClient.registerLocationListener(myListener);
	     		LocationClientOption option = new LocationClientOption();
	     		option.setOpenGps(true);// 打开gps
	     		option.setCoorType("bd09ll"); // 设置坐标类型
	     		option.setScanSpan(1000);
	     		mLocClient.setLocOption(option);
	     		mLocClient.start();
			
	    }

	    
	    
		/**
		 * 定位SDK监听函数
		 */
		public class MyLocationListenner implements BDLocationListener {

			public void onReceiveLocation(BDLocation location) {
				// map view �?毁后不在处理新接收的位置
				if (location == null || mMapView == null)
					return;
				MyLocationData locData = new MyLocationData.Builder()
						.accuracy(location.getRadius())
						// 此处设置�?发�?�获取到的方向信息，顺时�?0-360
						.direction(100).latitude(location.getLatitude())
						.longitude(location.getLongitude()).build();
				mBaidumap.setMyLocationData(locData);
				if (isFirstLoc) {
					isFirstLoc = false;
					LatLng ll = new LatLng(location.getLatitude(),
							location.getLongitude());
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
					mBaidumap.animateMapStatus(u);
				}
			}

			public void onReceivePoi(BDLocation poiLocation) {
			}
		}
	    
	    
	    
	    
	    
	    /**
	     * 发起路线规划搜索示例
	     *
	     * @param v
	     */
	    public void SearchButtonProcess(View v) {
	        //重置浏览节点的路线数�?
	        route = null;
	        mBtnPre.setVisibility(View.INVISIBLE);
	        mBtnNext.setVisibility(View.INVISIBLE);
	        mBaidumap.clear();
	        // 处理搜索按钮响应
	        EditText editSt = (EditText) findViewById(R.id.start);
	        EditText editEn = (EditText) findViewById(R.id.end);
	        //设置起终点信息，对于tranist search 来说，城市名无意�?
	        PlanNode stNode = PlanNode.withCityNameAndPlaceName("成都", editSt.getText().toString());//获取起点
	        PlanNode enNode = PlanNode.withCityNameAndPlaceName("成都", editEn.getText().toString());//获取终点

	        // 实际使用中请对起点终点城市进行正确的设定
	        if (v.getId() == R.id.drive) {
	            mSearch.drivingSearch((new DrivingRoutePlanOption())
	                    .from(stNode)
	                    .to(enNode));
	        } else if (v.getId() == R.id.transit) {
	            mSearch.transitSearch((new TransitRoutePlanOption())
	                    .from(stNode)
	                    .city("成都")
	                    .to(enNode));
	        } else if (v.getId() == R.id.walk) {
	            mSearch.walkingSearch((new WalkingRoutePlanOption())
	                    .from(stNode)
	                    .to(enNode));
	        }
	    }

	    /**
	     * 节点浏览示例
	     *
	     * @param v
	     */
	    public void nodeClick(View v) {
	        if (route == null ||
	                route.getAllStep() == null) {
	            return;
	        }
	        if (nodeIndex == -1 && v.getId() == R.id.pre) {
	        	return;
	        }
	        //设置节点索引
	        if (v.getId() == R.id.next) {
	            if (nodeIndex < route.getAllStep().size() - 1) {
	            	nodeIndex++;
	            } else {
	            	return;
	            }
	        } else if (v.getId() == R.id.pre) {
	        	if (nodeIndex > 0) {
	        		nodeIndex--;
	        	} else {
	            	return;
	            }
	        }
	        //获取节结果信�?
	        LatLng nodeLocation = null;
	        String nodeTitle = null;
	        Object step = route.getAllStep().get(nodeIndex);
	        if (step instanceof DrivingRouteLine.DrivingStep) {
	            nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrace().getLocation();
	            nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
	        } else if (step instanceof WalkingRouteLine.WalkingStep) {
	            nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrace().getLocation();
	            nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
	        } else if (step instanceof TransitRouteLine.TransitStep) {
	            nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrace().getLocation();
	            nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
	        }

	        if (nodeLocation == null || nodeTitle == null) {
	            return;
	        }
	        //移动节点至中�?
	        mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
	        // show popup
	        popupText = new TextView(LocationAndRoutePlan.this);
	        popupText.setBackgroundResource(R.drawable.popup);
	        popupText.setTextColor(0xFF000000);
	        popupText.setText(nodeTitle);
	        mBaidumap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));

	    }

	    /**
	     * 切换路线图标，刷新地图使其生�?
	     * 注意�?起终点图标使用中心对�?
	     */
	    public void changeRouteIcon(View v) {
	        if (routeOverlay == null) {
	            return;
	        }
	        if (useDefaultIcon) {
	            ((Button) v).setText("自定义起终点图标");
	            Toast.makeText(this,
	                    "将使用系统起终点图标",
	                    Toast.LENGTH_SHORT).show();

	        } else {
	            ((Button) v).setText("系统起终点图�?");
	            Toast.makeText(this,
	                    "将使用自定义起终点图�?",
	                    Toast.LENGTH_SHORT).show();

	        }
	        useDefaultIcon = !useDefaultIcon;
	        routeOverlay.removeFromMap();
	        routeOverlay.addToMap();
	    }


	    @Override
	    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	        super.onRestoreInstanceState(savedInstanceState);
	    }

	    public void onGetWalkingRouteResult(WalkingRouteResult result) {
	        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
	            Toast.makeText(LocationAndRoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
	        }
	        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
	            //起终点或途经点地�?��岐义，�?过以下接口获取建议查询信�?
	            //result.getSuggestAddrInfo()
	            return;
	        }
	        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	            nodeIndex = -1;
	            mBtnPre.setVisibility(View.VISIBLE);
	            mBtnNext.setVisibility(View.VISIBLE);
	            route = result.getRouteLines().get(0);
	            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaidumap);
	            mBaidumap.setOnMarkerClickListener(overlay);
	            routeOverlay = overlay;
	            overlay.setData(result.getRouteLines().get(0));
	            overlay.addToMap();
	            overlay.zoomToSpan();
	        }

	    }

	    public void onGetTransitRouteResult(TransitRouteResult result) {

	        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
	            Toast.makeText(LocationAndRoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
	        }
	        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
	            //起终点或途经点地�?��岐义，�?过以下接口获取建议查询信�?
	            //result.getSuggestAddrInfo()
	            return;
	        }
	        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	            nodeIndex = -1;
	            mBtnPre.setVisibility(View.VISIBLE);
	            mBtnNext.setVisibility(View.VISIBLE);
	            route = result.getRouteLines().get(0);
	            TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidumap);
	            mBaidumap.setOnMarkerClickListener(overlay);
	            routeOverlay = overlay;
	            overlay.setData(result.getRouteLines().get(0));
	            overlay.addToMap();
	            overlay.zoomToSpan();
	        }
	    }

	    public void onGetDrivingRouteResult(DrivingRouteResult result) {
	        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
	            Toast.makeText(LocationAndRoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
	        }
	        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
	            //起终点或途经点地�?��岐义，�?过以下接口获取建议查询信�?
	            //result.getSuggestAddrInfo()
	            return;
	        }
	        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
	            nodeIndex = -1;
	            mBtnPre.setVisibility(View.VISIBLE);
	            mBtnNext.setVisibility(View.VISIBLE);
	            route = result.getRouteLines().get(0);
	            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
	            routeOverlay = overlay;
	            mBaidumap.setOnMarkerClickListener(overlay);
	            overlay.setData(result.getRouteLines().get(0));
	            overlay.addToMap();
	            overlay.zoomToSpan();
	        }
	    }

	    //定制RouteOverly
	    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

	        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
	            super(baiduMap);
	        }

	        @Override
	        public BitmapDescriptor getStartMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
	            }
	            return null;
	        }

	        @Override
	        public BitmapDescriptor getTerminalMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
	            }
	            return null;
	        }
	    }

	    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

	        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
	            super(baiduMap);
	        }

	        @Override
	        public BitmapDescriptor getStartMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
	            }
	            return null;
	        }

	        @Override
	        public BitmapDescriptor getTerminalMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
	            }
	            return null;
	        }
	    }

	    private class MyTransitRouteOverlay extends TransitRouteOverlay {

	        public MyTransitRouteOverlay(BaiduMap baiduMap) {
	            super(baiduMap);
	        }

	        @Override
	        public BitmapDescriptor getStartMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
	            }
	            return null;
	        }

	        @Override
	        public BitmapDescriptor getTerminalMarker() {
	            if (useDefaultIcon) {
	                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
	            }
	            return null;
	        }
	    }

	    public void onMapClick(LatLng point) {
	        mBaidumap.hideInfoWindow();
	    }

	    public boolean onMapPoiClick(MapPoi poi) {
	    	return false;
	    }

	    @Override
	    protected void onPause() {
	        mMapView.onPause();
	        super.onPause();
	    }

	    @Override
	    protected void onResume() {
	        mMapView.onResume();
	        super.onResume();
	    }

	    @Override
	    protected void onDestroy() {
	    	
	    	mLocClient.stop();
			// 关闭定位图层
			mBaidumap.setMyLocationEnabled(false);
			
	        mSearch.destroy();
	        mMapView.onDestroy();
	        super.onDestroy();
	        
	        
	        
	    }

	}
