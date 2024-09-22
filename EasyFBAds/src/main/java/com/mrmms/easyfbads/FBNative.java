package com.mrmms.easyfbads;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeAdViewAttributes;

public class FBNative {
    private final Context context;
    private NativeAd nativeAd;
    private boolean isNativeLoaded = false;
    private NativeAdViewAttributes viewAttributes = null;

    public FBNative(Context context) {
        this.context = context;
    }

    // Interface for native ad events
    public interface MMS_NativeListener {
        void onAdLoaded();
        void onAdFailedToLoad();
    }

    // Load native ad with listener for events
    public void loadNativeAd(MMS_NativeListener listener) {
        nativeAd = new NativeAd(context, FBInit_MMS.FB_NATIVE_UNIT);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // You can add custom logic here if needed
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                isNativeLoaded = false;
                listener.onAdFailedToLoad();
                Log.d(FBInit_MMS.TAG, "onError: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                isNativeLoaded = true;
                listener.onAdLoaded();
                Log.d(FBInit_MMS.TAG, "onAdLoaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                Log.d(FBInit_MMS.TAG, "onAdClicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d(FBInit_MMS.TAG, "onLoggingImpression");
            }
        };

        // Load ad with listener
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                        .build()
        );
    }

    // Show native ad in a specified container
    public void ShowNativeAd(RelativeLayout container) {
        if (isNativeLoaded && nativeAd != null) {
            View adView;

            // Clear previous views in the container to avoid duplicate ads
            container.removeAllViews();

            if (viewAttributes != null) {
                adView = NativeAdView.render(context, nativeAd, viewAttributes);
            } else {
                adView = NativeAdView.render(context, nativeAd);
            }

            container.addView(adView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800));
        } else {
            Log.d(FBInit_MMS.TAG, "ShowNativeAd: Native ad is not loaded or null");
        }
    }

    // Customize native ad appearance using integer color values
    public void CustomiseNativeAd(int bgColor, int titleColor, int descColor, int buttonColor, int buttonTextColor) {
        viewAttributes = new NativeAdViewAttributes()
                .setBackgroundColor(bgColor)
                .setTitleTextColor(titleColor)
                .setDescriptionTextColor(descColor)
                .setButtonColor(buttonColor)
                .setButtonTextColor(buttonTextColor);
    }

    // Overloaded method to customize native ad appearance using hex color strings
    public void CustomiseNativeAd(String bgColor, String titleColor, String descColor, String buttonColor, String buttonTextColor) {
        try {
            viewAttributes = new NativeAdViewAttributes()
                    .setBackgroundColor(Color.parseColor(bgColor))
                    .setTitleTextColor(Color.parseColor(titleColor))
                    .setDescriptionTextColor(Color.parseColor(descColor))
                    .setButtonColor(Color.parseColor(buttonColor))
                    .setButtonTextColor(Color.parseColor(buttonTextColor));
        } catch (IllegalArgumentException e) {
            Log.e(FBInit_MMS.TAG, "CustomiseNativeAd: Invalid color format", e);
        }
    }

    // Set native ad unit
    public void setNativeUnit(String NativeUnit) {
        if (NativeUnit != null && !NativeUnit.isEmpty()) {
            FBInit_MMS.FB_NATIVE_UNIT = NativeUnit;
        }
    }

    // Destroy native ad and handle potential exceptions
    public void destroyNativeAd() {
        if (nativeAd != null) {
            try {
                nativeAd.destroy();
                nativeAd = null;  // Avoid memory leaks by nullifying the reference
                isNativeLoaded = false;
            } catch (Exception e) {
                Log.e(FBInit_MMS.TAG, "DestroyNativeAd: Error destroying native ad", e);
            }
        }
    }
}
