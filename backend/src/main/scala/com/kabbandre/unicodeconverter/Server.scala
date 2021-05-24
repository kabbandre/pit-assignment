package com.kabbandre.unicodeconverter

import scala.util.Properties.envOrNone
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.ProcessApp
import scalaz.concurrent.Task
import scalaz.stream.Process

object Server extends ProcessApp {
  val port: Int = envOrNone("HTTP_PORT").fold(8080)(_.toInt)
  val urlSuffix = "/api"

  def process(args: List[String]): Process[Task, Nothing] = BlazeBuilder
    .bindHttp(port)
    .mountService(UnicodeConverter.service, urlSuffix)
    .serve
}
