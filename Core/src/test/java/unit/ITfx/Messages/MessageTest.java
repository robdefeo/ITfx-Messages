package unit.ITfx.Messages;

import ITfx.Messages.Message;
import ITfx.Messages.Message.Type;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTest {
    @Test
    public void constructorSpecifiedIdTest() {
        Message target = Message
                .id("id")
                .content("content")
                .type(Type.SingleSms)
                .topic(Message.Topic.Exercise)
                .category(Message.Category.Health)
                .build();
        assertEquals("id", target.get_id());
        assertEquals("content", target.getContent());
        assertEquals(Type.SingleSms, target.getType());
        assertEquals(Message.Topic.Exercise, target.getTopic());
        assertEquals(Message.Category.Health, target.getCategory());
    }

    @Test
    public void constructorNullIdSpecifiedTest() {
        Message target = Message
                .content("content")
                .type(Type.SingleSms)
                .build();
        assertNotNull(target.get_id());
        assertFalse(target.get_id().isEmpty());

        assertEquals(target.getContent(), "content");
        assertEquals(target.getType(), Type.SingleSms);
    }

    @Test
    public void constructorEmptyIdSpecifiedTest() {
        Message target = Message
                .id("")
                .content("content")
                .type(Type.SingleSms)
                .build();
        assertNotNull(target.get_id());
        assertFalse(target.get_id().isEmpty());

        assertEquals(target.getContent(), "content");
        assertEquals(target.getType(), Type.SingleSms);
    }

    @Test
    public void constructorFromJson() {
        String json = "{\"_id\":\"99d5d85f-969e-4562-ba0c-2bcc36b5205d\",\"content\":\"things are not as they seam\",\"type\":\"SingleSms\"};";
        Message target = new Message((DBObject) JSON.parse(json));
        assertNotNull(target);
        assertEquals("99d5d85f-969e-4562-ba0c-2bcc36b5205d", target.get_id());
        assertEquals("things are not as they seam", target.getContent());
        assertEquals("SingleSms", target.getType().toString());
        assertEquals(Message.Category.None, target.getCategory());
        assertEquals(Message.Topic.None, target.getTopic());
    }
}
