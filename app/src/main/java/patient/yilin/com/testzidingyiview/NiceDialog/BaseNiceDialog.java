package patient.yilin.com.testzidingyiview.NiceDialog;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import patient.yilin.com.testzidingyiview.R;
import patient.yilin.com.testzidingyiview.utils.UiUtils;

/**
 * 自定义 通用dialog  基类
 * 集成字DIialogFragment DialogFragment  是Android3.0之后提出的 也是官方推荐的使用方式
 * Created by Administrator on 2017/9/6.
 */

public abstract class BaseNiceDialog extends DialogFragment{
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String BOTTOM = "show_bottom";
    private static final String CANCEL = "out_cancel";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";
    //标记  保存这些状态值的标记


    private int margin ; //左右边距
    private int width ; //宽度
    private int height ; //高度
    private float dimAmout = 0.5f ;  //灰度深浅
    private boolean showBottom ; //是否底部显示
    private boolean outCancel = true ; //是否点击外部取消

    @StyleRes
    private int animStyle ;
    @LayoutRes
    protected int layoutId ;
    public abstract int initLayoutId() ;  //布局id方法

    public abstract void convertView(ViewHolder  holder,BaseNiceDialog dialog) ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.NiceDialog);
        layoutId = initLayoutId() ;

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN) ;
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmout = savedInstanceState.getFloat(DIM);
            showBottom = savedInstanceState.getBoolean(BOTTOM);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId,container,false) ;
        convertView(ViewHolder.create(view),this);
        return view;
    }

//    切换横屏的时候 保存当前状态值 否则 dialog会销毁  如果不保存 会出现 id值找不到的情况
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN,margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmout);
        outState.putBoolean(BOTTOM, showBottom);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }



    @Override
    public void onStart() {
        super.onStart();
        initParams() ;
    }

    //初始化  dialog的params 参数
    private void initParams() {
        Window window = getDialog().getWindow() ;
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes() ;
            //调节灰色透明度背景 [0-1]  默认0.5f
            params.dimAmount = dimAmout ;
            //是否在底部显示
            if (showBottom) {
                params.gravity = Gravity.BOTTOM ;
                if (animStyle==0) {
                    animStyle = R.style.DefaultAnimation ;
                }
            }

            //  设置dialog 宽度
            if (width==0) {
                params.width = UiUtils.getScreenWidth(getContext()) - 2*UiUtils.dp2px(getContext(),margin) ;
            }else{
                params.width = UiUtils.dp2px(getContext(),width);
            }

            //设置dialog的高度
            if (height==0) {
                params.height = WindowManager.LayoutParams.WRAP_CONTENT ;
            }else{
                params.height = UiUtils.dp2px(getContext(),height) ;
            }

            //设置dialog进入 退出的动画
            window.setWindowAnimations(animStyle);
            window.setAttributes(params);

        }

        setCancelable(outCancel);

    }


    public BaseNiceDialog setMargin(int margin){
        this.margin = margin ;
        return this ;
    }

    public BaseNiceDialog setWidth(int width){
        this.width = width ;
        return this ;
    }

    public BaseNiceDialog setHeight(int height){
        this.height = height ;
        return  this ;
    }

    public BaseNiceDialog setDimAmout(float dimAmout){
        this.dimAmout = dimAmout ;
        return this ;
    }

    public BaseNiceDialog setShowBottom(boolean showBottom){
        this.showBottom= showBottom ;
        return this ;
    }

    public BaseNiceDialog setOutCancel(boolean outCancel){
        this.outCancel = outCancel ;
        return this ;
    }

    public BaseNiceDialog setAnimStyle(@StyleRes int animStyle){
        this.animStyle = animStyle ;
        return  this ;
    }

    public BaseNiceDialog show(FragmentManager manager){
        super.show(manager,String.valueOf(System.currentTimeMillis()));
        return this ;
    }


}
