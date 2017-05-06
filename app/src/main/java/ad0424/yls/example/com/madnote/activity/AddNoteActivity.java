package ad0424.yls.example.com.madnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hdl.mricheditor.bean.CamaraRequestCode;
import com.hdl.mricheditor.bean.EditorBean;
import com.hdl.mricheditor.view.MRichEditor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import ad0424.yls.example.com.madnote.utils.DatabaseUtils;
import ad0424.yls.example.com.madnote.utils.MediaUtils;
import ad0424.yls.example.com.madnote.model.NoteType;
import ad0424.yls.example.com.madnote.R;

public class AddNoteActivity extends AppCompatActivity {

    private MRichEditor editor;
    private List<EditorBean> mEditorBeen;
    private ImageView iv_bg_love, iv_bg_study, iv_bg_sport, iv_bg_game, iv_bg_life, iv_bg_news;
    private ImageView iv_title;
    private TextView tv_title;
    private ImageView iv_confirm;
    private String title;
    private String content;
    private String imgPath;
    private String createTime;
    private String modifyTime;
    private String temp = "";
    private int noteType;
    private FloatingActionButton mBtnRecorder;
    private FloatingActionButton mBtnVideo;
    private String mAudioRecorderPath = "";
    private String mVideoPath = "";
    private final int RESULT_AUDIO_RECORDER_PATH = 1001;
    private final int RESULT_VIDEO_PATH = 1002;
    private TextView info;
    private ImageView micIcon;
    private MediaUtils mediaUtils;
    private boolean isCancel;
    private Chronometer chronometer;
    private RelativeLayout audioLayout;
    private String duration;
    private Handler mHandler;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明定位回调监听器

    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption = null;
    private String location = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();

        initData();
        initLocation();
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);


//        mLocationListener = new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//
//            }
//        };
//        //启动定位
        mLocationClient.startLocation();
        //   mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        //  mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    private void initData() {

    }

    private void initViews() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.add_note_toolBar);
