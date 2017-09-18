package au.com.ecaporali.unfiltered

import au.com.ecaporali.unfiltered.Restaurant.{ApiError, Food, RequestId}
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import unfiltered.directives.Result.Success

object WaiterSpec extends Properties("Waiter") with Arbitraries {

  property("parseValidRequest") = forAll(validHttpRequests) {
    request => {
      Waiter.parseRequest(request) ==
        Success(Right(Food(
          food = request.parameterValues("food").head,
          quantity = request.parameterValues("quantity").head.toInt,
          deliveryType = DeliveryType.withName(request.parameterValues("deliveryType").head))))
    }
  }

  property("parseInvalidRequest") = forAll(invalidHttpRequests)(request => {
    Waiter.parseRequest(request) == Success(Left(ApiError("oops")))
  })

  property("parseValidQuestion") = forAll(validQuestion)(question => {
    Waiter.parseQuestion(question) ==
      Success(Right(RequestId(
        id = question.parameterValues("requestId").head)))
  })

  property("parseInvalidQuestion") = forAll(invalidQuestion)(question => {
    Waiter.parseQuestion(question) == Success(Left(ApiError("oops")))
  })

}
