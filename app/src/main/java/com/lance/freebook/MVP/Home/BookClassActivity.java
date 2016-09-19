package com.lance.freebook.MVP.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lance.freebook.MVP.Adapter.BookClassGridAdapter;
import com.lance.freebook.MVP.Adapter.BookInfoListAdapter;
import com.lance.freebook.MVP.Base.BaseActivity;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.R;
import com.xiaochao.lcrapiddeveloplibrary.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookClassActivity extends BaseActivity {

    @BindView(R.id.book_class_toolbar)
    Toolbar bookClassToolbar;
    @BindView(R.id.book_class_list)
    RecyclerView bookClassList;
    private List<BookTypeDto> bookTypeList = new ArrayList<BookTypeDto>();
    private BookClassGridAdapter mQuickAdapter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_book_class);
        Intent intent = getIntent();
        bookTypeList = intent.getParcelableArrayListExtra("bookTypeList");
    }

    @Override
    protected void findViewById() {
        bookClassToolbar.setTitle("");
        setSupportActionBar(bookClassToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookClassToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        bookClassList.setLayoutManager(new GridLayoutManager(this,3));
        //如果Item高度固定  增加该属性能够提高效率
        bookClassList.setHasFixedSize(true);
        //设置适配器
        mQuickAdapter = new BookClassGridAdapter(R.layout.item_book_class_layout,bookTypeList);
        //设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //将适配器添加到RecyclerView
        bookClassList.setAdapter(mQuickAdapter);

        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setResult(position);
                finish();
            }
        });
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View v) {

    }
}
