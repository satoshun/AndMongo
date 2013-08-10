package jp.co.satoshun.andmongo.tests;


import android.app.Activity;
import android.content.Context;
import android.test.AndroidTestCase;
import jp.co.satoshun.andmongo.Cursor;
import jp.co.satoshun.andmongo.MongoClient;
import jp.co.satoshun.andmongo.MongoCollection;
import jp.co.satoshun.andmongo.NotFoundValueException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoCollectionTest extends AndroidTestCase {
    MongoCollection mc;
    Context mcontext;

    public void setUp() throws Exception {
        mcontext = getContext();
        mc = new MongoClient(mcontext).getDB("test").getCollection("col");
    }

    public void tearDown() throws Exception {
        getContext().getSharedPreferences("test____col", Activity.MODE_PRIVATE).edit().clear().commit();
    }

    public void testSaveAndFind() throws Exception {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("test", "hogehoge");
        mc.save(query);

        List<String> q = new ArrayList<String>();
        q.add("test");
        Cursor c = mc.find(q);

        List<String> result = c.get();
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), "hogehoge");

        query.clear();
        query.put("gagag", "2gagal;");
        query.put("ppll", "gawga@");
        mc.save(query);

        q.add("gagag");
        q.add("ppll");

        c = mc.find(q);

        result = c.get();
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "hogehoge");
        assertEquals(result.get(1), "2gagal;");
        assertEquals(result.get(2), "gawga@");

        c = mc.find(new ArrayList<String>());
        result = c.get();
        assertEquals(result.size(), 0);

        q.clear();
        q.add("hoge");
        c = mc.find(q);
        result = c.get();
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), null);
    }

    public void testSaveAndFindOne() throws Exception {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("test", "hogehoge");
        mc.save(query);

        try {
            Cursor c = mc.findOne("test");
            List<String> result = c.get();
            assertEquals("hogehoge", result.get(0));
        } catch(NotFoundValueException e) {
            fail("NotFoundValueException");
        }

        try {
            mc.findOne("testtest");
            fail("NotFoundValueException");
        } catch(NotFoundValueException e) {
        }
    }

    public void testSaveAndFindOneListInt() throws Exception {
        mc.save("hoget", new ArrayList<Integer>() {
            {add(1);add(3);add(5);}
        });

        Cursor c = mc.findOne("hoget");
        List<Integer> list = c.getOne();

        assertEquals(list.size(), 3);
        assertEquals((int) list.get(0), 1);
        assertEquals((int) list.get(1), 3);
        assertEquals((int) list.get(2), 5);
    }

    public void testSaveAndFindOneListFloat() throws Exception {
        mc.save("hoget", new ArrayList<Float>() {
            {add(1.1f);add(3.05f);add(5.11f);}
        });

        Cursor c = mc.findOne("hoget");
        List<Float> list = c.getOne();

        assertEquals(list.size(), 3);
        assertEquals((float) list.get(0), 1.1f);
        assertEquals((float) list.get(1), 3.05f);
        assertEquals((float) list.get(2), 5.11f);
    }

    public void testSaveAndFindOneListBoolean() throws Exception {
        mc.save("hoget", new ArrayList<Boolean>() {
            {add(true);add(false);add(false);}
        });

        Cursor c = mc.findOne("hoget");
        List<Boolean> list = c.getOne();

        assertEquals(list.size(), 3);
        assertEquals((boolean) list.get(0),true);
        assertEquals((boolean) list.get(1), false);
        assertEquals((boolean) list.get(2), false);
    }
}
