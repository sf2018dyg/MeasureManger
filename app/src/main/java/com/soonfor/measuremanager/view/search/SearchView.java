package com.soonfor.measuremanager.view.search;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.soonfor.measuremanager.R;


/**
 * Created by DC-DingYG on 2017/1/2 0002.
 */
public class SearchView extends LinearLayout implements View.OnClickListener {

    private Context mContext;//上下文对象
    private EditText etInput;// 输入框
    private ImageView ivDelete;//删除键
    private SearchViewListener mListener;//搜索回调接口

    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
        notifySearchHint();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_search_compant, this);
        initViews();
    }

    private void initViews() {
        etInput = (EditText) findViewById(R.id.search_et_input);
        ivDelete = (ImageView) findViewById(R.id.search_iv_delete);

        ivDelete.setOnClickListener(this);

        etInput.addTextChangedListener(new EditChangedListener());
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    notifyStartSearching();
                }
                return true;
            }
        });
    }

    /**
     * 通知监听者 进行搜索操作
     */
    private void notifyStartSearching(){
        if (mListener != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            mListener.onSearch(etInput.getText().toString().trim());
        }
    }
    private void notifyChanged(){
        if(mListener != null){
            //隐藏软键盘
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            mListener.onChaged(etInput.getText().toString().trim());
        }
    }
    private void notifySearchHint(){
        if (mListener != null)
            mListener.onSetHint(etInput);
    }


    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {
            if (s.toString().trim()!=null && !"".equals(s.toString().trim())) {
                ivDelete.setVisibility(VISIBLE);
            } else {
                ivDelete.setVisibility(GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.toString().trim()==null || editable.toString().trim().equals("")){
                notifyChanged();
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv_delete:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                break;
        }
    }

    public void clearText(){
        etInput.setText("");
    }

    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        void onSetHint(EditText etfInput);
        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
        //void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);

        //        /**
        //         * 提示列表项点击时回调方法 (提示/自动补全)
        //         */
        //        void onTipsItemClick(String text);
        void onChaged(String text);
    }
}
