package com.pws.formee.formeewheel.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pws.formee.formeewheel.R;
import com.pws.formee.formeewheel.data.MenuItemData;
import com.pws.formee.lib.wheel.CursorWheelLayout;

import java.util.List;

/**
 * Created by chensuilun on 16/4/24.
 */
public class SimpleTextAdapter extends CursorWheelLayout.CycleWheelAdapter {
    private List<MenuItemData> mMenuItemDatas;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    public static final int INDEX_SPEC = 9;
    private int mGravity;

//    public SimpleTextAdapter(Context context, List<MenuItemData> menuItemDatas) {
//        this(context, menuItemDatas, Gravity.START);
//    }

    public SimpleTextAdapter(Context context, List<MenuItemData> menuItemDatas, int gravity) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mMenuItemDatas = menuItemDatas;
        mGravity = gravity;
    }

    @Override
    public int getCount() {
        return mMenuItemDatas == null ? 0 : mMenuItemDatas.size();
    }

    @Override
    public View getView(View parent, int position) {
        MenuItemData item = getItem(position);
        View root = mLayoutInflater.inflate(R.layout.wheel_menu_item, null, false);
        TextView textView = (TextView) root.findViewById(R.id.wheel_menu_item_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setText(item.mTitle);
//        if (textView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
//            ((FrameLayout.LayoutParams) textView.getLayoutParams()).gravity = mGravity;
//        }
//        if (position == INDEX_SPEC) {
//            textView.setTextColor(ActivityCompat.getColor(mContext, R.color.red));
//        }
        return root;
    }

    @Override
    public MenuItemData getItem(int position) {
        return mMenuItemDatas.get(position);
    }

}
