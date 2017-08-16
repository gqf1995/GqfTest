package lili.com.gqftest;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/14.
 */

public class CameraDialog extends Dialog implements View.OnClickListener {
    private TextView photo;
    private TextView camera;
    private Button dimess;

    private Context mContext;
    private String content;
    OnCloseListener listener;

    public CameraDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CameraDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CameraDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected CameraDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_dialog);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        photo = (TextView) findViewById(R.id.photo);
        camera = (TextView) findViewById(R.id.camera);
        dimess = (Button) findViewById(R.id.dimess);
        photo.setOnClickListener(this);
        camera.setOnClickListener(this);
        dimess.setOnClickListener(this);
    }

    public void showDialog() {
        this.getWindow().setGravity(Gravity.BOTTOM);
        this.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        this.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                listener.photo();
                break;
            case R.id.camera:
                listener.camera();
                break;
            case R.id.dimess:
                listener.dimess();
                break;
        }
    }

    public interface OnCloseListener {
        void photo();

        void camera();

        void dimess();
    }
}