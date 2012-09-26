package ITfx.Messages;

import com.google.common.collect.ImmutableCollection;

public final class PhaseBuilder {
    final String id;
    final String name;
    final Integer order;
    final ImmutableCollection<PhaseMessage> phaseMessages;
    final Boolean isPhaseOrdered;


    public Phase build() {
        return new Phase(id,
                name,
                order,
                phaseMessages == null ? null : phaseMessages.asList(),
                isPhaseOrdered);
    }

    PhaseBuilder(String id, String name, Integer order, ImmutableCollection<PhaseMessage> phaseMessages, Boolean isPhaseOrdered) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.phaseMessages = phaseMessages;
        this.isPhaseOrdered = isPhaseOrdered;
    }

    public PhaseBuilder id(String id) {
        return new PhaseBuilder(id, name, order, phaseMessages, isPhaseOrdered);
    }

    public PhaseBuilder name(String name) {
        return new PhaseBuilder(id, name, order, phaseMessages, isPhaseOrdered);
    }

    public PhaseBuilder order(int order) {
        return new PhaseBuilder(id, name, order, phaseMessages, isPhaseOrdered);
    }
    public PhaseBuilder phaseMessages(ImmutableCollection<PhaseMessage> phaseMessages) {
        return new PhaseBuilder(id, name, order, phaseMessages, isPhaseOrdered);
    }

}
