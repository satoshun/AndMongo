package jp.co.satoshun.andmongo;

import jp.co.satoshun.andmongo.connect.IMongoConnector;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by satouhayabusa on 7/14/13.
 */
public class MongoCollection implements IMongoOperator {
    String colName;
    MongoDatabase db;

    public MongoCollection(MongoDatabase db, String colName) {
        this.db = db;
        this.colName = colName;
    }

    @Override
    public void save(Map<String, Object> query) {
        getConnector().save(query, false);
    }

    @Override
    public void save(String key, Object value) {
        getConnector().save(key, value, false);
    }

    @Override
    public Cursor find(List<String> query) {
        return new Cursor(getConnector().get(query));
    }

    @Override
    public Cursor findOne(String query) throws NotFoundValueException {
        List<?> result = getConnector().get(Arrays.asList(query));
        if (result.get(0) == null) {
            throw new NotFoundValueException("no search value : " + query);
        }

        return new Cursor(result);
    }

    private IMongoConnector getConnector() {
        return db.getClient().getConnector(db.getDbName(), colName);
    }
}
