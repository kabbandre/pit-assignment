package com.kabbandre.unicodeconverter

import org.mongodb.scala._
import org.bson.types.ObjectId
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.bson.BsonValue

object MongoDBServer {
  val uri: String = "mongodb+srv://admin:admin@cluster0.jpaeu.mongodb.net/UnicodeConverter?retryWrites=true&w=majority"
  System.setProperty("org.mongodb.async.type", "netty")
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("UnicodeConverter")
  
  val imagesCollection: MongoCollection[Document] = db.getCollection("images")
  val filtersCollection: MongoCollection[Document] = db.getCollection("filters")

  def generateAscii(): String = {
    return "Suka"
  }
  
  def addImage(image: Image): BsonValue = {    
    val doc: Document = Document(
      "_id" -> 0,
      "title" -> image.title,
      "width" -> image.width,
      "filterId" -> image.filterId,
      "image" -> image.image,
      "createdAt" -> image.createdAt,
      "processedImage" -> image.processedImage
    )

    val e = imagesCollection.insertOne(doc)
    val r = Await.result(e.toFuture(), Duration.Inf)

    println(r)
    r.getInsertedId()
  }

  def getImage(id: ObjectId): Document = {
    val e = imagesCollection.find(Document("_id" -> id)).first()
    val r = Await.result(e.toFuture(), Duration.Inf)

    println(r)
    r
  }

  def getImages(): Seq[Document] = {
    val e = imagesCollection.find()
    val r = Await.result(e.toFuture(), Duration.Inf)

    println(r)
    r
  }

  def getFilters(): Seq[Document] = {
    val e = filtersCollection.find()
    val r = Await.result(e.toFuture(), Duration.Inf)

    println(r)
    r
  }
}
