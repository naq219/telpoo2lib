package com.lemy.telpoo2lib.model;

import android.content.ContentValues;

import com.lemy.telpoo2lib.utils.Mlog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BObject {

    protected JSONObject data = null;

    public BObject() {

    }

    public BObject(JSONObject jsonObject) {
        data=jsonObject;
    }

    public BObject(String jsonObjectString) {
        try {
            data=new JSONObject(jsonObjectString);
        } catch (JSONException e) {
            e.printStackTrace();
            data=new JSONObject();
        }
    }

    /**
     * @return danh sach cac key cua contentvalues
     */
    public ArrayList<String> getKeys() {

        ArrayList<String> listKey = new ArrayList<String>();
        if (data == null) return listKey;

        Iterator<String> interrator = data.keys();

        while (interrator.hasNext()) {
            listKey.add(interrator.next());
        }
        return listKey;
    }

    public ContentValues getDataContentValue(){
        ArrayList<String> keys = getKeys();
        ContentValues cv= new ContentValues();
        for (int i = 0; i < keys.size(); i++) {
            String key=keys.get(i);
            Object value= get(key);
            if (value instanceof String) cv.put(key,(String)value);
            else if (value instanceof Integer) cv.put(key,(Integer) value);
            else if (value instanceof Long) cv.put(key,(Long) value);
            else if (value instanceof Double) cv.put(key,(Double) value);
            else cv.put(key,String.valueOf(value) );
        }
        return cv;
    }

    /**
     * tao ra key de lam viec voi db. voi keys[0] la primary key
     *
     * @param keys
     * @return
     */
    public static String[] genkeysDbSimple(String[] keys) {
        String[] keysD = new String[keys.length + 1];
        for (int i = 0; i < keys.length; i++) {
            keysD[i + 1] = keys[i];
        }
        keysD[0] = "primarykey_id_" + keys[0];
        return keysD;

    }

    /**
     * chuyen key va value sang kieu param de goi method GET, POST ...
     * vi du : &username=naq&password=123
     *
     * @return
     */
    public String convert2NetParrams() {
        String parrams = "";
        ArrayList<String> keys = getKeys();
        for (String key : keys) {
            try {
                parrams += "&" + key + "=" + URLEncoder.encode("" + get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Mlog.E("convert2NetParrams " + e);
            }
        }
        return parrams;
    }



    public String convert2NetParramsNotEncode() {
        String parrams = "";
        ArrayList<String> keys = getKeys();
        for (String key : keys) {

            parrams += "&" + key + "=" + get(key);

        }
        return parrams;
    }


    /**
     * set du lieu cho object
     *
     * @param data
     */
    public void putData(JSONObject data) {
        this.data = data;
    }

    public boolean putData(ContentValues cv){
        data=null;
        initData();
        Iterator<Map.Entry<String, Object>> interrator = cv.valueSet().iterator();
            try {
                while (interrator.hasNext()) {
                    String key=interrator.next().getKey();
                    data.put(key,cv.get(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                data=new JSONObject();
                return false;
            }
            return true;


    }

    public JSONObject getData() {
        return data;
    }

    public boolean set(String key, Object value) {
        initData();
        try {
            data.put(key, value);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void initData() {
        if (data == null) data = new JSONObject();
    }

    /**
     *
     * @param key
     * @return null nếu ko có dữ liệu
     */
    public Object get(String key) {
//        initData();
//       return data.opt(key);
        return get(key,null);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return defaultValue nếu k có dữ liệu
     */
    public Object get(String key, Object defaultValue) {
        initData();
        Object o = opt(key);
        if (o==null) return defaultValue;
        return o;
    }

    public String getAsString(String key){return getAsString(key,null);};
    public String getAsString(String key,String defaultValue){
        initData();
        Object o= opt(key);
        if (o==null) return defaultValue;
        return String.valueOf(o);
    }

    public Integer getAsInt(String key){return getAsInt(key,null);};
    public Integer getAsInt(String key,Integer defaultValue){
        Object o= get(key);
        if (o==null) return defaultValue;
        if (o instanceof Integer) return (Integer)o;

        try {
            Integer i = Integer.parseInt(String.valueOf(o));
            return i;
        }
        catch (NumberFormatException e){
            return defaultValue;
        }

    }


    public Object remove(String key) {
        return data.remove(key);
    }



    public boolean has(String key) {
        return data.has(key);
    }

    public void removeEmpty() {
        ArrayList<String> keys = getKeys();
        for (String key : keys) {
            if (getAsString(key,"").isEmpty())
                remove(key);
        }
    }

    public boolean isEmpty(String key) {
        String vl = getAsString(key,"");
        return vl.isEmpty();

    }

    public static  List<BObject> JsonArray2List(JSONArray ja){

        List<BObject> ojs = new ArrayList<>();
        try {
        for (int i = 0; i < ja.length(); i++) {
            BObject oj = new BObject(ja.getJSONObject(i));
            ojs.add(oj);
        }
        return ojs;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private  Object opt( String key)
    {
        if (data.isNull(key))
            return null;
        else
            return data.opt(key);

    }




}
