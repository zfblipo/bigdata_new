package com.xingnext.bigdata;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lipo.utils.KeyBoardUtils;
import com.lipo.views.ToastView;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends BaseActivity {

    private Handler handler = new Handler();
    private int code_num = 60;
    private TitleHelper titleHelper;
    private EditText reset_password_phone,reset_password_psw,reset_password_code_edit;
    private TextView reset_password_code,reset_password_button;

    private MyHttpConn httpConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        httpConn = new MyHttpConn(mContext);

        initView();
    }

    private void initView(){
        titleHelper = new TitleHelper(mContext,R.id.reset_password_top,"重置密码");
        reset_password_phone = (EditText) findViewById(R.id.reset_password_phone);
        reset_password_psw = (EditText) findViewById(R.id.reset_password_psw);
        reset_password_code_edit = (EditText) findViewById(R.id.reset_password_code_edit);
        reset_password_code = (TextView) findViewById(R.id.reset_password_code);
        reset_password_button = (TextView) findViewById(R.id.reset_password_button);

        reset_password_code.setOnClickListener(onclick);
        reset_password_button.setOnClickListener(onclick);

    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reset_password_code:
                    getCode();
                    break;
                case R.id.reset_password_button:
                    registSubmit();
                    break;
            }
        }
    };


    private void getCode(){
        String mobile = reset_password_phone.getText().toString().trim();

        if(mobile == null){
            ToastView.setToasd(mContext,"请输入手机号码");
            return;
        }

        if(mobile.length()!=11){
            ToastView.setToasd(mContext,"请输入正确的手机号码");
            return;
        }

        closeKeyBoard();

        String url = MyUrl.smsUrl;
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                ToastView.setToasd(mContext,"手机短信验证码发送成功");
                handler.post(task);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    private void registSubmit(){
        String mobile = reset_password_phone.getText().toString().trim();
        String password = reset_password_psw.getText().toString().trim();
        String smscode = reset_password_code_edit.getText().toString().trim();

        if(mobile == null){
            ToastView.setToasd(mContext,"请输入手机号码");
            return;
        }

        if(mobile.length()!=11){
            ToastView.setToasd(mContext,"请输入正确的手机号码");
            return;
        }

        if(password == null){
            ToastView.setToasd(mContext,"请输入密码");
            return;
        }

        if(password.length()<6){
            ToastView.setToasd(mContext,"您输入的密码太简单，请重新输入");
            return;
        }

        if(smscode == null){
            ToastView.setToasd(mContext,"请输入手机验证码");
            return;
        }

        closeKeyBoard();

        String url = MyUrl.forgetPswUrl;
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", mobile);
        params.put("password", password);
        params.put("smscode", smscode);
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                ToastView.setToasd(mContext,"重置密码成功");
                finishActivity();

            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }


    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if(code_num>0){
                reset_password_code.setText("还剩"+code_num+"s");
                reset_password_code.setTextColor(getResources().getColor(R.color.main_text6));
                reset_password_code.setEnabled(false);
                code_num--;
                handler.postDelayed(task,1000);
            }else{
                reset_password_code.setText("获取验证码");
                reset_password_code.setTextColor(getResources().getColor(R.color.success_blue));
                reset_password_code.setEnabled(true);
            }
        }
    };

    private void closeKeyBoard(){
        KeyBoardUtils.closeKeybord(reset_password_phone,mContext);
        KeyBoardUtils.closeKeybord(reset_password_psw,mContext);
        KeyBoardUtils.closeKeybord(reset_password_code_edit,mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task);
    }
}
