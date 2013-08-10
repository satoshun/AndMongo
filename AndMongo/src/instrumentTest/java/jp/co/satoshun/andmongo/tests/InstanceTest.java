package jp.co.satoshun.andmongo.tests;

import android.content.Context;
import android.test.AndroidTestCase;

import jp.co.satoshun.andmongo.MongoClient;
import jp.co.satoshun.andmongo.MongoCollection;
import jp.co.satoshun.andmongo.MongoDatabase;


public class InstanceTest extends AndroidTestCase {
    Context mcontext;

    public void setUp() throws Exception {
        mcontext = getContext();
    }

    public void testAll() throws Exception {
        MongoClient client = new MongoClient(mcontext);
        assertNotNull(client);

        MongoDatabase db = client.getDB("test");
        assertNotNull(db);

        MongoCollection collection = db.getCollection("test");
        assertNotNull(collection);
    }
}
