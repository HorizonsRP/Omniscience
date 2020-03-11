package net.lordofthecraft.omniscience.io.mongo;

import com.google.common.collect.Lists;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.connection.ClusterSettings;
import net.lordofthecraft.omniscience.OmniConfig;
import net.lordofthecraft.omniscience.Omniscience;
import net.lordofthecraft.omniscience.io.RecordHandler;
import net.lordofthecraft.omniscience.io.StorageHandler;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MongoStorageHandler implements StorageHandler {

    private static MongoDatabase database;
    private final String collectionName;
    private MongoRecordHandler recordHandler;

    public MongoStorageHandler() {
        this.collectionName = OmniConfig.INSTANCE.getTableName();
    }

    protected static MongoCollection<Document> getCollection(String collectionName) {
        try {
            return database.getCollection(collectionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean connect(Omniscience omniscience) {
        String username = omniscience.getConfig().getString("mongodb.user");
        String password = omniscience.getConfig().getString("mongodb.password");
        String ip = omniscience.getConfig().getString("mongodb.authenticationDatabase");

        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://" + username + ":" + password + "@" + ip);
        MongoClient mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase("test");

        this.recordHandler = new MongoRecordHandler(this);
        try {
            MongoCollection<Document> collection = getCollection(collectionName);
            if (collection != null) {
                collection.createIndex(
                        new Document("Location.X", 1).append("Location.Z", 1).append("Location.Y", 1).append("Created", -1)
                );
                collection.createIndex(new Document("Created", -1).append("Event", 1));
                collection.createIndex(new Document("Created", -1).append("Player", 1));

                IndexOptions options = new IndexOptions().expireAfter(0L, TimeUnit.SECONDS);
                collection.createIndex(new Document("Expires", 1), options);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public RecordHandler records() {
        return recordHandler;
    }

    @Override
    public void close() {

    }
}
