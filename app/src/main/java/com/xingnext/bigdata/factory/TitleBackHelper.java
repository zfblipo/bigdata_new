package com.xingnext.bigdata.factory;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingnext.bigdata.R;

/**
 * Created by Administrator on 2017/3/30.
 */
public class TitleBackHelper extends BaseHelper {

    //导入cell_title_black.xml

    private ImageView cell_title_black_back,cell_title_black_icon;
    private TextView cell_title_black_name;

    public TitleBackHelper(Activity context, int id) {
        super(context, id);
        initView();
    }

    public TitleBackHelper(Activity context, int id, String titleName) {
        this(context, id);
        cell_title_black_name.setText(titleName);

    }

    private void initView() {
        cell_title_black_back = (ImageView) findViewById(R.id.cell_title_black_back);
        cell_title_black_name = (TextView) findViewById(R.id.cell_title_black_name);
        cell_title_black_icon = (ImageView) findViewById(R.id.cell_title_black_icon);

        cell_title_black_back.setOnClickListener(onclick);
    }

    public void setTitleName(String titleName) {
        cell_title_black_name.setText(titleName);
    }


    public void setTitleBg(int colorId) {
        mainView.setBackgroundResource(colorId);
    }

    public void dismissBack() {
        cell_title_black_back.setVisibility(View.GONE);
        cell_title_black_back.setOnClickListener(null);
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cell_title_black_back:
                    finish();
                    break;
            }
        }
    };

    public ImageView getTitleIcon(){
        cell_title_black_icon.setVisibility(View.VISIBLE);
        return cell_title_black_icon;
    }

}
