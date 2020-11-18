package all.agent.simulator.Mapper;

public class mt_data {

    String msg_type;
    String username;
    String password;
    String priority;
    String msisdn_sender;
    String msisdn;
    String message;
    String dr_url;

    public mt_data(String msg_type, String username, String password, String priority, String msisdn_sender, String msisdn, String message, String dr_url) {
        this.msg_type = msg_type;
        this.username = username;
        this.password = password;
        this.priority = priority;
        this.msisdn_sender = msisdn_sender;
        this.msisdn = msisdn;
        this.message = message;
        this.dr_url = dr_url;
    }


    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMsisdn_sender() {
        return msisdn_sender;
    }

    public void setMsisdn_sender(String msisdn_sender) {
        this.msisdn_sender = msisdn_sender;
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

    public String getDr_url() {
        return dr_url;
    }

    public void setDr_url(String dr_url) {
        this.dr_url = dr_url;
    }
}

