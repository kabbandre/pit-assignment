package com.kabbandre.unicodeconverter

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
import scalaz.concurrent.Task

object UnicodeConverter {
  def generateImage (req: Request): Task[Response] = {
    Ok("Generate")
  }
  def saveImage (req: Request): Task[Response] = {
    Ok("Save")
  }
  def getAllImages (): Task[Response] = {
    Ok("Get all images")
  }
  def getOneImage (id: String): Task[Response] = {
    Ok("get image " + id)
  }
  val service = HttpService {
    case req @ POST -> Root / "generate" => generateImage(req)
    case req @ POST -> Root / "save" => saveImage(req)
    case GET -> Root => getAllImages
    case GET -> Root / id => getOneImage(id)
  }
}