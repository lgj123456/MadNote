package ad0424.yls.example.com.madnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;

import ad0424.yls.example.com.madnote.R;

/**
 * Created by yhdj on 2017/5/3.
 */

public class SelectTimeActivity extends AppCompatActivity {
    private MaterialCalendarView mMaterialCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecttime);

        initViews();

    }

    private void initViews() {
        mMaterialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        mMaterialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String time = getSelectedDatesString(widget);
//                Intent i = getIntent();
//                boolean isBeginTime= i.getBooleanExtra("isBeginTime",false);
//                Intent intent = new Intent(SelectTimeActivity.this,SearchActivity.class);
//                if(isBeginTime){
//                    intent.putExtra("beginTime",time);
//                    intent.putExtra("isBeginTime",true);
//                    startActivity(intent);
//                }else{
//                    intent.putExtra("endTime",time);
//                    intent.putExtra("isBeginTime",false);
//                    startActivity(intent);
//                }

                Intent intent = new Intent();
                intent.putExtra("time",time);
                setResult(RESULT_OK,intent);
                finish();

                Toast.makeText(SelectTimeActivity.this, "time" + time, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getSelectedDatesString(MaterialCalendarView widget) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return  simpleDateFormat.format(date.getDate());
    }
}
