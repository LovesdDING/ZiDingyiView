package patient.yilin.com.testzidingyiview.AddBankCard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import patient.yilin.com.testzidingyiview.R;
import patient.yilin.com.testzidingyiview.utils.UiUtils;

/**
 * Created by Administrator on 2017/9/5.
 */

public class PhoneView extends LinearLayout{

    private int index = 0 ;
    private String[] texts = {"您","的","手","机","号","码"} ;
    TranslateAnimation tvTranslation ; //位移动画
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(index<texts.length){
                getChildAt(index++).startAnimation(tvTranslation);
            }
        }
    } ;

    public PhoneView(Context context) {
        this(context,null);
    }

    public PhoneView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public PhoneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init() ;
    }

    private void init() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, UiUtils.dp2px(getContext(),100)) ;
        for (int i = 0; i < texts.length; i++) {
            TextView tv = new TextView(getContext()) ;
            tv.setTextColor(getResources().getColor(R.color.phoneTextBlue));
            tv.setText(texts[i]);
            tv.setTextSize(16);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.BOTTOM);
            tv.setPadding(0,0,0,UiUtils.dp2px(getContext(),30));
            addView(tv);
        }
        
    }


    public void startAnim(){
        Timer timer = new Timer() ;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tvTranslation = new TranslateAnimation(0,0,0,-100) ;
                tvTranslation.setDuration(300);
                tvTranslation.setFillAfter(true);
                handler.sendEmptyMessage(0) ;
            }
        } ;

        timer.schedule(task,0,50);  //添加定时器 每隔50ms  对textView 执行动画
    }
}
