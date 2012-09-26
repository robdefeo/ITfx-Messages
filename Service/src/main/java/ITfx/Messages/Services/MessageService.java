package ITfx.Messages.Services;

import ITfx.Messages.DataAccess.MessageRepository;
import ITfx.Messages.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;

@Path("/message")
public class MessageService {
    private static final Log log = LogFactory.getLog(MessageService.class);
    final private MessageRepository messageRepository;

    public MessageService() throws UnknownHostException {
        messageRepository = new MessageRepository();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String List(@QueryParam("messageType") int messageType) {

        throw new NotImplementedException();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response Get(@PathParam("id") String id) {
    return Response
            .status(Response.Status.OK)
            .entity(new Message(messageRepository.getById(id)))
            .build();
}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Insert(Message messageJson) {
        System.out.println(messageJson);
        //return new Message(messageRepository.insert(messageJson));
        return Response
                .status(Response.Status.OK)
                .entity(new Message(messageRepository.insert(messageJson)))
                .build();
    }

}
