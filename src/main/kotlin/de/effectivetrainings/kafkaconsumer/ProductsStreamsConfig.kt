package de.effectivetrainings.kafkaconsumer

import de.effectivetrainings.Product
import de.effectivetrainings.Purchase
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.KafkaStreamsConfiguration
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Configuration
class ProductsStreamsConfig {
    @Autowired
    private lateinit var builder: StreamsBuilder;
    @Autowired
    private lateinit var kafkaStreamConfig: KafkaStreamsConfiguration;

    @Bean
    fun productPurchasesStream(props: KafkaProperties): KStream<String, Purchase> {
        val avroSerde: SpecificAvroSerde<Purchase> = SpecificAvroSerde()
        avroSerde.configure(props.properties, false);
        val stream: KStream<String, Purchase> = builder.stream("test-topic")
        return stream
    }

    @Bean
    fun productStreams(props: KafkaProperties): KStream<String, Product> {
        val avroSerde: SpecificAvroSerde<Product> = SpecificAvroSerde()
        var properties = props.properties
//        properties.put("specific.avro.reader", "true")
        avroSerde.configure(properties, false);
        val stream: KStream<String, Product> = builder.stream("products-topic")
        return stream


    }


}

fun <T, U> asSerde(keySerde: Serde<T>, valueSerde: Serde<U>): Consumed<T, U> {
    return Consumed.with(keySerde, valueSerde)
}

