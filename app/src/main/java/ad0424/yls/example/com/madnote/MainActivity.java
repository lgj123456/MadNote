package ad0424.yls.example.com.madnote;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private SwipeMenuListView mSwipeMenuListView;
    private SwipeMenuCreator mSwipeMenuCreator;
    private MyAdapter mMyAdapter;
    private ArrayList<Note> mNoteArrayList = new ArrayList<>();
    private int index;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        initViews();
        initData();
        initMyAdapter();
    }

    private void initData() {
        Note note = new Note();
        note.setContent("123");
        note.setTitle("a");
        Date date = new Date();
        String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
        note.setModifyTime(time);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);
        mNoteArrayList.add(note);


    }

    private void initMyAdapter() {
        mMyAdapter = new MyAdapter(MainActivity.this, mNoteArrayList);
        mSwipeMenuListView.setAdapter(mMyAdapter);
    }

    private void initViews() {
        mSwipeMenuListView = (SwipeMenuListView) findViewById(R.id.listView);
        mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(120);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);

                // add to menu
                menu.addMenuItem(openItem);

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
                index = index1;
                Toast.makeText(MainActivity.this, "" + index1, Toast.LENGTH_SHORT).show();
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
                        // open
                        break;
                    case 1:
                        // delete
                        mNoteArrayList.remove(index-1);
                        Log.i("aaaaaaaaaaa", "onMenuItemClick: " + mNoteArrayList.size());
                        mMyAdapter.notifyDataSetChanged();
                        Log.i("aaaaaaaaaaa", "onMenuItemClick: " + mNoteArrayList.size());
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
                Toast.makeText(MainActivity.this, "add", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.iv_search:
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
        }
        return true;
    }
}
