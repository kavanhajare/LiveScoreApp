package in.vs2.navjeevanadmin.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import in.vs2.navjeevanadmin.R;


public class AspectImageView extends ImageView {
    
    private static final int DEFAULT_XRATIO = 1;
    
    private static final int DEFAULT_YRATIO = 1;
 
    private int xRatio = DEFAULT_XRATIO;
 
    private int yRatio = DEFAULT_YRATIO;
 
    public int getXRatio() {
        return xRatio;
    }
 
    public void setXRatio(int xRatio) {
        this.xRatio = xRatio;
    }
 
    public int getYRatio() {
        return yRatio;
    }
 
    public void setYRatio(int yRatio) {
        this.yRatio = yRatio;
    }
 
    public AspectImageView(Context context) {
        super(context);
        init(context, null, 0);
    }
 
    public AspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }
 
    public AspectImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }
 
    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AspectImageView, 0, 0);
        try {
            xRatio = a.getInt(R.styleable.AspectImageView_xRatio, DEFAULT_XRATIO);
            yRatio = a.getInt(R.styleable.AspectImageView_yRatio, DEFAULT_YRATIO);
        } finally {
            a.recycle();
        }
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
 
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
 
        if(widthMode == MeasureSpec.EXACTLY && (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED)) {
            setMeasuredDimension(widthSize, (int)((double)widthSize / xRatio * yRatio));
        } else if(heightMode == MeasureSpec.EXACTLY && (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED)) {
            setMeasuredDimension((int)((double)heightSize / yRatio * xRatio), heightSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
 
    }
}