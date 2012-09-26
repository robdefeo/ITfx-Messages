package ITfx.Messages;

import com.mongodb.BasicDBObject;

import java.util.Map;

public class MessagesDBObject extends BasicDBObject {
    public final static String ID_FIELD_NAME = "_id";

    public MessagesDBObject() {
    }

    public MessagesDBObject(Map map) {
        super(map);
    }

    public String get_id() {
        return getString(ID_FIELD_NAME);
    }
}
