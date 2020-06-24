package com.vvalitsky.service

import com.vvalitsky.model.Car

/**
 * Created by Vladislav Valitsky at 24.06.2020
 */

interface CarService {
    fun getCarInfo(): Car
    fun updateCarInfo(car: Car): Car
}
