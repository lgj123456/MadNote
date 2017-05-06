package ad0424.yls.example.com.madnote.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class BothWayProgressBar extends View {
    private static final String TAG = "BothWayProgressBar";

    private boolean isCancel = false;
    private Context mContext;

    private Paint mRecordPaint;

    private Paint mCancelPaint;

    private int mVisibility;

    private int progress;

    private OnProgressEndListener mOnProgressEndListener;

    public BothWayProgressBar(Context context) {
        super(context, null);
    }
    public BothWayProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }
    private void init() {
        mVisibility = INVISIBLE;
        mRecordPaint = new Paint();
        mRecordPaint.setColor(Color.GREEN);
        mCancelPaint = new Paint();
        mCancelPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mVisibility == View.VISIBLE) {
            int height = getHeight();
            int width = getWidth();
            int mid = width / 2;


            //画出进度条
            if (progress < mid){
                canvas.drawRect(progress, 0, width-progress, height, isCancel ? mCancelPaint : mRecordPaint);
            } else {
                if (mOnProgressEndListener != null) {
                    mOnProgressEndListener.onProgressEndListener();
                }
            }
        } else {
            canvas.drawColor(Color.argb(0, 0, 0, 0));
        }
    }





    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }


    public void setCancel(boolean isCancel) {
        this.isCancel = isCancel;
        invalidate();
    }

    @Override
    public void setVisibility(int visibility) {
        mVisibility = visibility;
        //重新绘制
        invalidate();
    }


    public void setOnProgressEndListener(OnProgressEndListener onProgressEndListener) {
        mOnProgressEndListener = onProgressEndListener;
    }


    public interface OnProgressEndListener{
        void onProgressEndListener();
    }

}
