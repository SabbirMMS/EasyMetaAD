# Facebook Ads Library by SabbirMMS

> **Version**: Based on Facebook SDK version [6.16.0]

This Android library enables seamless integration of Facebook Audience Network ads into your application. It provides support for Banner Ads, Interstitial Ads, and Native Ads, with configurable options for controlling click and display limits.

## Key Features

- **Banner Ads**: Easily integrate banner ads within a `RelativeLayout`.
- **Interstitial Ads**: Manage interstitial ads with options for pre-loading and displaying at your preferred time.
- **Native Ads**: Create customizable native ads with flexible visual settings.
- **Click and Display Limits**: Set and control limits for clicks and impressions on both banner and interstitial ads.

## Getting Started

### Step 1: Add the JitPack Repository
In your root `build.gradle` file, add the JitPack repository as follows:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the Dependency
Include the following dependency in your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.SabbirMMS:EasyMetaAD:1.0'
}
```

### Step 3: Enable Jetifier
Add the following configuration in `gradle.properties` to enable Jetifier:

```gradle
android.enableJetifier=true
```

---

## Usage

### 1. Initialization

To initialize the Facebook Audience Network, use the `FBInit_MMS` class in your `Activity`:

```java
FBInit_MMS initMms = new FBInit_MMS(this);
initMms.setTestDevices(new String[]{
    "YOUR_TEST_DEVICE_ID",
    "ANOTHER_TEST_DEVICE_ID"
    // Add more test devices as needed
});
```

### 2. Configuring Ad Units

You can set ad units for banner, interstitial, and native ads in bulk or individually. Replace `"YOUR_PLACEMENT_ID"` with the actual placement IDs from your Facebook ad account.

#### Set All Ad Units:

```java
initMms.setAdUnits(
    "IMG_16_9_APP_INSTALL#YOUR_BANNER_PLACEMENT_ID",
    "IMG_16_9_APP_INSTALL#YOUR_INTERSTITIAL_PLACEMENT_ID",
    "IMG_16_9_APP_INSTALL#YOUR_NATIVE_PLACEMENT_ID"
);
```

#### Set Ad Units Individually:

```java
initMms.setFbBannerUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
initMms.setFbInterstitialUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
initMms.setFbNativeUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
```

### 3. Setting Click and Show Limits

Define the click and impression limits for banner and interstitial ads as follows:

```java
initMms.setClickCountsAndLimits(
    0, // bannerClickCount
    2, // bannerClickLimit
    0, // interstitialClickCount
    2, // interstitialClickLimit
    0, // interstitialShowCount
    1  // interstitialShowLimit
);
```

Alternatively, set limits for each ad type individually:

```java
initMms.setBannerClickLimit(2);
initMms.setInterstitialClickLimit(2);
initMms.setInterstitialShowLimit(1);
```

---

## Ad Types and Examples

### Banner Ads

#### Loading and Displaying Banner Ads
To display a banner ad within a `RelativeLayout`, use the following code:

```java
RelativeLayout adContainer = findViewById(R.id.adContainer);
FBBanner banner = new FBBanner(this, adContainer);
banner.loadBannerAd();  // Loads the banner ad
```

#### Managing Banner Ads
Set the banner ad unit and manage click limits:

```java
banner.setBannerUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
banner.setBannerClickLimit(2);
banner.destroyBannerAd();  // Destroys the banner ad
```

### Interstitial Ads

#### Loading and Showing Interstitial Ads
To load an interstitial ad, use the following code:

```java
FBInterstitial interstitial = new FBInterstitial(this);
interstitial.loadInterstitialAd(false, true);
```

To show the interstitial ad:

```java
if (!interstitial.showInterstitialAd(null)) {
    Toast.makeText(this, "Not Loaded", Toast.LENGTH_SHORT).show();
}
```

#### Managing Interstitial Ads
Configure the interstitial ad unit and manage click/show limits:

```java
interstitial.setInterstitialUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
interstitial.setInterstitialLimits(2, 1);  // Set click and show limits
interstitial.setInterstitialCounts(0, 0);  // Reset click and show counts
interstitial.destroyInterstitialAd();  // Destroys the interstitial ad
```

### Native Ads

#### Loading and Displaying Native Ads
To load and display a native ad within a `RelativeLayout`:

```java
RelativeLayout nativeAdContainer = findViewById(R.id.nativeAdContainer);
FBNative nativeAd = new FBNative(this);
nativeAd.loadNativeAd(new FBNative.MMS_NativeListener() {
    @Override
    public void onAdLoaded() {
        nativeAd.ShowNativeAd(nativeAdContainer);
    }

    @Override
    public void onAdFailedToLoad() {
        // Handle the failure
    }
});
```

#### Customizing Native Ads
To customize the appearance of the native ad, use either hex color codes or predefined color constants:

```java
nativeAd.CustomiseNativeAd("#2B2B2B", "#4670F0", "#FAFAFA", "#429472", "#E2E2E9"); // Using hex color strings
nativeAd.CustomiseNativeAd(Color.GRAY, Color.BLUE, Color.WHITE, Color.GREEN, Color.RED); // Using color constants
nativeAd.setNativeUnit("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
nativeAd.destroyNativeAd();  // Destroys the native ad
```

---

## API Overview

### `FBInit_MMS` Methods
- `setClickCountsAndLimits(int bannerClickCount, int bannerClickLimit, int interstitialClickCount, int interstitialClickLimit, int interstitialShowCount, int interstitialShowLimit)`
- `setAdUnits(String bannerUnit, String interstitialUnit, String nativeUnit)`
- `setFbBannerUnit(String bannerUnit)`
- `setFbInterstitialUnit(String interstitialUnit)`
- `setFbNativeUnit(String nativeUnit)`

### `FBBanner` Methods
- `loadBannerAd()`
- `setBannerUnit(String bannerUnit)`
- `setBannerClickLimit(int limit)`
- `destroyBannerAd()`

### `FBInterstitial` Methods
- `loadInterstitialAd(boolean showAfterLoad, boolean keepPreLoaded)`
- `showInterstitialAd(Intent intent)`
- `setInterstitialUnit(String interstitialUnit)`
- `setInterstitialLimits(int clickLimit, int showLimit)`
- `setInterstitialCounts(int clickCount, int showCount)`
- `destroyInterstitialAd()`

### `FBNative` Methods
- `loadNativeAd(MMS_NativeListener listener)`
- `ShowNativeAd(RelativeLayout container)`
- `CustomiseNativeAd(int bgColor, int titleColor, int descColor, int buttonColor, int buttonTextColor)`
- `CustomiseNativeAd(String bgColor, String titleColor, String descColor, String buttonColor, String buttonTextColor)`
- `setNativeUnit(String nativeUnit)`
- `destroyNativeAd()`
