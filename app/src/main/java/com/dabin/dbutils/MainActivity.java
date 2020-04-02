package com.dabin.dbutils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dabin.basedialog.BaseDialog;
import com.dabin.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShortToast(MainActivity.this, System.currentTimeMillis()+"!!!!!!!");
                System.out.println("输出啊。。。。。。。。。。。。。。。。");
            }
        });

        final View.OnClickListener clickNegativeButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLongToast(MainActivity.this, System.currentTimeMillis()+"!!!!!!!");
            }
        };
        final View.OnClickListener clickPositiveButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLongToast(MainActivity.this, System.currentTimeMillis()+"???????");
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
