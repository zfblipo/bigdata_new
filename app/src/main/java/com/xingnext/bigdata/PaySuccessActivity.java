package com.xingnext.bigdata;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xingnext.bigdata.factory.TitleHelper;


public class PaySuccessActivity extends BaseActivity {

    private TextView pay_success_button;
    private TitleHelper titleBackHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);

        pay_success_button = (TextView) findViewById(R.id.pay_success_button);

        pay_success_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startIntent(MyreadRecordActivity.class);
            }
        });

        titleBackHelper = new TitleHelper(mContext,R.id.pay_success_top,"支付成功");

    }



}
