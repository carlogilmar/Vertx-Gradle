vertx.eventBus().consumer("com.cg.ping"){ message ->
  def pingMsg = message.body()
  pingMsg.step++
  println "Ping Consumer On: Increase Step ${pingMsg.step}"
  vertx.eventBus().send("com.cg.pong", pingMsg)
}
