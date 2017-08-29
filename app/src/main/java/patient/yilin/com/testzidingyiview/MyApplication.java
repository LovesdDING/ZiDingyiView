package patient.yilin.com.testzidingyiview;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/8/29.
 */

public class MyApplication extends Application {
    private static MyApplication app ;
    private Context mContext ;

    public static MyApplication getInstance(){
        return app ;
    }

    public   Context getContext(){
        return  mContext ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this ;
        mContext = getApplicationContext() ;
    }
}
