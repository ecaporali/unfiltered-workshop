package au.com.ecaporali.unfiltered

import au.com.ecaporali.unfiltered.Restaurant.{Food, RequestId}
import org.scalacheck.Gen.{alphaStr, posNum}

trait Arbitraries {

  def validHttpRequests = for {
    foodValue <- alphaStr suchThat (_.length > 0)
    count <- posNum[Int]
    deliveryType <- alphaStr suchThat (_ == "Deliver") suchThat (_ == "Pick up")
  } yield UnfilteredHttpRequest.POST(
    Map(
      "food" -> Seq(foodValue.toString),
      "quantity" -> Seq(count.toString),
      "deliveryType" -> Seq(deliveryType.toString)
    )
  )

  val invalidHttpRequests = for {
    param1 <- alphaStr
    param2 <- alphaStr
    param3 <- alphaStr
    value1 <- alphaStr
    value2 <- alphaStr
    value3 <- alphaStr
  } yield UnfilteredHttpRequest.POST(Map(param1 -> Seq(value1), param2 -> Seq(value2), param3 -> Seq(value3)))

  val validQuestion = for {
    requestId <- alphaStr suchThat (_.length > 0)
  } yield UnfilteredHttpRequest.GET(Map("requestId" -> Seq(requestId)))

  val invalidQuestion = for {
    param <- alphaStr
    value <- alphaStr
  } yield UnfilteredHttpRequest.GET(Map(param -> Seq(value)))

  val foodRequests = for {
    food <- alphaStr suchThat (_.length > 0)
    quantity <- posNum[Int]
    deliveryType <- alphaStr suchThat (_ == "Deliver") suchThat (_ == "Pick up")
  } yield Food(food, quantity, DeliveryType.withName(deliveryType))

  val questions = for {
    id <- alphaStr suchThat (_.length > 0)
  } yield RequestId(id)

}
