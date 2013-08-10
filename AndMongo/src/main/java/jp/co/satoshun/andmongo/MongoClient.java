package jp.co.satoshun.andmongo;

import android.content.Context;
import jp.co.satoshun.andmongo.connect.ConnectorSharedPreferences;
import jp.co.satoshun.andmongo.connect.IMongoConnector;

/**
 * Created by satouhayabusa on 7/14/13.
 */
public class MongoClient {
    IMongoConnector connector = null;
    Context mcontext;

    public MongoClient(Context context) {
        mcontext = context;
    }

    public void setConnector(IMongoConnector connector) {
        this.connector = connector;
    }

    public MongoDatabase getDB(String dbName) {
        return new MongoDatabase(this, dbName);
    }

    public IMongoConnector getConnector(String dbName, String colName) {
        if (connector == null) {
            setConnector(getDefaultConnector(dbName, colName));
        }

        return connector;
    }

    private IMongoConnector getDefaultConnector(String dbName, String colName) {
        return new ConnectorSharedPreferences(mcontext, dbName, colName);
    }
}
