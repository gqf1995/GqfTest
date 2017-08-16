package lili.com.gqftest;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/15.
 */

public class PromptingDialog extends Dialog implements View.OnClickListener {
    private TextView okTxt;
    private TextView canalTxt;
    private TextView titleTxt;

    private Context mContext;
    private String content;
    OnCloseListener listener;

    public PromptingDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public PromptingDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public PromptingDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected PromptingDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.propting_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        okTxt = (TextView) findViewById(R.id.ok_txt);
        canalTxt = (TextView) findViewById(R.id.canal_txt);
        titleTxt = (TextView) findViewById(R.id.title_txt);
        okTxt.setOnClickListener(this);
        canalTxt.setOnClickListener(this);
        titleTxt.setOnClickListener(this);
        titleTxt.setText(content);
    }

    public void showDialog() {
        this.getWindow().setGravity(Gravity.CENTER);
        this.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_txt:
                listener.ok();
                break;
            case R.id.canal_txt:
                listener.dimess();
                break;
        }
    }

    public interface OnCloseListener {
        void ok();

        void dimess();
    }
}
