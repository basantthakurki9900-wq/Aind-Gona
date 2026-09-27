# Captcha Ads App — सेटअप गाइड

यह एक Android Studio प्रोजेक्ट है: यूज़र को एक रैंडम कोड (कैप्चा) टाइप करना होता है, सही होने पर Unity Ads का Rewarded Ad दिखाया जाता है।

## शुरू करने के लिए

1. **Android Studio** में यह फ़ोल्डर खोलें (`Open an existing project` से `CaptchaAdsApp` फ़ोल्डर चुनें)।
2. `app/src/main/java/.../MainActivity.java` में यह दो लाइनें अपनी असली डिटेल से बदलें:
   ```java
   private static final String GAME_ID = "YOUR_GAME_ID";       // Unity Ads Dashboard > Platforms
   private static final String AD_UNIT_ID = "Rewarded_Android"; // Monetization > Ad Units में जो नाम/ID दिया
   ```
3. Gradle सिंक करें (Android Studio अपने आप पूछेगा) — यह `unity-ads` लाइब्रेरी डाउनलोड कर लेगा।
4. फ़ोन या एमुलेटर पर Run करें।

## टेस्ट मोड

`MainActivity.java` में `TEST_MODE = true` रखें जब तक टेस्ट कर रहे हों — तभी असली की जगह टेस्ट ऐड दिखेंगे।
**ऐप को Play Store पर लाइव करने से पहले इसे `false` कर दें**, वरना असली कमाई नहीं होगी।

## कैसे काम करता है

1. ऐप खुलते ही एक रैंडम 5-अक्षर का कोड दिखता है (जैसे `A7K2M`)।
2. यूज़र उसे टाइप करके "सबमिट करें" दबाता है।
3. सही होने पर Unity Ads का Rewarded Ad चलता है।
4. ऐड खत्म होने के बाद नया कोड जनरेट होता है और अगला ऐड पहले से लोड कर लिया जाता है।

## आगे क्या बदल सकते हैं

- गलत जवाब पर कितनी कोशिश दें, इसकी लिमिट जोड़ सकते हैं।
- कैप्चा की जगह गणित का सवाल या इमेज-बेस्ड टास्क भी लगा सकते हैं।
- `unity-ads` का लेटेस्ट version नंबर हमेशा Unity के आधिकारिक docs पर चेक कर लें, क्योंकि यह समय-समय पर अपडेट होता है।

## ज़रूरी चीज़ें जो पहले से होनी चाहिए

- Unity Ads Dashboard पर Project + Ad Unit (Rewarded) बना हो — आपके पास पहले से है, तो सिर्फ ID कॉपी करनी है।
- Android Studio इंस्टॉल हो और इंटरनेट कनेक्शन हो (Gradle लाइब्रेरी डाउनलोड करने के लिए)।

## GitHub से बिना Android Studio के APK बनाना (GitHub Actions)

इस प्रोजेक्ट में `.github/workflows/build.yml` पहले से है, जो हर बार कोड पुश करने पर अपने आप APK बना देता है — आपको कंप्यूटर पर Android Studio की ज़रूरत नहीं।

1. GitHub पर एक नया **repository** बनाएं (public या private, दोनों चलेगा)।
2. इस पूरे फ़ोल्डर (`CaptchaAdsApp`) को उस repository में पुश करें:
   ```
   git init
   git add .
   git commit -m "पहला कमिट"
   git branch -M main
   git remote add origin https://github.com/आपका-यूज़रनेम/आपका-repo.git
   git push -u origin main
   ```
   (अगर `git` कमांड लाइन नहीं आती, तो GitHub की वेबसाइट पर "Upload files" बटन से भी सारी फाइलें/फ़ोल्डर सीधे अपलोड कर सकते हैं।)
3. GitHub पर अपने repository में जाकर ऊपर **Actions** टैब खोलें।
4. "Build APK" वर्कफ़्लो अपने आप चलेगा (या "Run workflow" बटन से मैन्युअल भी चला सकते हैं)।
5. वर्कफ़्लो पूरा होने पर, उसी रन के नीचे **Artifacts** सेक्शन में `captcha-ads-app-debug` नाम से APK डाउनलोड कर सकते हैं — उसे ज़िप से निकालकर फ़ोन में इंस्टॉल कर लें।

नोट: यह Debug APK बनाता है (टेस्टिंग के लिए)। Play Store पर पब्लिश करने के लिए बाद में साइन की हुई Release APK/AAB चाहिए होगी, जिसके लिए अलग सेटअप (keystore) चाहिए — जब तैयार हों तो बता दीजिए, वो भी जोड़ दूंगा।
