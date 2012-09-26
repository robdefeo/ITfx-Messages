package ITfx.Messages;

public class PhaseMessageBuilder {
    private final Integer order;
    private final String messageId;

    PhaseMessageBuilder(Integer order, String messageId) {
        this.order = order;
        this.messageId = messageId;
    }

    public PhaseMessageBuilder order(Integer order) {
        return new PhaseMessageBuilder(order, messageId);
    }

    public PhaseMessageBuilder messageId(String messageId) {
        return new PhaseMessageBuilder(order, messageId);
    }

    public PhaseMessage build() {
        return new PhaseMessage(messageId, order);
    }
}
