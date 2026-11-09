package it.chatgpt.torciarapida;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final int REQUEST_CAMERA = 41;
    private CameraManager cameraManager;
    private String cameraId;
    private boolean requestedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            for (String id : cameraManager.getCameraIdList()) {
                CameraCharacteristics c = cameraManager.getCameraCharacteristics(id);
                Boolean flash = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                Integer facing = c.get(CameraCharacteristics.LENS_FACING);
                if (Boolean.TRUE.equals(flash)
                        && (facing == null || facing == CameraCharacteristics.LENS_FACING_BACK)) {
                    cameraId = id;
                    break;
                }
            }
        } catch (CameraAccessException e) {
            finish();
            return;
        }

        if (cameraId == null) {
            Toast.makeText(this, "Torcia non disponibile su questo telefono", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        boolean wasOn = getPreferences(MODE_PRIVATE).getBoolean("torch_on", false);
        requestedOn = !wasOn;

        if (Build.VERSION.SDK_INT >= 23
                && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            toggleTorch();
        }
    }

    private void toggleTorch() {
        try {
            cameraManager.setTorchMode(cameraId, requestedOn);
            getPreferences(MODE_PRIVATE).edit().putBoolean("torch_on", requestedOn).apply();
        } catch (CameraAccessException | SecurityException e) {
            Toast.makeText(this, "Impossibile comandare la torcia", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toggleTorch();
            } else {
                Toast.makeText(this, "Permesso fotocamera necessario per usare il flash", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
