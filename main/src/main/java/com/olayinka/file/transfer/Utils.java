/*
 * Copyright 2015
 *
 *     Olayinka S. Folorunso <mail@olayinkasf.com>
 *     http://olayinkasf.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.olayinka.file.transfer;

import com.olayinka.file.transfer.model.Device;
import org.apache.commons.codec.binary.Hex;
import ripped.android.json.JSONArray;
import ripped.android.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by olayinka on 7/30/15.
 */
public class Utils {

    public static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String macToString(byte[] mac) {
        if (mac != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        }
        return null;
    }

    public static String currentDirectory() {
        return System.getProperty("user.dir");
    }


    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(CHARSET.charAt(rnd.nextInt(CHARSET.length())));
        return sb.toString();
    }

    public static int randomSize(int min, int max) {
        return rnd.nextInt(max - min) + min;
    }

    public static String hash(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA-256");
        md.update(string.getBytes());
        byte[] digest = md.digest();
        return String.valueOf(Hex.encodeHex(digest));
    }

    public static JSONArray jsonArrayOfDevice(JSONObject mThisDevice) {
        JSONArray ret = new JSONArray();
        ret.put(mThisDevice.getString(Device.Columns.MAC_ADDRESS));
        ret.put(mThisDevice.getString(Device.Columns.LAST_KNOWN_IP));
        ret.put(mThisDevice.getString(Device.Columns.NAME));
        ret.put(mThisDevice.getString(Device.Columns.DEVICE_TYPE));
        return ret;
    }

    public static JSONObject jsonObjectOfDevice(JSONArray mThisDevice) {
        JSONObject ret = new JSONObject();
        ret.put(Device.Columns.MAC_ADDRESS, mThisDevice.getString(0));
        ret.put(Device.Columns.LAST_KNOWN_IP, mThisDevice.getString(1));
        ret.put(Device.Columns.NAME, mThisDevice.getString(2));
        ret.put(Device.Columns.DEVICE_TYPE, mThisDevice.getString(3));
        return ret;
    }
}
