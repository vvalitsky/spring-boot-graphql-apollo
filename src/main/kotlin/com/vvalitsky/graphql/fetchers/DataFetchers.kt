package com.vvalitsky.graphql.fetchers

import com.fasterxml.jackson.databind.ObjectMapper
import com.vvalitsky.model.Car
import com.vvalitsky.service.CarService
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

/**
 * Created by Vladislav Valitsky at 24.06.2020
 */

@Component
class DataFetchers(
    private val carService: CarService,
    private val objectMapper: ObjectMapper
) {

    fun carInfoFetcher(): DataFetcher<Car> {
        return DataFetcher {
            carService.getCarInfo()
        }
    }

    fun updateCarInfo(): DataFetcher<Car> {
        return DataFetcher { env: DataFetchingEnvironment ->
            carService.updateCarInfo(objectMapper.convertValue(
                env.getArgument("input"),
                Car::class.java
            ))
        }
    }
}
