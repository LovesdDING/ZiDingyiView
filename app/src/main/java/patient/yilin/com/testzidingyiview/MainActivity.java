package patient.yilin.com.testzidingyiview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import patient.yilin.com.testzidingyiview.chenjinshi.TestTanslucent2Activity;
import patient.yilin.com.testzidingyiview.chenjinshi.TestTranslucentActivity;

/**
   github 绿色  是新增文件 红色 是忽略文件
 */
public class MainActivity extends AppCompatActivity {

    private  static final int REQUEST_IMAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //MUltiImageSelector 仿 微信多图选取
                MultiImageSelector.create()
                            .showCamera(true)
                            .count(9)   //最大图片个数
                           .multi()     // multi mode  多选模式
                         .start(MainActivity.this,REQUEST_IMAGE);
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestTanslucent2Activity.class));
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE&&resultCode==RESULT_OK){  //图片
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);  //这里取到  选中的图片
            PostImagesActivity.startpostActivity(MainActivity.this,data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT));
        }
    }
}
