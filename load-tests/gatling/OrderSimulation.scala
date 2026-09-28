package com.veridian.loadtests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class OrderSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val orderFeeder = Iterator.continually(Map(
    "accountId" -> s"ACC-${scala.util.Random.nextInt(5000)}",
    "symbol" -> (if (scala.util.Random.nextBoolean()) "BTC-USD" else "ETH-USD"),
    "side" -> (if (scala.util.Random.nextBoolean()) "BUY" else "SELL"),
    "price" -> (60000 + scala.util.Random.nextInt(500)).toString,
    "quantity" -> "0.5"
  ))

  val scn = scenario("High-Frequency Order Placement")
    .feed(orderFeeder)
    .exec(
      http("Place Order")
        .post("/api/v1/orders")
        .body(StringBody("""{"accountId":"${accountId}","symbol":"${symbol}","side":"${side}","orderType":"LIMIT","price":${price},"quantity":${quantity},"timeInForce":"GTC"}""")).asJson
        .check(status.is(201))
    )

  setUp(
    scn.inject(
      rampUsersPerSec(100).to(10000).during(1.minute),
      constantUsersPerSec(10000).during(2.minutes)
    ).protocols(httpProtocol)
  )
}
