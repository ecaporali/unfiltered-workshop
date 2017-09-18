package au.com.ecaporali.unfiltered

import au.com.ecaporali.unfiltered.DeliveryType.DeliveryType

object Restaurant {

  case class Food(food: String, quantity: Int, deliveryType: DeliveryType)

  case class RequestId(id: String)

  case class ApiError(value: String)

}
