package com.makingdevs

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions

class MainVerticle extends AbstractVerticle {
  @Override
  void start(){
    vertx.deployVerticle("src/main/groovy/com/makingdevs/InitVerticle.groovy")
  }
}


/*
* For example:
* You can deploy a main verticle with this:
* Remember write the complete path to the verticles

def options = [instances:4]

vertx.deployVerticle("com/makingdevs/BatchCardVerticle.groovy")
vertx.deployVerticle("com/makingdevs/WsVerticle.groovy")
vertx.deployVerticle("com/makingdevs/ProcessorVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/ClientWsCardVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/StatusVerticle.groovy")

*/
