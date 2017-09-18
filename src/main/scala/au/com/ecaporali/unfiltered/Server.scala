package au.com.ecaporali.unfiltered

import unfiltered.netty.Server.http

/** embedded server */
object Server {
  def main(args: Array[String]): Unit = {
    http(8080)
      .handler(RestaurantHandler(RestaurantQueue()))
      .run()
  }
}
