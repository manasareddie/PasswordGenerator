package com.example.passwordgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GeneratedPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_pass);

        List<String> List1 = (List<String>) getIntent().getExtras().get(MainActivity.MY_LIST_KEY);
        LinearLayout l1= (LinearLayout) findViewById(R.id.LayoutByThread);
        for(int i=0;i<List1.size();i++) {
            TextView t1 = new TextView(this);
            t1.setText(List1.get(i).toString());
            l1.addView(t1);
        }

        List<String> List2 = (List<String>) getIntent().getExtras().get(MainActivity.MY_LIST_KEY_2);
        LinearLayout l2= (LinearLayout) findViewById(R.id.LayoutByAsync);
        for (int i=0;i<List2.size();i++){
            TextView t2=new TextView(this);
            t2.setText(List2.get(i).toString());
            l2.addView(t2);
        }

        findViewById(R.id.Finish2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(GeneratedPass.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
