package unit.ITfx.Messages;

import ITfx.Messages.Phase;
import ITfx.Messages.PhaseMessage;
import com.google.common.collect.ImmutableList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PhaseTest {

    @Test
    public void constructorFromJson() throws IOException {
        String json = "{\"_id\":\"003f5f5e-d7c2-47e7-aad3-55c050916a4e\"," +
                "\"name\":\"unit test phase\"," +
                "\"order\":2," +
                "\"phaseMessages\":null," +
                "\"deliveries\":null};";
        Phase target = new Phase((DBObject) JSON.parse(json));
        assertNotNull(target);
        assertEquals("003f5f5e-d7c2-47e7-aad3-55c050916a4e", target.get_id());
        assertEquals("unit test phase", target.getName());

    }

    @Test
    public void constructorTest() {
        Phase target = new Phase("id", "name", 4, null, true);

        assertNotNull(target);
        assertEquals("id", target.get_id());
        assertEquals("name", target.getName());
    }

    @Test
    public void addPhaseMessageNoExisiting() {
        Phase target = Phase.id("100").name("Name").order(0).build();
        assertNotNull(target.getPhaseMessages());
        assertEquals(0, target.getPhaseMessages().size());

        Phase actual = target.addPhaseMessage(new PhaseMessage("messageId", 0));

        assertNotNull(actual);
        assertNotNull(actual.getPhaseMessages());
        assertEquals(1, actual.getPhaseMessages().size());
    }

    @Test
    public void addPhaseMessagePreExisting() {
        Phase target = Phase.id("100").name("Name").order(0).phaseMessages(ImmutableList.of(new PhaseMessage("id", 2))).build();

        assertNotNull(target.getPhaseMessages());
        assertEquals(1, target.getPhaseMessages().size());

        Phase actual = target.addPhaseMessage(new PhaseMessage("messageId", 0));

        assertNotNull(actual);
        assertNotNull(actual.getPhaseMessages());
        assertEquals(2, actual.getPhaseMessages().size());
    }
}
