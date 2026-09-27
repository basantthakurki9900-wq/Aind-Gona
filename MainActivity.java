package com.example.captchaads;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // ===== यहाँ अपनी Unity Ads डैशबोर्ड डिटेल्स डालें =====
    private static final String GAME_ID = "YOUR_GAME_ID";       // Unity Ads Dashboard > Platforms से मिलेगा
    private static final String AD_UNIT_ID = "Rewarded_Android"; // Monetization > Ad Units में बनाया गया नाम/ID
    private static final boolean TEST_MODE = true;               // लाइव करने से पहले false करें
    // =======================================================

    private TextView captchaText;
    private EditText userInput;
    private Button submitButton;
    private String currentCaptcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        captchaText = findViewById(R.id.captchaText);
        userInput = findViewById(R.id.userInput);
        submitButton = findViewById(R.id.submitButton);

        // Unity Ads इनिशियलाइज़ करें (ऐप शुरू होते ही, ताकि 10 सेकंड का इंतज़ार पहले हो जाए)
        UnityAds.initialize(getApplicationContext(), GAME_ID, TEST_MODE, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                loadAd();
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                Toast.makeText(MainActivity.this, "Unity Ads init विफल: " + message, Toast.LENGTH_LONG).show();
            }
        });

        generateNewCaptcha();

        submitButton.setOnClickListener(v -> checkAnswer());
    }

    // रैंडम 5-अक्षर का कैप्चा बनाता है
    private void generateNewCaptcha() {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 5; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        currentCaptcha = sb.toString();
        captchaText.setText(currentCaptcha);
        userInput.setText("");
    }

    // यूज़र का जवाब जांचता है
    private void checkAnswer() {
        String typed = userInput.getText().toString().trim();
        if (typed.equalsIgnoreCase(currentCaptcha)) {
            Toast.makeText(this, "सही जवाब! ऐड दिखाया जा रहा है...", Toast.LENGTH_SHORT).show();
            showAd();
        } else {
            Toast.makeText(this, "गलत जवाब, दोबारा कोशिश करें", Toast.LENGTH_SHORT).show();
            generateNewCaptcha();
        }
    }

    // अगला ऐड पहले से लोड करके रखता है ताकि दिखाते वक़्त देरी न हो
    private void loadAd() {
        UnityAds.load(AD_UNIT_ID, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                // ऐड तैयार है
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                Toast.makeText(MainActivity.this, "Ad load विफल: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // कैप्चा सही होने पर ऐड दिखाता है
    private void showAd() {
        UnityAds.show(this, AD_UNIT_ID, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                Toast.makeText(MainActivity.this, "Ad show विफल: " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {}

            @Override
            public void onUnityAdsShowClick(String placementId) {}

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                // ऐड पूरा देखे जाने के बाद यहाँ इनाम/reward का लॉजिक जोड़ सकते हैं
                generateNewCaptcha();
                loadAd(); // अगला ऐड प्री-लोड करें
            }
        });
    }
}
