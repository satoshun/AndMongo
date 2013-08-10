package jp.co.satoshun.andmongo.tests.connect;

import android.app.Activity;
import android.test.AndroidTestCase;
import jp.co.satoshun.andmongo.connect.ConnectorSharedPreferences;
import jp.co.satoshun.andmongo.connect.IMongoConnector;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectorSharedPreferencesTest extends AndroidTestCase {
    ConnectorSharedPreferences cs;

    public void setUp() throws Exception {
        cs = new ConnectorSharedPreferences(getContext(), "test", "col");
    }

    private String getSharedKey() {
        Class<?> c = cs.getClass();
        try {
            Method m = c.getDeclaredMethod("getSharedKey");
            m.setAccessible(true);
            return (String) m.invoke(cs);
        } catch (Exception e) {
        }
        return null;
    }

    public void tearDown() throws Exception {
        getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).edit().clear().commit();
    }

    public void testSave() throws Exception {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("key", "gagag");

        cs.save(query, false);

        String v = getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getString("key", null);
        assertEquals(v, "gagag");
    }

    public void testSave2() {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("testes", "value");
        query.put("testestes", "valuev");

        cs.save(query, false);
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getString("testes", null), "value");
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getString("testestes", null), "valuev");
    }

    public void testSave3() {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("testes", 190);
        query.put("testestes", 2000);
        query.put("testestesga", 4000);

        cs.save(query, false);
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getInt("testes", 0), 190);
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getInt("testestes", 0), 2000);
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getInt("testestesga", 0), 4000);
    }

    public void testSave4() {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("testes", 20.0f);
        query.put("testestes", 2000.0f);
        query.put("testestesga", 4000.0f);

        cs.save(query, false);
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getFloat("testes", 0f), 20.0f);
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getFloat("testestes", 0f), 2000.0f);
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getFloat("testestesga", 0f), 4000.0f);
    }

    public void testSave5() {
        Map<String, Object> query = new HashMap<String, Object>();
        List<String> v = Arrays.asList("test", "hogehoge");
        query.put("testes", v);
        cs.save(query, false);
        assertEquals(getContext().getSharedPreferences(getSharedKey(), Activity.MODE_PRIVATE).getString("testes", null), v.toString());
    }

    private void saveQuery() {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("testes", 20.0f);
        query.put("testestes", 200);
        query.put("testestesga", "hogehoge");

        cs.save(query, false);
    }

    public void testGet() {
        List<String> query = new ArrayList<String>();
        saveQuery();

        query.add("testestesga");
        List<Object> result = cs.get(query);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), "hogehoge");
    }

    public void testGet2() {
        List<String> query = new ArrayList<String>();
        saveQuery();

        query.add("testes");
        List<Object> result = cs.get(query);
        assertEquals(result.size(), 1);
        assertEquals(Float.parseFloat(result.get(0).toString()), 20.0f);
    }

    public void testGet3() {
        List<String> query = new ArrayList<String>();
        saveQuery();

        query.add("testestes");
        List<Object> result = cs.get(query);
        assertEquals(result.size(), 1);
        assertEquals(Integer.parseInt(result.get(0).toString()), 200);
    }

    public void testGet4() {
        List<String> query = new ArrayList<String>();
        saveQuery();

        query.add("testes");
        query.add("testestesga");
        query.add("testestes");

        List<Object> result = cs.get(query);
        assertEquals(result.size(), 3);
        assertEquals(Float.parseFloat(result.get(0).toString()), 20.0f);
        assertEquals(result.get(1), "hogehoge");
        assertEquals(Integer.parseInt(result.get(2).toString()), 200);
    }

    public void testListSaveGet() {
        Map<String, Object> query = new HashMap<String, Object>();
        List<String> v = Arrays.asList("test", "hogehoge");
        query.put("testes", v);
        cs.save(query, false);

        List<Object> result = cs.get(Arrays.asList("testes"));
        assertEquals(result.size(), 1);

        List<String> one = (List<String>) result.get(0);
        assertEquals(one.size(), 2);
        assertEquals(one.get(0), "test");
        assertEquals(one.get(1), "hogehoge");
    }
}
