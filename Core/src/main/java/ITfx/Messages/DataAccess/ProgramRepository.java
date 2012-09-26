package ITfx.Messages.DataAccess;

import ITfx.Messages.Phase;
import ITfx.Messages.PhaseMessage;
import ITfx.Messages.Program;

import java.net.UnknownHostException;

public final class ProgramRepository extends Repository implements IProgramRepository {
    public ProgramRepository() throws UnknownHostException {
        super(Program.class);
    }

    @Override
    public Phase addPhase(Program program, Phase phase) {
        this.dbCollection.save(program.addPhase(phase));
        return phase;
    }

    @Override
    public PhaseMessage addPhaseMessage(final Program program, final Phase phase, final PhaseMessage phaseMessage) {
        this.dbCollection.save(program.addPhaseMessage(phase.get_id(), phaseMessage));
        return phaseMessage;
    }

}
