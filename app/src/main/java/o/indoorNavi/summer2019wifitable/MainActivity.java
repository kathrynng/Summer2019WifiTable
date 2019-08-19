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
import java.util.Queue;

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

        final Button buttonAP1 = findViewById(R.id.buttonAP1);

        wManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ArrayList<String> closeBSSID = new ArrayList<String>();

                registerReceiver(receiverScanner, new IntentFilter((WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))); // display list
                
                wManager.startScan();

                HashMap<String, Integer> item = new HashMap<String, Integer>();

                Toast.makeText(MainActivity.this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
                try
                {
                    int counter = 0;
                    Log.d("Winfo", String.valueOf(size));

                    while (counter < size) {
//                        Log.d("Winfo", list.get(counter).SSID + " " + list.get(counter).BSSID + list.get(counter).level);
                    //frequency1 = list.get(counter).level;
                        String BSSID = list.get(counter).BSSID;
                        Integer RSSI = list.get(counter).level;

                    if (list.get(counter).SSID.equals("ubcvisitor") && (RSSI > -60)) {

//                        if(item.containsKey(BSSID)) {
//                            Queue q = (Queue) item.get(BSSID).element();
//                            q.add(RSSI);
//                            item.put(BSSID, q);
//                        }else{
                            item.put(BSSID, RSSI);

//                        }

                        Log.d("Winfo",BSSID + " " + RSSI);

                    }
                    counter++;
                }

                    if(item.containsKey("84:b8:02:bf:71:04")){
                        buttonAP1.setText("FIP139");
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


