package patient.yilin.com.testzidingyiview.NiceDialog;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/6.
 */

public interface ViewConvertListener extends Serializable{

    long serialVersionUID = System.currentTimeMillis() ;
    void convertView(ViewHolder holder,BaseNiceDialog dialog) ;

}
