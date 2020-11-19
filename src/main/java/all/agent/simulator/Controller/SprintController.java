package all.agent.simulator.Controller;


import all.agent.simulator.Model.MessageData;
import all.agent.simulator.Repository.IDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@ConditionalOnExpression("${my.property:true}")
public class SprintController {

    Logger log = LoggerFactory.getLogger(SprintController.class);

    @Autowired
    IDataRepository repository;

    @Value("${my.agent.name}")
    String agentName;

    @Value("${acknowledge.status.message}")
    String acknowledge;

    @RequestMapping(method = RequestMethod.GET, value = "${base.path.request}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> get(@RequestParam("u") String username,
                                      @RequestParam("p") String password,
                                      @RequestParam("d") String destination,
                                      @RequestParam("m") String Message) {

        String agentMessageID = randomSessionID();

        String response = acknowledge + "-" + agentMessageID;

        MessageData messageData = new MessageData(agentMessageID,
                username,
                password,
                destination,
                "",
                agentName,
                Message,
                "0");

        log.info("{}", messageData.toString());
        HttpHeaders headers = new HttpHeaders();

        repository.insertDeliveryStatus(messageData);

        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<String>(response, headers, HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> post(@RequestBody Map<String, String> param) {
        String username = param.get("u");
        String password = param.get("p");
        String destination = param.get("d");
        String message = param.get("m");

        String agentMessageID = randomSessionID();
        String response = acknowledge + "-" + agentMessageID;

        MessageData messageData = new MessageData(agentMessageID,
                username,
                password,
                destination,
                "",
                "Sprint",
                message,
                "0");

        repository.insertDeliveryStatus(messageData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<String>(response, headers, HttpStatus.ACCEPTED);

    }


    static String randomSessionID() {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(20);

        for (int i = 0; i < 20; i++)
        {

            int index = (int) (AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }


}
