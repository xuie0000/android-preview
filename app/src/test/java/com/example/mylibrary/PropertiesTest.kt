package com.example.mylibrary

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

object PropertiesTest {

  var FILE_AZ_WEB_PATH = "build/config.properties"

  private fun getConfig(type: String): String? {
    return getProperties().getProperty(type)
  }

  private fun putConfig(key: String, value: String) {
    val properties = getProperties()
    properties.setProperty(key, value)
    properties.store(FileOutputStream(File(FILE_AZ_WEB_PATH)), "")
  }

  private fun getProperties(): Properties {
    val properties = Properties()
    val file = File(FILE_AZ_WEB_PATH)
    if (!file.exists()) {
      print("create file")
      file.createNewFile()
    }
    properties.load(FileInputStream(file))
    return properties
  }

  @JvmStatic
  fun main(args: Array<String>) {
    putConfig("supporting_http_address", "http://127.0.0.1:8081")
    print("get config ${getConfig("supporting_http_address")}")
  }
}