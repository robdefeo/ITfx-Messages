package ITfx.Messages;

import org.joda.time.DateTime;

import java.util.List;

public final class UserBuilder {

    private final String id;
    private final String deliveryAddress;
    private final Delivery.DeliveryType deliveryType;
    private final String firstName;
    private final List<UserProgram> userPrograms;
    private final List<Delivery> deliveries;
    private final Boolean deliveryEnabled;
    private final DateTime nextScheduledDeliveryDate;

    UserBuilder(String id, String deliveryAddress, Delivery.DeliveryType deliveryType, String firstName, List<UserProgram> userPrograms, List<Delivery> deliveries, Boolean deliveryEnabled, DateTime nextScheduledDeliveryDate) {
        this.id = id;
        this.deliveryAddress = deliveryAddress;
        this.deliveryType = deliveryType;
        this.firstName = firstName;
        this.userPrograms = userPrograms;
        this.deliveries = deliveries;
        this.deliveryEnabled = deliveryEnabled;
        this.nextScheduledDeliveryDate = nextScheduledDeliveryDate;
    }

    public User build() {
        return new User(id, deliveryAddress, deliveryType.toString(), firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }

    public UserBuilder id(String id) {
        return new UserBuilder(id, deliveryAddress, deliveryType, firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }
    public UserBuilder deliveryAddress(String deliveryAddress) {
        return new UserBuilder(id, deliveryAddress, deliveryType, firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }
    public UserBuilder deliveryType(Delivery.DeliveryType deliveryType) {
        return new UserBuilder(id, deliveryAddress, deliveryType, firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }
    public UserBuilder firstName(String firstName) {
        return new UserBuilder(id, deliveryAddress, deliveryType, firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }
    public UserBuilder userPrograms(List<UserProgram> userPrograms) {
        return new UserBuilder(id, deliveryAddress, deliveryType, firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }
    public UserBuilder deliveries(List<Delivery> deliveries) {
        return new UserBuilder(id, deliveryAddress, deliveryType, firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }
    public UserBuilder deliveryEnabled(Boolean deliveryEnabled) {
        return new UserBuilder(id, deliveryAddress, deliveryType, firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }
    public UserBuilder nextScheduledDeliveryDate(DateTime nextScheduledDeliveryDate) {
        return new UserBuilder(id, deliveryAddress, deliveryType, firstName, userPrograms, deliveries, deliveryEnabled, nextScheduledDeliveryDate);
    }

}

