package nl.ipsen3server.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Anthony Scheeres
 */
class JsonConverterUtilities {

    /**
     * @author Anthony Scheeres
     */
    public JSONArray convertToJSON(ResultSet resultSet)
            throws Exception {
        JSONArray json = new JSONArray();
        ResultSetMetaData rsmd = resultSet.getMetaData();
        while (resultSet.next()) {
            int numColumns = rsmd.getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 1; i <= numColumns; i++) {
                String column_name = rsmd.getColumnName(i);
                obj.put(column_name, resultSet.getObject(column_name));
            }
            json.put(obj);
        }
        return json;
    }

}
