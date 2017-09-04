package patient.yilin.com.testzidingyiview.chenjinshi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 封装 基类 沉浸式 状态栏 +导航栏
 * Created by Administrator on 2017/8/31.
 */

public class BaseTranslucentActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断版本 如果 [4.4,5.0)  就设置 状态栏和导航栏 为透明  KITKAT=4.4  LOLLIPOP=5.0
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //设置状态栏 透明 但是toolBar  会顶上去   状态栏 会遮挡一部分界面
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //设置 虚拟导航栏 透明


        }


    }

    public void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar,int primaryColor){
        //判断版本 如果 [4.4,5.0)  就设置 状态栏和导航栏 为透明  KITKAT=4.4  LOLLIPOP=5.0
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //设置状态栏 透明 但是toolBar  会顶上去   状态栏 会遮挡一部分界面
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //设置 虚拟导航栏 透明

            if(toolbar!=null){
           ViewGroup.LayoutParams params = toolbar.getLayoutParams() ;
           int statusBarHeight = getStatusBarHeight(this)  ;

               params.height += statusBarHeight ; //这里如果 xml中使用的是wrap_content   那么在没有绘制内容的时候 就去取高度 这里的高度为0
              toolbar.setLayoutParams(params);
               //在 设置 Padding  以避免 状态栏 遮挡toolBar的内容
              toolbar.setPadding(toolbar.getPaddingLeft(),
                toolbar.getPaddingTop()+getStatusBarHeight(this),
                toolbar.getPaddingRight(),
                toolbar.getPaddingBottom());

                //设置顶部的颜色
                toolbar.setBackgroundColor(primaryColor);

            }

            if(bottomNavigationBar!=null){
                if(hasNavigationBarShow(getWindowManager())){
                    ViewGroup.LayoutParams params = bottomNavigationBar.getLayoutParams() ;
                    params.height+=getNavigationBarHeight(this) ;
                    bottomNavigationBar.setLayoutParams(params);
                    //设置底部的颜色
                    bottomNavigationBar.setBackgroundColor(primaryColor);
                }

            }

        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){  //
            getWindow().setNavigationBarColor(primaryColor);
            getWindow().setStatusBarColor(primaryColor);
        }else{  // <4.4的，不做处理

        }
    }


    private int getNavigationBarHeight(Context context){
        return getSystemComPonentDimen(context,"navigation_bar_height") ;
    }

    private int getStatusBarHeight(Context context){
                return getSystemComPonentDimen(context,"status_bar_height") ;
    }

    private static int getSystemComPonentDimen(Context context, String dimenName){
        int statusHeight = -1 ;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen") ;  //$dimen  是因为 dimen  是R文件内的内部类
            Object obj = clazz.newInstance() ;
            String heightStr = clazz.getField(dimenName).get(obj).toString() ;
            int height = Integer.parseInt(heightStr) ;  //取到系统设定的默认状态栏高度
            //dp-> px
            statusHeight = context.getResources().getDimensionPixelSize(height) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;

    }


    @SuppressLint("NewApi")
    private  static boolean hasNavigationBarShow(WindowManager wm){
       Display display =  wm.getDefaultDisplay() ;
        DisplayMetrics outMetrics = new DisplayMetrics() ;

        //获取整个屏幕的高度
        display.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels ;
        int widthPixels = outMetrics.widthPixels ;
        //获取内容展示部分的高度
        outMetrics = new DisplayMetrics() ;
        display.getMetrics(outMetrics);
        int heightPixels2 = outMetrics.heightPixels ;
        int widthPixels2 = outMetrics.widthPixels ;


        int w = widthPixels-widthPixels2 ;
        int h = heightPixels-heightPixels2 ;

        return w>0||h>0;   //竖屏  横屏两种情况
    }
}
