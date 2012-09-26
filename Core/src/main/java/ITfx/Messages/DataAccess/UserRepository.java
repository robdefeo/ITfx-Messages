package ITfx.Messages.DataAccess;

import ITfx.Messages.Delivery;
import ITfx.Messages.User;
import ITfx.Messages.UserProgram;
import com.google.common.collect.ImmutableList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.joda.time.DateTime;

import java.net.UnknownHostException;

public final class UserRepository extends Repository implements IUserRepository {

    public UserRepository() throws UnknownHostException {
        super(User.class);
    }

    @Override
    public UserProgram addProgram(User user, UserProgram program) {
        this.dbCollection.save(user.addProgram(program));
        return program;
    }

    @Override
    public Delivery addDelivery(User user, Delivery delivery) {
        this.dbCollection.save(user.addDelivery(delivery));
        return delivery;
    }

    @Override
    public ImmutableList<User> getEnabledUsers() {
        final BasicDBObject query = new BasicDBObject();
        query.put(User.DELIVERY_ENABLED_FIELD_NAME, Boolean.TRUE);

        final DBCursor cursor = dbCollection.find(query);
        ImmutableList.Builder<User> builder = new ImmutableList.Builder<User>();
        while (cursor.hasNext()) {
            builder = builder.add(new User(cursor.next()));
        }
        return builder.build();
    }

    @Override
    public ImmutableList<User> getDeliveryQueue(int limit) {
        final DBObject query = BasicDBObjectBuilder
                .start(User.DELIVERY_ENABLED_FIELD_NAME, Boolean.TRUE)
                .add(User.NEXT_SCHEDULED_DELIVERY_DATE_FIELD_NAME, new BasicDBObject("$lte", new DateTime()))
                .get();

        final DBCursor cursor = dbCollection.find(query).limit(limit);
        ImmutableList.Builder<User> builder = new ImmutableList.Builder<User>();
        while (cursor.hasNext()) {
            builder = builder.add(new User(cursor.next()));
        }
        return builder.build();
    }
}
