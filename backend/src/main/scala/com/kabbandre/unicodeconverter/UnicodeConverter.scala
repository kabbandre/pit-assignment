package com.kabbandre.unicodeconverter

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
import scalaz.concurrent.Task
import org.bson.types._
import scalaz.effect.IO
import org.bson.{BsonValue, BsonBinary, BsonDateTime}
import org.mongodb.scala._
import org.mongodb.scala.bson.collection.mutable

object ImageModels {
  type ImageId = ObjectId
  type Width = Int  
  type ImageFile = BsonBinary
  type CreatedAt = BsonDateTime
  type ProcessedImage = String
  type Title = String
}

object FilterModels {
  type FilterId = ObjectId
  type FilterName = String
}

case class Image (title: ImageModels.Title, width: ImageModels.Width, filterId: FilterModels.FilterId, image: ImageModels.ImageFile, createdAt: ImageModels.CreatedAt, processedImage: ImageModels.ProcessedImage)
case class ImageWithId (title: ImageModels.Title, id: ImageModels.ImageId, width: ImageModels.Width, filterId: FilterModels.FilterId, image: ImageModels.ImageFile, createdAt: ImageModels.CreatedAt, processedImage: ImageModels.ProcessedImage)
case class Filter (filterId: FilterModels.FilterId, name: FilterModels.FilterName)

trait UnicodeConverterRepo {
  def generateAscii(): String
  def addImage(image: Image): BsonValue
  def getImage(id: ImageModels.ImageId): Document
  def getImages(): Seq[Document]
  def getFilters(): Seq[Document]
}

object Impl extends UnicodeConverterRepo {    
  override def generateAscii(): String = MongoDBServer.generateAscii()
  override def addImage(image: Image): BsonValue = MongoDBServer.addImage(image)
  override def getImage(id: ImageModels.ImageId) = MongoDBServer.getImage(id)
  override def getImages() = MongoDBServer.getImages()
  override def getFilters() = MongoDBServer.getFilters()
}

object UnicodeConverter {  
  import Impl._

  def jsonizeDocs(cDocuments: Seq[Document]): String = {
    val sb = new StringBuilder
    for (doc <- cDocuments) {
      if (sb.nonEmpty) {
        sb.append(",")
      }
      sb.append(doc.toJson)
    }
    sb.toString
  }
  def jsonizeDoc(cDocument: Document): String = {
    val sb = new StringBuilder
    sb.append(cDocument.toJson)
    sb.toString
  }

  def generateImage (req: Request): Task[Response] = {
    Ok(generateAscii())
  }
  def saveImage (req: Request): Task[Response] = {
    Ok("Save")
  }
  def getAllImages (): Task[Response] = {
    Ok(jsonizeDocs(getImages()))
  }
  def getOneImage (id: ImageModels.ImageId): Task[Response] = {
    Ok(jsonizeDoc(getImage(id)))
  }
  val service = HttpService {
    case req @ POST -> Root / "generate" => generateImage(req)
    case req @ POST -> Root / "save" => saveImage(req)
    case GET -> Root => getAllImages
    case GET -> Root / id => getOneImage(new ObjectId(id))
  }
}