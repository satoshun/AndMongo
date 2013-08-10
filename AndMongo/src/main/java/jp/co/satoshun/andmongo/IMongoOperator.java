package jp.co.satoshun.andmongo;

import java.util.List;
import java.util.Map;

interface IMongoOperator {
    Cursor find(List<String> query);
    Cursor findOne(String query) throws NotFoundValueException;
    void save(Map<String, Object> query);
    void save(String key, Object value);
}
