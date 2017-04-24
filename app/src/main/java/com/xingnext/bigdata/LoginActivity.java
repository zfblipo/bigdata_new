package com.xingnext.bigdata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lipo.utils.KeyBoardUtils;
import com.lipo.views.ToastView;
import com.xingnext.bigdata.beans.UserData;
import com.xingnext.bigdata.factory.TitleHelper;
import com.xingnext.bigdata.utils.MyHttpConn;
import com.xingnext.bigdata.utils.MyStatic;
import com.xingnext.bigdata.utils.MyUrl;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    private TitleHelper titleHelper;
    private EditText login_phone,login_psw;

    private TextView login_forget_psw,login_regist,login_button;

    private MyHttpConn httpConn;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        httpConn = new MyHttpConn(mContext);
        preferences = getSharedPreferences("xing_big_user_info",
                Context.MODE_PRIVATE);

        initView();
    }

    private void initView(){
        titleHelper = new TitleHelper(mContext,R.id.login_top,"登录");

        login_phone = (EditText) findViewById(R.id.login_phone);
        login_psw = (EditText) findViewById(R.id.login_psw);
        login_forget_psw = (TextView) findViewById(R.id.login_forget_psw);
        login_regist = (TextView) findViewById(R.id.login_regist);
        login_button = (TextView) findViewById(R.id.login_button);

        login_forget_psw.setOnClickListener(onclick);
        login_regist.setOnClickListener(onclick);
        login_button.setOnClickListener(onclick);
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login_forget_psw:
                    startIntent(ResetPasswordActivity.class);
                    break;
                case R.id.login_regist:
                    startIntent(RegistActivity.class);
                    break;
                case R.id.login_button:
                    loginSubmit();
                    break;
            }
        }
    };

    private void loginSubmit(){

        String mobile = login_phone.getText().toString().trim();
        String password = login_psw.getText().toString().trim();

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

        closeKeyBoard();

        String url = MyUrl.loginUrl;
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", mobile);
        params.put("password", password);
        httpConn.httpPost(url, params, new MyHttpConn.OnCallBack() {
            @Override
            public void Success(JSONObject json) {
                JSONObject data = json.optJSONObject("data");
                UserData.fromJson(data,MyStatic.userData);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user_id",MyStatic.userData.user_id);
                editor.putString("access_token",MyStatic.userData.access_token);
                editor.putString("refresh_token",MyStatic.userData.refresh_token);
                editor.commit();
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startIntent(intent,MainActivity.class);
            }

            @Override
            public void onError(int code, String msg) {

            }
        });
    }

    private void closeKeyBoard(){
        KeyBoardUtils.closeKeybord(login_psw,LoginActivity.this);
        KeyBoardUtils.closeKeybord(login_phone,LoginActivity.this);
    }

}
