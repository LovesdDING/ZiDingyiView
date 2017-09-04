package patient.yilin.com.testzidingyiview.chenjinshi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import patient.yilin.com.testzidingyiview.R;

public class TestTranslucentActivity extends AppCompatActivity {

    private static final String TAG = TestTranslucentActivity.class.getSimpleName() ;

    private Toolbar toolbar;

    @SuppressLint("NewApi")   //屏蔽android  lint错误
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //设置状态栏 透明 但是toolBar  会顶上去   状态栏 会遮挡一部分界面
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //设置 虚拟导航栏 透明
//        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorAccent));  //设置导航栏颜色
        setContentView(R.layout.activity_test_translucent);
        //5.0+ 可以直接使用API 来改变状态栏的颜色
//        getWindow().setStatusBarColor(getResources().getColor(R.color.holo_red_light));
        //先设置 toolBar的高度
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        ViewGroup.LayoutParams params = toolbar.getLayoutParams() ;
        int statusBarHeight = getStatusBarHeight(this)  ;
        Log.i(TAG, "onCreate: statusBarHeight"+statusBarHeight);  //48px   24dp

        params.height += statusBarHeight ; //这里如果 xml中使用的是wrap_content   那么在没有绘制内容的时候 就去取高度 这里的高度为0
        toolbar.setLayoutParams(params);
        //在 设置 Padding  以避免 状态栏 遮挡toolBar的内容
        toolbar.setPadding(toolbar.getPaddingLeft(),
                toolbar.getPaddingTop()+getStatusBarHeight(this),
                toolbar.getPaddingRight(),
                toolbar.getPaddingBottom());

    }

    /**
     * 通过 反射 获取状态栏的高度
     * @param context
     * @return
     */
    private int getStatusBarHeight(Context context) { //反射 手机运行的类
        int statusHeight = -1 ;
        try {
           Class<?> clazz = Class.forName("com.android.internal.R$dimen") ;  //$dimen  是因为 dimen  是R文件内的内部类
            Object obj = clazz.newInstance() ;
            String heightStr = clazz.getField("status_bar_height").get(obj).toString() ;
            int height = Integer.parseInt(heightStr) ;  //取到系统设定的默认状态栏高度
            //dp-> px
             statusHeight = context.getResources().getDimensionPixelSize(height) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


}
