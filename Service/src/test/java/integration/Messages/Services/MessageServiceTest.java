package integration.Messages.Services;

import ITfx.Messages.Message;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MessageServiceTest {
    protected static final String baseEndPoint = ResourceBundle.getBundle("stories-context").getString("baseEndPoint");

    public static Message addMessage() {
        final WebResource insertResource = Client.create().resource(URI.create(baseEndPoint + "message/"));
        final ClientResponse insertResponse = insertResource
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, JSON.serialize(Message.content("content").build()));

        assertNotNull(insertResponse);
        assertEquals(200, insertResponse.getStatus());

        final Message insertedMessage = new Message((DBObject) JSON.parse(insertResponse.getEntity(String.class)));
        assertNotNull(insertedMessage);

        return insertedMessage;
    }

    @Test
    public void addMessageTest() {

        final Message insertedMessage = addMessage();
        assertNotNull(insertedMessage);
        assertEquals("content", insertedMessage.getContent());
    }
}
