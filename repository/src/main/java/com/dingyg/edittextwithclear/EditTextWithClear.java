//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dingyg.edittextwithclear;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.soonfor.repository.R;

public class EditTextWithClear extends RelativeLayout implements OnClickListener {
    private Context mContext;
    EditText etInput;
    ImageView imgClear;
    private EditTextWithClear.EditListener mListener;
    private RelativeLayout rlfclearText;

    public void setSearchViewListener(EditTextWithClear.EditListener listener) {
        this.mListener = listener;
        this.notifySearchHint();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_cleartext, this);
        this.initViews();
    }

    private void initViews() {
        this.rlfclearText = (RelativeLayout)this.findViewById(R.id.rlfclearText);
        this.etInput = (EditText)this.findViewById(R.id.etfInputS);
        this.imgClear = (ImageView)this.findViewById(R.id.imgfClear);
        this.imgClear.setOnClickListener(this);
        this.etInput.addTextChangedListener(new EditTextWithClear.EditChangedListener());
        this.etInput.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                EditTextWithClear.this.notifyEditorAction(EditTextWithClear.this.etInput, actionId);
                return true;
            }
        });
    }

    public EditText getEditText() {
        if (this.etInput == null) {
            this.etInput = (EditText)this.findViewById(R.id.etfInputS);
        }

        return this.etInput;
    }

    private void notifyEditorAction(View view, int actionId) {
        if (this.mListener != null && this.mContext != null) {
            ((InputMethodManager)this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 2);
            this.mListener.onEditAction(actionId);
        }

    }

    private void notifyChanged(View view) {
        if (this.mListener != null && this.mContext != null) {
            this.mListener.atetTextChanged(this.etInput.getText().toString().trim());
        }

    }

    private void notifySearchHint() {
        if (this.mListener != null) {
            this.mListener.onSetHint(this.etInput);
        }

    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.imgfClear) {
            this.etInput.setText("");
        }

    }

    public void setBackground(int resouseId) {
        this.rlfclearText.setBackgroundResource(resouseId);
    }

    public void setInputType(int typeId) {
        this.etInput.setInputType(typeId);
    }

    public void setEditTextSize(float size) {
        float scale = this.mContext.getResources().getDisplayMetrics().density;
        this.etInput.setTextSize(size * scale + 0.5F);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.etInput.addTextChangedListener(textWatcher);
    }

    public void setImeOpton(int actionid) {
        this.etInput.setImeOptions(actionid);
    }

    public void setOnEditorActionListener(int actionid, OnEditorActionListener editorActionListener) {
        this.etInput.setImeOptions(actionid);
        this.etInput.setOnEditorActionListener(editorActionListener);
    }

    public void setText(String text) {
        this.etInput.setText(text);
    }

    public String getText() {
        try {
            return this.etInput.getText().toString().trim();
        } catch (Exception var2) {
            return "";
        }
    }

    public interface EditListener {
        void onSetHint(EditText var1);

        void atetTextChanged(String var1);

        void onEditAction(int var1);
    }

    private class EditChangedListener implements TextWatcher {
        private EditChangedListener() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence s, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
            if (editable != null && !editable.toString().trim().equals("")) {
                EditTextWithClear.this.imgClear.setVisibility(VISIBLE);
            } else {
                EditTextWithClear.this.imgClear.setVisibility(GONE);
            }

            EditTextWithClear.this.notifyChanged(EditTextWithClear.this.etInput);
        }
    }
}
