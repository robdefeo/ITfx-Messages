package ITfx.Messages;

import com.google.common.collect.ImmutableList;

public class ProgramBuilder {
    private final String id;
    private final String name;
    private final ImmutableList<Phase> phases;

    ProgramBuilder(String id, String name, ImmutableList<Phase> phases) {
        this.id = id;
        this.name = name;
        this.phases = phases;
    }

    public Program build() {
        return new Program(id, name, phases);
    }

    public ProgramBuilder id(String id) {
        return new ProgramBuilder(id, name, phases);
    }

    public ProgramBuilder name(String name) {
        return new ProgramBuilder(id, name, phases);

    }

    public ProgramBuilder phases(ImmutableList<Phase> phases) {
        return new ProgramBuilder(id, name, phases);
    }


}
