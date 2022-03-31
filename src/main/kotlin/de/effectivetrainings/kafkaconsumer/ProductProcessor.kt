import de.effectivetrainings.Product
import de.effectivetrainings.Purchase
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Printed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

class ProductProcessor(private val purchases:KStream<String, Purchase>, private val products:KStream<String, Product>) {

    @Autowired
    fun joinPurchasesWithProducts() {

        purchases


            .foreach {key, value -> println(value)}

    }


}
