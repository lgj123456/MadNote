package ad0424.yls.example.com.madnote.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ad0424.yls.example.com.madnote.utils.BmobUtil;
import ad0424.yls.example.com.madnote.utils.DatabaseUtils;
import ad0424.yls.example.com.madnote.R;

public class UpdateActivity extends AppCompatActivity {

    private EditText edt_update_title;
    private EditText edt_update_content;
    private Button btn_update;
    private String title;
    private String content;
    private String modifyTime;
    private int id;
    private Button mBtn_audioPlayer;
    private Button mBtn_videoPlayer;
    private String mAudioPath = null;
    private String mVideoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initViews();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("content");
            id = intent.getIntExtra("id", 0);
            mAudioPath = intent.getStringExtra("audioPath");
            mVideoPath = intent.getStringExtra("videoPath");
            edt_update_title.setText(title);
            edt_update_content.setText(content);
        }
        if (!mAudioPath.isEmpty()) {
            mBtn_audioPlayer.setVisibility(View.VISIBLE);
        }
        if (!mVideoPath.isEmpty()) {
            mBtn_videoPlayer.setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        edt_update_title = (EditText) findViewById(R.id.edt_update_title);
        edt_update_content = (EditText) findViewById(R.id.edt_update_content);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_update_title.getText().toString();
                content = edt_update_content.getText().toString();
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                modifyTime = simpleDateFormat.format(date);

                BmobUtil.update(id,title,content,modifyTime);
                DatabaseUtils.update(id, title, content, modifyTime);
                Toast.makeText(UpdateActivity.this, "修改成功！！！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                mBtn_videoPlayer.setVisibility(View.GONE);
                mBtn_audioPlayer.setVisibility(View.GONE);
                finish();
            }
        });


        mBtn_audioPlayer = (Button) findViewById(R.id.btn_audioPlayer);
        mBtn_videoPlayer = (Button) findViewById(R.id.btn_videoPlayer);
        mBtn_audioPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAudioPath != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file:///" + mAudioPath), "audio/MP3");
                    startActivity(intent);
                }
            }
        });

        mBtn_videoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVideoPath != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file:///" + mVideoPath), "video/mp4");
                    startActivity(intent);
                }
            }
        });
    }
}
