package com.xingnext.bigdata.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.textservice.TextInfo;
import android.widget.ListView;
import android.widget.TextView;

import com.lipo.utils.DisplayUtil;
import com.xingnext.bigdata.R;
import com.xingnext.bigdata.adapter.UpgradeTextAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */
public abstract class MyUpgradDialog extends AlertDialog {

    private int temp = 0;
    private Context context;
    private Resources resourse;
    private DisplayMetrics metrics;

    private int dpItem;
    private int dp120;

    private List<String> list;
    private String title;
    private UpgradeTextAdapter adapter;

    private TextView dialog_upgrade_title, dialog_upgrade_sure_button, dialog_upgrade_cancel_button;
    private ListView dialog_upgrade_list;

    private ListDialogDismiss dialogDismiss;

    public MyUpgradDialog(Context context) {
        super(context, R.style.mydialog);
        init(context);
    }

    public MyUpgradDialog(Context context, String title, List<String> list) {
        super(context, R.style.mydialog);
        this.title = title;
        this.list = list;
        init(context);
    }

    protected MyUpgradDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        resourse = context.getResources();
        metrics = context.getResources().getDisplayMetrics();

        dpItem = DisplayUtil.dip2px(context, 40);
        dp120 = DisplayUtil.dip2px(context, 120);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_upgrade);
        setSizeMode();

        initView();

    }

    private void initView() {
        dialog_upgrade_title = (TextView) findViewById(R.id.dialog_upgrade_title);
        dialog_upgrade_sure_button = (TextView) findViewById(R.id.dialog_upgrade_sure_button);
        dialog_upgrade_cancel_button = (TextView) findViewById(R.id.dialog_upgrade_cancel_button);

        dialog_upgrade_list = (ListView) findViewById(R.id.dialog_upgrade_list);

        if (list != null) {
            adapter = new UpgradeTextAdapter(context, list);
            dialog_upgrade_list.setAdapter(adapter);
        }
        if (title != null) {
            dialog_upgrade_title.setText(title);
        }

        dialog_upgrade_sure_button.setOnClickListener(onclick);
        dialog_upgrade_cancel_button.setOnClickListener(onclick);
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.dialog_upgrade_sure_button:
                    onClickSure();
                    dismiss();
                    break;
                case R.id.dialog_upgrade_cancel_button:
                    dismiss();
                    break;
            }
        }
    };

    public void notifiyData(List<TextInfo> list) {
        adapter.notifyDataSetChanged();
        // adapter = new DialogTextAdapter(context, list);
        // dialog_list_view.setAdapter(adapter);
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    private void setSizeMode() {
        int size = list.size();
        int paramHeight = metrics.heightPixels * 3 / 5;
        int dialogHeight = dpItem * size+dp120;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = metrics.widthPixels * 9 / 11;
        params.height = Math.min(paramHeight, dialogHeight);
        getWindow().setAttributes(params);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialogDismiss != null) {
            dialogDismiss.onDismiss();
        }
    }

    public ListDialogDismiss getDialogDismiss() {
        return dialogDismiss;
    }

    public void setDialogDismiss(ListDialogDismiss dialogDismiss) {
        this.dialogDismiss = dialogDismiss;
    }

    public interface ListDialogDismiss {
        public void onDismiss();
    }

    public abstract void onClickSure();

}
