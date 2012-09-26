package ITfx.Messages.DataAccess;

import ITfx.Messages.MessagesDBObject;
import com.mongodb.BasicDBObject;

import java.util.List;

public interface IRepository<T extends MessagesDBObject> {
    List<BasicDBObject> getAll();
    BasicDBObject insert(BasicDBObject item);
    BasicDBObject save(BasicDBObject item);
    BasicDBObject getById(String id);
}
