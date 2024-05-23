package com.example.smscheck;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button start;
    private Button stop;
    private EditText keywordsEdit;
    private EditText phoneNumEdit;
    private CheckBox allCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        keywordsEdit= findViewById(R.id.keywords);
        phoneNumEdit = findViewById(R.id.phoneNum);
        allCheck = findViewById(R.id.allCheck);
        //收取
        Intent intent = new Intent(MainActivity.this, MessageService.class);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keywordsEdit.getText().toString().isEmpty() || phoneNumEdit.getText().toString().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("信息没有填写完整");
                    StringBuffer sb = new StringBuffer();
                    if (keywordsEdit.getText().toString().isEmpty() ){
                        sb.append("监听关键字缺失\n");
                    }
                    if (phoneNumEdit.getText().toString().isEmpty() ){
                        sb.append("告警电话缺失\n");
                    }
                    builder.setMessage(sb.toString());

                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                intent.putExtra("keywords",keywordsEdit.getText().toString());
                intent.putExtra("phoneNum",phoneNumEdit.getText().toString());
                intent.putExtra("allCheck",allCheck.isChecked());
                intent.setData(Uri.parse(keywordsEdit.getText().toString()));
                startService(intent);
                Toast.makeText(MainActivity.this, "开始监听", Toast.LENGTH_LONG).show();
                start.setEnabled(false);
                stop.setEnabled(true);

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
                Toast.makeText(MainActivity.this, "关闭监听", Toast.LENGTH_LONG).show();
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });

    }

}