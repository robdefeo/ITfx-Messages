package ITfx.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DBObject;

import java.util.Map;
import java.util.UUID;

final public class Message extends MessagesDBObject {
    // <editor-fold defaultstate="collapsed" desc="Properties">
    final static String CONTENT_FIELD_NAME = "content";
    final static String TYPE_FIELD_NAME = "type";
    final static String TOPIC_FIELD_NAME = "topic";
    final static String CATEGORY_FIELD_NAME = "category";


    public String getContent() {
        return getString(CONTENT_FIELD_NAME);
    }

    public Type getType() {
        return get(TYPE_FIELD_NAME) != null ? Type.valueOf(getString(TYPE_FIELD_NAME)) : Type.Unknown;
    }

    public Category getCategory() {
        return get(CATEGORY_FIELD_NAME) != null ? Category.valueOf(getString(CATEGORY_FIELD_NAME)) : Category.None;
    }

    public Topic getTopic() {
        return get(TOPIC_FIELD_NAME) != null ? Topic.valueOf(getString(TOPIC_FIELD_NAME)) : Topic.None;
    }

    // </editor-fold>
    public static MessageBuilder id(String id) {
        return new MessageBuilder(id, null, null, null, null);
    }

    public static MessageBuilder content(String content) {
        return new MessageBuilder(null, content, null, null, null);
    }

    @JsonCreator
    public Message(@JsonProperty(ID_FIELD_NAME) final String id,
                   @JsonProperty(CONTENT_FIELD_NAME) final String content,
                   @JsonProperty(TYPE_FIELD_NAME) final String messageType,
                   @JsonProperty(CATEGORY_FIELD_NAME) final String messageCategory,
                   @JsonProperty(TOPIC_FIELD_NAME) final String messageTopic
    ) {

        put(ID_FIELD_NAME, id != null && id.isEmpty() || id == null ? String.valueOf(UUID.randomUUID()) : id);
        put(CONTENT_FIELD_NAME, content);
        put(TYPE_FIELD_NAME, messageType);
        put(TOPIC_FIELD_NAME, messageTopic);
        put(CATEGORY_FIELD_NAME, messageCategory);

    }

    public Message(DBObject object) {
        super((Map) object);
    }

    public enum Type {
        Unknown,
        SingleSms
    }

    public enum Category {
        None,
        Relationships,
        Health,
        Wealth,
        Career,
        Leadership
    }

    public enum Topic {
        None,
        Personal,
        Family,
        Eating,
        Exercise,
        Spiritual,
        Job,
        Success
    }
}
