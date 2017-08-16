package lili.com.gqftest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.commit_txt)
    TextView commitTxt;
    @BindView(R.id.item_list)
    RecyclerView itemList;

    public static final int REQUEST_IMAGE = 1234;//图库选择码
    public static final int REQUEST_ORIGINAL = 4321;//相机牌照码

    List<String> mSelectPath;//图片选择路径
    List<ViewType> datas;//数据源
    ItemClickAdapter itemClickAdapter;
    View header;//头部标题输入
    View foot;//足部按钮
    PromptingDialog promptingDialog;//默认提示
    CameraDialog cameraDialog;//图片选择提示
    SrcEdiDialog srcEdiDialog;//网址输入提示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        commitTxt.setOnClickListener(this);
        //初始化总数据
        datas = new ArrayList<>();
        //初始化列表
        initList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commit_txt:
                //弹出提交提示
                commitAll();
                break;
            case R.id.edi_type:
                //添加文本输入
                datas.add(new ViewType(ViewType.edi_type));
                initList();
                break;
            case R.id.camera_type:
                //弹出图片选择提示
                cameraDialog();
                break;
            case R.id.wap_type:
                break;
            case R.id.src_type:
                //弹出网址输入
                srcDialog();
                break;
            case R.id.lin_type:
                //添加分割线
                datas.add(new ViewType(ViewType.lin_type));
                initList();
                break;
        }
    }

    public void commitAll() {
        promptingDialog = new PromptingDialog(this, R.style.dialog, "确定提交吗", new PromptingDialog.OnCloseListener() {
            @Override
            public void ok() {
                promptingDialog.dismiss();
                promptingDialog = null;
            }

            @Override
            public void dimess() {
                promptingDialog.dismiss();
                promptingDialog = null;
            }
        });
        promptingDialog.showDialog();
    }

    public void srcDialog() {
        srcEdiDialog = new SrcEdiDialog(this, R.style.src_dialog, "", new SrcEdiDialog.OnCloseListener() {
            @Override
            public void ok(String src) {
                ViewType viewType = new ViewType(ViewType.src_type);
                viewType.setSrc(src);
                datas.add(viewType);
                initList();
                srcEdiDialog.dismiss();
                srcEdiDialog = null;
            }

            @Override
            public void dimess() {
                srcEdiDialog.dismiss();
                srcEdiDialog = null;
            }
        });
        //横向全屏显示dialog
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        srcEdiDialog.setMaxWidth(display.getWidth());
        srcEdiDialog.showDialog();

    }

    public void cameraDialog() {
        cameraDialog = new CameraDialog(this, R.style.dialog, "", new CameraDialog.OnCloseListener() {
            @Override
            public void photo() {
                //图库选择
                initPermissions(1);
            }
            @Override
            public void camera() {
                //拍照选择
                initPermissions(2);
            }
            @Override
            public void dimess() {
                cameraDialog.dismiss();
                cameraDialog = null;
            }
        });
        cameraDialog.showDialog();
    }

    public void initPermissions(int type) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE);
        } else {
            if (type == 1) {
                getPhoto();
            } else {
                getCaream();
            }
        }
    }

    public void getPhoto() {
        Album.albumRadio(this)
                .toolBarColor(this.getResources().getColor(R.color.colorPrimary)) // Toolbar 颜色，默认蓝色。
                .statusBarColor(this.getResources().getColor(R.color.colorPrimary)) // StatusBar 颜色，默认蓝色。
                .title("图库")
                .columnCount(2) // 相册展示列数，默认是2列。
                .camera(true) // 是否有拍照功能。
                .start(REQUEST_IMAGE);
        cameraDialog.dismiss();
        cameraDialog = null;
    }

    public void getCaream() {
        Album.camera(this).start(REQUEST_ORIGINAL);
        cameraDialog.dismiss();
        cameraDialog = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //图库
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) { // Successfully.
                mSelectPath = Album.parseResult(data);
            } else if (resultCode == RESULT_CANCELED) { // User canceled.
            }
        }
        //相机
        if (requestCode == REQUEST_ORIGINAL) {
            if (resultCode == RESULT_OK) { // Successfully.
                mSelectPath = Album.parseResult(data); // Parse path.
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
        //添加图片
        if (mSelectPath != null) {
            ViewType viewType = new ViewType(ViewType.camera_type);
            viewType.setImgpath(mSelectPath.get(0));
            datas.add(viewType);
            initList();
        }
        mSelectPath = null;
    }

    //足部按钮监听
    public void initLinsener() {
        foot.findViewById(R.id.edi_type).setOnClickListener(this);
        foot.findViewById(R.id.camera_type).setOnClickListener(this);
        foot.findViewById(R.id.wap_type).setOnClickListener(this);
        foot.findViewById(R.id.src_type).setOnClickListener(this);
        foot.findViewById(R.id.lin_type).setOnClickListener(this);
    }

    public void initList() {
        //设置位置坐标
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setPosition(i);
        }
        itemClickAdapter = new ItemClickAdapter(this, datas);
        itemClickAdapter.openLoadAnimation();
        itemList.removeAllViews();
        itemList.setLayoutManager(new LinearLayoutManager(this));
        //加载layout
        header = getLayoutInflater().inflate(R.layout.header_list_layout, null);
        foot = getLayoutInflater().inflate(R.layout.foot_list_layout, null);
        initLinsener();
        //添加头部足部
        itemClickAdapter.addHeaderView(header);
        itemClickAdapter.addFooterView(foot);
        itemList.setAdapter(itemClickAdapter);

        itemClickAdapter.setMyClickLinsener(new ItemClickAdapter.MyClickLinsener() {
            @Override
            public void delect(int position) {
                delectItem(position);
            }
            @Override
            public void down(int position) {
                datas = itemClickAdapter.getDatas();
                upOrDown(position, 0);
            }
            @Override
            public void up(int position) {
                datas = itemClickAdapter.getDatas();
                upOrDown(position, 1);
            }
        });

    }

    public void delectItem(final int position) {
        promptingDialog = new PromptingDialog(this, R.style.dialog, "确定删除吗", new PromptingDialog.OnCloseListener() {
            @Override
            public void ok() {
                promptingDialog.dismiss();
                promptingDialog = null;
                datas.remove(position);
                initList();
            }
            @Override
            public void dimess() {
                promptingDialog.dismiss();
                promptingDialog = null;
            }
        });
        promptingDialog.showDialog();
    }

    public void upOrDown(int position, int type) {
        ViewType viewType = null;
        try {
            viewType = datas.get(position).clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (viewType != null) {
            if (type == 0) {
                datas.remove(position);
                datas.add(position + 1, viewType);
                initList();
            } else {
                datas.remove(position);
                datas.add(position - 1, viewType);
                initList();
            }
        }
    }
}
