package all.agent.simulator.Mapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "mt_data")
public class MessageEntity {
    @JacksonXmlProperty(localName = "username", isAttribute = true)
    private String username;

    @JacksonXmlProperty(localName = "password", isAttribute = true)
    private String password;

    @JacksonXmlProperty(localName = "msisdn", isAttribute = true)
    private String msisdn;

    @JacksonXmlProperty(localName = "message", isAttribute = true)
    private String message;

    @JacksonXmlProperty(localName = "msisdn_sender", isAttribute = true)
    private String msisdn_sender;

    @JacksonXmlProperty(localName = "msg_type", isAttribute = true)
    private String message_type;

    @JacksonXmlProperty(localName = "priority", isAttribute = true)
    private String priority;

    @JacksonXmlProperty(localName = "dr_url", isAttribute = true)
    private String deliveryUrl;

    public MessageEntity()
    {

    }

    public MessageEntity(String message_type, String username, String password,
                         String priority, String msisdn, String msisdn_sender,
                         String message, String deliveryUrl)
    {
        this.username = username;
        this.password = password;
        this.msisdn = msisdn;
        this.message = message;
        this.msisdn_sender = msisdn_sender;
        this.message_type = message_type;
        this.priority = priority;
        this.deliveryUrl = deliveryUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsisdn_sender() {
        return msisdn_sender;
    }

    public void setMsisdn_sender(String msisdn_sender) {
        this.msisdn_sender = msisdn_sender;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDeliveryUrl() {
        return deliveryUrl;
    }

    public void setDeliveryUrl(String deliveryUrl) {
        this.deliveryUrl = deliveryUrl;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "message_type='" + message_type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", priority='" + priority + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", msisdnSender='" + msisdn_sender + '\'' +
                ", message='" + message + '\'' +
                ", deliveryUrl='" + deliveryUrl + '\'' +
                '}';
    }
}
