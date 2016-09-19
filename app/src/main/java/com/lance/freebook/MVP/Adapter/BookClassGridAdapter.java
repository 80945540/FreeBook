package com.lance.freebook.MVP.Adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.R;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;
import com.xiaochao.lcrapiddeveloplibrary.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class BookClassGridAdapter extends BaseQuickAdapter<BookTypeDto> {

    public BookClassGridAdapter(int layoutResId, List<BookTypeDto> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookTypeDto item) {
        Glide.with(mContext)
                .load(item.getBookTypeImageUrl())
                .crossFade()
                .placeholder(R.mipmap.book_class_error)
                .into((ImageView) helper.getView(R.id.book_class_item_imageview_name));
        helper.setText(R.id.book_class_item_textview_name,item.getBookTypeName());
    }
}
