package all.agent.simulator.Controller;


import all.agent.simulator.Model.MessageData;
import all.agent.simulator.Repository.IDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TelkomselController.BASE_PATH)
public class TelkomselController {

    public static final String BASE_PATH = "/cp/smsbulk/submit.jsp";

    @Value("${acknowledge.status.message}")
    String ackstatus;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDataRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> get(@RequestParam("cp_name") String username,
                                      @RequestParam("pwd") String password,
                                      @RequestParam("sid") String sender,
                                      @RequestParam("msisdn") String destination,
                                      @RequestParam("sms") String message){

        MessageData messageData = new MessageData();
        messageData.setUsername(username);
        messageData.setPassword(password);
        messageData.setMsisdn_sender(sender);
        messageData.setMsisdn(destination);
        messageData.setAgentMessageId(randomSessionID());

        log.info("Message Data :{}", messageData);

        HttpHeaders headers = new HttpHeaders();
        String response = null;

        if(ackstatus.equals("1")){

            response = ackstatus;
            int i = repository.insertDeliveryStatus(messageData);
            log.info("Insert to DB {}", i);

        }
        else
        {
            response = ackstatus + "\n";
        }

        return new ResponseEntity<String>(response, headers, HttpStatus.OK);
    }

    static String randomSessionID() {

        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(7);

        for (int i = 0; i < 7; i++)
        {

            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));

        }

        return sb.toString();
    }
}

