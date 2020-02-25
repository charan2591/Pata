package e.pata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;



public class IdentificationActivity extends AppCompatActivity
{

    EditText id, phone;
    ProgressDialog progressDialog;
    String TAG = "PataLog";
    private InterstitialAd interstitialAd;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        TextView already = (TextView)findViewById(R.id.already);
        already.setText("Already applied?check loan status ");
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setInAd();
                Intent start = new Intent(IdentificationActivity.this,LoanstatusActivity.class);
                startActivity(start);
            }
        });



        id =(EditText)findViewById(R.id.id);
        phone =(EditText)findViewById(R.id.phone);

        Button btn_proceed = (Button)findViewById(R.id.btn_proceed);
        setBanAds();
        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(id.getText().toString())){
                    id.setError("Enter your ID");
                }else  if (TextUtils.isEmpty(phone.getText().toString())) {
                    phone.setError("Enter your Phone Number");

                }else {

                    progressDialog = new ProgressDialog(IdentificationActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setProgress(0);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    setInAd();
                    Intent start = new Intent(IdentificationActivity.this, PurposeActivity.class);
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


