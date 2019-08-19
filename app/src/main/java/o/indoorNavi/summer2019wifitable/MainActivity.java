package o.indoorNavi.summer2019wifitable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
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


        wManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        final String ITEM_KEY = "key";

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int frequency1 = -99;
                int frequency2 = -100;
                ArrayList<String> closeBSSID = new ArrayList<String>();

                registerReceiver(receiverScanner, new IntentFilter((WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))); // display list

                wManager.startScan();
                Toast.makeText(MainActivity.this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
                try
                {
                    int counter = 0;
                    Log.d("Winfo", String.valueOf(size));while (counter < size) {
//                        Log.d("Winfo", list.get(counter).SSID + " " + list.get(counter).BSSID + list.get(counter).level);
                    //frequency1 = list.get(counter).level;
                    HashMap<String, String> item = new HashMap<String, String>();
                    if (list.get(counter).SSID.equals("ubcvisitor")) {

                        item.put(ITEM_KEY, list.get(counter).SSID + "  " + list.get(counter).BSSID + " " + list.get(counter).level);

                        if (frequency1 < list.get(counter).level){
                            frequency1 = list.get(counter).level;
                            closeBSSID.add(list.get(counter).BSSID);
                        }
                        else if (frequency1 >= frequency2 && frequency2 < list.get(counter).level ){
                            frequency2 = list.get(counter).level;
                            closeBSSID.add(list.get(counter).BSSID);
                        }

                        Log.d("Winfo", String.valueOf(frequency1) + frequency2 + " " + closeBSSID);

                    }
                    counter++;
                }

                    //Log.d("BSSID",BSSID);
                    //Log.d("BSSID", String.valueOf(MVS.getSectionFromBSSID(BSSID)));
                    //Log.d("BSSID", String.valueOf(ivS1.isShown()));

                }
                catch (Exception e)
                { Log.d("WinFo", e.toString());};


                handler.postDelayed(this, 5000);
            }
        }, 5000);

        }

}


