package ad0424.yls.example.com.madnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hdl.mricheditor.bean.CamaraRequestCode;
import com.hdl.mricheditor.bean.EditorBean;
import com.hdl.mricheditor.view.MRichEditor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();

        initData();
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

                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    createTime = simpleDateFormat.format(date);
                    modifyTime = simpleDateFormat.format(date);
                    DatabaseUtils.insert(title, content, createTime, imgPath, modifyTime, noteType);
                    Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(AddNoteActivity.this, "请输入标题或内容！！！", Toast.LENGTH_SHORT).show();

                }
            }
        });
        initBackground();
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
        super.onActivityResult(requestCode, resultCode, data);
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
}
