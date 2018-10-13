package com.example.user.classic_5rak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_PW_editText)
    EditText password;
    @BindView(R.id.main_ID_editText)
    EditText id;
    @BindView(R.id.main_showPW_RadioButton)
    RadioButton showPassWord;
    @BindView(R.id.main_signUp_Btn)
    Button signUp;
    @BindView(R.id.main_login_btn)
    Button login;
    @BindView(R.id.main_autoLogin_checkBox)
    CheckBox autologin;
    @BindView(R.id.main_gameStart_Btn)
    Button gameStart;

    boolean pwdCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showPassWord.setChecked(false);
    }

    @OnClick(R.id.main_signUp_Btn)
    public void signUpBtn(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        Toast.makeText(this, "회원가입 창으로 이동합니다.", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.main_showPW_RadioButton)
    public void showPassword(){

        if(pwdCheck){
            password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
            pwdCheck=false;
            showPassWord.setChecked(pwdCheck);
        }
        else{
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            pwdCheck=true;
            showPassWord.setChecked(pwdCheck);
        }

    }

    @OnClick(R.id.main_gameStart_Btn)
    public void gamestart(){
        Intent intent = new Intent(this, gameStartActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.main_login_btn)
    public void login(){
        if(id.getText().toString().length() == 0||password.getText().toString().length()==0){
            Toast.makeText(this, "빈 칸을 다 채워주세요", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.main_autoLogin_checkBox)
    public void autoLogin(){
        Toast.makeText(this, "자동로그인 활성화", Toast.LENGTH_SHORT).show();
    }

}
