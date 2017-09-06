package patient.yilin.com.testzidingyiview.AddBankCard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import patient.yilin.com.testzidingyiview.R;

/**
 *
 * 星星布局view
 * Created by Administrator on 2017/9/5.
 */

public class StarView extends LinearLayout{
    TranslateAnimation tvTranslation ; //位移动画
    final int[] count = new int[2] ; //记录子view的个数
    private boolean isDone = false ; //记录动画是否执行
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(count[0]>0){
                        getChildAt(count[0]).startAnimation(tvTranslation);
                    }
                    break;
            }

        }
    } ;

    public StarView(Context context) {
        this(context,null);
    }

    public StarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init() ;
    }

    //  初始化 显示银行卡效果
    //  首先 添加16个TextView  然后每4个添加一个 padding
    private void init() {
        for (int i = 0; i < 16; i++) {
            TextView tv = new TextView(getContext()) ;
            tv.setTextColor(getResources().getColor(R.color.white));
            if(i%4 == 0){
                tv.setPadding(10,0,0,0);
            }
            tv.setText("*");
            tv.setTextSize(20);
            addView(tv);
        }
    }

    //星星坠落动画
    public void startAnim(){
        if(isDone) return ; //  执行过 不在执行
        isDone = true ;
        Timer timer = new Timer() ;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tvTranslation = new TranslateAnimation(0,0,0,500) ;
                tvTranslation.setDuration(100);
                tvTranslation.setFillAfter(true);
                count[0] -- ;
                handler.sendEmptyMessage(0) ;
            }
        } ;

        timer.schedule(task,0,50);  //添加定时器 间隔50ms 执行坠落动画
    }

    public void setText(CharSequence s){
        ((TextView)getChildAt(0)).setText(s);
    }  //用第一个TextView  显示卡号
}
