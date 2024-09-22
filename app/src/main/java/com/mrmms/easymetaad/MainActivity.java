package com.mrmms.easymetaad;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mrmms.easyfbads.FBBanner;
import com.mrmms.easyfbads.FBInit_MMS;
import com.mrmms.easyfbads.FBInterstitial;
import com.mrmms.easyfbads.FBNative;

public class MainActivity extends AppCompatActivity {

    RelativeLayout adContainer, nativeAdContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adContainer = findViewById(R.id.adContainer);
        nativeAdContainer = findViewById(R.id.nativeAdContainer);

        FBInit_MMS initMms = new FBInit_MMS(this);
        initMms.setTestDevices(new String[]{
                "YOUR_TEST_DEVICE_ID",
                "ANOTHER_TEST_DEVICE_ID",
                "ANOTHER_TEST_DEVICE_ID"
                // Add more test devices as needed
        });

        /*initMms.setClickCountsAndLimits(
                0,
                2,
                0,
                2,
                0,
                1
        );
        initMms.setAdUnits(
                "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
                "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID",
                "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"
        );

        // Set individually
        initMms.setBannerClickLimit(2);
        initMms.setInterstitialClickLimit(3);
        initMms.setInterstitialShowLimit(3);
        // you can manipulate click count and limit as well
        initMms.setBannerClickCount(0);
        initMms.setInterstitialShowCount(0);
        initMms.setInterstitialClickCount(0);

        // Set Ad Units
        initMms.setFbBannerUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        initMms.setFbInterstitialUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        initMms.setFbNativeUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");*/

        // Banner
        FBBanner banner = new FBBanner(this, adContainer);
        banner.loadBannerAd(); // loadBannerAd will load the banner ad
        //banner.setBannerUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"); // setBannerUnit will set the banner unit
        //banner.setBannerClickLimit(2); // setBannerClickLimit will set the banner click limit
        //banner.destroyBannerAd(); // destroyBannerAd will destroy the banner ad


        // Interstitial

        // Intent targetActivity = new Intent(this, SecondActivity.class);
        // showAfterLoad will show Interstitial Ad after loading complete
        // keepPreLoaded will keep the Interstitial Ad pre-loaded after the initial impression (it will not showAfterLoad)
        FBInterstitial interstitial = new FBInterstitial(this);
        interstitial.loadInterstitialAd(false, true);
        //interstitial.setInterstitialUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
        //interstitial.setInterstitialLimits(2, 1);
        //interstitial.setInterstitialCounts(0, 0);
        //interstitial.destroyInterstitialAd();
        interstitial.showInterstitialAd(null); // showInterstitialAd will show the Interstitial Ad returns boolean if it is loaded or not

        findViewById(R.id.showAd).setOnClickListener(v -> {
            if (!interstitial.showInterstitialAd(null))
                Toast.makeText(this, "Not Loaded", Toast.LENGTH_SHORT).show();

        });

        // Native
        FBNative nativeAd = new FBNative(this);
        nativeAd.setNativeUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID"); //YOU CAN ALSO ADD AD UNIT HERE
        //nativeAd.CustomiseNativeAd("#2B2B2B","#4670F0","#FAFAFA","#429472","#E2E2E9"); // YOU CAN ALSO ADD COLOR HERE at string format
        nativeAd.CustomiseNativeAd(Color.GRAY, Color.BLUE, Color.WHITE, Color.GREEN, Color.RED);
        nativeAd.loadNativeAd(new FBNative.MMS_NativeListener() {
            @Override
            public void onAdLoaded() {
                nativeAd.ShowNativeAd(nativeAdContainer);
            }

            @Override
            public void onAdFailedToLoad() {

            }
        });

        //nativeAd.destroyNativeAd();
    }
}