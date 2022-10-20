package com.example.personalehealth.ui.adapter;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.personalehealth.weatherupdate.WeatherFragment;
import com.example.personalehealth.internalsensors.devices.ScanDeviceFragment;
import com.example.personalehealth.internalsensors.location.LocationFragment;
import com.example.personalehealth.internalsensors.pulse.PulseFragment;

public class InternalAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public InternalAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LocationFragment locationFragment = new LocationFragment();
                return locationFragment;
            case 1:
                ScanDeviceFragment deviceFragment = new ScanDeviceFragment();
                return deviceFragment;

            case 2:
                PulseFragment pulseFragment = new PulseFragment();
                return pulseFragment;
            default:
                return new LocationFragment();
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}