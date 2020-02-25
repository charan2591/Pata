package e.pata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SinginActivity extends AppCompatActivity {
     EditText email,password;
     ProgressDialog progressDialog;


    private InterstitialAd interstitialAd;
    private AdView adView;
    String TAG = "PataLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        TextView term = (TextView)findViewById(R.id.already);
        term.setText("NO ACCOUNT YET? CREATE ONE ");

         email =(EditText)findViewById(R.id.email);
         password =(EditText)findViewById(R.id.password);

        term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent start = new Intent(SinginActivity.this, RegisterActivity.class);
                startActivity(start);
            }
        });
        Button btn_reg = (Button)findViewById(R.id.btn_proceed);
        setBanAds();
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Enter your Email");
                }else  if (TextUtils.isEmpty(password.getText().toString())){
                    password.setError("Enter your Password");
                }else {
                    progressDialog = new ProgressDialog(SinginActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setProgress(0);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    setInAd();
                    Intent start = new Intent(SinginActivity.this, IdentificationActivity.class);
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
