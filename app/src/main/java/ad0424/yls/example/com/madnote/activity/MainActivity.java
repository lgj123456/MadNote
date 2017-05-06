package ad0424.yls.example.com.madnote.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ad0424.yls.example.com.madnote.R;
import ad0424.yls.example.com.madnote.adapter.MyAdapter;
import ad0424.yls.example.com.madnote.model.Note;
import ad0424.yls.example.com.madnote.utils.BmobUtil;
import ad0424.yls.example.com.madnote.utils.DatabaseUtils;

public class MainActivity extends AppCompatActivity {

    private SwipeMenuListView mSwipeMenuListView;
    private SwipeMenuCreator mSwipeMenuCreator;
    private MyAdapter mMyAdapter;
    private List<Note> mNoteArrayList = new ArrayList<>();
    private int del_index;
    private FloatingActionButton mFloatingActionButton;
    private AlarmManager alarmManager;
    private PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
        applyPermissions();
        initViews();
        createDatabase();
        initData();
        initMyAdapter();

    }

    private void initLocation() {

    }

    private void applyPermissions() {
        String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO};
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1001);
        }
    }

    private void createDatabase() {
        LitePal.getDatabase();
    }

    private void initData() {
        mNoteArrayList = DatabaseUtils.query();
    }

    private void initMyAdapter() {
        mMyAdapter = new MyAdapter(MainActivity.this, mNoteArrayList);
        mSwipeMenuListView.setAdapter(mMyAdapter);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.listView);
        mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
//                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(120);
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(120);

                // set a icon
                deleteItem.setIcon(R.drawable.ic_menu_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        mSwipeMenuListView.setMenuCreator(mSwipeMenuCreator);
        mSwipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        mSwipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int index1) {
                del_index = index1;

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

        mSwipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {

                    case 0:
                        // delete
                        Toast.makeText(MainActivity.this, "del_index =" + del_index, Toast.LENGTH_SHORT).show();
                        int id = mNoteArrayList.get(del_index).getId();
                        Toast.makeText(MainActivity.this, "id=" + id, Toast.LENGTH_SHORT).show();
                        BmobUtil.del(id);
                        DatabaseUtils.del(id);
                        mNoteArrayList.clear();
                        mNoteArrayList = DatabaseUtils.query();
                        initMyAdapter();

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.btn_Addnode);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteTypeActivity.class);
                startActivity(intent);

            }
        });

        mSwipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = mNoteArrayList.get(position).getTitle();
                String content = mNoteArrayList.get(position).getContent();
                int id2 = mNoteArrayList.get(position).getId();
                String audioPath = mNoteArrayList.get(position).getAudioPath();
                String videoPath = mNoteArrayList.get(position).getVideoPath();
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("id", id2);
                intent.putExtra("audioPath", audioPath);
                intent.putExtra("videoPath", videoPath);
                startActivity(intent);

            }
        });
        mSwipeMenuListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                Intent intent = new Intent(MainActivity.this, ClockActivity.class);
                pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(MainActivity.this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                Calendar c = Calendar.getInstance();
                                c.setTimeInMillis(System.currentTimeMillis());

                                c.set(Calendar.HOUR, hourOfDay);
                                c.set(Calendar.MINUTE, minute);

                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                Log.e("HEHE", c.getTimeInMillis() + "");

                                Toast.makeText(MainActivity.this, "闹钟设置完毕~" + c.getTimeInMillis(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                        .get(Calendar.MINUTE), false).show();

                return true;
            }
        });

    }

    private int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iv_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
        }
        return true;
    }


    @Override
    protected void onResume() {
        mMyAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
