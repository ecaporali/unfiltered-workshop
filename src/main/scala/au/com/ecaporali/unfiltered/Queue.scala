package au.com.ecaporali.unfiltered

import au.com.ecaporali.unfiltered.Restaurant.{ApiError, Food, RequestId}


trait Queue {
  def askFor(food: Food): Either[ApiError, RequestId]

  def isItDoneYet(id: RequestId): Either[ApiError, String]
}
