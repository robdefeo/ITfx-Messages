package ITfx.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.mongodb.DBObject;

import java.util.List;
import java.util.Map;
import java.util.UUID;

final public class Phase extends MessagesDBObject {
    final static String NAME_FIELD_NAME = "name";
    final static String ORDER_FIELD_NAME = "order";
    final static String PHASE_MESSAGES_FIELD_NAME = "phaseMessages";
    final static String IS_PHASE_ORDERED_FIELD_NAME = "isPhaseOrdered";

    public static PhaseBuilder id(String id) {
        return new PhaseBuilder(id, null, null, null, null );
    }
    public static PhaseBuilder name(String name) {
        return new PhaseBuilder(null, name, null, null, null );
    }

    public String get_id()
    {
        return getString(ID_FIELD_NAME);
    }
    public Boolean getIsPhaseOrdered() {
        return (Boolean) get(IS_PHASE_ORDERED_FIELD_NAME);
    }

    public String getName() {
        return getString(NAME_FIELD_NAME);
    }

    public Integer getOrder() {
        return getInt(ORDER_FIELD_NAME);
    }

    public ImmutableCollection<PhaseMessage> getPhaseMessages() {
        return get(PHASE_MESSAGES_FIELD_NAME) != null
                ? ImmutableList.copyOf((List<PhaseMessage>) get(PHASE_MESSAGES_FIELD_NAME))
                : ImmutableList.<PhaseMessage>of();
    }

    @JsonCreator
    public Phase(
            @JsonProperty(ID_FIELD_NAME) final String id,
            @JsonProperty(NAME_FIELD_NAME) final String name,
            @JsonProperty(ORDER_FIELD_NAME) final Integer order,
            @JsonProperty(PHASE_MESSAGES_FIELD_NAME) final List<PhaseMessage> phaseMessages,
            @JsonProperty(IS_PHASE_ORDERED_FIELD_NAME) final Boolean isPhaseOrdered
    ) {

        put(ID_FIELD_NAME, id != null && id.isEmpty() || id == null ? String.valueOf(UUID.randomUUID()) : id);
        put(NAME_FIELD_NAME, name);
        put(ORDER_FIELD_NAME, order);
        put(PHASE_MESSAGES_FIELD_NAME, phaseMessages);
        put(IS_PHASE_ORDERED_FIELD_NAME, isPhaseOrdered);

    }

    public Phase(DBObject object) {
        super((Map) object);
    }

    public Phase addPhaseMessage(PhaseMessage phaseMessage) {
        return new Phase(
                this.get_id(),
                this.getName(),
                this.getOrder(),
                ImmutableList.<PhaseMessage>builder()
                        .addAll(this.getPhaseMessages())
                        .add(phaseMessage)
                        .build(),
                this.getIsPhaseOrdered()
        );
    }


}
