package jp.co.satoshun.andmongo.connect;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConnectorSharedPreferences implements IMongoConnector {
    private final String DBNAME;
    private final String COLNAME;
    private final String SECRET_TYPE_KEY = "___type";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ConnectorSharedPreferences() {
        DBNAME = null;
        COLNAME = null;
    }

    public ConnectorSharedPreferences(Context context) {
        this(context, "connector_shared_preferences");
    }

    public ConnectorSharedPreferences(Context context, String dbname) {
        this(context, dbname, "default");
    }

    public ConnectorSharedPreferences(Context context, String dbname, String colname) {
        DBNAME = dbname;
        COLNAME = colname;

        setSharedPreferences(context);
    }

    private String getSharedKey() {
        return DBNAME + "____" + COLNAME;
    }

    private void setSharedPreferences(Context context) {
        pref = context.getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    @Override
    public boolean save(final Map<String, Object> query, final boolean safeMode) {
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            execSave(entry.getKey(), entry.getValue());
        }

        editor.commit();
        return true;
    }

    @Override
    public boolean save(final String key, final Object value, boolean safeMode) {
        return save(new HashMap<String, Object>() {
            {put(key, value);}
        }, safeMode);
    }

    private void execSave(String key, Object value) {
        String type = null;
        if (value instanceof String) {
            editor.putString(key, value.toString());
            type = "String";
        } else if (value instanceof Integer) {
            editor.putInt(key, Integer.parseInt(value.toString()));
            type = "Int";
        } else if (value instanceof Float) {
            editor.putFloat(key, Float.parseFloat(value.toString()));
            type = "Float";
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, Boolean.parseBoolean(value.toString()));
            type = "Boolean";
        } else if (value instanceof List) {
            editor.putString(key, value.toString());
            type = "List";
        }

        if (type != null) {
            editor.putString(key + SECRET_TYPE_KEY, type);
        }
    }

    @Override
    public List<Object> get(List<String> query) {
        List<Object> result = new ArrayList<Object>();

        for (String key : query) {
            String type = pref.getString(key + SECRET_TYPE_KEY, null);
            Object addValue = null;
            if (type != null) {
                addValue = get(key, type);
            }
            result.add(addValue);
        }
        return result;
    }

    private Object get(String key, String type) {
        if (type.equals("String")) {
            return pref.getString(key, null);
        }

        if (type.equals("Boolean")) {
            return pref.getBoolean(key, false);
        }

        if (type.equals("Int")) {
            int value = pref.getInt(key, -1000000);
            return value != -1000000 ? value : null;
        }

        if (type.equals("Float")) {
            float value = pref.getFloat(key, -1000000f);
            return value != -1000000f ? value : null;
        }

        if (type.equals("List")) {
            return stringToList(pref.getString(key, null));
        }

        return null;
    }

    private boolean isInt(String s) {
        return s.matches("^[+-]?\\d+");
    }

    private boolean isFloat(String s) {
        return s.matches("^[+-]?\\d+\\.(\\d)*");
    }

    private boolean isBoolean(String s) {
        return s.matches("^(true|false)(\\t)*$");
    }

    private List<Object> stringToList(String s) {
        if (s == null) {
            return null;
        }

        List<Object> list = new ArrayList<Object>();
        s = s.replace("[", "");
        s = s.replace("]", "");

        for (String element : s.split(",")) {
            // FIXME
            element = element.replace(" ", "");
            if (isFloat(element)) {
                list.add(Float.valueOf(element));
            } else if (isInt(element)) {
                list.add(Integer.valueOf(element));
            } else if (isBoolean(element)) {
                list.add(Boolean.valueOf(element));
            } else {
                list.add(element);
            }
        }
        return list;
    }
}
