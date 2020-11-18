package all.agent.simulator.Controller;


import all.agent.simulator.Mapper.MessageEntity;
import all.agent.simulator.Model.MessageData;
import all.agent.simulator.Repository.IDataRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;

import static java.net.URLDecoder.decode;

@RestController
@RequestMapping("${base.path.request}")
public class SimulatorController {


    @ResponseBody
    public String SprintGetMessage1(@PathVariable String agentId, HttpServletRequest request)
    {
        return SprintController.BASE_PATH;
    }

    @ResponseBody
    public String SprintGetMessage()
    {
        return "redirect:/api/msg.php";
    }

    @ResponseBody
    public String MonthyGetMessage()
    {
        return "redirect:/Home/SendSMS";
    }

    @ResponseBody
    public String ImitraPostMessage()
    {
        return "redirect:sms_gateway/engine/bulk_receiver_api/bulk_mt_receiver.php";
    }

    @ResponseBody
    public String TelkomselGetMessage()
    {
        return "redirect:/cp/smsbulk/submit.jsp";
    }

    private String randomSessionID()
    {
        String alphanumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvwxyz";

        StringBuilder sb = new StringBuilder(20);

        for (int i=0; i < 20; i++)
        {
            int index = (int) (alphanumericString.length() * Math.random());
            sb.append(alphanumericString.charAt(index));
        }

        return sb.toString();
    }
}
