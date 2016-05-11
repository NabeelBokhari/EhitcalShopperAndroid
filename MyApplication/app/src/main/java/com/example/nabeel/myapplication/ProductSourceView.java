package com.example.nabeel.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * TODO: document your custom view class.
 */
public class ProductSourceView extends LinearLayout {
    TextView sourceTitle;
    ImageView sourceTypeImage;
    TextView sourcePath;
    private boolean positive;

    public ProductSourceView(Context context) {
        super(context);
        View.inflate(context, R.layout.product_source_view, this);
        init(null, 0);
    }

    public ProductSourceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.product_source_view, this);
        init(attrs, 0);
    }

    public ProductSourceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.product_source_view, this);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate();
    }

    private void inflate() {
        sourceTitle = (TextView)findViewById(R.id.sourceTitleText);
        sourceTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        sourcePath = (TextView)findViewById(R.id.sourcePathText);
        sourceTypeImage = (ImageView)findViewById(R.id.sourceTypeImage);
    }

    public void setGoodSource() {
        sourceTypeImage.setBackgroundResource(R.drawable.color_rating_good);
        positive = true;
    }

    public void setBadSource() {
        sourceTypeImage.setBackgroundResource(R.drawable.color_rating_bad);
        positive = false;
    }

    public void setSourceTitle(String title) {
        sourceTitle.setText(title);
    }

    public void setSourcePath(String path) {
        sourcePath.setText("--" + path);
    }

    public void setAttributes (final ProductSource source) {
        setSourceTitle(source.getTitle());
        setSourcePath(source.getSourceName());
        if (source.getPositive()) {
            setGoodSource();
        } else {
            setBadSource();
        }
        sourceTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(source.getUrl() == null) {
                    return;
                }
                Uri uri = Uri.parse(source.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(source.getUrl()));
                getContext().startActivity(intent);
            }
        });
    }

    public boolean isPositive() {
        return positive;
    }

    public static class ProductSource implements Serializable{
        String title;
        String url;
        String sourceName;
        boolean positive;

        public ProductSource(String title, String url, String sourceName, boolean positive) {
            this.title = title;
            this.url = url;
            this.sourceName = sourceName;
            this.positive = positive;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getSourceName() {
            return sourceName;
        }

        public boolean getPositive() {
            return positive;
        }
    }

}
