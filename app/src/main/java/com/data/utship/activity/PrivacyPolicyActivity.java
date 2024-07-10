package com.data.utship.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.data.utship.R;
import com.data.utship.utills.SharedPrefUtils;
import com.data.utship.utills.utility.AppConstant;


public class PrivacyPolicyActivity extends AppCompatActivity {
WebView webview;
    SharedPrefUtils appPreferences;
    ProgressDialog progressDialog;
    CheckBox chechbox;
    TextView btnAccept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        appPreferences = new SharedPrefUtils();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        webview=findViewById(R.id.webview);
        chechbox=findViewById(R.id.chechbox);
        btnAccept=findViewById(R.id.btnAccept);
        webviewLoad();
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chechbox.isChecked()){
                    Toast.makeText(
                            getApplicationContext(),
                            "" + "Please accept Terms & Conditions",
                            Toast.LENGTH_SHORT
                    ).show();
                }else {
                    appPreferences.setValue(AppConstant.isPrivacyPolicy,true);
                    if (appPreferences.getBooleanValue(AppConstant.RememberMe,false)) {
                        // QBUser qbUser = new QBUser(appPreferences.getStringValue(AppPreferences.NAME),"12345678");
                        //QBUsers.signIn(qbUser);
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        finish();

                    } else {
                        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                        finish();
                    }
                }

            }
        });
    }
    private void webviewLoad(){
        webview.loadUrl("https://casheze.co.in/Privacy-Policy.html");

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webview.getSettings().setJavaScriptEnabled(true);

        // if you want to enable zoom feature
        webview.getSettings().setSupportZoom(true);
    }
}