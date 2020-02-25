package e.pata;

import android.app.Application;

import com.facebook.ads.AudienceNetworkAds;

public class MyApplication extends Application
{
    // Initialize the Audience Network SDK

        @Override
        public void onCreate() {
                super.onCreate();
                AudienceNetworkAds.initialize(this);
        }
}
