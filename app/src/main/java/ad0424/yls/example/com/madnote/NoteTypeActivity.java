package ad0424.yls.example.com.madnote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class NoteTypeActivity extends AppCompatActivity {

    private CircleMenu circleMenu;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_type);

        initViews();
    }

    private void initViews() {
        circleMenu = (CircleMenu) findViewById(R.id.circle_menu);
        circleMenu.openMenu();
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.mipmap.icon_menu, R.drawable.icon_cancel)
                .addSubMenu(Color.parseColor("#30A400"), R.mipmap.life)
                .addSubMenu(Color.parseColor("#258CFF"), R.mipmap.study)
                .addSubMenu(Color.parseColor("#FF4B32"), R.mipmap.love)
                .addSubMenu(Color.parseColor("#ff0000"), R.mipmap.game)
                .addSubMenu(Color.parseColor("#8A39FF"), R.mipmap.sport)
                .addSubMenu(Color.parseColor("#FF6A00"), R.mipmap.news)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {

                    @Override
                    public void onMenuSelected(int index) {
                        waitToActivity(index);

                    }

                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {

            }

            @Override
            public void onMenuClosed() {

            }

        });
    }

    private void waitToActivity(final int index) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1300);
                    switch (index) {
                        case NoteType.LOVE:
                            Intent intentLove = new Intent(NoteTypeActivity.this, AddNoteActivity.class);
                            intentLove.putExtra("NoteType", NoteType.LOVE);
                            startActivity(intentLove);
                            break;
                        case NoteType.LIFE:
                            Intent intentLife = new Intent(NoteTypeActivity.this, AddNoteActivity.class);
                            intentLife.putExtra("NoteType", NoteType.LIFE);

                            startActivity(intentLife);
                            break;

                        case NoteType.STUDY:
                            Intent intentStudy = new Intent(NoteTypeActivity.this, AddNoteActivity.class);
                            intentStudy.putExtra("NoteType", NoteType.STUDY);

                            startActivity(intentStudy);
                            break;

                        case NoteType.SPORT:
                            Intent intentSport = new Intent(NoteTypeActivity.this, AddNoteActivity.class);
                            intentSport.putExtra("NoteType", NoteType.SPORT);

                            startActivity(intentSport);
                            break;

                        case NoteType.GAME:
                            Intent intentGame = new Intent(NoteTypeActivity.this, AddNoteActivity.class);
                            intentGame.putExtra("NoteType", NoteType.GAME);

                            startActivity(intentGame);
                            break;

                        case NoteType.NEWS:
                            Intent intentNews = new Intent(NoteTypeActivity.this, AddNoteActivity.class);
                            intentNews.putExtra("NoteType", NoteType.NEWS);

                            startActivity(intentNews);
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
