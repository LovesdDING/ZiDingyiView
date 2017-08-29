package patient.yilin.com.testzidingyiview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class PostArticleImgAdapter extends RecyclerView.Adapter<PostArticleImgAdapter.MyViewHolder>{

   private List<String> mDatas ;
    private final LayoutInflater inflater ;
    private final Context mContext ;

    public PostArticleImgAdapter(Context context,List<String> datas){
            this.mDatas = datas ;
            this.mContext = context ;
            this.inflater = LayoutInflater.from(context) ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(inflater.inflate(R.layout.item_post_activity,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position>= PostImagesActivity.IMAGE_SIZE) {  //图片已选完时  隐藏添加按钮
            holder.imageView.setVisibility(View.GONE);
        }else{
            holder.imageView.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(mDatas.get(position)).into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView ;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.sdv);

        }
    }
}
