package phoenix.com.circleindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;

/**
 * Created by lichengfeng on 2016/8/30.
 */
public class CircleIndicator extends View {
    private static final int STYLE_STROKE = 0;
    private static final int STYLE_FILL = 1;
    private static final int ACTIVE_DEFAULT_COLOR = 0xFFFFFFFF;
    private static final int INACTIVE_DEFAULT_COLOR = 0x44FFFFFF;
    //radius unit dp
    private static final float DEFAULT_RADIUS = 2;
    private static final float DEFAULT_SPACE = 4;
    private static final int DEFAULT_COUNT = 2;
    private final Paint mActivePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mInactivePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float inactiveRadius;
    private float activeRadius;
    private float space;
    private int count;
    private int currentIndex = 0;

    public CircleIndicator(Context context) {
        this(context,null);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleIndicator);
        int activeType = a.getInt(R.styleable.CircleIndicator_activeType,STYLE_FILL);
        int activeColor = a.getInt(R.styleable.CircleIndicator_activeColor,ACTIVE_DEFAULT_COLOR);

        int inactiveType = a.getInt(R.styleable.CircleIndicator_inactiveType,STYLE_FILL);
        int inactiveColor = a.getInt(R.styleable.CircleIndicator_inactiveColor,INACTIVE_DEFAULT_COLOR);

        inactiveRadius = a.getDimension(R.styleable.CircleIndicator_inactiveRadius,dip2px(context,DEFAULT_RADIUS));
        activeRadius = a.getDimension(R.styleable.CircleIndicator_activeRadius,dip2px(context,DEFAULT_RADIUS));
        space = a.getDimension(R.styleable.CircleIndicator_space,dip2px(context,DEFAULT_SPACE));
        count = a.getInt(R.styleable.CircleIndicator_count,DEFAULT_COUNT);
        a.recycle();
        initPaint(activeType,activeColor,inactiveType,inactiveColor);
    }

    private void initPaint(int activeType,int activeColor,int inactiveType,int inactiveColor){
        switch(activeType){
            case STYLE_STROKE:
                mActivePaint.setStyle(Paint.Style.STROKE);
                break;
            default:
                mActivePaint.setStyle(Paint.Style.FILL);
                break;
        }
        mActivePaint.setColor(activeColor);

        switch(activeType){
            case STYLE_STROKE:
                mInactivePaint.setStyle(Paint.Style.STROKE);
                break;
            default:
                mInactivePaint.setStyle(Paint.Style.FILL);
                break;
        }
        mInactivePaint.setColor(inactiveColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measuredWidth(widthMeasureSpec),measuredHeight(heightMeasureSpec));
    }

    private int measuredWidth(int measureSpec){
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else{
            result = (int)((getPaddingLeft()+getPaddingRight()) +
                    (2*inactiveRadius*(count-1)) + 2*activeRadius + ((count -1)*space));
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(result,specSize);
            }
        }
        return result;
    }

    private  int measuredHeight(int measureSpec){
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else{
            float a = 2*inactiveRadius;
            float b = 2*activeRadius;

            float c = Math.max(a,b);
            result = (int)((getPaddingTop()+getPaddingBottom()) + (Math.max(2*inactiveRadius,2*activeRadius)));
            if(specMode == MeasureSpec.AT_MOST){
                result = Math.min(result,specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<count;i++){
            if(i==currentIndex){
                canvas.drawCircle(getPaddingLeft()+activeRadius+(2*inactiveRadius+space)*currentIndex,getPaddingTop()+activeRadius,
                        activeRadius,mActivePaint);
            }else if(i<currentIndex){
                canvas.drawCircle(getPaddingLeft() + inactiveRadius + ((2 * inactiveRadius + space) * i), getPaddingTop() + activeRadius,
                        inactiveRadius, mInactivePaint);
            }else {
                canvas.drawCircle(getPaddingLeft() + inactiveRadius + ((2 * inactiveRadius) * (i-1) + i*space + 2*activeRadius), getPaddingTop() + activeRadius,
                        inactiveRadius, mInactivePaint);
            }
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
        invalidate();
    }

    public void setActiveColor(int color){
        mActivePaint.setColor(color);
        invalidate();
    }

    public void setInactiveColor(int color){
        mInactivePaint.setColor(color);
        invalidate();
    }

    private static int dip2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale +0.5f);
    }

}
