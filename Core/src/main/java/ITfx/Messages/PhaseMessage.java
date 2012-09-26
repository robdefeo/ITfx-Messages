package ITfx.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Comparator;
import java.util.Map;

public final class PhaseMessage extends BasicDBObject {
    final static String MESSAGE_ID_FIELD_NAME = "messageId";
    final static String ORDER_FIELD_NAME = "order";

    public static PhaseMessageBuilder messageId(String messageId) {
        return new PhaseMessageBuilder(null, messageId);
    }

    public static PhaseMessageBuilder order(Integer order) {
        return new PhaseMessageBuilder(order, null);
    }

    public final String getMessageId() {
        return getString(MESSAGE_ID_FIELD_NAME);
    }

    public final Integer getOrder() {
        return getInt(ORDER_FIELD_NAME, 0);
    }

    @JsonCreator
    public PhaseMessage(
            @JsonProperty(MESSAGE_ID_FIELD_NAME) final String messageId,
            @JsonProperty(ORDER_FIELD_NAME) final Integer order) {
        put(MESSAGE_ID_FIELD_NAME, messageId);
        put(ORDER_FIELD_NAME, order);
    }

    public PhaseMessage(DBObject object) {
        super((Map) object);
    }

    public static Comparator<PhaseMessage> orderComparator() {
        return new Comparator<PhaseMessage>() {
            @Override
            public int compare(PhaseMessage phaseMessage, PhaseMessage phaseMessage1) {
                return phaseMessage.getOrder() - phaseMessage1.getOrder();
            }
        };
    }
}