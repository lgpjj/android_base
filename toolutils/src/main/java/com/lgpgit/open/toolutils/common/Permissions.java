package com.lgpgit.open.toolutils.common;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BODY_SENSORS;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.PROCESS_OUTGOING_CALLS;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_MMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECEIVE_WAP_PUSH;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.USE_SIP;
import static android.Manifest.permission.WRITE_CALENDAR;
import static android.Manifest.permission.WRITE_CALL_LOG;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * 危险权限大全
 *
 * @author lugp
 * @date 2019/01/30
 */
public class Permissions {

    /** 定位 **/
    public static final String access_fine_location = ACCESS_FINE_LOCATION;
    public static final String access_coarse_location = ACCESS_COARSE_LOCATION;
    /** 日历 **/
    public static final String read_calendar = READ_CALENDAR;
    public static final String write_calendar = WRITE_CALENDAR;
    /** 照相机 **/
    public static final String camera = CAMERA;
    /** 通讯录 **/
    public static final String read_contacts = READ_CONTACTS;
    public static final String write_contacts = WRITE_CONTACTS;
    /** 存储 **/
    public static final String read_external_storage = READ_EXTERNAL_STORAGE;
    public static final String write_external_storage = WRITE_EXTERNAL_STORAGE;
    /** 传感器 **/
    public static final String body_sensors = BODY_SENSORS;
    /** 麦克风 **/
    public static final String record_audio = RECORD_AUDIO;
    /** 电话 **/
    public static final String read_phone_state = READ_PHONE_STATE;
    public static final String call_phone = CALL_PHONE;
    public static final String read_call_log = READ_CALL_LOG;
    public static final String write_call_log = WRITE_CALL_LOG;
    public static final String use_sip = USE_SIP;
    public static final String process_outgoing_calls = PROCESS_OUTGOING_CALLS;
    /** 短信 **/
    public static final String send_sms = SEND_SMS;
    public static final String receive_sms = RECEIVE_SMS;
    public static final String read_sms = READ_SMS;
    public static final String receive_wap_push = RECEIVE_WAP_PUSH;
    public static final String receive_mms = RECEIVE_MMS;
}
