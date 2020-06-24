package com.vvalitsky.service

import com.vvalitsky.model.Car
import org.springframework.stereotype.Service

/**
 * Created by Vladislav Valitsky at 24.06.2020
 */

@Service
class CarServiceImpl : CarService {

    // This is just, for example, of course you can use a real database or else :)
    companion object {
        val carInfoStorage = Car(
            model = "718 Cayman S",
            power = "257 kW",
            manufacturer = "Porsche",
            acceleration = "4,6 s"
        )
    }

    override fun getCarInfo(): Car {
        return carInfoStorage
    }

    override fun updateCarInfo(car: Car): Car {
        return carInfoStorage.apply {
            this.acceleration = car.acceleration
            this.manufacturer = car.manufacturer
            this.model = car.model
            this.power = car.power
        }
    }
}
