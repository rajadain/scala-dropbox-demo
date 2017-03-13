package com.tuhinanshu.scala

import scala.collection.JavaConversions._
import java.io.{BufferedReader, FileOutputStream, InputStreamReader}

import com.dropbox.core.{DbxAppInfo, DbxRequestConfig, DbxWebAuth}
import com.dropbox.core.v2.DbxClientV2

import scala.util.Try


object Main extends App {
  import Credentials._

  val appInfo = new DbxAppInfo(app_key, app_secret)
  val config = new DbxRequestConfig("azavea/rf-dropbox-test")

  val webAuth = new DbxWebAuth(config, appInfo)
  val authRequest = DbxWebAuth.newRequestBuilder()
    .withNoRedirect()
    .build()
  val authUrl = webAuth.authorize(authRequest)

  println(s"1. Go to: ${authUrl}")
  println("2. Click 'Allow' (you may have to log in)")
  println("3. Copy the authrozation code and paste here:")

  val code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim()
  val authFinish = webAuth.finishFromCode(code)

  println(s"Access Token = ${authFinish.getAccessToken}")

  val client = new DbxClientV2(config, authFinish.getAccessToken)

  /** BASIC ACCOUNT TEST **/
  println("==> TEST BASIC ACCOUNT DETAILS")
  val account = client.users.getCurrentAccount
  println(s"Linked Account is ${account.getName.getDisplayName}")

  /** UPLOAD ITEM TEST **/
  println("==> TEST UPLOADING LOCAL FILE")
  val localResourcePath = "/jabberwocky.txt"
  val targetDropboxPath = "/jabberwocky.txt"
  val inputStream = getClass.getResourceAsStream(localResourcePath)
  try {
    val metadata = client.files
      .uploadBuilder(targetDropboxPath)
      .uploadAndFinish(inputStream)

    println(s"Uploaded file ${metadata.getPathLower}")
  } finally {
    inputStream.close()
  }

  /** LIST ITEMS TEST **/
  // NOTE getEntries returns a Java.util.List, which
  // is mutable, since it fetches only the first N
  // entries. May have to find a way around this.
  // We use JavaConversions to automatically convert
  // to a Scala list.
  // See https://dropbox.github.io/dropbox-sdk-java/api-docs/v2.1.x/com/dropbox/core/v2/files/DbxUserFilesRequests.html#listFolder-java.lang.String-
  // and also https://www.dropbox.com/developers/documentation/java#tutorial
  println("==> TEST LISTING FILES")
  val items = client.files.listFolder("").getEntries
  for (item <- items)
    println(item.getPathLower)

  /** DOWNLOAD ITEMS TEST **/
  println("==> DOWNLOAD ITEM TEST")
  val outputStream = new FileOutputStream("jabberwocky.txt")
  val downloadedFile = Try {
    client.files
      .downloadBuilder(targetDropboxPath)
      .download(outputStream)
  }
  outputStream.close()

  println(s"Downloaded file ${downloadedFile}")
}
