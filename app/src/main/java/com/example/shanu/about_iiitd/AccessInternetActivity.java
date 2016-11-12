package com.example.shanu.about_iiitd;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AccessInternetActivity extends AppCompatActivity {
    public static final String SAVED_VALUE="about";
    String mAbout_string="About IIITD";
  Button mShowButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_internet);
        mShowButton=(Button)findViewById(R.id.button_show);
        if(savedInstanceState!=null) {
            mAbout_string=savedInstanceState.getString(SAVED_VALUE);
            TextView textView=(TextView)findViewById(R.id.txt_about);
            textView.setText(mAbout_string);
            mShowButton.setEnabled(false);
        }

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected())
                {
                    new  About().execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"connection is not available",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class About extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            Document dc= null;
            try {
                dc = Jsoup.connect("https://www.iiitd.ac.in/about").get();
            Elements elmt=dc.getElementsByTag("p");
                mAbout_string=elmt.get(6).text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TextView textView=(TextView)findViewById(R.id.txt_about);
            textView.setText(mAbout_string);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putString(SAVED_VALUE, mAbout_string);

    }
}