package com.pws.formee.formeewheel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.pws.formee.formeewheel.R;
import com.pws.formee.formeewheel.data.ImageData;
import com.pws.formee.lib.wheel.CursorWheelLayout;

import java.util.List;

/**
 * Created by chensuilun on 16/4/24.
 */
public class SimpleImageAdapter extends CursorWheelLayout.CycleWheelAdapter {
    private List<ImageData> mMenuItemDatas;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public SimpleImageAdapter(Context context, List<ImageData> menuItemDatas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mMenuItemDatas = menuItemDatas;
    }

    @Override
    public int getCount() {
        return mMenuItemDatas == null ? 0 : mMenuItemDatas.size();
    }

    @Override
    public View getView(View parent, int position) {
        ImageData item = getItem(position);
        View root = mLayoutInflater.inflate(R.layout.wheel_image_item, null, false);
        ImageView imgView = (ImageView) root.findViewById(R.id.wheel_menu_item_iv);
        imgView.setImageResource(item.mImageRes);
        return root;
    }

    @Override
    public ImageData getItem(int position) {
        return mMenuItemDatas.get(position);
    }

}
