package com.chinaitop.linxia.custominputview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinaitop.linxia.custominputview.R;

import java.util.List;

/**
 * Created by mq on 2017/9/14 0014.
 * 自定义输入控件
 * 1.左侧标题
 * 2.可设置输入和只点击
 * 3.自动根据状态切换编辑框背景根据
 */

public class AddView extends FrameLayout implements View.OnClickListener, TextWatcher {

    public static final int TYPE_INPUT = 100;
    public static final int TYPE_CLICK = 101;
    public static final int TYPE_NO = 102;
    public static final int TYPE_SPINNER = 103;

    private Context context;

    private String leftText;
    private String rightHintText;
    private String rightText;
    private int rightDrawableRight;
    private View mView;
    private LinearLayout rootView;
    private TextView tvLeft;
    private TextView tvMao;
    private EditText tvRight;
    private int mType;

    private OnClickListener clickListener;
    private TextWatcher textListener;
    private PopMenu popMenu;
    private PopMenu.CallBack itemListener;
    private LinearLayout linear_bg;
    private ImageView ivRight;
    private boolean showMao;//是否显示冒号（：）

    public AddView(Context context) {
        super(context, null);
        this.context = context;
        initView();
    }

    public AddView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs);

    }

    public AddView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 初始化，引入相关属性
     */
    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AddView);
        leftText = typedArray.getString(R.styleable.AddView_left_Text);
        rightText = typedArray.getString(R.styleable.AddView_right_Text);
        rightHintText = typedArray.getString(R.styleable.AddView_right_HintText);
        rightDrawableRight = typedArray.getResourceId(R.styleable.AddView_right_drawableRight, 0);
        showMao = typedArray.getBoolean(R.styleable.AddView_show_Mao, true);
        mType = typedArray.getInt(R.styleable.AddView_type, TYPE_INPUT);
        typedArray.recycle();
        initView();
        setData();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        mView = LayoutInflater.from(context).inflate(R.layout.addview_layout, null);
        rootView = (LinearLayout) mView.findViewById(R.id.rootView);
        tvLeft = (TextView) mView.findViewById(R.id.tvLeft);
        tvMao = (TextView) mView.findViewById(R.id.tvMao);
        linear_bg = (LinearLayout) mView.findViewById(R.id.linear_bg);
        tvRight = (EditText) mView.findViewById(R.id.tvRight);
        ivRight = (ImageView) mView.findViewById(R.id.ivRight);
        tvRight.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        tvRight.addTextChangedListener(this);
    }

    /**
     * 初始化控件内容
     */
    private void setData() {
        tvLeft.setText(leftText);
        tvRight.setText(rightText);
        tvRight.setHint(rightHintText);
        if (showMao)
            tvMao.setVisibility(VISIBLE);
        else
            tvMao.setVisibility(GONE);

        setRightDrawableRight(rightDrawableRight);

        setType(mType);
        this.addView(mView);
    }

    /**
     * 设置左侧标题
     *
     * @param leftText
     */
    public void setLeftText(String leftText) {
        this.leftText = leftText;
        tvLeft.setText(leftText);
    }

    /**
     * 设置右侧内容
     *
     * @param rightText
     */
    public void setRightText(String rightText) {
        this.rightText = rightText;
        tvRight.setText(rightText);
    }

    /**
     * 设置右侧提示内容
     *
     * @param rightHintText
     */
    public void setRightHintText(String rightHintText) {
        this.rightHintText = rightHintText;
        tvRight.setHint(rightHintText);
    }

    /**
     * 设置右侧图片
     *
     * @param resId
     */
    public void setRightDrawableRight(@DrawableRes int resId) {
        if (resId != 0) {
            ivRight.setVisibility(VISIBLE);
            rightDrawableRight = resId;
            ivRight.setImageResource(resId);
        } else {
            ivRight.setVisibility(GONE);
        }
    }

    /**
     * 是否显示冒号（：）
     * @param showMao
     */
    public void setShowMao(boolean showMao) {
        this.showMao = showMao;
        if (showMao)
            tvMao.setVisibility(VISIBLE);
        else
            tvMao.setVisibility(GONE);
    }

    /**
     * 设置错误提示
     *
     * @param ErrorText
     */
    public void setError(String ErrorText) {
        tvRight.setError(ErrorText);
        linear_bg.setBackgroundResource(R.drawable.addview_error_bg);
    }

    /**
     * 设置控件类型
     *
     * @param type
     */
    public void setType(int type) {
        mType = type;
        switch (type) {
            case TYPE_INPUT:
                //可输入
                setInput();
                break;
            case TYPE_CLICK:
                //可点击
                setClick();
                break;
            case TYPE_NO:
                //不可点击
                setNoClick();
                break;
            case TYPE_SPINNER:
                //点击弹出列表
                setSpinnerData(null);
                break;
        }
    }

    /**
     * 设置控件类型为Spinner
     */
    public void setType(List<String> list) {
        mType = TYPE_SPINNER;
        setSpinnerData(list);
    }

    //设置可输入
    private void setInput() {
        tvRight.setFocusable(true);
        tvRight.setEnabled(true);
        tvRight.setFocusableInTouchMode(true);
        tvRight.requestFocus();
        tvRight.requestFocusFromTouch();
        tvRight.setLongClickable(true);
        linear_bg.setBackgroundResource(R.drawable.addview_bg);
    }

    //设置只可点击
    private void setClick() {
        tvRight.setClickable(true);
        tvRight.setEnabled(true);
        tvRight.setFocusableInTouchMode(false);
        tvRight.setLongClickable(false);
        linear_bg.setBackgroundResource(R.drawable.addview_bg);
    }

    //设置不可点击、输入
    private void setNoClick() {
        tvRight.setClickable(false);
        tvRight.setEnabled(false);
        tvRight.setLongClickable(false);
        tvRight.setFocusableInTouchMode(false);
        linear_bg.setBackgroundResource(R.drawable.addview_no_bg);
    }

    //设置弹出列表
    public void setSpinnerData(List<String> list) {
        tvRight.setClickable(true);
        tvRight.setEnabled(true);
        tvRight.setFocusableInTouchMode(false);
        tvRight.setLongClickable(false);
        linear_bg.setBackgroundResource(R.drawable.addview_bg);
        if (popMenu == null)
            popMenu = new PopMenu(context);
        popMenu.addItems(list);
        if (itemListener != null)
            popMenu.setOnItemClickListener(itemListener);
    }


    /*************************点击事件*************************/
    /**
     * 设置点击事件
     *
     * @param listener
     */
    public void setOnRightClickListener(@Nullable OnClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (mType) {
            case TYPE_INPUT:
                //可输入
                break;
            case TYPE_CLICK:
                //可点击
                if (clickListener != null) {
                    clickListener.onClick(this);
                }
                break;
            case TYPE_NO:
                //不可点击
                break;
            case TYPE_SPINNER:
                //点击弹出列表
                if (popMenu != null)
                    popMenu.showAsDropDown(tvRight);
                break;
        }
    }

    /*************************输入监听*************************/

    /**
     * 设置输入监听
     *
     * @param textWatcher
     */
    public void setRightTextChangedListener(@Nullable TextWatcher textWatcher) {
        this.textListener = textWatcher;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textListener != null)
            textListener.beforeTextChanged(s, start, count, after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (textListener != null)
            textListener.onTextChanged(s, start, before, count);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textListener != null)
            textListener.afterTextChanged(s);
        linear_bg.setBackgroundResource(R.drawable.addview_bg);
    }

    /*************************弹出框点击事件*************************/
    /**
     * 设置弹出框事件
     *
     * @param popCallBack
     */
    public void setOnRightItemListener(@Nullable PopMenu.CallBack popCallBack) {
        this.itemListener = popCallBack;
        if (popMenu != null)
            popMenu.setOnItemClickListener(popCallBack);

    }

    public interface OnClickListener {
        void onClick(View v);
    }

    public interface TextWatcher {
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);
    }

}
