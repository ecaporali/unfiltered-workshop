package au.com.ecaporali.unfiltered

import au.com.ecaporali.unfiltered.DeliveryType.DeliveryType
import au.com.ecaporali.unfiltered.Restaurant.{ApiError, Food, RequestId}
import unfiltered.directives.Directives._
import unfiltered.directives.{Directive, data}

object Waiter {

  /**
    * 1. Check out data.as.Int named "quantity"
    * `named` is a function which gives you back directive.
    * 2. Directives have monadic behaviour.
    * Which means you can compose them together.
    * How?
    * Check out flatMap or for comprehension?
    * 3. One more challange: how to convert Option into Either?
    * Check out pattern matching or fold function.
    **/

  val parseRequest: Directive[Any, Nothing, Either[ApiError, Food]] = {
    val quantity = data.as.Int.named("quantity")
    val food = data.as.String.named("food")

    val reqDelivery = "Deliver".r
    val regPickup = "PickUp".r

    val deliveryType: Directive[Any, Nothing, Option[DeliveryType]] =
      data.as.String.named("deliveryType").map {
        case Some(reqDelivery(_)) => Some(DeliveryType.DELIVER)
        case Some(regPickup(_)) => Some(DeliveryType.PICK_UP)
        case None => None
      }

    val thing: Directive[Any, Nothing, (Option[String], Option[Int], Option[DeliveryType])] = for {
      maybeFood <- food
      maybeQuantity <- quantity
      maybeDeliveryType <- deliveryType
    } yield (maybeFood, maybeQuantity, maybeDeliveryType)

    thing.map {
      case (Some(f), Some(q), Some(d)) => Right(Food(f, q, d))
      case _ => Left(ApiError("oops"))
    }
  }

  val parseQuestion: Directive[Any, Nothing, Either[ApiError, RequestId]] = {
    val requestId = data.as.String.named("requestId")

    for {
      maybeRequestId <- requestId
    } yield maybeRequestId match {
      case Some(reqId) => Right(RequestId(reqId))
      case None => Left(ApiError("oops"))
    }
  }
}
