package au.com.ecaporali.unfiltered

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties
import unfiltered.response.ResponseString

object RestaurantHandlerSpec extends Properties("Handler") with Arbitraries {
  property("askForFoodHandler:valid request") = forAll(validHttpRequests)(request => {
    RestaurantHandler(NoopQueue).cookPlease(request) ==
      ResponseString("Your requestID is: such-id")
  })

  property("areYouDoneYet:valid response") = forAll(validQuestion)(question => {
    RestaurantHandler(NoopQueue).areYouDoneYet(question) ==
      ResponseString("Your status is: ok")
  })

  property("askForFoodHandler:invalid request") = forAll(invalidHttpRequests)(request => {
    RestaurantHandler(NoopQueue).cookPlease(request) ==
      ResponseString("Sorry, there was an oops")
  })

  property("areYouDoneYet:invalid response") = forAll(invalidQuestion)(question => {
    RestaurantHandler(NoopQueue).areYouDoneYet(question) ==
      ResponseString("Sorry, there was an oops")
  })
}
