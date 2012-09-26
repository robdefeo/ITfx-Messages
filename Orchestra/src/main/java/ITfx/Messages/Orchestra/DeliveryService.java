package ITfx.Messages.Orchestra;

import ITfx.Messages.User;
import com.mongodb.util.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequestMapping("/delivery")
public class DeliveryService {
    private final RabbitTemplate emailDeliveryTemplate;
    private final String baseUrl = "http://127.0.0.1:8080/Service/";

    public DeliveryService(RabbitTemplate emailDeliveryTemplate) {
        this.emailDeliveryTemplate = emailDeliveryTemplate;
    }

    @RequestMapping(value = "/deliverAll", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void doEnqueueId() {
        RestTemplate restTemplate = new RestTemplate();
        String json = restTemplate.getForObject(String.format(baseUrl + "user/deliveryQueue/10"), String.class);
        List<User> users = (List<User>) JSON.parse(json);
        DeliveryLogic.processUsers(users);
    }

}