//        setSupportActionBar(toolbar);


        editor = (MRichEditor) findViewById(R.id.mre_editor);
        editor.setPreviewBtnVisibility(View.GONE);

        iv_bg_game = (ImageView) findViewById(R.id.iv_bg_game);
        iv_bg_love = (ImageView) findViewById(R.id.iv_bg_love);
        iv_bg_study = (ImageView) findViewById(R.id.iv_bg_study);
        iv_bg_sport = (ImageView) findViewById(R.id.iv_bg_sport);
        iv_bg_life = (ImageView) findViewById(R.id.iv_bg_life);
        iv_bg_news = (ImageView) findViewById(R.id.iv_bg_news);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_confirm = (ImageView) findViewById(R.id.iv_confirm);
        iv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditorBeen = editor.getEditorList();


                if (mEditorBeen.size() > 0) {
                    for (int i = 0; i < mEditorBeen.size(); i++) {
                        temp = temp + mEditorBeen.get(i);

                    }
                    String str[] = dealString(temp).split(",");

                    if (str.length < 2) {
                        Toast.makeText(AddNoteActivity.this, "请输入标题或内容！！！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    title = str[0];
                    content = str[1];
                    if (str.length >= 3) {
                        imgPath = str[2];
                    }
                    Toast.makeText(AddNoteActivity.this, "您所在的位置是： " + location, Toast.LENGTH_SHORT).show();
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    createTime = simpleDateFormat.format(date);
                    modifyTime = simpleDateFormat.format(date);
                    DatabaseUtils.insert(title, content, createTime, imgPath, modifyTime, noteType, mAudioRecorderPath, mVideoPath);
                    Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(AddNoteActivity.this, "请输入标题或内容！！！", Toast.LENGTH_SHORT).show();

                }
            }
        });
        initBackground();

        mBtnRecorder = (FloatingActionButton) findViewById(R.id.btn_record);
        mBtnVideo = (FloatingActionButton) findViewById(R.id.btn_video);


        mBtnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteActivity.this, VideoActivity.class);
                startActivityForResult(intent, RESULT_VIDEO_PATH);
            }
        });


        mediaUtils = new MediaUtils(this);
        mediaUtils.setRecorderType(MediaUtils.MEDIA_AUDIO);
        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        mediaUtils.setTargetName(UUID.randomUUID() + ".m4a");
        // btn
        initHandler();

        info = (TextView) findViewById(R.id.tv_info);
        mBtnRecorder.setOnTouchListener(touchListener);
        chronometer = (Chronometer) findViewById(R.id.time_display);
        chronometer.setOnChronometerTickListener(tickListener);
        micIcon = (ImageView) findViewById(R.id.mic_icon);
        audioLayout = (RelativeLayout) findViewById(R.id.audio_layout);


    }

    private void initBackground() {
        Intent intent = getIntent();
        if (intent != null) {
            noteType = intent.getIntExtra("NoteType", 0);
            switch (noteType) {
                case NoteType.LOVE:
                    iv_bg_love.setVisibility(View.VISIBLE);
                    iv_title.setImageResource(R.mipmap.bg_love);
                    tv_title.setText("爱情");
//                    iv_bg_game.setVisibility(View.GONE);
//                    iv_bg_study.setVisibility(View.GONE);
//                    iv_bg_sport.setVisibility(View.GONE);
//                    iv_bg_life.setVisibility(View.GONE);
//                    iv_bg_news.setVisibility(View.GONE);
                    break;
                case NoteType.LIFE:
//                    iv_bg_love.setVisibility(View.GONE);
//                    iv_bg_game.setVisibility(View.GONE);
//                    iv_bg_study.setVisibility(View.GONE);
//                    iv_bg_sport.setVisibility(View.GONE);
                    iv_bg_life.setVisibility(View.VISIBLE);
                    iv_title.setImageResource(R.mipmap.bg_life);
                    tv_title.setText("生活");
                    // iv_bg_news.setVisibility(View.GONE);
                    break;

                case NoteType.GAME:
                    //    iv_bg_love.setVisibility(View.GONE);
                    iv_bg_game.setVisibility(View.VISIBLE);
                    iv_title.setImageResource(R.mipmap.bg_game);
                    tv_title.setText("游戏");
//                    iv_bg_study.setVisibility(View.GONE);
//                    iv_bg_sport.setVisibility(View.GONE);
//                    iv_bg_life.setVisibility(View.GONE);
//                    iv_bg_news.setVisibility(View.GONE);
                    break;

                case NoteType.SPORT:
//                    iv_bg_love.setVisibility(View.GONE);
//                    iv_bg_game.setVisibility(View.GONE);
//                    iv_bg_study.setVisibility(View.GONE);
                    iv_bg_sport.setVisibility(View.VISIBLE);
                    iv_title.setImageResource(R.mipmap.bg_sport);
                    tv_title.setText("运动");
//                    iv_bg_life.setVisibility(View.GONE);
//                    iv_bg_news.setVisibility(View.GONE);
                    break;

                case NoteType.STUDY:
//                    iv_bg_love.setVisibility(View.GONE);
//                    iv_bg_game.setVisibility(View.GONE);
                    iv_bg_study.setVisibility(View.VISIBLE);
                    iv_title.setImageResource(R.mipmap.bg_study);
                    tv_title.setText("学习");
//                    iv_bg_sport.setVisibility(View.GONE);
//                    iv_bg_life.setVisibility(View.GONE);
//                    iv_bg_news.setVisibility(View.GONE);
                    break;

                case NoteType.NEWS:
//                    iv_bg_love.setVisibility(View.GONE);
//                    iv_bg_game.setVisibility(View.GONE);
//                    iv_bg_study.setVisibility(View.GONE);
//                    iv_bg_sport.setVisibility(View.GONE);
//                    iv_bg_life.setVisibility(View.GONE);
                    iv_bg_news.setVisibility(View.VISIBLE);
                    iv_title.setImageResource(R.mipmap.bg_news);
                    tv_title.setText("新闻");
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            case RESULT_VIDEO_PATH:
                if (resultCode == RESULT_OK) {
                    mVideoPath = data.getStringExtra("videoPath");
                    Toast.makeText(this, "video = " + mVideoPath, Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消操作", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == CamaraRequestCode.CAMARA_GET_IMG) {
            editor.insertImg(data.getData());
        } else if (requestCode == CamaraRequestCode.CAMARA_TAKE_PHOTO) {
            editor.insertImg(data);
        }

    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.add_note_toolbar,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.iv_confirm:
//                mEditorBeen = editor.getEditorList();
//                Toast.makeText(this, "" + mEditorBeen.size(), Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }

    private String dealString(String str) {

        String temp[] = str.split("EditorBean");
        String result = "";

        for (int i = 0; i < temp.length; i++) {
            int beginIndex = temp[i].indexOf("content='");
            int endIndex = temp[i].indexOf("',");
            if (beginIndex != -1) {
                result = result + temp[i].substring((beginIndex + 9), endIndex) + ",";
            }

        }

        return result;
    }

    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                int volumn = msg.arg1;
                updateVolume(volumn);
                return true;
            }
        });
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean ret = false;
            float downY = 0;
            int action = event.getAction();
            switch (v.getId()) {
                case R.id.btn_record:
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            startAnim(true);
                            mediaUtils.record();
                            ret = true;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (true) {
                                        try {
                                            Thread.sleep(100);
                                            int volume = getVolume();
                                            Message msg = new Message();
                                            msg.arg1 = volume;
                                            mHandler.sendMessage(msg);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }).start();

                            break;
                        case MotionEvent.ACTION_UP:
                            stopAnim();
                            if (isCancel) {
                                isCancel = false;
                                mediaUtils.stopRecordUnSave();
                                Toast.makeText(AddNoteActivity.this, "取消保存", Toast.LENGTH_SHORT).show();
                            } else {
                                int duration = getDuration(chronometer.getText().toString());
                                switch (duration) {
                                    case -1:
                                        break;
                                    case -2:
                                        mediaUtils.stopRecordUnSave();
                                        Toast.makeText(AddNoteActivity.this, "时间太短", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        mediaUtils.stopRecordSave();
                                        String path = mediaUtils.getTargetFilePath();
                                        mAudioRecorderPath = path;
                                        Toast.makeText(AddNoteActivity.this, "maudiopath" + mAudioRecorderPath, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(AddNoteActivity.this, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float currentY = event.getY();
                            if (downY - currentY > 10) {
                                moveAnim();
                                isCancel = true;
                            } else {
                                isCancel = false;
                                startAnim(false);
                            }
                            break;
                    }
                    break;
            }
            return ret;
        }
    };

    Chronometer.OnChronometerTickListener tickListener = new Chronometer.OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            if (SystemClock.elapsedRealtime() - chronometer.getBase() > 60 * 1000) {
                stopAnim();
                mediaUtils.stopRecordSave();
                Toast.makeText(AddNoteActivity.this, "录音超时", Toast.LENGTH_SHORT).show();
                String path = mediaUtils.getTargetFilePath();

                Toast.makeText(AddNoteActivity.this, "文件以保存至：" + path, Toast.LENGTH_SHORT).show();
            }
        }
    };


    private int getDuration(String str) {
        String a = str.substring(0, 1);
        String b = str.substring(1, 2);
        String c = str.substring(3, 4);
        String d = str.substring(4);
        if (a.equals("0") && b.equals("0")) {
            if (c.equals("0") && Integer.valueOf(d) < 1) {
                return -2;
            } else if (c.equals("0") && Integer.valueOf(d) > 1) {
                duration = d;
                return Integer.valueOf(d);
            } else {
                duration = c + d;
                return Integer.valueOf(c + d);
            }
        } else {
            duration = "60";
            return -1;
        }

    }

    private void startAnim(boolean isStart) {
        audioLayout.setVisibility(View.VISIBLE);
        info.setText("上滑取消");

        micIcon.setBackground(null);
        micIcon.setBackground(getResources().getDrawable(R.drawable.p1));
        if (isStart) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.setFormat("%S");
            chronometer.start();
        }
    }

    private void stopAnim() {
        audioLayout.setVisibility(View.GONE);

        chronometer.stop();
    }

    private void moveAnim() {
        info.setText("松手取消");
        micIcon.setBackground(null);
        micIcon.setBackground(getResources().getDrawable(R.drawable.ic_undo_black_24dp));
    }


    // 获取音量值，只是针对录音音量
    public int getVolume() {
        int volume = 0;
        // 录音
        if (mediaUtils.isAudioRecorderNull() && mediaUtils.isRecordering()) {
            volume = mediaUtils.getMediaRecorder().getMaxAmplitude() / 650;
            Log.d("db", volume + "");
            if (volume != 0)
                volume = (int) (10 * Math.log10(volume)) / 3;
            Log.d("volume", volume + "");
        }
        return volume;
    }


    // 更新音量图
    private void updateVolume(int volume) {
        switch (volume) {
            case 1:

                micIcon.setBackground(getResources().getDrawable((R.drawable.p1)));
                break;
            case 2:

                micIcon.setBackground(getResources().getDrawable((R.drawable.p2)));
                break;
            case 3:

                micIcon.setBackground(getResources().getDrawable((R.drawable.p3)));
                break;
            case 4:

                micIcon.setBackground(getResources().getDrawable((R.drawable.p4)));
                break;
            case 5:

                micIcon.setBackground(getResources().getDrawable((R.drawable.p5)));
                break;
            case 6:

                micIcon.setBackground(getResources().getDrawable((R.drawable.p6)));
                break;

            default:
                break;
        }
    }

    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    Log.i("kkkkkkkkkkk", "aMapLocation.getAdCode() : =  " + aMapLocation.getAdCode() + "\n"
                            + " aMapLocation.getAddress() = " + aMapLocation.getAddress() + "\n"
                            + "aMapLocation.getAoiName()  = " + aMapLocation.getAoiName() + "\n"
                            + "aMapLocation.getBuildingId() = " + aMapLocation.getBuildingId() + "\n"
                            + "aMapLocation.getCity() = " + aMapLocation.getCity() + "\n"
                            + "aMapLocation.getProvince() = " + aMapLocation.getProvince() + "\n"
                            + "aMapLocation.getGpsAccuracyStatus() = " + aMapLocation.getGpsAccuracyStatus() + "\n"
                            + "aMapLocation.getLocationDetail() = " + aMapLocation.getLocationDetail() + "\n"
                            + "aMapLocation.getLatitude() = " + aMapLocation.getLatitude() + "\n"
                            + "aMapLocation.getLongitude() = " + aMapLocation.getLongitude() + "\n"
                    );
                      location = aMapLocation.getAddress() + "  " + aMapLocation.getAoiName()+"\n"
                            +"纬度:"+aMapLocation.getLatitude()+" 经度：" + aMapLocation.getLongitude();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }


        }
    };

    @Override
    protected void onStop() {
        mLocationClient.stopLocation();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.onDestroy();
        super.onDestroy();
    }

}
