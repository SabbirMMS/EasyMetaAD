package com.mrmms.easyfbads;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class FBBanner {
    private AdView adView;
    private final RelativeLayout adContainer;
    private final Context context;

    public FBBanner(Context context, RelativeLayout adContainer) {
        this.context = context;
        this.adContainer = adContainer;
    }

    public void loadBannerAd() {
        // Early return if banner click limit is reached or adContainer is null
        if (isBannerClickLimitReached() || adContainer == null) {
            Log.d(FBInit_MMS.TAG, "LoadBannerAd: Banner Click Limit Reached or adContainer is null");
            return;
        }

        adView = new AdView(context, FBInit_MMS.FB_BANNER_UNIT, AdSize.BANNER_HEIGHT_50);  // Consider making AdSize dynamic
        adContainer.addView(adView);

        AdListener listener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.d(FBInit_MMS.TAG, "onError: " + adError.getErrorMessage());
                // You can manage the ad visibility here if needed
            }

            @Override
            public void onAdLoaded(Ad ad) {
                adContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClicked(Ad ad) {
                FBInit_MMS.bannerClickCount++;
                if (isBannerClickLimitReached()) {
                    destroyBannerAd();
                }
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                Log.d(FBInit_MMS.TAG, "onLoggingImpression");
            }
        };

        adView.loadAd(adView.buildLoadAdConfig().withAdListener(listener).build());
    }

    public void destroyBannerAd() {
        if (adView != null) {
            try {
                adView.destroy();
                adView = null;  // Set adView to null to avoid memory leaks
            } catch (Exception e) {
                Log.e(FBInit_MMS.TAG, "DestroyBannerAd: Error while destroying ad view", e);
            }
        }
    }

    public void setBannerUnit(String BannerUnit) {
        if (BannerUnit != null && !BannerUnit.isEmpty()) {
            FBInit_MMS.FB_BANNER_UNIT = BannerUnit;
        }
    }

    public void setBannerClickLimit(int BannerClickLimit) {
        if (BannerClickLimit > 0) {
            FBInit_MMS.bannerClickLimit = BannerClickLimit;
        }
    }

    private boolean isBannerClickLimitReached() {
        return FBInit_MMS.bannerClickLimit > 0 && FBInit_MMS.bannerClickLimit <= FBInit_MMS.bannerClickCount;
    }

}
