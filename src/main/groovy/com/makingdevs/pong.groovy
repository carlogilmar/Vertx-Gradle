import io.vertx.ext.web.client.WebClient
def client = WebClient.create(vertx)
def verticleId = UUID.randomUUID()
println "Pong Arriba ${verticleId}"

vertx.eventBus().consumer("com.cg.pong"){ message ->

  def pongMsg = message.body()
  pongMsg.step++
  println "Pong Consumer On: Increase Step ${pongMsg.step}"

  if(pongMsg.step <= 500000){

    println "${verticleId} atediendo peticion para task ${pongMsg.step}"

    client.post(5000, "localhost", "/todos").sendJsonObject([task:"Task ${pongMsg.step} Agregando desde Vertx!"], { ar ->
      if (ar.succeeded()) {
        vertx.eventBus().send("com.cg.ping", pongMsg)
        println "  Done request ${pongMsg.step}"
      }
      else{
        println " >>> >>> Error en request ${pongMsg.step}"
        vertx.eventBus().send("com.cg.ping", pongMsg)
      }
    })

  }else{
    println "***************************************"
    println "Process Ended Ok"
    println "***************************************"
  }
}
