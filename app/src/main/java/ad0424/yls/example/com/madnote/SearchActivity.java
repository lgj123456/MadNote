package ad0424.yls.example.com.madnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by yhdj on 2017/5/3.
 */
public class SearchActivity extends AppCompatActivity {
    private EditText mEdiKeyword;
    private Button mBtnBeginTime;
    private Button mBtnEndTime;
    private Button btn_search;
    private boolean isBeginTime = true;
    private final int BEGIN_TIME = 1001;
    private final int END_TIME = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        initTime();
    }

    private void initTime() {
//        Intent intent = getIntent();
//        if (intent != null) {
//
//            isBeginTime = intent.getBooleanExtra("isBeginTime", false);
//            if (isBeginTime) {
//                String beginTime = intent.getStringExtra("beginTime");
//                mBtnBeginTime.setText(beginTime);
//            } else {
//                String endTime = intent.getStringExtra("endTime");
//                mBtnEndTime.setText(endTime);
//            }
//
//        }
    }

    private void initViews() {
        mEdiKeyword = (EditText) findViewById(R.id.edt_keyword);
        mBtnBeginTime = (Button) findViewById(R.id.btn_beginTime);
        mBtnEndTime = (Button) findViewById(R.id.btn_endTime);

        mBtnBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBeginTime = true;
                Intent intent = new Intent(SearchActivity.this, SelectTimeActivity.class);
              startActivityForResult(intent,BEGIN_TIME);
            }
        });

        mBtnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBeginTime = false;
                Intent intent = new Intent(SearchActivity.this, SelectTimeActivity.class);
               startActivityForResult(intent,END_TIME);

            }
        });

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode){
           case BEGIN_TIME:
               if(resultCode == RESULT_OK){
                   String time = data.getStringExtra("time");
                   if (time != null){
                       mBtnBeginTime.setText(time);
                   }else{
                       mBtnBeginTime.setText("开始时间");
                   }

               }
               break;
           case END_TIME:
               if(resultCode == RESULT_OK){
                   String time = data.getStringExtra("time");
                   if (time != null) {
                       mBtnEndTime.setText(time);
                   }else{
                       mBtnEndTime.setText("结束时间");
                   }
               }
       }
    }
}
