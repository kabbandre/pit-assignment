package com.kabbandre.unicodeconverter

import org.mongodb.scala._
import org.bson._

object MongoDBServer {
  val uri: String = "mongodb+srv://admin:admin@cluster0.jpaeu.mongodb.net/UnicodeConverter?retryWrites=true&w=majority"
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("UnicodeConverter")
  val filters = db.getCollection("Filters")
  val pictures = db.getCollection("Pictures")
}
