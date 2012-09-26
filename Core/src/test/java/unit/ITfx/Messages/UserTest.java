package unit.ITfx.Messages;


import ITfx.Messages.Delivery;
import ITfx.Messages.User;
import ITfx.Messages.UserProgram;
import com.google.common.collect.ImmutableList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class UserTest {
    @Test
    public void constructorFromJson()  {
        String json = "{" +
                "\"_id\":\"52863a9c-bcd2-4e24-a88d-e62b4be56444\"," +
                "\"deliveryAddress\":\"address\"," +
                "\"deliveryType\":\"Email\"," +
                "\"firstName\":\"Mike\"}";
        User target =new User((DBObject) JSON.parse(json));

        assertNotNull(target);
        assertEquals("52863a9c-bcd2-4e24-a88d-e62b4be56444", target.get_id());
        assertEquals("address", target.getDeliveryAddress());
        assertEquals(Delivery.DeliveryType.Email, target.getDeliveryType());
        assertEquals("Mike", target.getFirstName());

    }
    @Test
    public void constructorSpecifiedIdTest() {
        User target = User.id("id")
                .deliveryAddress("address")
                .deliveryType(Delivery.DeliveryType.MultipleSms)
                .firstName("Mike")
                .build();
        assertEquals(target.get_id(), "id");
        assertEquals(target.getDeliveryAddress(), "address");
        assertEquals(target.getDeliveryType(), Delivery.DeliveryType.MultipleSms);
        assertEquals(target.getFirstName(), "Mike");
    }

    @Test
    public void constructorNullIdSpecifiedTest() {
        User target = User
                .deliveryAddress("address")
                .deliveryType(Delivery.DeliveryType.MultipleSms)
                .firstName("Mike")
                .build();
        assertEquals(target.getDeliveryAddress(), "address");
        assertEquals(target.getDeliveryType(), Delivery.DeliveryType.MultipleSms);
        assertEquals(target.getFirstName(), "Mike");

        assertNotNull(target.get_id());
        assertFalse(target.get_id().isEmpty());

    }

    @Test
    public void constructorEmptyIdSpecifiedTest() {
        User target = User.id("")
                .deliveryAddress("address")
                .deliveryType(Delivery.DeliveryType.MultipleSms)
                .firstName("Mike")
                .build();
        assertEquals(target.getDeliveryAddress(), "address");
        assertEquals(target.getDeliveryType(), Delivery.DeliveryType.MultipleSms);
        assertEquals(target.getFirstName(), "Mike");

        assertNotNull(target.get_id());
        assertFalse(target.get_id().isEmpty());
    }
    @Test
    public void addProgramNoExisiting() {
        User target = User.id("id")
                .deliveryAddress("address")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .firstName("Mike")
                .build();

        User actual = target.addProgram(new UserProgram("newProgramId", "newPhaseId"));

            assertNotNull(actual);
            assertEquals(target.get_id(),  actual.get_id() );
            assertEquals(target.getDeliveryAddress(),  actual.getDeliveryAddress() );
            assertEquals(target.getDeliveryType(),  actual.getDeliveryType() );
            assertEquals(target.getFirstName(),  actual.getFirstName() );
            assertNotNull(actual.getUserPrograms());
            assertEquals(1, actual.getUserPrograms().size());
    }
    @Test
    public void addProgramPreExisting() {
        User target = User.id("id")
                .deliveryAddress("address")
                .deliveryType(Delivery.DeliveryType.MultipleSms)
                .firstName("Mike")
                .userPrograms(ImmutableList.of(new UserProgram("programId", "phaseId")))
                .build();
        User actual = target.addProgram(new UserProgram("newProgramId", "newPhaseId"));

        assertNotNull(actual);
        assertEquals(target.get_id(),  actual.get_id() );
        assertEquals(target.getDeliveryAddress(),  actual.getDeliveryAddress() );
        assertEquals(target.getDeliveryType(),  actual.getDeliveryType() );
        assertEquals(target.getFirstName(),  actual.getFirstName() );
        assertNotNull(actual.getUserPrograms());
        assertEquals(2, actual.getUserPrograms().size());
    }
    @Test
    public void addDeliveryNoExisiting() {
        User target = User.id("id")
                .deliveryAddress("address")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .firstName("Mike")
                .build();

        User actual = target.addDelivery( Delivery
                .date(DateTime.now())
                .deliveryAddress("deliveryAddress")
                .deliveryType(Delivery.DeliveryType.MultipleSms)
                .messageId("messageId")
                .build()
        );

        assertNotNull(actual);
        assertEquals(target.get_id(),  actual.get_id() );
        assertEquals(target.getDeliveryAddress(),  actual.getDeliveryAddress() );
        assertEquals(target.getDeliveryType(),  actual.getDeliveryType() );
        assertEquals(target.getFirstName(),  actual.getFirstName() );
        assertNotNull(actual.getDeliveries());
        assertEquals(1, actual.getDeliveries().size());
    }
    @Test
    public void addDeliveryPreExisting() {
        User target = User.id("id")
                .deliveryAddress("address")
                .deliveryType(Delivery.DeliveryType.SingleSms)
                .firstName("Mike")
                .deliveries(ImmutableList.of(Delivery
                        .date(DateTime.now())
                        .deliveryAddress("deliveryAddress")
                        .deliveryType(Delivery.DeliveryType.MultipleSms)
                        .messageId("messageId")
                        .build()
                ))
                .build();
        User actual = target.addDelivery(Delivery
                .date(DateTime.now())
                .deliveryAddress("deliveryAddress")
                .deliveryType(Delivery.DeliveryType.MultipleSms)
                .messageId("messageId")
                .build()
        );
        assertNotNull(actual);
        assertEquals(target.get_id(),  actual.get_id() );
        assertEquals(target.getDeliveryAddress(),  actual.getDeliveryAddress() );
        assertEquals(target.getDeliveryType(),  actual.getDeliveryType() );
        assertEquals(target.getFirstName(),  actual.getFirstName() );
        assertNotNull(actual.getDeliveries());
        assertEquals(2, actual.getDeliveries().size());
    }

}
