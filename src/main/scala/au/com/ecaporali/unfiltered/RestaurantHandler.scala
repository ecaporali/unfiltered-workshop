package au.com.ecaporali.unfiltered

import unfiltered.directives._
import unfiltered.netty.cycle.Plan.Intent
import unfiltered.netty.{ServerErrorResponse, cycle}
import unfiltered.request.{GET, POST, Path}
import unfiltered.response.ResponseString

@io.netty.channel.ChannelHandler.Sharable
case class RestaurantHandler(queue: Queue) extends cycle.Plan
  with cycle.SynchronousExecution with ServerErrorResponse {

  override def intent = areYouDoneYet orElse cookPlease

  /*
	1. Check out the signatures first.
	2. Directive.Intent takes partial function as an input.
		That's where we can pattern match on the Path
			`case GET(Path("/suchPath")) => ???`
  */
  val cookPlease: Intent = Directive.Intent {
    case POST(Path("/imhungry")) =>
      Waiter.parseRequest.map(maybeReq => {
        val response = for {
          food <- maybeReq
          requestId <- queue.askFor(food)
        } yield requestId

        response.fold(
          err => ResponseString(s"Sorry, there was an ${err.value}"),
          requestId => ResponseString(s"Your requestID is: ${requestId.id}")
        )
      })
  }

  val areYouDoneYet: Intent = Directive.Intent {
    case GET(Path("/imhungry")) =>
      Waiter.parseQuestion.map(maybeReq => {
        val response = for {
          requestId <- maybeReq
          answer <- queue.isItDoneYet(requestId)
        } yield answer

        response.fold(
          err => ResponseString(s"Sorry, there was an ${err.value}"),
          answer => ResponseString(s"Your status is: $answer")
        )
      })
  }

}
