package project.cnu.daehakro.domain.chat.config;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import project.cnu.daehakro.domain.enums.ResponseEnum;
import project.cnu.daehakro.domain.handler.CustomApiException;

import java.util.List;
import java.util.Map;

public class CustomPartitioner implements Partitioner {

    public void configure(Map<String, ?> configs) {}

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();

        // roomId를 key로 한다 어짜피 roomId는 채팅방마다 고유하니..
        int roomId = (Integer) key;

        if (keyBytes == null)
            throw new CustomApiException(ResponseEnum.KAFKA_PARTITION_KEY_ERR);
        else if (!(key instanceof Integer))
            throw new CustomApiException(ResponseEnum.KAFKA_PARTITION_KEY_TYPE_ERR);
        return roomId % numPartitions;
    }

    public void close() {
        // No-ops
    }
}
