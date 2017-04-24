package com.xingnext.bigdata;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class RegistActivity extends BaseActivity {

    private Handler handler = new Handler();
    private int code_num = 60;
    private TitleHelper titleHelper;
    private EditText regist_phone,regist_psw,regist_code_edit;
    private TextView regist_code,regist_agreement,regist_button;
    private ImageView regist_checked;

    private boolean isChecked = true;

    private MyHttpConn httpConn;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        httpConn = new MyHttpConn(mContext);
        preferences = getSharedPreferences("xing_big_user_info",
                Context.MODE_PRIVATE);

        initView();
    }

    private void initView(){
        titleHelper = new TitleHelper(mContext,R.id.regist_top,"注册");
        regist_phone = (EditText) findViewById(R.id.regist_phone);
        regist_psw = (EditText) findViewById(R.id.regist_psw);
        regist_code_edit = (EditText) findViewById(R.id.regist_code_edit);
        regist_code = (TextView) findViewById(R.id.regist_code);
        regist_agreement = (TextView) findViewById(R.id.regist_agreement);
        regist_button = (TextView) findViewById(R.id.regist_button);
        regist_checked = (ImageView) findViewById(R.id.regist_checked);

        regist_code.setOnClickListener(onclick);
        regist_agreement.setOnClickListener(onclick);
        regist_button.setOnClickListener(onclick);
        regist_checked.setOnClickListener(onclick);

    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.regist_code:
                    getCode();
                    break;
                case R.id.regist_agreement:
                    break;
                case R.id.regist_button:
                    registSubmit();
                    break;
                case R.id.regist_checked:
                    if(isChecked){
                        isChecked = false;
                        regist_checked.setImageResource(R.mipmap.checkbox_n);
                    }else{
                        isChecked = true;
                        regist_checked.setImageResource(R.mipmap.checkbox_s);
                    }
                    break;
            }
        }
    };

    private void getCode(){
        String mobile = regist_phone.getText().toString().trim();

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
        String mobile = regist_phone.getText().toString().trim();
        String password = regist_psw.getText().toString().trim();
        String smscode = regist_code_edit.getText().toString().trim();

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

        String url = MyUrl.registUrl;
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", mobile);
        params.put("password", password);
        params.put("smscode", smscode);
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

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            if(code_num>0){
                regist_code.setText("还剩"+code_num+"s");
                regist_code.setTextColor(getResources().getColor(R.color.main_text6));
                regist_code.setEnabled(false);
                code_num--;
                handler.postDelayed(task,1000);
            }else{
                regist_code.setText("获取验证码");
                regist_code.setTextColor(getResources().getColor(R.color.success_blue));
                regist_code.setEnabled(true);
            }
        }
    };

    private void closeKeyBoard(){
        KeyBoardUtils.closeKeybord(regist_phone,mContext);
        KeyBoardUtils.closeKeybord(regist_psw,mContext);
        KeyBoardUtils.closeKeybord(regist_code_edit,mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(task);
    }
}
