package au.com.ecaporali.unfiltered

import au.com.ecaporali.unfiltered.Restaurant.{ApiError, Food, RequestId}

trait DB {
  def insert(food: Food): Either[ApiError, RequestId]

  def get(request: RequestId): Either[ApiError, String]
}
