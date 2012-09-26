package unit.ITfx.Messages;

import ITfx.Messages.Delivery;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeliveryTest {
    @Test
    public void constructorTest() {
        DateTime date = new DateTime();
        Delivery target = new Delivery(date, "messageId", "SingleSms", "test@test.com");
        assertEquals("messageId", target.getMessageId());
        assertEquals(Delivery.DeliveryType.SingleSms, target.getDeliveryType());
        assertEquals(date, target.getRequestedDeliveryDate());
        assertEquals("test@test.com", target.getDeliveryAddress());
    }
}
