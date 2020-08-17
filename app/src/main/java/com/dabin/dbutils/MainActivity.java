package com.dabin.dbutils;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.dabin.basedialog.BaseDialog;
import com.dabin.basedialog.LoadDialog;
import com.dabin.baseframe.BaseActivity;
import com.dabin.utils.ToastUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View.OnClickListener clickNegativeButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLongToast(MainActivity.this, System.currentTimeMillis() + "!!!!!!!");
            }
        };
        final View.OnClickListener clickPositiveButton = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showLongToast(MainActivity.this, System.currentTimeMillis() + "???????");
            }
        };

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //text

                final LoadDialog loadDialog = new LoadDialog(MainActivity.this,
                        BaseDialog.Location.CENTER, true, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShortToast(MainActivity.this,"dismiss----------");
                    }
                });
                loadDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while (i <= 100) {
                            final int index = i++;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadDialog.update(index);
                                    if(index == 100)
                                        loadDialog.dismiss();
                                }
                            });
                            SystemClock.sleep(100);
                        }
                    }
                }).start();


//                new BaseDialog(MainActivity.this, BaseDialog.Location.BOTTOM)
//                        .setTitleName("please enter your password")
//                        .setMidView(R.layout.dialogmy, true)
//                        .setNegativeButton(clickNegativeButton)
//                        .setPositiveButton(clickPositiveButton)
//                        .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                ViewGroup.LayoutParams.WRAP_CONTENT)
//                        .setAnim(BaseDialog.AnimType.BOTTOMUP)
//                        .show();
            }
        });
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //text
                new BaseDialog(MainActivity.this, BaseDialog.Location.TOP)
                        .setTitleName("please enter your password")
                        .setNegativeButton(clickNegativeButton)
                        .setPositiveButton(clickPositiveButton)
                        .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnim(BaseDialog.AnimType.TOPDOWN)
                        .show();
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //text
                new BaseDialog(MainActivity.this, BaseDialog.Location.CENTER)
                        .setTitleName("please enter your password")
                        .setNegativeButton(clickNegativeButton)
                        .setPositiveButton(clickPositiveButton)
                        .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT)
//                        .setAnim(BaseDialog.AnimType.WARNING)
                        .show();
            }
        });

    }

}
