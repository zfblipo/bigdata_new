package com.xingnext.bigdata.factory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.utils.PayResult;

import java.util.Map;

public class PayHelper extends BaseHelper {

    private Activity mContext;
    private ImageView[] selecteds = new ImageView[2];
    private View[] items = new View[2];

    public int temp;
    private PayTask alipay;

    public PayHelper(Activity mContext, int id) {
        super(mContext, id);
        temp = 0;
        this.mContext = mContext;
        alipay = new PayTask(mContext);
        initView();
    }

    private void initView() {
        selecteds[0] = (ImageView) findViewById(R.id.cell_pay_select1);
        selecteds[1] = (ImageView) findViewById(R.id.cell_pay_select2);

        items[0] = findViewById(R.id.cell_pay_item1);
        items[1] = findViewById(R.id.cell_pay_item2);

        for (int i = 0; i < 2; i++) {
            final int position = i;
            items[i].setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (position != temp) {
                        selecteds[position].setImageResource(R.mipmap.checkbox_pressed);
                        selecteds[temp].setImageResource(R.mipmap.checkbox_normal);
                        temp = position;
                    }
                }
            });
        }
    }

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    public void toAlipay(final String orderStr) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                Map<String, String> result = alipay.payV2(orderStr, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


}
