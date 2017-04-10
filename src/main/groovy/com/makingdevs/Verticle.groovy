package com.makingdevs
vertx.eventBus().consumer("com.carlogilmar.reader"){ message ->
  def text = message.body()
  message.reply("[It Works]")

  def instruction = []
  def counter = 0

  if(instruction.size>5){
    println "counter mayor a cinco"
    println counter
    vertx.eventBus().send("com.carlogilmar.showInstruction", instruction)
  }
  text.eachLine{ item ->
    vertx.eventBus().send("com.carlogilmar.prepareCard", item){replyCardComplete ->
      if(replyCardComplete.succeeded()){
        println "---------------"*5
        println replyCardComplete.result().body().dump()
        println "---------------"*5
        instruction.add(replyCardComplete.result().body())
        println "Tamanio de lista ${instruction.size}"
      }
    }
    counter++
  }

}

vertx.eventBus().consumer("com.carlogilmar.showInstruction"){message->
  println "Recibiendo el bonch de tarjetazos"
  println message.body().dump()
}

vertx.eventBus().consumer("com.carlogilmar.prepareCard"){ message ->
  def card = message.body()
  vertx.eventBus().send("com.carlogilmar.services", card){ reply ->
    if(reply.succeeded()){
      message.reply(reply.result().body())
    }
    else{ println "No hay respuesta de -services verticle-" }
  }
}

vertx.eventBus().consumer("com.carlogilmar.services"){ message ->
  def creditCard = message.body()
  def resultCreditCard = [id:creditCard]

  vertx.eventBus().send("com.carlogilmar.ccvsServices", creditCard){ reply->
    if(reply.succeeded()){
      resultCreditCard.ccvs=reply.result().body()
    }
    else{
      println "Sin respuesta de ccvsServices"
    }
  }

  vertx.eventBus().send("com.carlogilmar.database", creditCard){ reply->
    if(reply.succeeded()){
      resultCreditCard.db=reply.result().body()
      //respuesta del verticle
      message.reply(resultCreditCard)
    }
    else{
      println "Sin respuesta de database"
    }
  }
}

vertx.eventBus().consumer("com.carlogilmar.ccvsServices"){ message ->
  message.reply("${message.body()} 123")
}
vertx.eventBus().consumer("com.carlogilmar.database"){ message ->
  message.reply("${message.body()} qwery01")
}
