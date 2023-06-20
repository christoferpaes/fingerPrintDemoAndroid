import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 1;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request fingerprint permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_BIOMETRIC},
                    REQUEST_PERMISSIONS);
        }

        // Initialize executor
        executor = Executors.newSingleThreadExecutor();

        // Create authentication callback
        authenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(MainActivity.this, "Authentication error: " + errString,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Authentication succeeded", Toast.LENGTH_SHORT).show();
                // Handle successful authentication
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        };

        // Create BiometricPrompt instance
        biometricPrompt = new BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Authentication")
                .setSubtitle("Scan your fingerprint")
                .setDescription("Place your finger on the fingerprint sensor")
                .setNegativeButton("Cancel", this.getMainExecutor(), (dialogInterface, i) -> {})
                .build();
    }

    public void authenticate(android.view.View view) {
        // Check if fingerprint hardware is available
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(this);
        if (!fingerprintManager.isHardwareDetected()) {
            Toast.makeText(this, "Fingerprint hardware not detected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if fingerprint permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Fingerprint permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        // Start fingerprint authentication
        biometricPrompt.authenticate(new BiometricPrompt.PromptInfo.Builder()
                .build(), new CancellationSignal(), executor, authenticationCallback);
    }
}
