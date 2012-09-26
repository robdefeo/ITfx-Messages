package ITfx.Messages.DataAccess;

import ITfx.Messages.Delivery;
import ITfx.Messages.User;
import ITfx.Messages.UserProgram;
import com.google.common.collect.ImmutableList;

public interface IUserRepository extends IRepository {
    UserProgram addProgram(User user, UserProgram program);
    Delivery addDelivery(User user, Delivery delivery);
    ImmutableList<User> getEnabledUsers();
    ImmutableList<User> getDeliveryQueue(int limit);
}
