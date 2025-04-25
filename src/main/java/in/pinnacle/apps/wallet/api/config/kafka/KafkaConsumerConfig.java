package in.pinnacle.apps.wallet.api.config.kafka;

import in.pinnacle.apps.wallet.api.util.dto.WalletTransactionDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String packagesToSearch;

    // Wallet Transaction Consumer
    @Bean
    public ConsumerFactory<Long, WalletTransactionDTO> walletTransactionConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, packagesToSearch);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, WalletTransactionDTO.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "wallet-transaction-group");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "walletTransactionKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, WalletTransactionDTO> walletTransactionKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, WalletTransactionDTO>();
        factory.setConsumerFactory(walletTransactionConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    // WalletDTO Consumer
    @Bean
    public ConsumerFactory<Long, Object> walletConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, packagesToSearch);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Object.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "wallet-group");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "walletKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Long, Object> walletKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, Object>();
        factory.setConsumerFactory(walletConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

}
