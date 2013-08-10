package jp.co.satoshun.andmongo.tests;


import android.content.Context;
import android.test.AndroidTestCase;
import jp.co.satoshun.andmongo.MongoClient;

public class MongoClientTest extends AndroidTestCase {
    Context mcontext;

    public void setUp() throws Exception {
        mcontext = getContext();
    }

    public void testConnection() throws Exception {
        MongoClient client = new MongoClient(mcontext);
        assertNotNull(client);
    }
}
