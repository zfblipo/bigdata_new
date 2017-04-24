package com.xingnext.bigdata.factory;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingnext.bigdata.R;

/**
 * Created by lipo on 2017/3/13.
 */
public class TitleHelper extends BaseHelper{

    //导入cell_title.xml

    private ImageView cell_title_back,cell_title_image;
    private TextView cell_title_name,cell_title_button;


    public TitleHelper(Activity context, int id) {
        super(context, id);
        initView();
    }

    public TitleHelper(Activity context, int id,String titleName){
        this(context,id);
        cell_title_name.setText(titleName);
    }

    private void initView(){
        cell_title_back = (ImageView) findViewById(R.id.cell_title_back);
        cell_title_image = (ImageView) findViewById(R.id.cell_title_image);
        cell_title_name = (TextView) findViewById(R.id.cell_title_name);
        cell_title_button = (TextView) findViewById(R.id.cell_title_button);

        cell_title_back.setOnClickListener(onclick);
    }

    public void setTitleName(String titleName){
        cell_title_name.setText(titleName);
    }

    public void setTitleButton(String titleButton,final OnRightClickListener onRightClick){
        cell_title_button.setVisibility(View.VISIBLE);
        cell_title_button.setText(titleButton);
        cell_title_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightClick.onClick();
            }
        });
    }

    public void dismissBack(){
        cell_title_back.setVisibility(View.GONE);
        cell_title_back.setOnClickListener(null);
    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cell_title_back:
                    finish();
                    break;
            }
        }
    };


    public interface OnRightClickListener{
        public void onClick();
    }

}
