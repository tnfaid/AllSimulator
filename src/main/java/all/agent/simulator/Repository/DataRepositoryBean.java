package all.agent.simulator.Repository;

import all.agent.simulator.Model.MessageData;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class DataRepositoryBean implements IDataRepository
{

    JdbcTemplate deliveryJdbcTemplate;

    public DataRepositoryBean(DataSource deliveryDataSouce)
    {
        this.deliveryJdbcTemplate = new JdbcTemplate(deliveryDataSouce);
    }


    @Override
    public int insertDeliveryStatus(MessageData messageData)
    {

        final String INSERT_DR_TABLE =
                "INSERT INTO DELIVERY_STATUS " +
                        "( AGENT_MESSAGE_ID, " +
                        "USERNAME, " +
                        "PASSWORD, " +
                        "MSISDN, " +
                        "MSISDN_SENDER, " +
                        "AGENT_SENDER, " +
                        "DELIVERY_STATUS_URL, " +
                        "EXECUTED) " +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?, ?);";

        Object[] param = new Object[]{
                messageData.getAgentMessageId(),
                messageData.getUsername(),
                messageData.getPassword(),
                messageData.getMsisdn(),
                messageData.getMsisdn_sender(),
                messageData.getAgent_sender(),
                messageData.getDr_url(),
                messageData.getMessage()
        };

        return deliveryJdbcTemplate.update(INSERT_DR_TABLE, param);
    }

    @Override
    public List<MessageData> findDeliveryReportUnprocessed( int executed )
    {
        final String FIND_DELIVERY_STATUS_UNPROCESS =
                "SELECT AGENT_MESSAGE_ID, " +
                        "MSISDN " +
                        "FROM DELIVERY_STATUS " +
                        "WHERE EXECUTED = ? ";
        return deliveryJdbcTemplate.query(
                FIND_DELIVERY_STATUS_UNPROCESS,
                new Object[]{0},
                MessageData.getUnprocessDeliveryReport()
        );
    }



    public void updateDeliveryStatus(MessageData messageData) {

        final String UPDATE_DR_TABLE =
                "UPDATE DELIVERY_STATUS " +
                        "SET DELIVERY_STATUS = ?, " +
                        "EXECUTED = 1  " +
                        "WHERE AGENT_MESSAGE_ID = ? " +
                        "AND MSISDN = ?";
        Object[] param = new Object[]{
                messageData.getDr_url(),
                messageData.getAgentMessageId(),
                messageData.getMsisdn()
        };

        deliveryJdbcTemplate.update(UPDATE_DR_TABLE, param);

    }


}
