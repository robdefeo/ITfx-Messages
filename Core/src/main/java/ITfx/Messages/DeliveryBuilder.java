package ITfx.Messages;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;

public final class DeliveryBuilder {
    private final DateTime requestedDeliveryDate;
    private final String deliveryAddress;
    private final String messageId;
    private final Delivery.DeliveryType deliveryType;

    DeliveryBuilder(DateTime requestedDeliveryDate, String deliveryAddress, String messageId, Delivery.DeliveryType deliveryType) {
        this.requestedDeliveryDate = requestedDeliveryDate;
        this.deliveryAddress = deliveryAddress;
        this.messageId = messageId;
        this.deliveryType = deliveryType;
    }
    public DeliveryBuilder requestedDeliveryDate(DateTime requestedDeliveryDate) {
        return new DeliveryBuilder(requestedDeliveryDate, messageId, deliveryAddress, deliveryType);
    }
    public DeliveryBuilder messageId(String messageId) {
        return new DeliveryBuilder(requestedDeliveryDate, messageId, deliveryAddress, deliveryType);
    }
    public DeliveryBuilder deliveryAddress(String deliveryAddress) {
        return new DeliveryBuilder(requestedDeliveryDate, messageId, deliveryAddress, deliveryType);
    }
    public DeliveryBuilder deliveryType(Delivery.DeliveryType deliveryType) {
        return new DeliveryBuilder(requestedDeliveryDate, messageId, deliveryAddress, deliveryType);
    }
    public Delivery build() {
        Preconditions.checkNotNull(messageId);

        return new Delivery(requestedDeliveryDate,
                messageId,
                deliveryAddress,
                deliveryType != null ? deliveryType.toString() : Delivery.DeliveryType.Unknown.toString());
    }
}
