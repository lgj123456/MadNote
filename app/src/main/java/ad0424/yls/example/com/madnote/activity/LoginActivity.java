package ad0424.yls.example.com.madnote.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ad0424.yls.example.com.madnote.R;
import ad0424.yls.example.com.madnote.model.NoteUser;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mPhoneNum;
    private EditText mGetCode;
    private Button mBtnLogin;
    private Button mSendCode;
    private String phoneNum;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "88550df99426c362c26d3ee1151a6bc6");

        initViews();
        isLogin();
    }

    private void isLogin() {
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            // 允许用户使用应用
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            //缓存用户对象为空时， 可打开用户注册界面…
        }
    }

    private void initViews() {
        mPhoneNum = (EditText) findViewById(R.id.edt_phoneNum);
        mGetCode = (EditText) findViewById(R.id.edt_getCode);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mSendCode = (Button) findViewById(R.id.sendCode);
        mSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNum = mPhoneNum.getText().toString().trim();
                if (!isMobileNO(phoneNum)) {
                    Toast.makeText(LoginActivity.this, "手机号码不合法，请重新输入！！！", Toast.LENGTH_SHORT).show();
                    return;
                }
                countTime();
                BmobSMS.requestSMSCode(phoneNum, "ADbmob", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException ex) {
                        if (ex == null) {//验证码发送成功
                            Toast.makeText(LoginActivity.this, "验证码已发送！！！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "发送失败！！！", Toast.LENGTH_SHORT).show();
                            mSendCode.setClickable(true);
                        }
                    }
                });
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnLogin.setClickable(false);
                mBtnLogin.setBackgroundColor(Color.GRAY);
                String code = mGetCode.getText().toString().trim();
                if (code.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "验证码不能为空！！！", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobUser.signOrLoginByMobilePhone(phoneNum, code, new LogInListener<NoteUser>() {

                    @Override
                    public void done(NoteUser user, BmobException e) {
                        if (user != null) {
                            Log.i("smile", "用户登陆成功");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败！！！", Toast.LENGTH_SHORT).show();
                            mBtnLogin.setClickable(true);
                            mBtnLogin.setBackgroundColor(Color.WHITE);
                        }
                    }
                });


            }
        });
    }

    private boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private void countTime() {
        mSendCode.setClickable(false);
        mSendCode.setBackgroundColor(Color.GRAY);
        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                mSendCode.setText(millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                mSendCode.setClickable(true);
                mSendCode.setText("重新发送");
                mSendCode.setBackgroundColor(Color.WHITE);
            }
        }.start();
    }
}
