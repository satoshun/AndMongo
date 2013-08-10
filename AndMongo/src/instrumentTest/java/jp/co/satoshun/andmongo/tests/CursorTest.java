package jp.co.satoshun.andmongo.tests;


import android.content.Context;
import android.test.AndroidTestCase;
import jp.co.satoshun.andmongo.Cursor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CursorTest extends AndroidTestCase {
    Context mcontext;

    public void setUp() throws Exception {
        mcontext = getContext();
    }

    private List<?> dummyData() {
        List<Object> dummy = new ArrayList<Object>();
        dummy.add("test");
        dummy.add("testgaga");
        return dummy; 
    }

    public void testIterator() throws Exception {
        Cursor cursor = new Cursor(dummyData());
        Iterator<String> iter = cursor.iterator();
        assertNotNull(iter);
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), "test");
        assertEquals(iter.next(), "testgaga");
        assertFalse(iter.hasNext());
    }

    public void testFor() throws Exception {
        Cursor cursor = new Cursor(dummyData());
        int index = 0;
        for (String s : cursor.<String>iterator()) {
            assertEquals(s, dummyData().get(index).toString());
            index += 1;
        }
    }

    public void testFor2() throws Exception {
        Cursor cursor = new Cursor(dummyData());
        int index = 0;
        for (String s : cursor.<String>get()) {
            assertEquals(s, dummyData().get(index).toString());
            index += 1;
        }
    }

    private List<?> dummyDataInt() {
        List<Object> dummy = new ArrayList<Object>();
        dummy.add(100);
        dummy.add(2000);
        dummy.add(-50);
        return dummy; 
    }

    public void testIteratorInt() throws Exception {
        Cursor cursor = new Cursor(dummyDataInt());
        Iterator<Integer> iter = cursor.iterator();
        assertNotNull(iter);
        assertTrue(iter.hasNext());
        assertEquals((int) iter.next(), 100);
        assertEquals((int) iter.next(), 2000);
        assertEquals((int) iter.next(), -50);
        assertFalse(iter.hasNext());
    }

    public void testFor3() throws Exception {
        Cursor cursor = new Cursor(dummyDataInt());

        int firstValue = cursor.<Integer>getOne();
        assertEquals(firstValue, 100);

        int index = 0;
        for (int target : cursor.<Integer>iterator()) {
            assertEquals(target, Integer.parseInt(dummyDataInt().get(index).toString()));
            index += 1;
        }
    }

    private List<?> dummyDataFloat() {
        List<Object> dummy = new ArrayList<Object>();
        dummy.add(100f);
        dummy.add(2000f);
        dummy.add(-50f);
        return dummy; 
    }

    public void testIteratorFloat() throws Exception {
        Cursor cursor = new Cursor(dummyDataFloat());
        assertEquals(cursor.getOne(), 100f);

        Iterator<Float> iter = cursor.iterator();

        assertNotNull(iter);
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), 100f);
        assertEquals(iter.next(), 2000f);
        assertEquals(iter.next(), -50f);
        assertFalse(iter.hasNext());
    }

    public void testForFloat() throws Exception {
        Cursor cursor = new Cursor(dummyDataFloat());
        int index = 0;
        for (float target : cursor.<Float>iterator()) {
            assertEquals(target, Float.parseFloat(dummyDataInt().get(index).toString()));
            index += 1;
        }
    }

    private List<?> dummyDataList() {
        List<Object> dummy = new ArrayList<Object>();
        dummy.add(new ArrayList<String>() {
            {add("hogehoge");add("hogeo");}
        });
        dummy.add(new ArrayList<String>() {
            {add("hogehoge");add("hogeo");add("testtes");}
        });
        return dummy; 
    }

    public void testIteratorList() throws Exception {
        Cursor cursor = new Cursor(dummyDataList());
        assertNotNull(cursor);

        Iterator<List> iter = cursor.iterator();
        assertTrue(iter.hasNext());
        List<String> one = iter.next();
        assertTrue(one.size() == 2);
        assertTrue(one.get(0) == "hogehoge");
        assertTrue(one.get(1) == "hogeo");

        List<String> two = iter.next();
        assertTrue(two.size() == 3);
        assertTrue(two.get(0) == "hogehoge");
        assertTrue(two.get(1) == "hogeo");
        assertTrue(two.get(2) == "testtes");

        assertFalse(iter.hasNext());
    }
}
