package jp.co.satoshun.andmongo;

/**
 * Created by satouhayabusa on 7/14/13.
 */
public class MongoDatabase {
    MongoClient client;
    String dbName;

    public MongoClient getClient() {
        return client;
    }

    public String getDbName() {
        return dbName;
    }

    public MongoDatabase(MongoClient client, String dbName) {
        this.client = client;
        this.dbName = dbName;
    }

    public MongoCollection getCollection(String colName) {
        return new MongoCollection(this, colName);
    }
}
