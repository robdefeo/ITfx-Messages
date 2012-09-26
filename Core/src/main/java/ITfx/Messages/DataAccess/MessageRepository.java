package ITfx.Messages.DataAccess;

import ITfx.Messages.Message;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.UnknownHostException;
import java.util.List;

public final class MessageRepository extends Repository implements IMessageRepository{

    public MessageRepository() throws UnknownHostException {
        super(Message.class);
    }

    public List<Message> getMessageByPhase(String phaseId) {
        throw new NotImplementedException();
        //new Query(Criteria.where("phaseId"). )
    }


}
