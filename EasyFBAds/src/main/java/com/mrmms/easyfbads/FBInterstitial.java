package com.mrmms.easyfbads;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class FBInterstitial {
    private final Context context;
    private InterstitialAd interstitialAd;
    private Intent targetActivity;

    public FBInterstitial(Context context) {
        this.context = context;
    }

    // Load Interstitial Ad with options for showing immediately and keeping it pre-loaded
    public void loadInterstitialAd(boolean showAfterLoad, boolean keepPreLoaded) {
        // Check click limit
        if (isClickLimitReached()) return;

        // Check show limit
        if (isShowLimitReached()) return;

        // Check if the ad is already loaded
        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            Log.d(FBInit_MMS.TAG, "LoadInterstitialAd: Interstitial Ad Already Loaded");
            if (showAfterLoad) showInterstitialAd(null);
            return;
        }

        // Create and load a new interstitial ad
        interstitialAd = new InterstitialAd(context, FBInit_MMS.FB_INTERSTITIAL_UNIT);
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                .withAdListener(createInterstitialAdListener(showAfterLoad, keepPreLoaded))
                .build());
    }

    // Listener for interstitial ad events
    private InterstitialAdListener createInterstitialAdListener(boolean showAfterLoad, boolean keepPreLoaded) {
        return new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // No-op, can be used if you want to log/display something when the ad is shown
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                FBInit_MMS.interstitialShowCount++;
                if (targetActivity != null) {
                    context.startActivity(targetActivity);
                    targetActivity = null; // Ensure it's not reused
                }
                if (keepPreLoaded) {
                    loadInterstitialAd(false, keepPreLoaded);
                }
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d(FBInit_MMS.TAG, "onError: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (showAfterLoad) {
                    showInterstitialAd(null);
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                FBInit_MMS.interstitialClickCount++;
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // No-op, can be used for logging impressions if needed
            }
        };
    }

    // Show interstitial ad, optionally passing an Intent to trigger after the ad
    public boolean showInterstitialAd(Intent intent) {
        if (intent != null) {
            this.targetActivity = intent;
        }

        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            interstitialAd.show();
            return true;
        }

        Log.d(FBInit_MMS.TAG, "showInterstitialAd: Ad not loaded");
        return false;
    }

    // Destroy the interstitial ad and nullify the reference
    public void destroyInterstitialAd() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;  // Nullify to avoid memory leaks
            Log.d(FBInit_MMS.TAG, "destroyInterstitialAd: Interstitial ad destroyed");
        }
    }

    // Set Interstitial Unit
    public void setInterstitialUnit(String interstitialUnit) {
        if (interstitialUnit != null && !interstitialUnit.isEmpty()) {
            FBInit_MMS.FB_INTERSTITIAL_UNIT = interstitialUnit;
        }
    }

    // Utility methods to set limits and counts
    public void setInterstitialLimits(int clickLimit, int showLimit) {
        if (clickLimit > 0) FBInit_MMS.interstitialClickLimit = clickLimit;
        if (showLimit > 0) FBInit_MMS.interstitialShowLimit = showLimit;
    }

    public void setInterstitialCounts(int clickCount, int showCount) {
        FBInit_MMS.interstitialClickCount = clickCount;
        FBInit_MMS.interstitialShowCount = showCount;
    }

    // Helper method to check if the click limit is reached
    private boolean isClickLimitReached() {
        if (FBInit_MMS.interstitialClickLimit > 0 && FBInit_MMS.interstitialClickCount >= FBInit_MMS.interstitialClickLimit) {
            Log.d(FBInit_MMS.TAG, "LoadInterstitialAd: Interstitial Click Limit Reached");
            return true;
        }
        return false;
    }

    // Helper method to check if the show limit is reached
    private boolean isShowLimitReached() {
        if (FBInit_MMS.interstitialShowLimit > 0 && FBInit_MMS.interstitialShowCount >= FBInit_MMS.interstitialShowLimit) {
            Log.d(FBInit_MMS.TAG, "LoadInterstitialAd: Interstitial Show Limit Reached");
            return true;
        }
        return false;
    }
}
