package com.vvalitsky.graphql.config

import com.vvalitsky.graphql.fetchers.DataFetchers
import graphql.GraphQL
import graphql.analysis.MaxQueryComplexityInstrumentation
import graphql.analysis.MaxQueryDepthInstrumentation
import graphql.execution.AsyncExecutionStrategy
import graphql.execution.AsyncSerialExecutionStrategy
import graphql.execution.instrumentation.ChainedInstrumentation
import graphql.execution.instrumentation.Instrumentation
import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import graphql.schema.idl.TypeRuntimeWiring
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.util.FileCopyUtils
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.function.Consumer

/**
 * Created by Vladislav Valitsky at 23.06.2020
 */

@Configuration
class GraphQlSchemaConfiguration(
    private val dataFetchers: DataFetchers
) {

    @Bean
    fun graphQL(
        @Value("classpath:graphql/query.graphqls") queryResource: Resource,
        @Value("classpath:graphql/mutation.graphqls") mutationResource: Resource
    ): GraphQL? {
        return GraphQL
            .newGraphQL(buildSchema(queryResource, mutationResource))
            .queryExecutionStrategy(AsyncExecutionStrategy())
            .mutationExecutionStrategy(AsyncSerialExecutionStrategy())
            .instrumentation(
                ChainedInstrumentation(
                    mutableListOf<Instrumentation>(
                        MaxQueryComplexityInstrumentation(200),
                        MaxQueryDepthInstrumentation(13)
                    )
                )
            )
            .build()
    }

    private fun buildSchema(queryResource: Resource, mutationResource: Resource): GraphQLSchema? {
        val typeRegistry = TypeDefinitionRegistry()
        val schemaParser = SchemaParser()
        val sdl = StringBuilder()
        listOf(queryResource, mutationResource).forEach(
            Consumer { resource: Resource ->
                val schema = loadSchema(resource)
                typeRegistry.merge(schemaParser.parse(schema))
                val schemaWithoutServiceField = schema.replace("_service: Service!", "")
                sdl.append(schemaWithoutServiceField).append("\n")
            }
        )
        return SchemaGenerator().makeExecutableSchema(typeRegistry, buildRuntimeWiring(sdl.toString()))
    }

    private fun buildRuntimeWiring(sdl: String): RuntimeWiring? {
        return RuntimeWiring.newRuntimeWiring()
            .type(
                TypeRuntimeWiring.newTypeWiring("Query")
                    .dataFetcher("getCarInfo", dataFetchers.carInfoFetcher())
                    .dataFetcher("_service") {
                        env ->
                        Service(sdl)
                    }
            )
            .type(
                TypeRuntimeWiring.newTypeWiring("Mutation")
                    .dataFetcher("updateCarInfo", dataFetchers.updateCarInfo())
            )
            .build()
    }

    private fun loadSchema(resource: Resource): String {
        InputStreamReader(resource.inputStream, StandardCharsets.UTF_8).use { reader ->
            return FileCopyUtils.copyToString(reader)
        }
    }

    private class Service internal constructor(val sdl: String)
}
