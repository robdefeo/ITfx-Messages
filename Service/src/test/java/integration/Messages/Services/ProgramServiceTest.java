package integration.Messages.Services;

import ITfx.Messages.Message;
import ITfx.Messages.Phase;
import ITfx.Messages.PhaseMessage;
import ITfx.Messages.Program;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ResourceBundle;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProgramServiceTest {
    protected final String baseEndPoint = ResourceBundle.getBundle("stories-context").getString("baseEndPoint");

    private Program addProgram() {
        final WebResource insertResource = Client.create().resource(URI.create(this.baseEndPoint + "program/"));
        final ClientResponse insertResponse = insertResource
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, JSON.serialize(new Program(UUID.randomUUID().toString(), "integration test program", null)));

        assertNotNull(insertResponse);
        assertEquals(200, insertResponse.getStatus());

        final Program insertedProgram = new Program((DBObject) JSON.parse(insertResponse.getEntity(String.class)));
        assertNotNull(insertedProgram);

        return insertedProgram;
    }

    private Program getProgram(String programId) {
        final WebResource getProgramResource = Client.create().resource(URI.create(this.baseEndPoint + "program/" + programId));
        final ClientResponse getProgramResponse = getProgramResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertNotNull(getProgramResponse);
        assertEquals(200, getProgramResponse.getStatus());

        final Program gottenProgram = new Program((DBObject) JSON.parse(getProgramResponse.getEntity(String.class)));
        assertNotNull(gottenProgram);

        return gottenProgram;
    }

    private Phase addPhase(String programId) {
        final WebResource insertPhaseResource = Client.create().resource(URI.create(this.baseEndPoint + "program/" + programId + "/phases"));
        final ClientResponse insertPhaseResponse = insertPhaseResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, JSON.serialize(Phase.name("integration test phase").order(0).build()));
        assertNotNull(insertPhaseResponse);
        assertEquals(200, insertPhaseResponse.getStatus());

        final Phase insertedPhase = new Phase((DBObject) JSON.parse(insertPhaseResponse.getEntity(String.class)));
        assertNotNull(insertedPhase);

        return insertedPhase;
    }

    private PhaseMessage addPhaseMessage(String programId, String phaseId) {
        final Message message = MessageServiceTest.addMessage();

        final WebResource insertPhaseMessageResource = Client.create().resource(URI.create(this.baseEndPoint + "program/" + programId + "/phases/" + phaseId + "/messages"));
        final ClientResponse insertPhaseResponse = insertPhaseMessageResource
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class,
                        JSON.serialize(new PhaseMessage(message.get_id(), 2)));

        assertNotNull(insertPhaseResponse);
        String responseString = insertPhaseResponse.getEntity(String.class);

        assertEquals("should not be " + responseString, 200, insertPhaseResponse.getStatus());

        final PhaseMessage insertedPhaseMessage = new PhaseMessage((DBObject) JSON.parse(responseString));
        assertNotNull(insertedPhaseMessage);

        return insertedPhaseMessage;
    }
    @Test
    public void addProgramTest() {
        final Program insertedProgram = addProgram();

        assertNotNull(insertedProgram);
    }
    @Test
    public void getProgramTest()  {
        final Program insertedProgram = addProgram();
        final Program program = getProgram(insertedProgram.get_id());

        assertNotNull(program);
        assertEquals(insertedProgram.get_id(), program.get_id());
    }

    @Test
    public void addPhaseToProgramTest() {
        final Program insertedProgram = addProgram();

        final Phase insertedPhase = addPhase(insertedProgram.get_id());
        assertEquals("integration test phase", insertedPhase.getName());

        final int previousPhaseCount = (insertedProgram.getPhases() == null ? 0 : insertedProgram.getPhases().size());

        final Program gottenProgram = getProgram(insertedProgram.get_id());
        assertNotNull(gottenProgram.getPhases());
        assertEquals(previousPhaseCount + 1, gottenProgram.getPhases().size());
    }

    @Test
    public void addPhaseToProgramNoExistingProgram() {
        final WebResource insertPhaseResource = Client.create().resource(URI.create(this.baseEndPoint + "program/fakeProgramId/phases"));
        final ClientResponse insertPhaseResponse = insertPhaseResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, JSON.serialize(Phase.name("integration test phase").order(0).build()));
        assertNotNull(insertPhaseResponse);
        assertEquals(404, insertPhaseResponse.getStatus());
        assertEquals("program not found", insertPhaseResponse.getEntity(String.class));
    }

    @Test
    public void addMessageToPhase() {
        final Program insertedProgram = addProgram();
        final Phase insertedPhase = addPhase(insertedProgram.get_id());

        final PhaseMessage insertedPhaseMessage = addPhaseMessage(insertedProgram.get_id(), insertedPhase.get_id());

        final Program gottenProgram = getProgram(insertedProgram.get_id());
        assertEquals(1, gottenProgram.getPhases().get(0).getPhaseMessages().size());
    }
}
