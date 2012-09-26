package integration.Messages.Services;

import ITfx.Messages.Delivery;
import ITfx.Messages.User;
import ITfx.Messages.UserProgram;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.joda.time.DateTime;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    protected final String baseEndPoint = ResourceBundle.getBundle("stories-context").getString("baseEndPoint");

    private UserProgram addProgram(String userId) {
        final WebResource insertItemResource = Client.create().resource(URI.create(this.baseEndPoint + "user/" + userId + "/programs"));
        final ClientResponse insertItemResponse = insertItemResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, JSON.serialize(new UserProgram(UUID.randomUUID().toString(), UUID.randomUUID().toString())));
        assertNotNull(insertItemResponse);
        assertEquals(200, insertItemResponse.getStatus());

        final UserProgram insertedItem = new UserProgram((DBObject) JSON.parse(insertItemResponse.getEntity(String.class)));
        assertNotNull(insertedItem);

        return insertedItem;
    }

    private User addUser() {
        final WebResource insertItemResource = Client.create().resource(URI.create(this.baseEndPoint + "user/"));
        final ClientResponse insertItemResponse = insertItemResource
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class,
                        User.id(null)
                                .deliveryAddress("test@test.com")
                                .deliveryType(Delivery.DeliveryType.SingleSms)
                                .firstName("Mark")
                                .deliveryEnabled(Boolean.TRUE)
                                .build()
                                .jsonSerialize());


        assertNotNull(insertItemResponse);
        assertEquals(200, insertItemResponse.getStatus());

        final User insertedItem = new User((DBObject) JSON.parse(insertItemResponse.getEntity(String.class)));
        assertNotNull(insertedItem);

        return insertedItem;
    }

    private void updateUser(User user) {
        final WebResource updateItemResource = Client.create().resource(URI.create(this.baseEndPoint + "user/"));
        updateItemResource
                .type(MediaType.APPLICATION_JSON)
                .put(user.jsonSerialize());
    }

    private User getUser(String userId) {
        final WebResource getProgramResource = Client.create().resource(URI.create(this.baseEndPoint + "user/" + userId));
        final ClientResponse getProgramResponse = getProgramResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertNotNull(getProgramResponse);
        assertEquals(200, getProgramResponse.getStatus());

        final User gottenUser = new User((DBObject) JSON.parse(getProgramResponse.getEntity(String.class)));
        assertNotNull(gottenUser);

        return gottenUser;
    }

    private List<User> getEnabledDeliveries() {
        final WebResource getProgramResource = Client.create().resource(URI.create(this.baseEndPoint + "user/enabledDeliveries"));
        final ClientResponse getProgramResponse = getProgramResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertNotNull(getProgramResponse);
        assertEquals(200, getProgramResponse.getStatus());


        final List<User> gottenUsers = (List<User>) JSON.parse(getProgramResponse.getEntity(String.class));
        assertNotNull(gottenUsers);

        assertFalse(0 == gottenUsers.size());

        return gottenUsers;
    }

    private List<User> getDeliveryQueue(int limit) {
        final WebResource getProgramResource = Client.create().resource(URI.create(this.baseEndPoint + "user/deliveryQueue/" + limit));
        final ClientResponse getProgramResponse = getProgramResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertNotNull(getProgramResponse);
        assertEquals(200, getProgramResponse.getStatus());


        final List<User> gottenUsers = (List<User>) JSON.parse(getProgramResponse.getEntity(String.class));
        assertNotNull(gottenUsers);

        assertEquals(limit, gottenUsers.size());

        return gottenUsers;
    }

    @Test
    public void getDeliveryQueueTest() {
        final List<User> originalList = getDeliveryQueue(3);

        final List<User> newList = getDeliveryQueue(2);

        assertEquals(originalList.get(0).get_id(), newList.get(0).get_id());
        assertEquals(originalList.get(1).get_id(), newList.get(1).get_id());
        assertEquals(originalList.get(2).get_id(), newList.get(2).get_id());

    }

    @Test
    public void getDeliveryQueueThenRemoveItemTest() {
        final List<User> originalList = getDeliveryQueue(2);
        updateUser(originalList.get(0).setNextScheduledDeliveryDate(DateTime.now()));

        final List<User> newList = getDeliveryQueue(2);

        assertNotSame(originalList.get(0).get_id(), newList.get(0).get_id());
    }

    @Test
    public void getEnabledUsersTest() {
        final List<User> enabledUsers = getEnabledDeliveries();

    }

    @Test
    public void updateUser() {
        final User insertedUser = addUser();

        assertNotNull(insertedUser);
        assertTrue(insertedUser.getDeliveryEnabled());

        updateUser(insertedUser.setDeliveryEnabled(false));

        final User updateUser = getUser(insertedUser.get_id());
        assertNotNull(updateUser);
        assertFalse(updateUser.getDeliveryEnabled());
    }

    @Test
    public void addUserTest() {
        final User insertedUser = addUser();

        assertNotNull(insertedUser);
    }

    @Test
    public void getUserTest() {
        final User insertedUser = addUser();
        final User user = getUser(insertedUser.get_id());

        assertNotNull(user);
        assertEquals(insertedUser.get_id(), user.get_id());
    }

    @Test
    public void addMultipleProgramToUserTest() {
        final User insertedUser = addUser();

        final UserProgram insertedFirstProgram = addProgram(insertedUser.get_id());
        //final int previousProgramCount = (insertedUser.getUserPrograms() == null ? 0 : insertedUser.getUserPrograms().size());

        final User gottenFirstUser = getUser(insertedUser.get_id());
        assertNotNull(gottenFirstUser.getUserPrograms());
        assertEquals(1, gottenFirstUser.getUserPrograms().size());

        final UserProgram insertedSecondProgram = addProgram(insertedUser.get_id());
        //final int previousProgramCount = (insertedUser.getUserPrograms() == null ? 0 : insertedUser.getUserPrograms().size());

        final User gottenSecondUser = getUser(insertedUser.get_id());
        assertNotNull(gottenSecondUser.getUserPrograms());
        assertEquals(2, gottenSecondUser.getUserPrograms().size());

    }
}
