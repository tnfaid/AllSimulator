package all.agent.simulator.Controller;


import all.agent.simulator.Model.MessageData;
import all.agent.simulator.Repository.IDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnExpression("${my.property:false}")
public class MonthyController
{

    @Value("${acknowledge.status.message}")
    String status;

    @Value("${my.agent.name}")
    String agentName;

    @Autowired
    IDataRepository repository;

    @Value("${url.delivery.status}")
    String url;


    @RequestMapping(method = RequestMethod.GET, value = "${base.path.request}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> post(@RequestParam("username") String username,
                                       @RequestParam("password") String password,
                                       @RequestParam("source") String sender,
                                       @RequestParam("destination") String destination,
                                       @RequestParam("text") String Message) {

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode node = mapper.createObjectNode();
        String agentMessageId = randomSessionID();

        MessageData messageData = new MessageData(agentMessageId,
                username,
                password,
                destination,
                "",
                agentName,
                url,
                "0");

        try
        {

            if(status.equals("0")){

                repository.insertDeliveryStatus(messageData);

            }

            node.put("ErrorCode", status);
            node.put("Ids", agentMessageId);

            return new ResponseEntity(node.toString(), HttpStatus.ACCEPTED);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
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


