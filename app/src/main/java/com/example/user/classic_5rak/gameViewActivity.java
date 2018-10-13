package com.example.user.classic_5rak;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class gameViewActivity extends AppCompatActivity {
    private static TextView gameView_lyric_textView;
    private static EditText gameView_answer_editText;
    private String title = "";

    @BindView(R.id.gameView_submit_btn)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameview);
        ButterKnife.bind(this);

        gameView_lyric_textView = findViewById(R.id.gameView_lyric_textView);
        gameView_answer_editText = findViewById(R.id.gameView_answer_editText);
        AsyncTask<String, Void, String> ppg =  new PapagoTranslateNMT();

        Random rand = new Random();
        int randomNum = rand.nextInt(2);

        switch (randomNum) {
            case 0:
                String lyrics = readTextFile();
                ppg.execute(lyrics);
            case 1:
                readImageFile();
        }
    }

    @OnClick(R.id.gameView_submit_btn)
    public void submit(){
        Boolean answer = title.contains(gameView_answer_editText.getText().toString());

        if (answer) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show();
            gameView_lyric_textView.setText("loading...");
        } else {
            Toast.makeText(this, "Nope!T..T", Toast.LENGTH_LONG).show();
            gameView_lyric_textView.setText("loading...");
        }
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public String readTextFile() {
        try {
            Field[] raw = R.raw.class.getFields();

            Random rand = new Random();
            int randomNum = rand.nextInt(3);

            InputStream in = getResources().openRawResource(raw[randomNum].getInt(raw[randomNum]));
            if (in != null) {
                InputStreamReader stream = new InputStreamReader(in, "utf-8");
                BufferedReader buffer = new BufferedReader(stream);

                String read;
                StringBuilder sb = new StringBuilder("");

                title = buffer.readLine();

                while ((read = buffer.readLine()) != null) {
                    sb.append(read + "\n");
                }

                in.close();
                return sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void readImageFile(){
        // drawable 리소스 객체 가져오기
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.crop1);

        // XML 에 있는 ImageView 위젯에 이미지 셋팅
        ImageView imageView = (ImageView) findViewById(R.id.gameView_img_imgView);
        imageView.setImageDrawable(drawable);
    }


    // 네이버 기계번역 (Papago SMT) API 예제
    private static class PapagoTranslateNMT  extends AsyncTask<String, Void, String> {

        String clientId = "";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "";//애플리케이션 클라이언트 시크릿값";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String str = strings[0];
            try {
                String text = URLEncoder.encode(str, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=ko&target=en&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                return response.toString();
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson;
            gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonElement rootObj = parser.parse(s.toString())
                    .getAsJsonObject().get("message")
                    .getAsJsonObject().get("result");

            TranslatedItem items = gson.fromJson(rootObj.toString(), TranslatedItem.class);

            //stringbuffer clear
            StringBuilder sb = new StringBuilder("");

            sb.setLength(0);
            sb.append(items.getTranslatedText() + "\n");
            //lyrics.setText(response.toString());
            gameView_lyric_textView.setText(sb);
        }

        private class TranslatedItem {
            String translatedText;

            public String getTranslatedText() {
                return translatedText;
            }
        }
    }
}

