package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BaseData;
import com.example.myapplication.BottomBar;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public static final String TAG = "MyFragment";

    private ListView lv_left;
    private ListView lv_right;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        ArrayList<BaseData> lists = new ArrayList<BaseData>();
        String title[] = {"蔬菜1", "水果1", "姓氏1", "蔬菜2", "水果2", "姓氏2", "蔬菜3",
                "水果3", "姓氏3"};
        String name1[] = {"萝卜", "大葱", "茄子", "大蒜", "生姜", "萝卜", "大葱", "茄子",
                "大蒜", "生姜", "萝卜", "大葱"};
        String name2[] = {"苹果", "梨", "香蕉", "西瓜", "橘子", "大枣", "菠萝", "红提", "葡萄",
                "樱桃", "椰子"};
        String name3[] = {"郑", "王", "伊", "荆", "汤", "王", "孙", "李", "钱", "赵",
                "祁", "韦", "宏"};
        for (int i = 0; i < name1.length; i++) {
            lists.add(new BaseData(name1[i] + 1, i, title[0]));
        }
        for (int i = 0; i < name2.length; i++) {
            lists.add(new BaseData(name2[i] + 1, i, title[1]));
        }
        for (int i = 0; i < name3.length; i++) {
            lists.add(new BaseData(name3[i] + 1, i, title[2]));
        }
        for (int i = 0; i < name1.length; i++) {
            lists.add(new BaseData(name1[i] + 2, i, title[3]));
        }
        for (int i = 0; i < name2.length; i++) {
            lists.add(new BaseData(name2[i] + 2, i, title[4]));
        }
        for (int i = 0; i < name3.length; i++) {
            lists.add(new BaseData(name3[i] + 2, i, title[5]));
        }
        for (int i = 0; i < name1.length; i++) {
            lists.add(new BaseData(name1[i] + 3, i, title[6]));
        }
        for (int i = 0; i < name2.length; i++) {
            lists.add(new BaseData(name2[i] + 3, i, title[7]));
        }
        for (int i = 0; i < name3.length; i++) {
            lists.add(new BaseData(name3[i] + 3, i, title[8]));
        }

//假数据创建的方式比较low,大家不喜勿喷

//看下边这个集合,这个集合是右边所有要显示标题的条目的position

        ArrayList<String> showTitle = new ArrayList<String>();
        for (int i = 0; i < lists.size(); i++) {
            if (i == 0) {//第一个必须显示
                showTitle.add(i + "");
            } else if (!TextUtils.equals(lists.get(i).getTitle(),
                    lists.get(i - 1).getTitle())) {//如果跟上一个条目的type不一样就必须显示
                showTitle.add(i + "");
            }
        }

        lv_left = root.findViewById(R.id.lv_left);

        lv_right = root.findViewById(R.id.lv_right);

        TextView tv_title = root.findViewById(R.id.tv_title);

        LeftAdapter leftAdapter = new LeftAdapter(getContext());


        lv_left.setAdapter(leftAdapter);

        RightAdapter rightAdapter = new RightAdapter(getContext());

        lv_right.setAdapter(rightAdapter);
        rightAdapter.updateData(lists);// 将数据源传递给Listview

        tv_title.setText(lists.get(0).getTitle());// 主标题栏设置默认初始值


        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {





            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                int firstVisibleItem = lv_right.getFirstVisiblePosition();
                //右边Listview当前第一个可见条目的position
//                updateLeftListview(firstVisibleItem, arg2);
                lv_right.setSelection(Integer.parseInt(showTitle.get(arg2)));
//arg2是点击(选择)左边条目的第几个
//根据这个数字我们就能通过Integer.parseInt(showTitle.get(arg2))得到在点击左边后应该跳转到右边第几个条目
                //  通过etSelection方法跳转
            }
        });

        return root;
    }




}