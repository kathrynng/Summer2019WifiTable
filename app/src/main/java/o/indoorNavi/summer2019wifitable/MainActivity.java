package o.indoorNavi.summer2019wifitable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WifiManager wManager;
    private List<ScanResult> list;
    private int size;

    private BroadcastReceiver receiverScanner = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                list = wManager.getScanResults();
                unregisterReceiver(this);
                Log.d("WinFo", String.valueOf(list.size()));
                size = list.size();


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAP1 = findViewById(R.id.buttonAP1);

    }

}
