package patient.yilin.com.testzidingyiview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.hys.utils.AppUtils;
import com.hys.utils.DensityUtils;
import com.hys.utils.ImageUtils;
import com.hys.utils.InitCacheFileUtils;
import com.hys.utils.SdcardUtils;
import com.hys.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 图片拖拽activity
 */
public class PostImagesActivity extends AppCompatActivity {
    public static final String FILE_DIR_NAME ="patient.yilin.com.testzidingyiview" ;  //应用缓存地址
    public static final String FILE_IMG_NAME="images" ;  //放置图片缓存
    public static final int IMAGE_SIZE = 9 ;  //可添加图片最大数
    public static final int REQUEST_IMAGE = 1002 ;

    private ArrayList<String> originImages ;  //原始图片
    private ArrayList<String> dragImages  ;//压缩长宽后的图片
    private Context mContext ;
    private PostArticleImgAdapter postArticleImgAdapter ;
    private RecyclerView rcvImg ;
    private TextView tv ;  //删除区域文本提示
    private ItemTouchHelper itemTouchHelper ;

    public static void startpostActivity(Context context,ArrayList<String> images){
        Intent intent = new Intent(context,PostImagesActivity.class) ;
        intent.putStringArrayListExtra("img",images) ;
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_images);
        initData();
        initView() ;
    }

    private void initView() {
        rcvImg = (RecyclerView) findViewById(R.id.rcv_img);
        tv = (TextView) findViewById(R.id.tv);
        initRcv() ;

    }

    private void initRcv() {
        postArticleImgAdapter = new PostArticleImgAdapter(mContext,dragImages) ;
        rcvImg.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        rcvImg.setAdapter(postArticleImgAdapter);
        MyCallBack myCallBack = new MyCallBack(postArticleImgAdapter,dragImages,originImages) ;
        itemTouchHelper = new ItemTouchHelper(myCallBack) ;
        itemTouchHelper.attachToRecyclerView(rcvImg);

        rcvImg.addOnItemTouchListener(new OnRecyclerItemClickListener(rcvImg) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if(originImages.get(vh.getAdapterPosition()).contains(getString(R.string.glide_plus_icon_string))){  //打开相册
                    MultiImageSelector.create()
                            .showCamera(true)
                            .count(IMAGE_SIZE-originImages.size()+1)
                            .multi()
                            .start(PostImagesActivity.this,REQUEST_IMAGE);
                }else{
                    ToastUtils.getInstance().show(MyApplication.getInstance().getContext(),"预览图片");
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item 不是最后一个 则执行拖拽
                if (vh.getLayoutPosition()!= dragImages.size()-1) {
                    itemTouchHelper.startDrag(vh);
                }
            }
        });

        myCallBack.setDragListener(new MyCallBack.DragListener() {
            @Override
            public void deleteState(boolean delete) {
                if (delete) {
                    tv.setBackgroundResource(R.color.holo_red_dark);
                    tv.setText(getResources().getString(R.string.post_delete_tv_s));
                }else{
                    tv.setText(getResources().getString(R.string.post_delete_tv_d));
                    tv.setBackgroundResource(R.color.holo_red_light);
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start) {
                    tv.setVisibility(View.VISIBLE);
                }else{
                    tv.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData() {
        originImages = getIntent().getStringArrayListExtra("img") ;
        mContext = getApplicationContext() ;
        InitCacheFileUtils.initImgDir(FILE_DIR_NAME,FILE_IMG_NAME);  //清除图片缓存
        //添加按钮图片资源
        String pluspath = getString(R.string.glide_plus_icon_string)+ AppUtils.getPackageInfo(mContext).packageName+"/mipmap/"+R.mipmap.icon_img_add ;
        dragImages = new ArrayList<>() ;
        originImages.add(pluspath) ; //添加按键 超过9张时 在adapter中隐藏
        dragImages.addAll(originImages) ;
        new Thread(new MyRunnable(dragImages,originImages,dragImages,myHandler,false)).start(); //开启新线程 压缩图片


    }

    //图片相关
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_IMAGE && resultCode==RESULT_OK){
            //压缩图片
            new Thread(new MyRunnable(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT),originImages,dragImages,myHandler,true)).start();

        }
    }

    /**
     * 另起线程 压缩图片
     */
    static class MyRunnable implements Runnable{

        ArrayList<String> images ;
        ArrayList<String> originImages ;
        ArrayList<String> dragImages ;
        boolean add ; //是否添加图片
        Handler handler ;


        public MyRunnable(ArrayList<String> images,ArrayList<String> originImages,ArrayList<String> dragImages,Handler handler,boolean add){
            this.images = images ;
            this.originImages = originImages ;
            this.dragImages = dragImages ;
            this.handler = handler ;
            this.add =add;
        }


        @Override
        public void run() {
            SdcardUtils sdcardUtils = new SdcardUtils() ;
            String filePath ;
            Bitmap newBitmap ;
            int addIndex = originImages.size()-1 ;
            for (int i = 0; i < images.size(); i++) {
                if(images.get(i).contains(MyApplication.getInstance().getString(R.string.glide_plus_icon_string))){ //是添加图片按钮
                    continue;
                }
                //压缩图片
                newBitmap = ImageUtils.compressScaleByWH(images.get(i),
                        DensityUtils.dp2px(MyApplication.getInstance().getContext(),100),
                        DensityUtils.dp2px(MyApplication.getInstance().getContext(),100))  ;

                //文件地址
                filePath = sdcardUtils.getSDPATH()+FILE_DIR_NAME+"/"+FILE_IMG_NAME+"/"+String.format("img_%d.jpg",System.currentTimeMillis()) ;
                    //保存图片
                ImageUtils.save(newBitmap,filePath,Bitmap.CompressFormat.JPEG,true) ;

                //设置值
                if (!add) {
                    images.set(i,filePath) ;
                }else{  //添加图片 要更新
                    dragImages.add(addIndex,filePath) ;
                    originImages.add(addIndex++,filePath) ;
                }

            }

            Message message = new Message() ;
            message.what = 1 ;
            handler.sendMessage(message) ;

        }
    }


    private MyHandler myHandler = new MyHandler(this) ;



    private static class MyHandler extends Handler{
        private WeakReference<Activity> reference ;

        public MyHandler(Activity activity){
            reference = new WeakReference<>(activity) ;
        }

        @Override
        public void handleMessage(Message msg) {
            PostImagesActivity activity = (PostImagesActivity) reference.get();

            if (activity != null) {
                switch (msg.what){
                    case 1:
                      activity.postArticleImgAdapter.notifyDataSetChanged();
                        break;
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }
}
