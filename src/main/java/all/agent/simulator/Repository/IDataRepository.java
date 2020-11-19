package all.agent.simulator.Repository;

import all.agent.simulator.Model.MessageData;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface IDataRepository
{
    int insertDeliveryStatus(MessageData messageData);

    List<MessageData> findDeliveryReportUnprocessed(int executed);

    void updateDeliveryStatus(MessageData messageData);
}
