package com.lance.freebook.MVP.BookInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lance.freebook.Data.HtmlData.HtmlData;
import com.lance.freebook.MVP.Base.BaseActivity;
import com.lance.freebook.MVP.BookInfo.presenter.BookInfoPresenter;
import com.lance.freebook.MVP.BookInfo.view.BookInfoView;
import com.lance.freebook.MVP.Entity.BookInfoDto;
import com.lance.freebook.MVP.Entity.BookInfoListDto;
import com.lance.freebook.MVP.Entity.BookTypeDto;
import com.lance.freebook.R;
import com.lance.freebook.common.Constant;
import com.xiaochao.lcrapiddeveloplibrary.viewtype.ProgressActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;

public class BookInfoActivity extends BaseActivity implements BookInfoView{

    @BindView(R.id.book_info_toolbar_textview_title)
    TextView bookInfoToolbarTextviewTitle;
    @BindView(R.id.book_info_toolbar)
    Toolbar bookInfoToolbar;
    @BindView(R.id.book_info_imageview_bookurl)
    ImageView bookInfoImageviewBookurl;
    @BindView(R.id.book_info_textview_name)
    TextView bookInfoTextviewName;
    @BindView(R.id.book_info_textview_author)
    TextView bookInfoTextviewAuthor;
    @BindView(R.id.book_info_textview_type)
    TextView bookInfoTextviewType;
    @BindView(R.id.book_info_textview_length)
    TextView bookInfoTextviewLength;
    @BindView(R.id.book_info_textview_progress)
    TextView bookInfoTextviewProgress;
    @BindView(R.id.book_info_textview_updatetime)
    TextView bookInfoTextviewUpdatetime;
    @BindView(R.id.book_info_layout_reading)
    LinearLayout bookInfoLayoutReading;
    @BindView(R.id.book_info_layout_download)
    LinearLayout bookInfoLayoutDownload;
    @BindView(R.id.book_info_textview_introduction)
    TextView bookInfoTextviewIntroduction;
    @BindView(R.id.book_info_progress)
    ProgressActivity bookInfoProgress;
    private BookInfoListDto bookinfo;
    private BookInfoDto bookInfoDto;
    private BookInfoPresenter presenter;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_book_info);
    }

    @Override
    protected void findViewById() {
        Intent intent = getIntent();
        bookinfo = intent.getParcelableExtra("bookinfo");
        bookInfoToolbar.setTitle("");
        setSupportActionBar(bookInfoToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookInfoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initview(BookInfoDto data){
        bookInfoDto=data;
        Glide.with(mContext)
                .load(data.getBookImageUrl())
                .crossFade()
                .placeholder(R.mipmap.image_error)
                .into(bookInfoImageviewBookurl);
        bookInfoTextviewName.setText(data.getBookName());
        bookInfoTextviewAuthor.setText(data.getBookAuthor());
        bookInfoTextviewType.setText(data.getBookType());
        bookInfoTextviewLength.setText(data.getBookLength());
        bookInfoTextviewProgress.setText(data.getBookProgress());
        bookInfoTextviewUpdatetime.setText(data.getBookUpdateTime());
        bookInfoTextviewIntroduction.setText(Html.fromHtml(data.getBookIntroduction()));
    }
    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {
        presenter = new BookInfoPresenter(this);
        presenter.loadData(bookinfo);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @OnClick({R.id.book_info_layout_reading, R.id.book_info_layout_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.book_info_layout_reading:
                Toast.makeText(mContext, "暂不支持", Toast.LENGTH_SHORT).show();
                break;
            case R.id.book_info_layout_download:
                Toast.makeText(mContext, bookInfoDto.getBookDownload(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void showProgress() {
        bookInfoProgress.showLoading();
    }

    @Override
    public void hideProgress() {
        bookInfoProgress.showContent();
    }

    @Override
    public void newData(BookInfoDto data) {
        initview(data);
    }

    @Override
    public void showLoadFailMsg() {
        toError();
    }
    public void toError(){
        bookInfoProgress.showError(getResources().getDrawable(R.mipmap.load_error), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookInfoProgress.showLoading();
                //重试
                presenter.loadData(bookinfo);
            }
        });
    }
}
