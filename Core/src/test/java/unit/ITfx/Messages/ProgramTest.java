package unit.ITfx.Messages;

import ITfx.Messages.Phase;
import ITfx.Messages.PhaseMessage;
import ITfx.Messages.Program;
import com.google.common.collect.ImmutableList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProgramTest {
    @Test
    public void constructorFromJson() throws IOException {
        String json = "{" +
                "\"_id\":4009," +
                "\"name\":\"a program of messages\"," +
                "\"phases\":null}";
        Program target = new Program((DBObject) JSON.parse(json));

        assertNotNull(target);
        assertEquals("4009", target.get_id());
        assertEquals("a program of messages", target.getName());

    }

    @Test
    public void constructorFromJsonWithPhases() throws IOException {
        String json = "{" +
                "\"_id\":\"4009\"," +
                "\"name\":\"a program of messages\"," +
                "\"phases\":[" +
                "{\"name\":\"Phase 1\"," +
                "\"order\":1," +
                "\"messages\":null," +
                "\"deliveries\":null}," +
                "{\"name\":\"Phase 1\"," +
                "\"order\":1," +
                "\"messages\":null," +
                "\"deliveries\":null}" +
                "]}";
        Program target = new Program((DBObject) JSON.parse(json));

        assertNotNull(target);
        assertEquals("4009", target.get_id());
        assertEquals("a program of messages", target.getName());
        assertNotNull(target.getPhases());
        assertEquals(2, target.getPhases().size());
    }

    @Test
    public void addPhaseNoExisting() {
        Program target = new Program("100", "Name", null);
        Program actual = target.addPhase(Phase
                .id("id")
                .name("name")
                .order(3)
                .build());

        assertNotNull(actual);
        assertNotNull(actual.getPhases());
        assertEquals(1, actual.getPhases().size());
    }

    @Test
    public void addPhasePreExisting() {
        Program target = new Program("100", "Name", ImmutableList.of(Phase
                .id("id")
                .name("existing")
                .order(3)
                .build()));
        Program actual = target.addPhase(Phase
                .id("id")
                .name("name")
                .order(3)
                .build());

        assertNotNull(actual);
        assertNotNull(actual.getPhases());
        assertEquals(2, actual.getPhases().size());
    }

    @Test
    public void addPhaseMessageNoExisting() {
        Program target = Program
                .name("name")
                .phases(ImmutableList.of(Phase
                        .id("phaseId")
                        .name("existing")
                        .order(3)
                        .build()))
                .build();

        Program actual = target.addPhaseMessage("phaseId", new PhaseMessage("newPhaseMessageId", 2));

        assertNotNull(actual);
        assertNotNull(actual.getPhases());
        assertEquals(1, actual.getPhases().size());

        assertNotNull(actual.getPhases().get(0).getPhaseMessages());
        assertEquals(1, actual.getPhases().get(0).getPhaseMessages().size());
        assertEquals("newPhaseMessageId", actual.getPhases().get(0).getPhaseMessages().asList().get(0).getMessageId());
    }

}
