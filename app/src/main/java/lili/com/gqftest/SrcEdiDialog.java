package lili.com.gqftest;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/14.
 */

public class SrcEdiDialog extends Dialog implements View.OnClickListener {
    private TextView okTxt;
    private TextView canalTxt;
    private EditText srcEdi;

    private Context mContext;
    private String content;
    OnCloseListener listener;

    private int maxWidth;

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public SrcEdiDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public SrcEdiDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public SrcEdiDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected SrcEdiDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.src_dialog_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        okTxt = (TextView) findViewById(R.id.ok_txt);
        canalTxt = (TextView) findViewById(R.id.canal_txt);
        srcEdi = (EditText) findViewById(R.id.src_edi);
        okTxt.setOnClickListener(this);
        canalTxt.setOnClickListener(this);
    }

    public void showDialog() {
        this.getWindow().setGravity(Gravity.BOTTOM);
        this.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        this.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_txt:
                if (!srcEdi.getText().toString().equals("")) {
                    listener.ok(srcEdi.getText().toString());
                } else {
                    srcEdi.setError("请输入网址");
                }
                break;
            case R.id.canal_txt:
                listener.dimess();
                break;
        }
    }

    public interface OnCloseListener {
        void ok(String src);

        void dimess();
    }
}