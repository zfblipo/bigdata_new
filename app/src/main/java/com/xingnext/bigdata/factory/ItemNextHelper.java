package com.xingnext.bigdata.factory;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingnext.bigdata.R;

/**
 * Created by lipo on 2017/3/19.
 */
public class ItemNextHelper extends BaseHelper {


    private ImageView cell_item_next_icon,cell_item_next_to;
    private TextView cell_item_next_name,cell_item_next_infor;

    public ItemNextHelper(Activity context, int id) {
        super(context, id);
        initView();
    }

    private void initView(){
        cell_item_next_icon = (ImageView) findViewById(R.id.cell_item_next_icon);
        cell_item_next_to = (ImageView) findViewById(R.id.cell_item_next_to);
        cell_item_next_name = (TextView) findViewById(R.id.cell_item_next_name);
        cell_item_next_infor = (TextView) findViewById(R.id.cell_item_next_infor);
    }

    public void fillData(int icon_id,String name,String content){
        cell_item_next_icon.setImageResource(icon_id);
        cell_item_next_name.setText(name);
        cell_item_next_infor.setText(content);
    }

    public void fillData(int icon_id,String name){
        cell_item_next_icon.setImageResource(icon_id);
        cell_item_next_name.setText(name);
        cell_item_next_infor.setVisibility(View.GONE);
    }

    public void setInforColor(int id){
        cell_item_next_infor.setTextColor(resources.getColor(id));
    }


}
