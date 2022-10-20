package mbaas.com.nifcloud.androidpayloadapp;

import android.Manifest;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.nifcloud.mbaas.core.NCMB;
import com.nifcloud.mbaas.core.NCMBInstallation;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView _pushId;
    TextView _richurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            JSONObject tmpBlank = new JSONObject("{'No key':'No value'}");
            ListView lv = (ListView) findViewById(R.id.lsJson);
            if (lv != null) {
                lv.setAdapter(new ListAdapter(this, tmpBlank));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(this.getApplicationContext(), "YOUR_APPLICATION_KEY", "YOUR_CLIENT_KEY");
        final NCMBInstallation installation = NCMBInstallation.getCurrentInstallation();

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 33) {
            askNotificationPermission();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //**************** ペイロード、リッチプッシュを処理する ***************
        Intent intent = getIntent();

        //プッシュ通知IDを表示
        _pushId = (TextView) findViewById(R.id.txtPushid);
        String pushid = intent.getStringExtra("com.nifcloud.mbaas.PushId");
        _pushId.setText(pushid);

        //RichURLを表示
        _richurl = (TextView) findViewById(R.id.txtRichurl);
        String richurl = intent.getStringExtra("com.nifcloud.mbaas.RichUrl");
        _richurl.setText(richurl);

        //プッシュ通知のペイロードを表示
        if (intent.getStringExtra("com.nifcloud.mbaas.Data") != null) {
            try {
                JSONObject json = new JSONObject(intent.getStringExtra("com.nifcloud.mbaas.Data"));
                if (json != null) {
                    ListView lv = (ListView) findViewById(R.id.lsJson);
                    lv.setAdapter(new ListAdapter(this, json));
                }
            } catch (JSONException e) {
                //エラー処理
            }
        }
        intent.removeExtra("com.nifcloud.mbaas.RichUrl");
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            // FCM SDK (and your app) can post notifications.
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            // TODO: display an educational UI explaining to the user the features that will be enabled
            //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
            //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
            //       If the user selects "No thanks," allow the user to continue without notifications.
        } else {
            // Directly ask for the permission
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}
