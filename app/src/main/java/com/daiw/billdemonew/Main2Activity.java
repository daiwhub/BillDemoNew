package com.daiw.billdemonew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageView imageView = findViewById(R.id.check_iv);
        imageView.setAlpha(0.4f);

        ImageView imageView1 = findViewById(R.id.check_iv1);
        imageView1.setAlpha(1f);

        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Main2Activity.this,Main3Activity.class),100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            Log.e("Main2Activity" , "==============setResult==========");
            setResult(RESULT_OK);
            finish();
        }
    }
}
