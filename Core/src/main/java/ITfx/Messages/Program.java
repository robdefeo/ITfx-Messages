package ITfx.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final public class Program extends MessagesDBObject {
    final static String NAME_FIELD_NAME = "name";
    final static String PHASES_FIELD_NAME = "phases";

    public static ProgramBuilder name(String name) {
        return new ProgramBuilder(null, name, null);
    }

    public String getName() {
        return getString(NAME_FIELD_NAME);
    }

    public static ProgramBuilder phases(ImmutableList<Phase> phases) {
        return new ProgramBuilder(null, null, phases);
    }

    public ImmutableList<Phase> getPhases() {
        if (get(PHASES_FIELD_NAME) != null) {
            final List<Phase> phases = new ArrayList<Phase>();
            for (BasicDBObject item : (List<BasicDBObject>) get(PHASES_FIELD_NAME)) {
                phases.add(new Phase(item));
            }
            return ImmutableList.copyOf(phases);
        } else {
            return ImmutableList.<Phase>of();
        }
    }

    @JsonCreator
    public Program(
            @JsonProperty(ID_FIELD_NAME) final String id,
            @JsonProperty(NAME_FIELD_NAME) final String name,
            @JsonProperty(PHASES_FIELD_NAME) final List<Phase> phases) {

        this.put(NAME_FIELD_NAME, name);
        this.put(PHASES_FIELD_NAME, phases);
        put(ID_FIELD_NAME, id != null && id.isEmpty() || id == null ? String.valueOf(UUID.randomUUID()) : id);
    }

    public Program(DBObject dbObject) {
        this.putAll(dbObject);
    }


    public Program addPhase(Phase phase) {
        return new Program(
                this.get_id(),
                this.getName(),
                ImmutableList.<Phase>builder()
                        .addAll(getPhases())
                        .add(phase)
                        .build()
        );
    }

    public Program addPhaseMessage(final String phaseId, final PhaseMessage phaseMessage) {

        final Phase existingPhase = Iterables.find(this.getPhases(), new Predicate<Phase>() {
            @Override
            public boolean apply(@Nullable Phase item) {
                return item.get_id().equals(phaseId);
            }
        }, null);

        final Phase phase = existingPhase.addPhaseMessage(phaseMessage);

        final Iterable<Phase> phases = Iterables.filter(this.getPhases(), new Predicate<Phase>() {
            @Override
            public boolean apply(@Nullable Phase item) {
                return !item.get_id().equals(phaseId);
            }
        });


        return new Program(
                this.get_id(),
                this.getName(),
                ImmutableList.<Phase>builder()
                        .add(phase)
                        .addAll(phases)
                        .build());
    }

    public static Predicate<Phase> getPhaseByIdPredicate(final String id) {
        return new Predicate<Phase>() {
            @Override
            public boolean apply(@Nullable Phase phase) {
                return phase.get_id() == id;
            }
        };
    }
}
