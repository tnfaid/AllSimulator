package all.agent.simulator.Model;

import org.springframework.jdbc.core.RowMapper;

public class MessageData {
    private Long id;
    private String agentMessageId;
    private String username;
    private String password;
    private String msisdn;
    private String msisdn_sender;
    private String agent_sender;
    private String message;
    private String msg_type;
    private String dr_url;
    private String executed;

    public MessageData() {

    }

    public MessageData(String agentMessageId,
                       String usernamme,
                       String password,
                       String msisdn,
                       String msisdn_sender,
                       String agent_sender,
                       String dr_url,
                       String executed)
    {
        this.agentMessageId = agentMessageId;
        this.username = usernamme;
        this.password = password;
        this.msisdn = msisdn;
        this.msisdn_sender = msisdn_sender;
        this.agent_sender = agent_sender;
        this.dr_url = dr_url;
        this.executed = executed;
    }

    public String getExecuted()
    {
        return executed;
    }

    public void setExecuted( String executed )
    {
        this.executed = executed;
    }

    public String getAgent_sender() {
        return agent_sender;
    }

    public void setAgent_sender(String agent_sender) {
        this.agent_sender = agent_sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentMessageId() {
        return agentMessageId;
    }

    public void setAgentMessageId(String agentMessageId) {
        this.agentMessageId = agentMessageId;
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

    public String getMsisdn_sender() {
        return msisdn_sender;
    }

    public void setMsisdn_sender(String msisdn_sender) {
        this.msisdn_sender = msisdn_sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getDr_url() {
        return dr_url;
    }

    public void setDr_url(String dr_url) {
        this.dr_url = dr_url;
    }

    public static RowMapper<MessageData> getUnprocessDeliveryReport()
    {
        return (rs, rownum) ->
        {
            MessageData messageData = new MessageData();
            messageData.setAgentMessageId(rs.getString("AGENT_MESSAGE_ID"));
            messageData.setMsisdn(rs.getString("MSISDN"));
            return messageData;
        };
    }





}
