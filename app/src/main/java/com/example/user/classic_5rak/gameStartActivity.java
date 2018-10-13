package com.example.user.classic_5rak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class gameStartActivity extends AppCompatActivity{
    @BindView(R.id.gs_gameStart_button)
    Button gs_gameStart;

    @BindView(R.id.gs_gameStop_button)
    Button gs_gameStop;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamestart);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.gs_gameStart_button)
    public void gs_gamestart(){
        Intent intent = new Intent(getApplicationContext(), gameViewActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.gs_gameStop_button)
    public void gs_gamestop(){
        finish();
    }
}
