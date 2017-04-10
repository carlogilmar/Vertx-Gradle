package com.makingdevs

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions

class MainVerticle extends AbstractVerticle {
  @Override
  void start(){
    vertx.deployVerticle("src/main/groovy/com/makingdevs/Verticle.groovy"){ res->
      if(res.succeeded()){

        def text = "4716 9309 9602 0116"

        vertx.eventBus().send("com.carlogilmar.reader", text){ reply ->
          if(reply.succeeded())
            println "* Reader Status: ${reply.result().body()}"
          else
            println "Sin recibir respuesta"
        }
      }
      else{
        println "Deploy error"
      }
    }
  }
}
