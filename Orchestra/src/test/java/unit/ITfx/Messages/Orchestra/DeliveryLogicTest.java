package unit.ITfx.Messages.Orchestra;

import ITfx.Messages.Delivery;
import ITfx.Messages.Orchestra.DeliveryLogic;
import ITfx.Messages.PhaseMessage;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DeliveryLogicTest {

    @Test
    public void successTest() {
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.put("http://localhost:5080/ITfx.Messages.Orchestra/delivery/deliverAll/", "");
    }

    @Test
    public void hasMessageIdForDeliveryNoneTest() {
        DeliveryLogic target = new DeliveryLogic(null);
        Boolean actual = target.hasMessageIdForDelivery(
                ImmutableList.<PhaseMessage>builder()
                        .add(PhaseMessage.messageId("test2").build())
                        .build(),
                ImmutableList.<Delivery>builder()
                        .add(Delivery.messageId("test2").build())
                        .build());

        assertEquals(Boolean.FALSE, actual);
    }
    @Test
    public void getNextMessageIdForDeliveryNoMessagesTest() {
        DeliveryLogic target = new DeliveryLogic(null);
        String actual = target.getNextMessageIdForDelivery(
                ImmutableList.<PhaseMessage>builder()
                        .add(PhaseMessage.messageId("test2").build())
                        .build(),
                ImmutableList.<Delivery>builder()
                        .add(Delivery.messageId("test2").build())
                        .build());

        assertNull(actual);
    }

    @Test
    public void getNextMessageIdForDeliveryTest() {
        DeliveryLogic target = new DeliveryLogic(null);
        String actual = target.getNextMessageIdForDelivery(
                ImmutableList.<PhaseMessage>builder()
                        .add(PhaseMessage.messageId("test1").order(6).build())
                        .add(PhaseMessage.messageId("test2").order(3).build())
                        .add(PhaseMessage.messageId("test3").order(4).build())
                        .build(),
                ImmutableList.<Delivery>builder()
                        .add(Delivery.messageId("test2").build())
                        .build());

        assertNotNull(actual);
        assertEquals("test3", actual);
    }



}
