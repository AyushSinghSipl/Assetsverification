package com.pws.formee.formeewheel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.pws.formee.formeewheel.adapter.SimpleTextAdapter;
import com.pws.formee.formeewheel.data.ImageData;
import com.pws.formee.formeewheel.data.MenuItemData;
import com.pws.formee.formeewheel.widget.SimpleTextCursorWheelLayout;

import java.util.ArrayList;
import java.util.List;

public class WheelActivity extends AppCompatActivity {
    SimpleTextCursorWheelLayout mTestCircleMenuLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        mTestCircleMenuLeft = findViewById(R.id.test_circle_menu_left);
        String[] res = new String[]{"Automative", "Jobs", "Home &\nGarden", "Electronics", "Community", "Pets          ", "Books &\nMusic", "Commercial\nReal estate", "Residential\nReal estate"};
        List<MenuItemData> menuItemDatas = new ArrayList<MenuItemData>();
        for (int i = 0; i < res.length; i++) {
            menuItemDatas.add(new MenuItemData(res[i]));
        }
        SimpleTextAdapter simpleTextAdapter = new SimpleTextAdapter(this, menuItemDatas, Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        mTestCircleMenuLeft.setAdapter(simpleTextAdapter);
//        mTestCircleMenuLeft.setOnMenuSelectedListener(this);
//        SimpleTextAdapter simpleTextAdapter2 = new SimpleTextAdapter(this, menuItemDatas, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
//        mTestCircleMenuRight.setAdapter(simpleTextAdapter2);
//        mTestCircleMenuRight.setOnMenuSelectedListener(this);
//        List<ImageData> imageDatas = new ArrayList<ImageData>();
//        imageDatas.add(new ImageData(R.drawable.ic_bank_bc, "0"));
//        imageDatas.add(new ImageData(R.drawable.ic_bank_china, "1"));
//        imageDatas.add(new ImageData(R.drawable.ic_bank_guangda, "2"));
//        imageDatas.add(new ImageData(R.drawable.ic_bank_guangfa, "3"));
//        imageDatas.add(new ImageData(R.drawable.ic_bank_jianshe, "4"));
//        imageDatas.add(new ImageData(R.drawable.ic_bank_jiaotong, "5"));
//        SimpleImageAdapter simpleImageAdapter = new SimpleImageAdapter(this, imageDatas);
//        mTestCircleMenuTop.setAdapter(simpleImageAdapter);
//        mTestCircleMenuTop.setOnMenuSelectedListener(new CursorWheelLayout.OnMenuSelectedListener() {
//            @Override
//            public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
//                Toast.makeText(MainActivity.this, "Top Menu selected position:" + pos, Toast.LENGTH_SHORT).show();
//            }
//        });
//        mTestCircleMenuTop.setOnMenuItemClickListener(new CursorWheelLayout.OnMenuItemClickListener() {
//            @Override
//            public void onItemClick(View view, int pos) {
//                Toast.makeText(MainActivity.this, "Top Menu click position:" + pos, Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }
}