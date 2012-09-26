package unit.ITfx.Messages;

import ITfx.Messages.PhaseMessage;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PhaseMessageTest {
    @Test
    public void constructorFromJson() throws IOException {
        String json = "{\"messageId\":\"003f5f5e-d7c2-47e7-aad3-55c050916a4e\"," +
                "\"order\":2," +
                "};";
        PhaseMessage target = new PhaseMessage((DBObject) JSON.parse(json));
        assertNotNull(target);
        assertEquals("003f5f5e-d7c2-47e7-aad3-55c050916a4e", target.getMessageId());
        assertEquals((Integer)2, target.getOrder());

    }
}
