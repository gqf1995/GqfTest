package lili.com.gqftest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 */

public class ItemClickAdapter extends BaseMultiItemQuickAdapter<ViewType, BaseViewHolder> {

    List<ViewType> datas;
    Context mContext;

    public ItemClickAdapter(Context context,List<ViewType> data) {
        super(data);
        datas=data;
        mContext=context;
        //设置页面以及对应的类型
        addItemType(ViewType.edi_type, R.layout.edi_item_layout);
        addItemType(ViewType.camera_type, R.layout.camera_item_layout);
        addItemType(ViewType.lin_type, R.layout.lin_item_layout);
        addItemType(ViewType.src_type, R.layout.src_item_layout);
    }

    public void updata(List<ViewType> data){
        datas=data;
        this.notifyDataSetChanged();
    }

    public List<ViewType> getDatas() {
        return datas;
    }

    public interface MyClickLinsener{
        public void delect(int Position);
        public void down(int Position);
        public void up(int Position);
    }
    MyClickLinsener myClickLinsener;

    public void setMyClickLinsener(MyClickLinsener myClickLinsener) {
        this.myClickLinsener = myClickLinsener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ViewType item) {
        if(item.getPosition()==0){
            helper.setGone(R.id.up_img,false);
        }
        if(item.getPosition()==this.getData().size()-1){
            helper.setGone(R.id.down_img,false);
        }
        helper.setOnClickListener(R.id.up_img, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通知,向上
                myClickLinsener.up(item.getPosition());
            }
        });
        helper.setOnClickListener(R.id.down_img, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通知,向下
                myClickLinsener.down(item.getPosition());
            }
        });
        helper.setOnClickListener(R.id.delect_img, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通知,删除
                myClickLinsener.delect(item.getPosition());
            }
        });
        switch (helper.getItemViewType()) {
            case ViewType.edi_type:
                //设置文本
                if(datas.get(item.getPosition()).getEdiTxt()!=null){
                    ((EditText) helper.getView(R.id.edi)).setText(datas.get(item.getPosition()).getEdiTxt());
                }
                //设置文字改变监听
                ((EditText) helper.getView(R.id.edi)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        datas.get(item.getPosition()).setEdiTxt(editable.toString());
                    }
                });
                //设置显示位置
                if(datas.get(item.getPosition()).getEdiTxtShowType()==0) {
                    datas.get(item.getPosition()).setEdiTxtShowType(ViewType.left_type);
                    ((RadioButton)helper.getView(R.id.edi_left_radio)).setChecked(true);
                }else {
                    setEdiGravity((EditText) helper.getView(R.id.edi), datas.get(item.getPosition()).getEdiTxtShowType());
                    switch (datas.get(item.getPosition()).getEdiTxtShowType()){
                        case ViewType.left_type:
                            //左
                            ((RadioButton)helper.getView(R.id.edi_left_radio)).setChecked(true);
                            break;
                        case ViewType.center_type:
                            //中
                            ((RadioButton)helper.getView(R.id.edi_center_radio)).setChecked(true);
                            break;
                        case ViewType.right_type:
                            //右
                            ((RadioButton)helper.getView(R.id.edi_right_radio)).setChecked(true);
                            break;
                    }
                }
                helper.setOnClickListener(R.id.edi_left_radio, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //文本向左
                        datas.get(item.getPosition()).setEdiTxtShowType(ViewType.left_type);
                        setEdiGravity((EditText) helper.getView(R.id.edi),ViewType.left_type);
                    }
                });
                helper.setOnClickListener(R.id.edi_center_radio, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //文本居中
                        datas.get(item.getPosition()).setEdiTxtShowType(ViewType.center_type);
                        setEdiGravity((EditText) helper.getView(R.id.edi),ViewType.center_type);
                    }
                });
                helper.setOnClickListener(R.id.edi_right_radio, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //文本向右
                        datas.get(item.getPosition()).setEdiTxtShowType(ViewType.right_type);
                        setEdiGravity((EditText) helper.getView(R.id.edi),ViewType.right_type);
                    }
                });
                break;
            case ViewType.camera_type:
                Bitmap bitmap= BitmapFactory.decodeFile(item.getImgpath());
                helper.setImageBitmap(R.id.img,bitmap);
                break;
            case ViewType.lin_type:
                break;
            case ViewType.src_type:
                break;
        }
    }
    public void setEdiGravity(EditText editText,int type){
        switch (type){
            case ViewType.left_type:
                //左
                editText.setGravity(Gravity.TOP | Gravity.LEFT);
                break;
            case ViewType.center_type:
                //中
                editText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                break;
            case ViewType.right_type:
                //右
                editText.setGravity(Gravity.TOP | Gravity.RIGHT);
                break;
        }

    }

}