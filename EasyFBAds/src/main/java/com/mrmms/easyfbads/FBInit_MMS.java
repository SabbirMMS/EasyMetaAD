package com.mrmms.easyfbads;

import android.content.Context;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FBInit_MMS {
    public static final String TAG = "FacebookAdsBy_SabbirMMS";

    public static String FB_BANNER_UNIT = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
    public static String FB_INTERSTITIAL_UNIT = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";
    public static String FB_NATIVE_UNIT = "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID";

    public static int bannerClickCount = 0;
    public static int bannerClickLimit = 2;
    public static int interstitialClickCount = 0;
    public static int interstitialClickLimit = 0;
    public static int interstitialShowCount = 0;
    public static int interstitialShowLimit = 0;

    // initialization method
    public FBInit_MMS(Context context) {
        AudienceNetworkAds.initialize(context);
    }

    // General setter for click counts and limits
    public void setClickCountsAndLimits(int bannerClickCount, int bannerClickLimit, int interstitialClickCount, int interstitialClickLimit, int interstitialShowCount, int interstitialShowLimit) {
        FBInit_MMS.bannerClickCount = bannerClickCount;
        FBInit_MMS.bannerClickLimit = bannerClickLimit;
        FBInit_MMS.interstitialClickCount = interstitialClickCount;
        FBInit_MMS.interstitialClickLimit = interstitialClickLimit;
        FBInit_MMS.interstitialShowCount = interstitialShowCount;
        FBInit_MMS.interstitialShowLimit = interstitialShowLimit;
    }

    // General setter for Ad Units
    public void setAdUnits(String bannerUnit, String interstitialUnit, String nativeUnit) {
        if (bannerUnit != null && !bannerUnit.isEmpty()) {
            FB_BANNER_UNIT = bannerUnit;
        }
        if (interstitialUnit != null && !interstitialUnit.isEmpty()) {
            FB_INTERSTITIAL_UNIT = interstitialUnit;
        }
        if (nativeUnit != null && !nativeUnit.isEmpty()) {
            FB_NATIVE_UNIT = nativeUnit;
        }
    }

    // Individual Setters (optional if the grouped setter isn't used)
    public void setBannerClickCount(int count) {
        bannerClickCount = count;
    }

    public void setBannerClickLimit(int limit) {
        bannerClickLimit = limit;
    }

    public void setInterstitialClickCount(int count) {
        interstitialClickCount = count;
    }

    public void setInterstitialClickLimit(int limit) {
        interstitialClickLimit = limit;
    }

    public void setInterstitialShowCount(int count) {
        interstitialShowCount = count;
    }

    public void setInterstitialShowLimit(int limit) {
        interstitialShowLimit = limit;
    }

    // Individual Ad Unit setters (optional if the grouped setter isn't used)
    public void setFbBannerUnit(String bannerUnit) {
        if (bannerUnit != null && !bannerUnit.isEmpty()) {
            FB_BANNER_UNIT = bannerUnit;
        }
    }

    public void setFbInterstitialUnit(String interstitialUnit) {
        if (interstitialUnit != null && !interstitialUnit.isEmpty()) {
            FB_INTERSTITIAL_UNIT = interstitialUnit;
        }
    }

    public void setFbNativeUnit(String nativeUnit) {
        if (nativeUnit != null && !nativeUnit.isEmpty()) {
            FB_NATIVE_UNIT = nativeUnit;
        }
    }

    public void setTestDevices(String[] devices) {
        if (devices != null && devices.length > 0) {
            List<String> testDevices = new ArrayList<>();
            Collections.addAll(testDevices, devices);
            AdSettings.addTestDevices(testDevices);
        }
    }
}
