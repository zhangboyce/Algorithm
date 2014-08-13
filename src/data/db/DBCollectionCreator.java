package data.db;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by boyce on 2014/7/7.
 */
public class DBCollectionCreator {
    private String host;
    private int port;
    private String dbName;
    private String collectionName;

    public DBCollectionCreator(String host, int port, String dbName, String collectionName) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.collectionName = collectionName;
    }

    public DBCollection newDBCollection() throws UnknownHostException {
        Mongo mongo = new Mongo(this.host, this.port);
        DB db = mongo.getDB(this.dbName);
        DBCollection dbCollection = db.getCollection(this.collectionName);

        return dbCollection;
    }

    public static DBCollection newDBCollection(String collectionName) throws UnknownHostException  {
        return new DBCollectionCreator("172.16.141.163", 27001, "eccrawler300", collectionName).newDBCollection();
    }
}
