package com.chinaitop.linxia.custominputview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chinaitop.linxia.custominputview.widget.AddView;
import com.chinaitop.linxia.custominputview.widget.PopMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddView.OnClickListener {

    private List<String> list = new ArrayList<>();
    private AddView av1, av2, av3, av4, av5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化控件
        av1 = (AddView) findViewById(R.id.av_1);
        av2 = (AddView) findViewById(R.id.av_2);
        av3 = (AddView) findViewById(R.id.av_3);
        av4 = (AddView) findViewById(R.id.av_4);
        av5 = (AddView) findViewById(R.id.av_5);

        //设置点击事件
        av2.setOnRightClickListener(this);
        //设置错误提示文字
        av3.setError("请输入内容！");
        //设置点击弹出列表选择
        getData();
        av5.setSpinnerData(list);
        av5.setOnRightItemListener(new PopMenu.CallBack() {
            @Override
            public void OnItemClickListener(int position) {
                Toast.makeText(MainActivity.this, "选择了：" + list.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.av_2:
                Toast.makeText(MainActivity.this, "不可输入", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //获取列表数据
    private void getData() {
        for (int i = 0; i < 9; i++) {
            list.add(String.valueOf((i + 1)));
        }
    }
}
