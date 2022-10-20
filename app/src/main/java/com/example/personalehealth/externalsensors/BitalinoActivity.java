package com.example.personalehealth.externalsensors;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.androidplot.util.Redrawer;
import com.androidplot.xy.AdvancedLineAndPointRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.bitalino.comm.BITalinoDevice;
import com.bitalino.comm.BITalinoException;
import com.bitalino.comm.BITalinoFrame;
import com.example.personalehealth.R;
import com.example.personalehealth.utils.Utilities;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import retrofit.RestAdapter;
import retrofit.client.Response;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class BitalinoActivity extends RoboActivity {

    private XYPlot plot, plot2;
    private TextView text_reading,textecgdata;
    private static final String TAG = "BitalinoActivity";
    private static final boolean UPLOAD = false;
    private Redrawer redrawer, redrawer2;
    BITalinoFrame[] frames;
    public BITalinoDevice bitalino;
    int[] arrayECg;
    int[] arrayPPg;
    /*
     * http://developer.android.com/reference/android/bluetooth/BluetoothDevice.html
     * #createRfcommSocketToServiceRecord(java.util.UUID)
     *
     * "Hint: If you are connecting to a Bluetooth serial board then try using the
     * well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB. However if you
     * are connecting to an Android peer then please generate your own unique
     * UUID."
     */

    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    @InjectView(R.id.log)
    private TextView tvLog;
    private boolean testInitiated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitalino);

        // initialize our XYPlot reference:
       plot = (XYPlot) findViewById(R.id.plot);
//        plot2 = (XYPlot) findViewById(R.id.plot2);
        text_reading = (TextView) findViewById(R.id.text_reading);

      plot.setDomainBoundaries(0, 30, BoundaryMode.FIXED);
       plot.setRangeBoundaries(0, 2, BoundaryMode.FIXED);
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1);
       plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 5);
//        plot2.setRangeBoundaries(0, 800, BoundaryMode.FIXED);
//        plot2.setDomainBoundaries(0, 30, BoundaryMode.FIXED);
//        plot2.setRangeStep(StepMode.INCREMENT_BY_VAL, 200);
//        plot2.setDomainStep(StepMode.INCREMENT_BY_VAL, 5);
//
//
        plot.setLinesPerRangeLabel(5);
