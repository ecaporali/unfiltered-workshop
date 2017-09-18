package au.com.ecaporali.unfiltered

import au.com.ecaporali.unfiltered.Restaurant.RequestId

object NoopQueue extends Queue {
  override def askFor(food: Restaurant.Food) = Right(RequestId("such-id"))

  override def isItDoneYet(id: Restaurant.RequestId) = Right("ok")
}
