package patient.yilin.com.testzidingyiview.chenjinshi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import patient.yilin.com.testzidingyiview.R;

public class TestTanslucent2Activity extends BaseTranslucentActivity {

    private Toolbar toolbar ;
    private View navigationBarBottom ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tanslucent2);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        navigationBarBottom = findViewById(R.id.nav2) ;
        setOrChangeTranslucentColor(toolbar,navigationBarBottom,getResources().getColor(R.color.colorPrimary_pink));

    }
}
