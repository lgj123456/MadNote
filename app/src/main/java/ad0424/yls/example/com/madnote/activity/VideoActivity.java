package ad0424.yls.example.com.madnote.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import ad0424.yls.example.com.madnote.R;
import ad0424.yls.example.com.madnote.widget.BothWayProgressBar;


public class VideoActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnTouchListener, BothWayProgressBar.OnProgressEndListener {

    private static final int LISTENER_START = 200;
    private static final String TAG = "VideoActivity";

    private SurfaceView mSurfaceView;
    private Camera mCamera;

    private View mStartButton;

    private BothWayProgressBar mProgressBar;

    private Thread mProgressThread;

    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mSurfaceHolder;

    private int videoWidth, videoHeight;

    private boolean isRecording;

    private File mTargetFile;

    private int mProgress;

    public static final int MAX_TIME = 10;

    private boolean isCancel;

    private GestureDetector mDetector;

    private boolean isZoomIn = false;

    private MyHandler mHandler;
    private TextView mTvTip;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_video);
        initView();
    }

    private void initView() {
        videoWidth = 640;
        videoHeight = 480;
        mSurfaceView = (SurfaceView) findViewById(R.id.main_surface_view);

        mDetector = new GestureDetector(this, new ZoomGestureListener());

        mSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });

        mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.setFixedSize(videoWidth, videoHeight);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(this);
        mStartButton = findViewById(R.id.main_press_control);
        mTvTip = (TextView) findViewById(R.id.main_tv_tip);

        mStartButton.setOnTouchListener(this);
        mProgressBar = (BothWayProgressBar) findViewById(R.
                id.main_progress_bar);
        mProgressBar.setOnProgressEndListener(this);
        mHandler = new MyHandler(this);
        mMediaRecorder = new MediaRecorder();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        startPreView(holder);
    }

    private void startPreView(SurfaceHolder holder) {
        Log.d(TAG, "startPreView: ");

        if (mCamera == null) {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        if (mCamera != null) {
            mCamera.setDisplayOrientation(90);
            try {
                mCamera.setPreviewDisplay(holder);
                Camera.Parameters parameters = mCamera.getParameters();

                List<String> focusModes = parameters.getSupportedFocusModes();
                if (focusModes != null) {
                    for (String mode : focusModes) {
                        mode.contains("continuous-video");
                        parameters.setFocusMode("continuous-video");
                    }
                }
                mCamera.setParameters(parameters);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            Log.d(TAG, "surfaceDestroyed: ");

            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }


    @Override
    public void onProgressEndListener() {

        stopRecordSave();
    }

    private void startRecord() {

        if (mMediaRecorder != null) {
            Log.i(TAG, "startRecord: eeeeeeeeeeeeeeeeeeeeee");
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.i(TAG, "startRecord: fffffffffffffffffffffffff");


                try {
                    //mMediaRecorder.reset();
                    mCamera.unlock();
                    mMediaRecorder.setCamera(mCamera);
                    //从相机采集视频
                    mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    // 从麦克采集音频信息
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mMediaRecorder.setVideoSize(videoWidth, videoHeight);

                    mMediaRecorder.setVideoFrameRate(24);

                    mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 1024 * 100);

                    File targetDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                    mTargetFile = new File(targetDir,
                            SystemClock.currentThreadTimeMillis() + ".mp4");
                    mMediaRecorder.setOutputFile(mTargetFile.getAbsolutePath());
                    mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

                    mMediaRecorder.setOrientationHint(90);

                    mMediaRecorder.prepare();

                    mMediaRecorder.start();
                    isRecording = true;
                    Toast.makeText(this, "isrecording=" + isRecording, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void stopRecordSave() {
        Log.i(TAG, "stopRecordSave: bbbbbbbbbbbbbbbbbbbbb");
        if (isRecording) {
            isRunning = false;
            mMediaRecorder.stop();
            isRecording = false;
            Log.i(TAG, "stopRecordSave: aaaaaaaaaaaaaaaaaaaaaaaaaa");
            Toast.makeText(this, "视频已经放至" + mTargetFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            saveTodatabase(mTargetFile.getAbsolutePath());
        }
    }

    private void saveTodatabase(String absolutePath) {
        Intent intent = new Intent();
        intent.putExtra("videoPath",absolutePath);
        setResult(RESULT_OK,intent);
        finish();
    }


    private void stopRecordUnSave() {
        if (isRecording) {
            isRunning = false;
            mMediaRecorder.stop();
            isRecording = false;
            if (mTargetFile.exists()) {

                mTargetFile.delete();
            }
        }
    }

    public void setZoom(int zoomValue) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            if (parameters.isZoomSupported()) {//判断是否支持
                int maxZoom = parameters.getMaxZoom();
                if (maxZoom == 0) {
                    return;
                }
                if (zoomValue > maxZoom) {
                    zoomValue = maxZoom;
                }
                parameters.setZoom(zoomValue);
                mCamera.setParameters(parameters);
            }
        }

    }


    private static class MyHandler extends Handler {
        private WeakReference<VideoActivity> mReference;
        private VideoActivity mActivity;

        public MyHandler(VideoActivity activity) {
            mReference = new WeakReference<VideoActivity>(activity);
            mActivity = mReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mActivity.mProgressBar.setProgress(mActivity.mProgress);
                    break;
            }

        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean ret = false;
        int action = event.getAction();
        float ey = event.getY();
        float ex = event.getX();

        int vW = v.getWidth();
        int left = LISTENER_START;
        int right = vW - LISTENER_START;

        float downY = 0;

        switch (v.getId()) {
            case R.id.main_press_control: {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (ex > left && ex < right) {
                            mProgressBar.setCancel(false);

                            mTvTip.setVisibility(View.VISIBLE);
                            mTvTip.setText("↑ 上滑取消");

                            downY = ey;

                            mProgressBar.setVisibility(View.VISIBLE);

                            Toast.makeText(VideoActivity.this, "开始录制", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onTouch: dddddddddddddddddddddddd");
                            startRecord();

                            mProgressThread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        mProgress = 0;
                                        isRunning = true;
                                        while (isRunning) {
                                            mProgress++;
                                            mHandler.obtainMessage(0).sendToTarget();
                                            Thread.sleep(20);
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            mProgressThread.start();
                            ret = true;
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                        if (ex > left && ex < right) {
                            mTvTip.setVisibility(View.INVISIBLE);
                            mProgressBar.setVisibility(View.INVISIBLE);
                            //判断是否为录制结束, 或者为成功录制(时间过短)
                            if (!isCancel) {
                                if (mProgress < 50) {
                                    //时间太短不保存
                                    stopRecordUnSave();
                                    Toast.makeText(this, "时间太短", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                //停止录制
                                Log.i(TAG, "stopRecordSave:cccccccccccccccccccc");
                                stopRecordSave();
                                Log.i(TAG, "stopRecordSave:gggggggggggggggggggggg");
                            } else {
                                //现在是取消状态,不保存
                                stopRecordUnSave();
                                isCancel = false;
                                Toast.makeText(this, "取消录制", Toast.LENGTH_SHORT).show();
                                mProgressBar.setCancel(false);
                            }

                            ret = false;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (ex > left && ex < right) {
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
                                isCancel = true;
                                mProgressBar.setCancel(true);
                            }
                        }
                        break;
                }
                break;

            }

        }
        return ret;
    }


    class ZoomGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            super.onDoubleTap(e);
            if (mMediaRecorder != null) {
                if (!isZoomIn) {
                    setZoom(20);
                    isZoomIn = true;
                } else {
                    setZoom(0);
                    isZoomIn = false;
                }
            }
            return true;
        }
    }


}

