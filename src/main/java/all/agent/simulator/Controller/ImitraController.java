package all.agent.simulator.Controller;


import all.agent.simulator.Mapper.MessageEntity;
import all.agent.simulator.Model.MessageData;
import all.agent.simulator.Repository.IDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;

import static java.net.URLDecoder.decode;

@RestController
@ConditionalOnExpression("${my.property:false}")
public class ImitraController
{

    @Value("${acknowledge.status.message}")
    String ackstatus;

    @Value("${my.agent.name}")
    String agentName;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDataRepository repository;

    @RequestMapping(method = RequestMethod.POST, value = "${base.path.request}", produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> post(@RequestBody String param) throws IOException {

        param = param.replace("data=", "");
        String decodeXMl = decode(param, Charset.defaultCharset());
        ObjectMapper objectMapper = new XmlMapper();
        MessageEntity messageEntity = objectMapper.readValue(decodeXMl, MessageEntity.class);

        log.info("Param : {}", param);
        log.info("Decode : {}", decodeXMl);
        log.info("Message Model : {}", messageEntity);

        String AgentMessageID = randomSessionID();
        MessageData messageData = new MessageData(AgentMessageID,
                messageEntity.getUsername(),
                messageEntity.getPassword(),
                messageEntity.getMsisdn(),
                messageEntity.getMsisdn_sender(),
                agentName,
                messageEntity.getDeliveryUrl(),
                "0");
        log.info("Message Data : {}", messageData);


        String response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);

        if (ackstatus.equals("6801"))
        {

            response = ackstatus + "\n" + AgentMessageID;
            int i = repository.insertDeliveryStatus(messageData);
            log.info("Insert To DB {}", i);


        }
        else
        {

            response = ackstatus + "\n";

        }
        return new ResponseEntity<String>(response, headers, HttpStatus.ACCEPTED);

    }

    static String randomSessionID() {

        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(20);

        for (int i = 0; i < 20; i++)
        {

            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));

        }

        return sb.toString();
    }

}