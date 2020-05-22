package com.example.myapplication.ui.dashboard;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.BaseData;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RightAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<BaseData> data = new ArrayList<>();

    public RightAdapter(Context context) {
        super();
        this.context = context;
    }


    public void updateData(ArrayList<BaseData> lists) {
        data.clear();
        data.addAll(lists);
        this.notifyDataSetChanged();
    }

    public void setData(ArrayList<BaseData> list) {
        this.data = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_right, null);
            vh = new ViewHold();
            convertView.setTag(vh);
            vh.tv_content = (TextView) convertView
                    .findViewById(R.id.tv_content);
            vh.tv_right = (TextView) convertView.findViewById(R.id.tv_right);

            vh.add_to_car = convertView.findViewById(R.id.add_to_car);

        } else {
            vh = (ViewHold) convertView.getTag();
        }
        vh.tv_content.setText(data.get(position).getName());

        if (position == 0) {
            vh.tv_right.setText(data.get(position).getTag());

        } else if (!TextUtils.equals(data.get(position).getTag(),
                data.get(position - 1).getTag())) {
            vh.tv_content.setText(data.get(position).getName());
        }

        vh.add_to_car.setOnClickListener(v -> {
            Log.d("ID", data.get(position).getId());
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("userID", "123456")
                    .add("goodsID", data.get(position).getId())
                    .build();
            Request request = new Request.Builder()
                    .url("http://192.168.0.104:8088/insertGoodsToShoppingCar")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Looper.prepare();
                    Toast.makeText(context, response.body().string(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });


        });


        return convertView;
    }

    private void showToast(String response){

    }


    public class ViewHold {
        TextView tv_content;
        TextView tv_right;
        ImageView add_to_car;
    }

}