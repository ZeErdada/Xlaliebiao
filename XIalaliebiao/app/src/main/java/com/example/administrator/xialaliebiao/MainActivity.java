package com.example.administrator.xialaliebiao;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * popuwido 下拉列表，属于组合式控件
 *
 * 1、首先添加ButterKnife的依赖，取消掉ActionBar，使用ToolBar代替
 * 2、完成整体布局，初始化控件，设置点击事件
 * 3、初始化Popuwindow所要显示的数据
 * 4、初始化Popuwindow控件的设置
 * 5、Popuwindow与ListView相关联
 * 6、三个Popuwindow所依附的LinearLayout，根据点击事件做相应的逻辑处理
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.supplier_list_product_tv)
    TextView mProductTv;  // 可以修改名称
    @Bind(R.id.supplier_list_product)
    LinearLayout mProduct;
    @Bind(R.id.supplier_list_sort_tv)
    TextView mSortTv;     // 可以修改名称
    @Bind(R.id.supplier_list_sort)
    LinearLayout mSort;
    @Bind(R.id.supplier_list_activity_tv)
    TextView mActivityTv;   // 可以修改名称
    @Bind(R.id.supplier_list_activity)
    LinearLayout mActivity;
    @Bind(R.id.supplier_list_lv)
    ListView mSupplierListLv;




    private ArrayList<Map<String, String>> mEnuData1;
    private ArrayList<Map<String, String>> mEnuData2;
    private ArrayList<Map<String, String>> mEnuData3;
    private PopupWindow mPopuMenu;
    private ListView mPopListView;
    private SimpleAdapter mMenuAdapter1;
    private SimpleAdapter mMenuAdapter2;
    private SimpleAdapter mMenuAdapter3;
    private  int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //初始化Popuwindow所要显示的数据
        initData();
        //初始化Popuwindow控件
        initPopMenu();

    }
    //初始化Popuwindow
    private void initPopMenu() {
        //把包裹ListView的布局XML文件转换成View对象
        View popview = LayoutInflater.from(this).inflate(R.layout.popuwin_supplier, null);
        //创建Popuwindow对象，参数1：popuwindow要显示的布局，参数2，3：定义popuwindow宽和高
        mPopuMenu = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //设置Popuwindow外部可以点击
        mPopuMenu.setOutsideTouchable(true);
        //设置popuwindow里面的ListView有焦点
        mPopuMenu.setFocusable(true);
        //设置动画必须先设置背景,没有设置就会无法执行动画
        mPopuMenu.setBackgroundDrawable(new ColorDrawable());
        //设置popuwindow结束监听事件
        mPopuMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置TextView的颜色,把所有LinearLayout的文本颜色该为灰色
                mProductTv.setTextColor(Color.parseColor("#5a5959"));
                mSortTv.setTextColor(Color.parseColor("#5a5959"));
                mActivityTv.setTextColor(Color.parseColor("#5a5959"));
            }
        });
        //设置点击popuwindow以外的地方，使popuwindow消失
        LinearLayout list_bottom = popview.findViewById(R.id.popwin_supplier_list_bottom);
        //给空白处设置颜色
        list_bottom.setBackgroundColor(Color.parseColor("#5a5959"));
        list_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当点击灰色区域，popuwindow消失
                mPopuMenu.dismiss();
            }
        });
        //获取Listview对象
        mPopListView = popview.findViewById(R.id.popwin_supplier_list_lv);
        //创建SimpleAdapter，一个ListView安卓原生封装的适配器
        mMenuAdapter1 = new SimpleAdapter(this, mEnuData1, R.layout.item_listview_popwin, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        mMenuAdapter2 = new SimpleAdapter(this, mEnuData2, R.layout.item_listview_popwin, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});
        mMenuAdapter3 = new SimpleAdapter(this, mEnuData3, R.layout.item_listview_popwin, new String[]{"name"}, new int[]{R.id.listview_popwind_tv});

        //设置popuwindow里的ListView点击事件，当点击ListView里的一个item时，将其数据显示到最上方
        mPopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //首先让popuwindow消失
                mPopuMenu.dismiss();
                //根据点击位置传回数据
                switch (type){
                    case 0:
                        String type1 = mEnuData1.get(i).get("name");
                        mProductTv.setText(type1);
                        break;
                    case 1:
                        String type2 = mEnuData2.get(i).get("name");
                        mSortTv.setText(type2);
                        break;
                    case 2:
                        String type3 = mEnuData3.get(i).get("name");
                        mActivityTv.setText(type3);
                        break;
                }
            }
        });
    }

    //初始化数据，需要三个Popuwindow，所以封装好三个数据，这里写的是假数据，开发用的是网上的数据
    private void initData() {
        //创建存放一个存放Popuwindow加载数据的大盒子，Map集合（键，值）
        mEnuData1 = new ArrayList<>();
        //存放String字符串数组
        String[] menuStr1 = new String[]{"全部", "粮油", "衣服", "图书", "电子产品",
                "酒水饮料", "水果"};
        //创建一个小盒子，存放编号和值
        Map<String,String> map1 ;
        for (int x = 0; x < menuStr1.length; x++) {
            map1 = new HashMap<String,String>();
            map1.put("name",menuStr1[x]);
            mEnuData1.add(map1);
        }

        //创建存放一个存放Popuwindow加载数据的大盒子，Map集合（键，值）
        mEnuData2 = new ArrayList<>();
        //存放String字符串数组
        String[] menuStr2 = new String[]{"综合排序", "配送费最低"};
        //创建一个小盒子，存放编号和值
        Map<String,String> map2 ;
        for (int x = 0; x < menuStr2.length; x++) {
            map2 = new HashMap<String,String>();
            map2.put("name",menuStr2[x]);
            mEnuData2.add(map2);
        }

        //创建存放一个存放Popuwindow加载数据的大盒子，Map集合（键，值）
        mEnuData3 = new ArrayList<>();
        //存放String字符串数组
        String[] menuStr3 = new String[]{"优惠活动", "特价活动", "免配送费",
                "可在线支付"};
        //创建一个小盒子，存放编号和值
        Map<String,String> map3 ;
        for (int x = 0; x < menuStr3.length; x++) {
            map3 = new HashMap<String,String>();
            map3.put("name",menuStr3[x]);
            mEnuData3.add(map3);
        }
        //每完成一个步骤，写代码测试有没有问题，以便轻松解决问题
    }

    @OnClick({R.id.supplier_list_product, R.id.supplier_list_sort, R.id.supplier_list_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            //第一个popuwindow所执行的点击后的逻辑
            case R.id.supplier_list_product:
                //设置其TextView点击变成绿色
                mProductTv.setTextColor(Color.GREEN);
                //设置popuwindow里的ListView适配器
                mPopListView.setAdapter(mMenuAdapter1);
                //让popuwindow显示出来 参数1：View对象，决定了popuwindow在哪个控件下显示  参数2，3：决定了popuwindow坐标（x,y）
                mPopuMenu.showAsDropDown(mProduct,0,2);
                type = 0;
                break;
            case R.id.supplier_list_sort:
                //设置其TextView点击变成绿色
                mSortTv.setTextColor(Color.GREEN);
                //设置popuwindow里的ListView适配器
                mPopListView.setAdapter(mMenuAdapter2);
                //让popuwindow显示出来 参数1：View对象，决定了popuwindow在哪个控件下显示  参数2，3：决定了popuwindow坐标（x,y）
                mPopuMenu.showAsDropDown(mProduct,0,2);
                type = 1;
                break;
            case R.id.supplier_list_activity:
                //设置其TextView点击变成绿色
                mActivityTv.setTextColor(Color.GREEN);
                //设置popuwindow里的ListView适配器
                mPopListView.setAdapter(mMenuAdapter3);
                //让popuwindow显示出来 参数1：View对象，决定了popuwindow在哪个控件下显示  参数2，3：决定了popuwindow坐标（x,y）
                mPopuMenu.showAsDropDown(mProduct,0,2);
                type = 2;
                break;
        }
    }

}
