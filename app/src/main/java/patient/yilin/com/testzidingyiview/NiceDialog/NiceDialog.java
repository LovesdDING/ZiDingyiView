package patient.yilin.com.testzidingyiview.NiceDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/9/6.
 */

public class NiceDialog  extends BaseNiceDialog{

    private ViewConvertListener convertListener ;

    public static NiceDialog init(){
        return new NiceDialog() ;
    }

    @Override
    public int initLayoutId() {
        return layoutId;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        if (convertListener != null) {
            convertListener.convertView(holder,dialog);
        }
    }

    public NiceDialog setConvertListener(ViewConvertListener convertListener) {
        this.convertListener = convertListener;
        return this ;
    }

    public NiceDialog setLayoutId(int layoutId){
        this.layoutId = layoutId ;
        return this ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            convertListener = (ViewConvertListener) savedInstanceState.getSerializable("listener");
        }
    }


    /**
     *  保存接口
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("listener",convertListener);
    }
}
