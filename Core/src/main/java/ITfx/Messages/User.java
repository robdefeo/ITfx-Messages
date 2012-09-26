package ITfx.Messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

final public class User extends MessagesDBObject {
    // <editor-fold defaultstate="collapsed" desc="Constants">
    public final static String DELIVERY_TYPE_FIELD_NAME = "deliveryType";
    public final static String DELIVERY_ADDRESS_FIELD_NAME = "deliveryAddress";
    public final static String FIRST_NAME_FIELD_NAME = "firstName";
    public final static String USER_PROGRAMS_FIELD_NAME = "userPrograms";
    public final static String DELIVERIES_FIELD_NAME = "deliveries";
    public final static String DELIVERY_ENABLED_FIELD_NAME = "deliveryEnabled";
    public final static String NEXT_SCHEDULED_DELIVERY_DATE_FIELD_NAME = "nextScheduledDeliveryDate";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Properties">
    public Delivery.DeliveryType getDeliveryType() {
        return Delivery.DeliveryType.valueOf(getString(DELIVERY_TYPE_FIELD_NAME));
    }

    public static UserBuilder id(String id) {
        return new UserBuilder(id, null, null, null, null, null, null, null);
    }

    public Boolean getDeliveryEnabled() {
        return (Boolean) get(DELIVERY_ENABLED_FIELD_NAME);
    }

    public static UserBuilder deliveryAddress(String deliveryAddress) {
        return new UserBuilder(null, deliveryAddress, null, null, null, null, null, null);
    }

    public String getDeliveryAddress() {
        return getString(DELIVERY_ADDRESS_FIELD_NAME);
    }

    public String getFirstName() {
        return getString(FIRST_NAME_FIELD_NAME);
    }

    public DateTime getNextScheduledDeliveryDate() {
        return (DateTime) get(NEXT_SCHEDULED_DELIVERY_DATE_FIELD_NAME);
    }

    public List<UserProgram> getUserPrograms() {
        if (get(USER_PROGRAMS_FIELD_NAME) != null) {
            final List<UserProgram> programs = new ArrayList<UserProgram>();
            for (BasicDBObject item : (List<BasicDBObject>) get(USER_PROGRAMS_FIELD_NAME)) {
                programs.add(new UserProgram(item));
            }
            return ImmutableList.copyOf(programs);
        } else {
            return ImmutableList.<UserProgram>of();
        }
    }

    public ImmutableList<Delivery> getSortedDeliveries() {
        return ImmutableList
                .copyOf(Ordering.from(Delivery.requestedDeliveryDateComparator())
                        .sortedCopy(getDeliveries()));
    }

    public Delivery getLastDelivery() {
        return getSortedDeliveries().size() == 0 ? null : Iterables.getLast(getSortedDeliveries());
    }

    public ImmutableList<Delivery> getDeliveries() {
        if (get(DELIVERIES_FIELD_NAME) != null) {
            final List<Delivery> deliveries = new ArrayList<Delivery>();
            for (BasicDBObject item : (List<BasicDBObject>) get(DELIVERIES_FIELD_NAME)) {
                deliveries.add(new Delivery(item));
            }
            return ImmutableList.copyOf(deliveries);
        } else {
            return ImmutableList.<Delivery>of();
        }
    }

    public String jsonSerialize() {
        return JSON.serialize(this);
    }
    // </editor-fold>

    @JsonCreator
    public User(
            @JsonProperty(ID_FIELD_NAME) final String id,
            @JsonProperty(DELIVERY_ADDRESS_FIELD_NAME) final String deliveryAddress,
            @JsonProperty(DELIVERY_TYPE_FIELD_NAME) final String deliveryType,
            @JsonProperty(FIRST_NAME_FIELD_NAME) final String firstName,
            @JsonProperty(USER_PROGRAMS_FIELD_NAME) final Iterable<UserProgram> userPrograms,
            @JsonProperty(DELIVERIES_FIELD_NAME) final List<Delivery> deliveries,
            @JsonProperty(DELIVERY_ENABLED_FIELD_NAME) final Boolean deliveryEnabled,
            @JsonProperty(NEXT_SCHEDULED_DELIVERY_DATE_FIELD_NAME) final DateTime nextScheduledDeliveryDate) {

        put(ID_FIELD_NAME, id != null && id.isEmpty() || id == null ? String.valueOf(UUID.randomUUID()) : id);
        put(DELIVERY_ADDRESS_FIELD_NAME, deliveryAddress);
        put(DELIVERY_TYPE_FIELD_NAME, deliveryType);
        put(FIRST_NAME_FIELD_NAME, firstName);
        put(USER_PROGRAMS_FIELD_NAME, userPrograms);
        put(DELIVERIES_FIELD_NAME, deliveries);
        put(DELIVERY_ENABLED_FIELD_NAME, deliveryEnabled);
        put(NEXT_SCHEDULED_DELIVERY_DATE_FIELD_NAME, nextScheduledDeliveryDate);
    }

    public User addProgram(UserProgram program) {
        return new User(
                this.get_id(),
                this.getDeliveryAddress(),
                this.getDeliveryType().toString(),
                this.getFirstName(),
                ImmutableList.<UserProgram>builder()
                        .addAll(getUserPrograms())
                        .add(program)
                        .build(),
                this.getDeliveries(),
                this.getDeliveryEnabled(),
                this.getNextScheduledDeliveryDate()
        );
    }

    public User addDelivery(Delivery delivery) {
        return new User(
                this.get_id(),
                this.getDeliveryAddress(),
                this.getDeliveryType().toString(),
                this.getFirstName(),
                this.getUserPrograms(),
                ImmutableList.<Delivery>builder()
                        .addAll(getDeliveries())
                        .add(delivery)
                        .build(),
                this.getDeliveryEnabled(),
                this.getNextScheduledDeliveryDate()
        );
    }

    public User setDeliveryEnabled(boolean value) {
        return new User(
                this.get_id(),
                this.getDeliveryAddress(),
                this.getDeliveryType().toString(),
                this.getFirstName(),
                this.getUserPrograms(),
                this.getDeliveries(),
                value,
                this.getNextScheduledDeliveryDate()
        );
    }

    public User setNextScheduledDeliveryDate(DateTime value) {
        return new User(
                this.get_id(),
                this.getDeliveryAddress(),
                this.getDeliveryType().toString(),
                this.getFirstName(),
                this.getUserPrograms(),
                this.getDeliveries(),
                this.getDeliveryEnabled(),
                value);
    }

    public User(DBObject object) {
        super((Map) object);
    }
}
