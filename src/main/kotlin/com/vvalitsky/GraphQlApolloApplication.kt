package com.vvalitsky

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * Created by Vladislav Valitsky at 21.06.2020
 */

@SpringBootApplication
class GraphQlApolloApplication

fun main(args: Array<String>) {
    SpringApplication.run(GraphQlApolloApplication::class.java, *args)
}
