package com.chinaitop.linxia.custominputview.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinaitop.linxia.custominputview.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mq on 2017/9/13 0013.
 */

public class PopMenu {

    private final ListView mListView;
    private final PopupWindow mPopupWindow;
    private final PopAdapter mAdapter;
    private Context context;
    private List<String> itemList;
    private View showView;
    private CallBack callBack;

    public PopMenu(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
        View view = LayoutInflater.from(context).inflate(R.layout.popmenu_layout, null);
        //listView
        mListView = (ListView) view.findViewById(R.id.listView);
        mAdapter = new PopAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setFocusableInTouchMode(true);
        mListView.setFocusable(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();
                if (showView != null) {
                    if (showView instanceof TextView) {
                        ((TextView) showView).setText(itemList.get(position));
                    } else if (showView instanceof EditText) {
                        ((EditText) showView).setText(itemList.get(position));
                    }
                }
                if (callBack != null) {
                    callBack.OnItemClickListener(position);
                }

            }
        });

        //popupwindow
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 使其聚集
        mPopupWindow.setFocusable(true);
        // 设置允许在外点击消失
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    // 设置菜单项点击监听器
    public void setOnItemClickListener(CallBack callBack) {
        this.callBack = callBack;
    }

    // 批量添加菜单项
    public void addItems(Collection<String> items) {
        itemList.clear();
        if (items != null)
            for (String s : items)
                itemList.add(s);
    }


    // 单个添加菜单项
    public void addItem(String item) {
        itemList.add(item);
    }

    // 批量添加菜单项
    public void clear() {
        itemList.clear();
    }

    // 下拉式 弹出 pop菜单 parent 右下角
    public void showAsDropDown(View parent) {
        showView = parent;
        mAdapter.notifyDataSetChanged();
//        int[] position = new int[2];
//        parent.getLocationOnScreen(position);
//        mPopupWindow.showAtLocation(context.findViewById(android.R.id.content), Gravity.START | Gravity.TOP, position[0] + parent.getWidth(), position[1]);
        if (parent.getWidth() > 0)
            mPopupWindow.setWidth(parent.getWidth());
        mPopupWindow.showAsDropDown(parent);
    }

    // 隐藏菜单
    public void dismiss() {
        mPopupWindow.dismiss();
    }

    public interface CallBack {
        void OnItemClickListener(int position);
    }

    // 适配器
    private final class PopAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.popmenu_item, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(itemList.get(position));
            return convertView;
        }

        private final class ViewHolder {
            TextView textView;
        }
    }

}
