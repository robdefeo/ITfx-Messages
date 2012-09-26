package ITfx.Messages.Services;

import ITfx.Messages.DataAccess.IMessageRepository;
import ITfx.Messages.DataAccess.IUserRepository;
import ITfx.Messages.DataAccess.MessageRepository;
import ITfx.Messages.DataAccess.UserRepository;
import ITfx.Messages.Delivery;
import ITfx.Messages.User;
import ITfx.Messages.UserProgram;
import com.google.common.collect.ImmutableList;
import com.mongodb.BasicDBObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;

@Path("/user")
public class UserService {
    public static String USER_NOT_FOUND_ERROR = "user not found";
    public static String DELIVERY_NOT_INSERTED_ERROR = "delivery not inserted";
    public static String USER_PROGRAM_NOT_INSERTED_ERROR = "delivery not inserted";
    public static String MESSAGE_NOT_FOUND_ERROR = "message not found";
    public static String USER_UPDATE_ERROR = "user update error";

    private static final Log log = LogFactory.getLog(UserService.class);
    final private IUserRepository userRepository;
    final private IMessageRepository messageRepository;

    public UserService() throws UnknownHostException {
        userRepository = new UserRepository();
        messageRepository = new MessageRepository();
    }

    public UserService(IUserRepository userRepository, IMessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(User item) {
        System.out.println(String.format("Json to update %s", item));
        final BasicDBObject insertedItem = userRepository.save(item);
        if (insertedItem == null ){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(USER_UPDATE_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        else {
            return Response
                    .status(Response.Status.OK)
                    .entity(insertedItem)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(User item) {
        final BasicDBObject insertedItem = userRepository.insert(item);
        if (insertedItem == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("null item")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(insertedItem)
                    .build();
        }
    }

    @GET
    @Path("enabledDeliveries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnabledDelivery() {
        final ImmutableList<User> list = userRepository.getEnabledUsers();
        return Response.status(Response.Status.OK)
                .entity(list)
                .build();
    }

    @GET
    @Path("deliveryQueue/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeliveryQueue(@PathParam("limit") int limit) {
        final ImmutableList<User> list = userRepository.getDeliveryQueue(limit);
        return Response.status(Response.Status.OK)
                .entity(list)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        final BasicDBObject user = userRepository.getById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(user)
                    .build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/programs")
    public Response insertProgram(@PathParam("id") String userId, UserProgram program) {
        final BasicDBObject user = userRepository.getById(userId);
        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(USER_NOT_FOUND_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        final UserProgram insertedProgram = userRepository.addProgram(new User(user), program);

        if (insertedProgram == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(USER_PROGRAM_NOT_INSERTED_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(insertedProgram)
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/deliveries")
    public Response insertDelivery(@PathParam("id") String userId, Delivery delivery) {
        final BasicDBObject user = userRepository.getById(userId);
        if (user == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(USER_NOT_FOUND_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        if (messageRepository.getById(delivery.getMessageId()) == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(MESSAGE_NOT_FOUND_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        final Delivery insertedItem = userRepository.addDelivery(new User(user), delivery);

        if (insertedItem == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(DELIVERY_NOT_INSERTED_ERROR)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(insertedItem)
                    .build();
        }
    }

}
