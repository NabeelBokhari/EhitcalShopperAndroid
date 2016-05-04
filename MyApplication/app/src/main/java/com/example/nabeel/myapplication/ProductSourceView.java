package com.example.nabeel.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class ProductSourceView extends LinearLayout {
    TextView sourceTitle;
    ImageView sourceTypeImage;
    TextView sourcePath;

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
    }

    public void setBadSource() {
        sourceTypeImage.setBackgroundResource(R.drawable.color_rating_bad);
    }

    public void setSourceTitle(String title) {
        sourceTitle.setText(title);
    }

    public void setSourcePath(String path) {
        sourcePath.setText("--" + path);
    }
}
