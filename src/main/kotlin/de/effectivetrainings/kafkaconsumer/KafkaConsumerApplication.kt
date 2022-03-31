package de.effectivetrainings.kafkaconsumer

import ProductProcessor
import de.effectivetrainings.Product
import de.effectivetrainings.Purchase
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerializer
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.streams.kstream.KStream
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@SpringBootApplication
@EnableKafkaStreams
@EnableKafka
class KafkaConsumerApplication

fun main(args: Array<String>) {
    runApplication<KafkaConsumerApplication>(*args)


}

@Configuration
class ConsumerConfig {
    @Bean
    fun productsProcessor(
        purchaseStream: KStream<String, Purchase>,
        productStream: KStream<String, Product>
    ): ProductProcessor {
        val processor: ProductProcessor = ProductProcessor(purchaseStream, productStream)
        processor.joinPurchasesWithProducts()
        return processor
    }
}
