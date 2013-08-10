package jp.co.satoshun.andmongo.connect;


import java.util.List;
import java.util.Map;

public interface IMongoConnector {
    boolean save(Map<String, Object> query, boolean safeMode);
    boolean save(String key, Object value, boolean safeMode);
    List<?> get(List<String> query);
}
