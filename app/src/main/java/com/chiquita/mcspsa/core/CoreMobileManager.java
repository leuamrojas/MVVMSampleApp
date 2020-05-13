package com.chiquita.mcspsa.core;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.SparseArray;

import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CoreMobileManager {

    private SparseArray<String> hawk_keys = new SparseArray<>();

    private SparseArray<CoreMenuEntity> menu = new SparseArray<>();

    private static CoreMobileManager singleton;

    public synchronized static CoreMobileManager getInstance() {
        if (singleton == null) {
            singleton = new CoreMobileManager();
        }
        return singleton;
    }

    public CoreMobileManager() {
        this.hawk_keys.put(1, "User");
        this.hawk_keys.put(2, "Server");

        this.menu.put(1, new CoreMenuEntity("PICQ1050"));
        this.menu.put(3, new CoreMenuEntity("OPLT3050"));
        this.menu.put(4, new CoreMenuEntity("OPLT3060"));
        this.menu.put(6, new CoreMenuEntity("OPLT3090"));
        this.menu.put(8, new CoreMenuEntity("OPLT3120"));
        this.menu.put(11, new CoreMenuEntity("OPLT3140"));
        this.menu.put(12, new CoreMenuEntity("OPLT3150"));
        this.menu.put(13, new CoreMenuEntity("MAFS0040"));
        this.menu.put(14, new CoreMenuEntity("MAFS0050"));
    }

    public String getHawkKeys(int paramInt) {
        String str = hawk_keys.get(paramInt, "");
        return str;
    }

    public String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    public String getDeviceModel() {
        return Build.MODEL;
    }

    public String getDeviceSerialNumber() {
        return Build.SERIAL;
    }

    public String getSdkVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public String getMacAddress(Context context) {
        try {
            WifiManager wimanager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            String str = wimanager.getConnectionInfo().getMacAddress();

            return str;
        } catch (Exception localException) {
            LogManager.getInstance().error(getClass().getCanonicalName(),
                    localException.getMessage());
        }
        return "";
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            LogManager.getInstance().error("IPAddress error", ex.getMessage());
        }
        return null;
    }

    public String getMacAddress() {
        String mac = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements() && mac.length() == 0) {
                NetworkInterface current = interfaces.nextElement();
                if (current.getDisplayName().startsWith("wlan") && current.isUp()) {
                    byte[] address = current.getHardwareAddress();
                    if (address != null) {
                        for (byte b : address) {
                            String part = Integer.toHexString(Integer.valueOf(b).intValue());
                            if (part.startsWith("ffffff")) {
                                part = part.substring(6);
                            }
                            if (mac.length() > 0) {
                                mac = new StringBuilder(String.valueOf(mac)).append(":").append(part).toString();
                            } else {
                                mac = new StringBuilder(String.valueOf(mac)).append(part).toString();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            LogManager.getInstance().error(getClass().getCanonicalName(), e.getMessage());
        }
        return mac;
    }

    public List<CoreMenuEntity> getApplicationsDeveloped() {
        List<CoreMenuEntity> menuItems = new ArrayList<>();
        SparseArray<CoreMenuEntity> items = getExistingMenu();

        for (int i = 0; i < items.size(); i++) {
            int key = items.keyAt(i);
            menuItems.add(items.get(key));
        }
        return menuItems;
    }

    public SparseArray<CoreMenuEntity> getExistingMenu() {
        return this.menu;
    }
}
