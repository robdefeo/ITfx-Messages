package unit.Messages.Services;

import ITfx.Messages.DataAccess.IProgramRepository;
import ITfx.Messages.Phase;
import ITfx.Messages.PhaseMessage;
import ITfx.Messages.Program;
import ITfx.Messages.Services.ProgramService;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProgramServiceTest {

    @Test
    public void getProgramNotFoundTest() {
        String id = "id";
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.getById(id)).thenReturn(null);

        ProgramService target = new ProgramService(repository);
        Response actual = target.get(id);
        assertNotNull(actual);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), actual.getStatus());

        Program program = (Program) actual.getEntity();

        assertNull(program);
    }

    @Test
    public void getProgramTest() {
        String id = "id";
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.getById(id)).thenReturn(new Program("id", "test", null));

        ProgramService target = new ProgramService(repository);
        Response response = target.get(id);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response);

        Program program = (Program) response.getEntity();

        assertEquals("id", program.get_id());
    }

    @Test
    public void insertProgramTest() {
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.insert(any(Program.class))).thenReturn(new Program("id", "Test", null));

        ProgramService target = new ProgramService(repository);

        Response response = target.insert(new Program(null, "test", null));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response);

        Program program = (Program) response.getEntity();

        assertEquals("id", program.get_id());
    }

    @Test
    public void insertProgramFailedTest() {
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.insert(any(Program.class))).thenReturn(null);

        ProgramService target = new ProgramService(repository);

        Response response = target.insert(new Program(null, "test", null));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertNotNull(response);

    }

    @Test
    public void insertPhaseNoProgramFound() {
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.getById("programId")).thenReturn(null);

        ProgramService target = new ProgramService(repository);
        Response response = target.insertPhase("programId", Phase.name("name").order(0).build());
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

        assertEquals(ProgramService.PROGRAM_NOT_FOUND_ERROR, response.getEntity());
    }

    @Test
    public void insertPhaseInsertFailed() {
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.getById("programId")).thenReturn(new Program(null, "program", null));
        when(repository.addPhase(any(Program.class), any(Phase.class))).thenReturn(null);

        ProgramService target = new ProgramService(repository);
        Response response = target.insertPhase("programId", Phase.name("name").order(0).build());
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void insertPhase() {
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.getById("programId")).thenReturn(new Program(null, "program", null));
        when(repository.addPhase(any(Program.class), any(Phase.class))).thenReturn(Phase.name("name").order(0).build());

        ProgramService target = new ProgramService(repository);
        Response response = target.insertPhase("programId", Phase.name("name").order(0).build());
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());


    }

    @Test
    public void insertPhaseMessageNoProgram() {
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.getById("programId")).thenReturn(null);

        ProgramService target = new ProgramService(repository);
        Response response = target.insertPhaseMessage("noId", "phaseId", new PhaseMessage("messageId", 2));
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(ProgramService.PROGRAM_NOT_FOUND_ERROR, response.getEntity());
    }

    @Test
    public void insertPhaseMessageNoPhase() {
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.getById("programId")).thenReturn(new Program("programId", "program name", null));

        ProgramService target = new ProgramService(repository);
        Response response = target.insertPhaseMessage("programId", "phaseId", new PhaseMessage("messageId", 2));
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(ProgramService.PHASE_NOT_FOUND_ERROR, response.getEntity());
    }

    @Test
    public void insertPhaseMessage() {
        IProgramRepository repository = mock(IProgramRepository.class);
        when(repository.getById("programId")).thenReturn(
                new Program("programId", "program name", null)
                        .addPhase(Phase.id("phaseId").name("Phase name").order(2).build()
                        ));

        ProgramService target = new ProgramService(repository);
        Response response = target.insertPhaseMessage("programId", "phaseId", new PhaseMessage("messageId", 2));
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

}
