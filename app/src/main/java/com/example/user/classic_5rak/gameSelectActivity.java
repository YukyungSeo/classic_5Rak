package com.example.user.classic_5rak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class gameSelectActivity extends AppCompatActivity{
    @BindView(R.id.gameselect_gamestart_button)
    Button gs_gameStart;

    @BindView(R.id.gameselect_info_textView)
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameStart);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.gameselect_gamestart_button)
    public void gs_gamestart(){
        Intent intent = new Intent(getApplicationContext(), gameViewActivity.class);
        startActivity(intent);
    }

}
