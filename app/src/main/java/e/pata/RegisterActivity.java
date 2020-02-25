package e.pata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;


public class RegisterActivity extends AppCompatActivity {

    EditText F_name,L_name,email,password,phone;
    ProgressDialog progressDialog;

    String TAG = "PataLog";
    private InterstitialAd interstitialAd;
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        F_name =(EditText)findViewById(R.id.F_name);
        L_name =(EditText)findViewById(R.id.L_name);
        phone =(EditText)findViewById(R.id.phone);
        password =(EditText)findViewById(R.id.password);
        email =(EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);

        TextView already = (TextView)findViewById(R.id.already);
        already.setText("Already a member? Login ");

        Button btn_reg = (Button)findViewById(R.id.btn_reg);
        setBanAds();
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(F_name.getText().toString())){
                    F_name.setError("Enter your First Name");
                }else  if (TextUtils.isEmpty(L_name.getText().toString())) {
                    L_name.setError("Enter your Last Name");

                }if (TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Enter your Email");
                }else  if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Enter your Password");
                }else  if (TextUtils.isEmpty(phone.getText().toString())) {
                    phone.setError("Enter your Phone Number");

                }else {
                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setProgress(0);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    setInAd();
                    Intent start = new Intent(RegisterActivity.this, DashActivity.class);
                    startActivity(start);


                }

            }
        });

    }

    private void setBanAds()
    {
        // Initialize the Audience Network SDK
        AudienceNetworkAds.initialize(this);

        adView = new AdView(this, getString(R.string.fb_bannerid), AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        adContainer.addView(adView);

//        AdSettings.addTestDevice("779caad2-530c-4389-9595-c9442325ebc4");
        AdSettings.addTestDevice("{hashed-id}");
        // Request an ad
        adView.loadAd();
    }

    private void setInAd()
    {
        interstitialAd = new InterstitialAd(this, getString(R.string.fb_interestialid));

        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e("", "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e("", "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d("", "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d("", "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d("", "Interstitial ad impression logged!");
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd();
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}