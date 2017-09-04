package patient.yilin.com.testzidingyiview.CardView;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import patient.yilin.com.testzidingyiview.R;

public class TestCardViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_test_card_view2);
    }

    private boolean reverse = false ;
    //点击 旋转
    public void rotate(View view) {
        float toDegree = reverse?-180f:180f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0.0f,toDegree).setDuration(400) ;  //属性动画的 旋转效果
        animator.start();
        reverse = !reverse ;
    }

//    public void clickCard1(View view) {
//
//    }
}
