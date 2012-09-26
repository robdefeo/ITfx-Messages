package ITfx.Messages.DataAccess;

import ITfx.Messages.MessagesDBObject;
import com.mongodb.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.UnknownHostException;
import java.util.List;

abstract class Repository<T extends MessagesDBObject> implements IRepository<T> {
    protected static final Log log = LogFactory.getLog(Repository.class);
    protected Mongo mongo;
    protected DB database;
    protected DBCollection dbCollection;
    private final Class<T> repositoryClass;

    public Repository(Class<T> tClass) throws UnknownHostException {
        repositoryClass = tClass;
        mongo = new Mongo();
        database = mongo.getDB("itfx-messages");
        dbCollection = database.getCollection(repositoryClass.getName());
    }

    @Override
    public List<BasicDBObject> getAll() {
        throw new NotImplementedException();
        //return _MongoOperations.findAll(repositoryClass);
    }

    @Override
    public BasicDBObject insert(BasicDBObject item) {
        log.info(String.format("inserting item: repository=mongoDB, action=insert, item_type=%s, %s=%s",
                repositoryClass.getName(),
                MessagesDBObject.ID_FIELD_NAME, item.get(MessagesDBObject.ID_FIELD_NAME)));
        dbCollection.insert(item);
        return item;
    }

    @Override
    public BasicDBObject getById(String id) {
        BasicDBObject object = (BasicDBObject) getDBObjectById(id);

        return object;
    }

    @Override
    public BasicDBObject save(BasicDBObject item) {
        log.info(String.format("updating item, repository=mongoDB, action=update, item_type=%s, %s=%s",
                repositoryClass.getName(),
                MessagesDBObject.ID_FIELD_NAME, item.get(MessagesDBObject.ID_FIELD_NAME)));

        final DBObject updateItem = getById(item.getString(MessagesDBObject.ID_FIELD_NAME));

        if (updateItem != null) {
            dbCollection.save(item);
            return item;
        } else {
            return null;
        }
    }

    protected DBObject getDBObjectById(String id) {
        final DBObject query = BasicDBObjectBuilder
                .start(MessagesDBObject.ID_FIELD_NAME, id)
                .get();

        DBCursor cursor = dbCollection.find(query);

        if (cursor.hasNext())
            return cursor.next();
        else
            return null;
    }
}