//        plot2.setLinesPerRangeLabel(10);
        // execute
        if (!testInitiated)
            new TestAsyncTask().execute();
    }

    public void save(View view) {
        if (testInitiated) {
            try {
                bitalino.stop();
                double ecg = calculateAverage(arrayECg);
            //  int ppg = (int) calculateAverage(arrayPPg);
                Utilities.saveString(getApplicationContext(), "ecg", String.valueOf(ecg));
             //   Utilities.saveString(getApplicationContext(), "ppg", String.valueOf(ppg));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                text_reading.setText(Html.fromHtml(
                        "<font color='#ff9052'><b>ECG :</b>" + ecg + "<br></font>"
                ));


//                finish();

            } catch (BITalinoException e) {
                e.printStackTrace();
            }
        }

    }


    private class TestAsyncTask extends AsyncTask<Void, String, Void> {
        private TextView tvLog = (TextView) findViewById(R.id.log);
        private BluetoothDevice dev = null;
        private BluetoothSocket sock = null;
        private InputStream is = null;
        private OutputStream os = null;


        @Override
        protected Void doInBackground(Void... paramses) {
            try {
                // Let's get the remote Bluetooth device
                final String remoteDevice = "20:19:07:00:80:E9";

                final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
                dev = btAdapter.getRemoteDevice(remoteDevice);

                /*
                 * Establish Bluetooth connection
                 *
                 * Because discovery is a heavyweight procedure for the Bluetooth adapter,
                 * this method should always be called before attempting to connect to a
                 * remote device with connect(). Discovery is not managed by the Activity,
                 * but is run as a system service, so an application should always call
                 * cancel discovery even if it did not directly request a discovery, just to
                 * be sure. If Bluetooth state is not STATE_ON, this API will return false.
                 *
                 * see
                 * http://developer.android.com/reference/android/bluetooth/BluetoothAdapter
                 * .html#cancelDiscovery()
                 */
                Log.d(TAG, "Stopping Bluetooth discovery.");

                btAdapter.cancelDiscovery();

                sock = dev.createRfcommSocketToServiceRecord(MY_UUID);
                sock.connect();
                testInitiated = true;

                bitalino = new BITalinoDevice(100, new int[]{0, 1, 2, 3, 4, 5});
                publishProgress("Connecting to BITalino [" + remoteDevice + "]..");
                bitalino.open(sock.getInputStream(), sock.getOutputStream());
                publishProgress("Connected.");

                // get BITalino version
                publishProgress("Version: " + bitalino.version());

                // start acquisition on predefined analog channels
                bitalino.start();

                // read until task is stopped
                int counter = 0;
                while (counter < 100) {
                    final int numberOfSamplesToRead = 100;
                    publishProgress("Reading samples..");
                    frames = bitalino.read(numberOfSamplesToRead);

                    if (UPLOAD) {
                        // prepare reading for upload
                        BITalinoReading reading = new BITalinoReading();
                        reading.setTimestamp(System.currentTimeMillis());
                        reading.setFrames(frames);
                        // instantiate reading service client
                        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setEndpoint("http://server_ip:8080/bitalino")
                                .build();
                        ReadingService service = restAdapter.create(ReadingService.class);
                        // upload reading
                        Response response = service.uploadReading(reading);
                        assert response.getStatus() == 200;
                    }

                    // present data in screen
                    for (BITalinoFrame frame : frames)
                        publishProgress(frame.toString());

                    counter++;


                     arrayECg = new int[frames.length];

                    for (int i = 0; i < frames.length; i++) {

                        int analog = frames[i].getAnalog(0);

                        arrayECg[i] = analog;
                    }
                  ECGModel ecgSeries = new ECGModel(arrayECg.length, 20);

                    // add a new series' to the xyplot:
                    MyFadeFormatter formatter = new MyFadeFormatter(2000);
                    formatter.setLegendIconEnabled(false);
                    plot.addSeries(ecgSeries, formatter);
//
//
//
//                    // add a new series' to the xyplot:

//
//
//
//
//                    // reduce the number of range labels
//
//
//                    // start generating ecg data in the background:
                    ecgSeries.start(new WeakReference<>(plot.getRenderer(AdvancedLineAndPointRenderer.class)));

//                    // set a redraw rate of 30hz and start immediately:
                    redrawer = new Redrawer(plot, 70, true);




                }
                // trigger digital outputs
                // int[] digital = { 1, 1, 1, 1 };
                // device.trigger(digital);
            } catch (Exception e) {
                Log.e(TAG, "There was an error.", e);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tvLog.append("\n".concat(values[0]));
        }

        @SuppressLint("WrongThread")
        @Override
        protected void onCancelled() {
            // stop acquisition and close bluetooth connection
            try {
                bitalino.stop();
                publishProgress("BITalino is stopped");

                sock.close();
                publishProgress("And we're done! :-)");
            } catch (Exception e) {
                Log.e(TAG, "There was an error.", e);
            }
        }

    }

    /**
     * Special {@link AdvancedLineAndPointRenderer.Formatter} that draws a line
     * that fades over time.  Designed to be used in conjunction with a circular buffer model.
     */
    public static class MyFadeFormatter extends AdvancedLineAndPointRenderer.Formatter {

        private int trailSize;

        MyFadeFormatter(int trailSize) {
            this.trailSize = trailSize;
        }

        @Override
        public Paint getLinePaint(int thisIndex, int latestIndex, int seriesSize) {
            // offset from the latest index:
            int offset;
            if(thisIndex > latestIndex) {
                offset = latestIndex + (seriesSize - thisIndex);
            } else {
                offset =  latestIndex - thisIndex;
            }

            float scale = 255f / trailSize;
            int alpha = (int) (255 - (offset * scale));
            getLinePaint().setAlpha(alpha > 0 ? alpha : 0);
            return getLinePaint();
        }
    }
    /**
     * Primitive simulation of some kind of signal.  For this example,
     * we'll pretend its an ecg.  This class represents the data as a circular buffer;
     * data is added sequentially from left to right.  When the end of the buffer is reached,
     * i is reset back to 0 and simulated sampling continues.
     */
    public static class ECGModel implements XYSeries {

        private final Number[] data;
        private final long delayMs;
        private final int blipInteral;
        private final Thread thread;
        private boolean keepRunning;
        private int latestIndex;

        private WeakReference<AdvancedLineAndPointRenderer> rendererRef;

        /**
         * @param size         Sample size contained within this model
         * @param updateFreqHz Frequency at which new samples are added to the model
         */
        ECGModel(int size, int updateFreqHz) {
            data = new Number[size];
            for (int i = 0; i < data.length; i++) {
                data[i] = 0;
            }

            // translate hz into delay (ms):
            delayMs = 1000 / updateFreqHz;

            // add 7 "blips" into the signal:
            blipInteral = size / 7;

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (keepRunning) {
                            if (latestIndex >= data.length) {
                                latestIndex = 0;
                            }

                            // generate some random data:
                            if (latestIndex % blipInteral == 0) {
                                // insert a "blip" to simulate a heartbeat:
                                data[latestIndex] = (Math.random() * 10) + 3;
                            } else {
                                // insert a random sample:
                                data[latestIndex] = Math.random() * 2;
                            }

                            if (latestIndex < data.length - 1) {
                                // null out the point immediately following i, to disable
                                // connecting i and i+1 with a line:
                                data[latestIndex + 1] = null;
                            }

                            if (rendererRef.get() != null) {
                                rendererRef.get().setLatestIndex(latestIndex);
                                Thread.sleep(delayMs);
                            } else {
                                keepRunning = false;
                            }
                            latestIndex++;
                        }
                    } catch (InterruptedException e) {
                        keepRunning = false;
                    }
                }
            });
        }

        void start(final WeakReference<AdvancedLineAndPointRenderer> rendererRef) {
            this.rendererRef = rendererRef;
            keepRunning = true;
            thread.start();
        }

        @Override
        public int size() {
            return data.length;
        }

        @Override
        public Number getX(int index) {
            return index;
        }

        @Override
        public Number getY(int index) {
            return data[index];
        }

        @Override
        public String getTitle() {
            return "Signal";
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        redrawer.finish();

    }
    private double calculateAverage(int[] marks) {
        Integer sum = 0;
        if(marks.length!=0) {
            for (Integer mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.length;
        }
        return sum;
    }
}
