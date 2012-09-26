package unit.Messages.Services;

import ITfx.Messages.DataAccess.IMessageRepository;
import ITfx.Messages.DataAccess.IUserRepository;
import ITfx.Messages.Delivery;
import ITfx.Messages.Message;
import ITfx.Messages.Services.UserService;
import ITfx.Messages.User;
import ITfx.Messages.UserProgram;
import org.joda.time.DateTime;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Test
    public void getUserNotFoundTest() {
        String id = "id";
        IUserRepository repository = mock(IUserRepository.class);
        when(repository.getById(id)).thenReturn(null);

        UserService target = new UserService(repository, mock(IMessageRepository.class));
        Response actual = target.get(id);
        assertNotNull(actual);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), actual.getStatus());

        User user = (User) actual.getEntity();

        assertNull(user);
    }

    @Test
    public void getUserTest() {
        String id = "id";
        IUserRepository repository = mock(IUserRepository.class);
        when(repository.getById(id)).thenReturn(User.id(id)
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());

        UserService target = new UserService(repository, mock(IMessageRepository.class));
        Response response = target.get(id);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response);

        User program = (User) response.getEntity();

        assertEquals("id", program.get_id());
    }

    @Test
    public void insertUserTest() {
        IUserRepository repository = mock(IUserRepository.class);
        when(repository.insert(any(User.class))).thenReturn(User.id("id")
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());

        UserService target = new UserService(repository, mock(IMessageRepository.class));

        Response response = target.insert(User.id("id")
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response);

        User user = (User) response.getEntity();

        assertEquals("id", user.get_id());
    }

    @Test
    public void insertUserFailedTest() {
        IUserRepository repository = mock(IUserRepository.class);
        when(repository.insert(any(User.class))).thenReturn(null);

        UserService target = new UserService(repository, mock(IMessageRepository.class));

        Response response = target.insert(User.id("id")
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertNotNull(response);
    }

    @Test
    public void insertProgramNoUserFound() {
        IUserRepository repository = mock(IUserRepository.class);
        when(repository.getById("userId")).thenReturn(null);

        UserService target = new UserService(repository, mock(IMessageRepository.class));
        Response response = target.insertProgram("userId", new UserProgram("programId", "currentPhaseId"));
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(UserService.USER_NOT_FOUND_ERROR, response.getEntity());
    }

    @Test
    public void insertProgramInsertFailed() {
        IUserRepository repository = mock(IUserRepository.class);
        when(repository.getById("userId")).thenReturn(User.id("userId")
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());

        when(repository.addProgram(any(User.class), any(UserProgram.class))).thenReturn(null);

        UserService target = new UserService(repository, mock(IMessageRepository.class));
        Response response = target.insertProgram("userId", new UserProgram("programId", "currentPhaseId"));
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(UserService.USER_PROGRAM_NOT_INSERTED_ERROR, response.getEntity());
    }

    @Test
    public void insertProgram() {
        IUserRepository repository = mock(IUserRepository.class);
        when(repository.getById("userId")).thenReturn(User.id("userId")
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());
        when(repository.addProgram(any(User.class), any(UserProgram.class))).thenReturn(new UserProgram("programId", "currentPhaseId"));

        UserService target = new UserService(repository, mock(IMessageRepository.class));
        Response response = target.insertProgram("userId", new UserProgram("programId", "currentPhaseId"));
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());


    }

    @Test
    public void insertDeliveryNoUserFound() {
        IUserRepository repository = mock(IUserRepository.class);
        when(repository.getById("userId")).thenReturn(null);

        UserService target = new UserService(repository, mock(IMessageRepository.class));
        Response response = target.insertDelivery("userId", Delivery.date(DateTime.now()).messageId("messageId").deliveryType(Delivery.DeliveryType.Email).build());
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(UserService.USER_NOT_FOUND_ERROR, response.getEntity());
    }

    @Test
    public void insertDeliveryNoMessageFound() {
        IUserRepository repository = mock(IUserRepository.class);
        IMessageRepository messageRepository = mock(IMessageRepository.class);
        when(repository.getById("userId")).thenReturn(User.id("userId")
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());
        when(messageRepository.getById("messageId")).thenReturn(null);
        when(repository.addDelivery(any(User.class), any(Delivery.class))).thenReturn(null);

        UserService target = new UserService(repository, messageRepository);
        Response response = target.insertDelivery("userId", Delivery.date(DateTime.now()).messageId("messageId").deliveryType(Delivery.DeliveryType.Email).build());
        assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(UserService.MESSAGE_NOT_FOUND_ERROR, response.getEntity());
    }

    @Test
    public void insertDeliveryInsertFailed() {
        IUserRepository repository = mock(IUserRepository.class);
        IMessageRepository messageRepository = mock(IMessageRepository.class);
        when(repository.getById("userId")).thenReturn(User.id("userId")
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());

        when(repository.addDelivery(any(User.class), any(Delivery.class))).thenReturn(null);
        when(messageRepository.getById("messageId")).thenReturn(Message.id("messageId").content("content").build());

        UserService target = new UserService(repository, messageRepository);
        Response response = target.insertDelivery("userId", Delivery.date(DateTime.now()).messageId("messageId").deliveryType(Delivery.DeliveryType.Email).build());
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals(UserService.DELIVERY_NOT_INSERTED_ERROR, response.getEntity());
    }

    @Test
    public void insertDelivery() {
        IUserRepository repository = mock(IUserRepository.class);
        IMessageRepository messageRepository = mock(IMessageRepository.class);
        when(repository.getById("userId")).thenReturn(User.id("userId")
                .firstName("Mike")
                .deliveryAddress("Mike@test.com")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .build());
        when(repository.addDelivery(any(User.class), any(Delivery.class))).thenReturn(Delivery.date(DateTime.now()).messageId("messageId").deliveryType(Delivery.DeliveryType.Email).build());
        when(messageRepository.getById("messageId")).thenReturn(Message.id("messageId").content("content").build());

        UserService target = new UserService(repository, messageRepository);
        Response response = target.insertDelivery("userId", Delivery.date(DateTime.now()).messageId("messageId").deliveryType(Delivery.DeliveryType.Email).build());
        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());


    }
}
