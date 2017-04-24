package com.xingnext.bigdata.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.lipo.utils.NetWork;
import com.lipo.views.MyProgreeDialog;
import com.lipo.views.ToastView;
import com.xingnext.bigdata.LoginActivity;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.beans.UserData;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by lipo on 2017/3/11.
 */
public class MyHttpConn {

    private Context context;
    private MyProgreeDialog myDialog;
    private boolean isToShowDialog = true;

    public MyHttpConn(Context context){
        this.context = context;
        isShowDialog(true);

    }

    public MyHttpConn(Context context, boolean isShowDialog) {
        this.context = context;
        isShowDialog(isShowDialog);
    }

    private void isShowDialog(boolean isShowDialog) {
        if (isShowDialog) {
            myDialog = new MyProgreeDialog(context);
        }
    }

    public void isToShowDialog(boolean isToShowDialog){
            this.isToShowDialog = isToShowDialog;
        if(!isToShowDialog){
            myDialog = null;
        }

    }

    public void httpGet(final String url,Map<String,String> params,final OnCallBack onCallBack){

        if (NetWork.isNetworkConnected(context)) {

            showDialog();

            com.lipo.utils.MyPublic.mapClearEmpty(params);
            if(!MyStatic.userData.access_token.isEmpty()){
                params.put("token", MyStatic.userData.access_token);
            }

            OkHttpUtils.get().headers(setHeader()).url(url).params(params).build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            onFailureCall("请求失败",onCallBack);
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            MyLog.i("url:"+url);
                            onResponseCall(s,onCallBack);
                        }
                    });
        }else{
            onFailureCall("无法连接网络",onCallBack);
        }
    }

    public void httpPost(String url,Map<String,String> params,final OnCallBack onCallBack){

        if (NetWork.isNetworkConnected(context)) {

            showDialog();

            com.lipo.utils.MyPublic.mapClearEmpty(params);
            if(!MyStatic.userData.access_token.isEmpty()){
                if(url.contains("?")){
                    url = url+"&token="+MyStatic.userData.access_token;
                }else{
                    url = url+"?token="+MyStatic.userData.access_token;
                }
            }
            OkHttpUtils.post().headers(setHeader()).url(url).params(params).build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {

                            onFailureCall("请求失败",onCallBack);
                        }

                        @Override
                        public void onResponse(String s, int i) {
                            onResponseCall(s,onCallBack);
                        }
                    });
        }else{
            onFailureCall("无法连接网络",onCallBack);
        }
    }

    public void onResponseCall(String body,OnCallBack onCallBack){
        dismissDialog();
        isToShowDialog(false);
        MyLog.i(body);

        JSONObject json = null;
        try {

            if (!body.startsWith("{") || !body.endsWith("}")){
                onCallBack.onError(-1,"请求数据有误");
            }else{
                json = new JSONObject(body);

                String status = json.optString("status");
                if("1".equals(status)){
                    onCallBack.Success(json);
                }else if("-1".equals(status)){
                    toLogin();
                }else {
                    String info = json.optString("info");
                    ToastView.setToasd(context,info);
                    onCallBack.onError(0,info);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void onFailureCall(String body,OnCallBack onCallBack){
        onCallBack.onError(-10,"无法连接到网络");
        dismissDialog();
    }


    private Map<String, String> setHeader(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("User-Agent", MyPublic.getUserAgent(context));
        return headers;
    }

    public interface OnCallBack{
        public void Success(JSONObject json);
        public void onError(int code,String msg);
    }

    public void showDialog() {
        if(isToShowDialog){
            if (myDialog != null && !myDialog.isShowing()) {
                myDialog.show();
            }
        }
    }

    public void dismissDialog() {
        if (isToShowDialog&&myDialog != null && myDialog.isShowing()) {
            myDialog.dismiss();
        }
    }

    private void toLogin(){

        if(MyStatic.userData.access_token.isEmpty()){
            Intent intent = new Intent();
            intent.setClass(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ((Activity)context).finish();
            ((Activity)context).startActivity(intent);
            ((Activity)context).overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }else{
            final SharedPreferences preferences = context.getSharedPreferences("xing_big_user_info",
                    Context.MODE_PRIVATE);
            String url = MyUrl.refreshToken;
            Map<String, String> params = new HashMap<String, String>();
            params.put("user_id",MyStatic.userData.user_id);
            params.put("refresh_token",MyStatic.userData.refresh_token);
            new MyHttpConn(context).httpPost(url, params, new MyHttpConn.OnCallBack() {
                @Override
                public void Success(JSONObject json) {
                    JSONObject data = json.optJSONObject("data");
                    UserData.fromJson(data,MyStatic.userData);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_id",MyStatic.userData.user_id);
                    editor.putString("access_token",MyStatic.userData.access_token);
                    editor.putString("refresh_token",MyStatic.userData.refresh_token);
                    editor.commit();
                }

                @Override
                public void onError(int code, String msg) {

                }
            });
        }
    }

}
