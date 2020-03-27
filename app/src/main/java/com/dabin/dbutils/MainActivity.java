package com.dabin.dbutils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dabin.basedialog.BaseDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View.OnClickListener clickNegativeButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "关关关关关关关关关", Toast.LENGTH_SHORT).show();
            }
        };
        final View.OnClickListener clickPositiveButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "开开开开开开开开", Toast.LENGTH_SHORT).show();
            }
        };

        //text

        new BaseDialog(MainActivity.this, BaseDialog.Location.BOTTOM)
                .setTitleName("请输入密码")
                .setNegativeButton(clickNegativeButton)
                .setPositiveButton(clickPositiveButton)
                .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnim(BaseDialog.AnimType.BOTTOMUP)
                .show();
    }
}
