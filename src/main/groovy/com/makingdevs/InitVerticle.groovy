println "1.- Vertx Application is done!"

def options = [instances:100]

vertx.deployVerticle("src/main/groovy/com/makingdevs/ping.groovy"){ ping ->
  if(ping.succeeded()){
    println "2.- Ping Verticle is ready!!"

    vertx.deployVerticle("src/main/groovy/com/makingdevs/pong.groovy", options){pong ->
      if(pong.succeeded()){
        println "3.- Pong Verticle is ready!!"
        vertx.eventBus().send("com.cg.ping", [step:0, task:"Running Vertx"] )
      }
    }
  }
}
