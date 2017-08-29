package patient.yilin.com.testzidingyiview;

/**
 * 公共工具类
 * Created by Administrator on 2017/8/29.
 */

public class CommonUtils {
    /**
     * 获取dimens定义的大小
     * @return
     */
    public static int getPixelById(int dimensionId){
        return MyApplication.getInstance().getResources().getDimensionPixelSize(dimensionId) ;
    }
}
