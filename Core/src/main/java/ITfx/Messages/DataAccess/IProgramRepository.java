package ITfx.Messages.DataAccess;

import ITfx.Messages.PhaseMessage;
import ITfx.Messages.Phase;
import ITfx.Messages.Program;

public interface IProgramRepository extends IRepository {
    Phase addPhase(Program program, Phase phase);

    PhaseMessage addPhaseMessage(Program program, Phase phase, PhaseMessage phaseMessage);
}
