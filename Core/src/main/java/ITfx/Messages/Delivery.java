package ITfx.Messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DBObject;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;


final public class Delivery extends MessagesDBObject {
    private final static String DELIVERY_ADDRESS_FIELD_NAME = "deliveryAddress";
    private final static String MESSAGE_ID_FIELD_NAME = "messageId";
    public  final static String REQUESTED_DELIVERY_DATE_FIELD_NAME = "requestedDeliveryDate";
    private final static String DELIVERY_TYPE_FIELD_NAME = "deliveryType";

    public String getDeliveryAddress() {
        return getString(DELIVERY_ADDRESS_FIELD_NAME);
    }

    public DateTime getRequestedDeliveryDate() {
        return (DateTime) get(REQUESTED_DELIVERY_DATE_FIELD_NAME);
    }

    public String getMessageId() {
        return getString(MESSAGE_ID_FIELD_NAME);
    }

    public DeliveryType getDeliveryType() {
        return DeliveryType.valueOf(getString(DELIVERY_TYPE_FIELD_NAME));
    }

    public static DeliveryBuilder date(DateTime date) {
        return new DeliveryBuilder(date, null, null, null);
    }
    public static DeliveryBuilder messageId(String messageId) {
        return new DeliveryBuilder(null, null, messageId, null);
    }
    @JsonCreator
    public Delivery(
            @JsonProperty(REQUESTED_DELIVERY_DATE_FIELD_NAME) DateTime date,
            @JsonProperty(MESSAGE_ID_FIELD_NAME) String messageId,
            @JsonProperty(DELIVERY_TYPE_FIELD_NAME) String deliveryType,
            @JsonProperty(DELIVERY_ADDRESS_FIELD_NAME) String deliveryAddress) {
        put(MESSAGE_ID_FIELD_NAME, messageId);
        put(DELIVERY_ADDRESS_FIELD_NAME, deliveryAddress);
        put(DELIVERY_TYPE_FIELD_NAME, deliveryType);
        put(REQUESTED_DELIVERY_DATE_FIELD_NAME, date);
    }

    public Delivery(DBObject item) {
        super((Map) item);
    }

    public enum DeliveryType {
        Unknown,
        SingleSms,
        MultipleSms,
        Email
    }

    public static Comparator<Delivery> requestedDeliveryDateComparator()
    {
        return new Comparator<Delivery>() {
            @Override
            public int compare(@Nullable Delivery delivery, @Nullable Delivery delivery1) {
                return delivery.getRequestedDeliveryDate().compareTo(delivery1.getRequestedDeliveryDate());
            }
        };
    }
}
