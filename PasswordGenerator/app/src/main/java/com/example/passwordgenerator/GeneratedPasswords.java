package com.example.passwordgenerator;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GeneratedPasswords extends AppCompatActivity {
    List<String> tpassListPassed=new ArrayList<String>();
    List<String> tpassListPassed2=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_passwords);

        ArrayList<String> a1 = (ArrayList<String>) getIntent().getExtras().get(MainActivity.MY_LIST_KEY);
        LinearLayout s1 = (LinearLayout) findViewById(R.id.ln1);

        for(int i=0;i<a1.size();i++) {
            TextView t1 = new TextView(this);
            t1.setText(a1.get(i).toString());
            s1.addView(t1);
        }

        ArrayList<String> a2 = (ArrayList<String>) getIntent().getExtras().get(MainActivity.MY_LIST_KEY_2);

        LinearLayout s2 = (LinearLayout) findViewById(R.id.ln2);

        for(int i=0;i<a2.size();i++) {
            TextView t1 = new TextView(this);
            t1.setText(a1.get(i).toString());
            s2.addView(t1);
        }

        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GeneratedPasswords.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
