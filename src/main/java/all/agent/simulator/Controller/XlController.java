package all.agent.simulator.Controller;

import all.agent.simulator.Model.MessageData;
import all.agent.simulator.Repository.IDataRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.util.Arrays;

import static org.springframework.web.util.UriUtils.decode;


@RestController
@ConditionalOnExpression("${my.property:true}")
public class XlController
{
    @Value("${acknowledge.status.message}")
    String ackStatus;

    @Value("${my.agent.name}")
    String agentName;

    @Value("${balance.status.message}")
    String balance;

    @Value("${count.status.message}")
    String count;

    @Value("${url.delivery.status}")
    String urlDelivery;


    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IDataRepository repository;

    @RequestMapping(value = "/sendsms", method = RequestMethod.POST, consumes = {"text/xml"}, produces = {"application/xml"})
    public ResponseEntity<String> post( @RequestBody String postRequestMessage ) throws IOException
    {
        postRequestMessage = postRequestMessage.replace("data=", "");
        String decodeXMl = decode(postRequestMessage, Charset.defaultCharset());
        XmlMapper xmlMapper= new XmlMapper();
        log.info("Decode : {}", decodeXMl);

        String[] messages = decodeXMl.split("(&)|(=)");
        log.info("split with delimiter : {}",  Arrays.toString(messages));

        String username = (String) Array.get(messages, 1);
        String password = (String)Array.get(messages, 3);
        String msisdn = (String)Array.get(messages,5);
        String msisdnSender = (String)Array.get(messages, 7);
        String message = (String)Array.get(messages, 9);

        log.info("Param : {}", postRequestMessage);

        String agentMessageID = randomSessionID();
        MessageData messageData = new MessageData(
                agentMessageID,
                username,
                password,
                msisdn,
                msisdnSender,
                agentName,
                urlDelivery,
                "0");
        log.info("Message Data : {}", messageData);

        String response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        if (ackStatus.equals("6801"))
        {
            response = ackStatus + " | BALANCE:" + balance + " | COUNT:" + count + " | TRANSACTIONID:" + agentMessageID;
            repository.insertDeliveryStatus(messageData);
        }
        else if(ackStatus.equals("6805"))
        {
            response = "Wrong credential (User, Password, IP)";
        }
        else if(ackStatus.equals("6806"))
        {
            response = "Unsupported prefix or unknown destination operator";
        }
        else if(ackStatus.equals("6809"))
        {
            response = "Unsupported/unregistered senderid";
        }
        else if(ackStatus.equals("6808"))
        {
            response = "Insufficient balance to be deducted";
        }
        else if(ackStatus.equals("6804"))
        {
            response = "Error from destination network operator";
        }
        else if(ackStatus.equals("6901"))
        {
            response = "Error from destination network operator – non chargable";
        }
        else if(ackStatus.equals("6902"))
        {
            response = "Fail to submit message to network operator";
        }
        else if(ackStatus.equals("6011"))
        {
            response = "Rejected message due to content filter (e.g: OTP message on regular account)";
        }
        else
        {
            response = ackStatus + "\n";
        }
        return new ResponseEntity<String>(response, headers, HttpStatus.ACCEPTED);

    }


    private String randomSessionID()
    {
        String alphanumericString =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(20);

        for (int i=0; i < 20; i++)
        {
            int index = (int) (alphanumericString.length() * Math.random());
            sb.append(alphanumericString.charAt(index));
        }
        return sb.toString();
    }

}
