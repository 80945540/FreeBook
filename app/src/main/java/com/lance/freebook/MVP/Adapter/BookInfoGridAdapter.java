package com.lance.freebook.MVP.Adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Home.model.TasksManager;
import com.lance.freebook.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class BookInfoGridAdapter extends BaseQuickAdapter<BookInfoListDto> {

    public BookInfoGridAdapter(int layoutResId, List<BookInfoListDto> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookInfoListDto item) {
        Glide.with(mContext)
                .load(item.getImageUrl())
                .crossFade()
                .placeholder(R.mipmap.nocover)
                .into((ImageView) helper.getView(R.id.home_recommend_item_imageview_url));
        if(item.getBookName().length()<=4){
            ((TextView) helper.getView(R.id.home_recommend_item_textview_title)).setTextSize(15);
        }else if(item.getBookName().length()>4&&item.getBookName().length()<=9){
            ((TextView) helper.getView(R.id.home_recommend_item_textview_title)).setTextSize(12);
        }else{
            ((TextView) helper.getView(R.id.home_recommend_item_textview_title)).setTextSize(10);
        }
        helper.setText(R.id.home_recommend_item_textview_title,item.getBookName());
    }
}
