package ITfx.Messages.Orchestra

import ITfx.Messages.Delivery
import ITfx.Messages.Message
import ITfx.Messages.Phase
import ITfx.Messages.PhaseMessage
import ITfx.Messages.Program
import ITfx.Messages.User
import ITfx.Messages.UserProgram
import com.google.common.collect.Iterables
import com.mongodb.util.JSON
import org.joda.time.DateTime
import org.springframework.web.client.RestTemplate
import sun.reflect.generics.reflectiveObjects.NotImplementedException

//@RequestMapping("/delivery")
public class DeliveryLogic {


    public static void processUsers(List<User> users) {
        RestTemplate restTemplate = new RestTemplate();

        users.each {user ->
            ((Iterable<UserProgram>) user.userPrograms).each { userProgram ->
                final Program program = (Program) JSON.parse(restTemplate.getForObject(baseUrl.concat("program/").concat(userProgram.getProgramId()), String.class));
                //  program.getPhases().find {x-> x.}
                //  ((Iterable<Phase>) program.phases).find(){x -> x.}
            }
        }
        users.each {user ->
            user.getUserPrograms().each {userProgram ->
                final Program program = (Program) JSON.parse(restTemplate.getForObject(baseUrl.concat("program/").concat(userProgram.getProgramId()), String.class));
                final Phase currentPhase = Iterables.find(program.getPhases(), Program.getPhaseByIdPredicate(userProgram.getCurrentPhaseId()), null);

                if (currentPhase.getIsPhaseOrdered()) {
                    final def messageIdForDelivery = getNextMessageIdForDelivery(currentPhase.phaseMessages, user.deliveries)
                    if (messageIdForDelivery != null) {
                    }
                } else {
                    throw new NotImplementedException();
                }
            }
            final User updatedUser = user.setNextScheduledDeliveryDate(DateTime.now().plusDays(1));
        }


    }

    public static void deliverMessage(User user, String messageId) {

    }

    public static Message getMessage(String messageId) {
        return (Message) JSON.parse(new RestTemplate().getForObject(baseUrl.concat("message/").concat(messageId), String.class));
    }

    public static boolean hasMessageIdForDelivery(Iterable<PhaseMessage> phaseMessages, Iterable<Delivery> deliveries) {
        return phaseMessages
                .any({ x -> deliveries.any {y -> x.messageId != y.messageId}});
        //.findAll({ x -> deliveries.any {y -> x.messageId != y.messageId}})
        //.any();
    }

    public static String getNextMessageIdForDelivery(Iterable<PhaseMessage> phaseMessages, Iterable<Delivery> deliveries) {
        if (hasMessageIdForDelivery(phaseMessages, deliveries)) {
            PhaseMessage phaseMessage = phaseMessages
                    .findAll({ x -> deliveries.any {y -> x.messageId != y.messageId}})
                    .min({x -> x.order});

            return phaseMessage.messageId;
        } else {
            return null;
        }
    }
}
