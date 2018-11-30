package domain

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val config = ConfigFactory.load("configuration")
    val akkaConf = config.getConfig("adp-client")
    val system = ActorSystem("adpSystem", akkaConf)

    system.actorOf(Props[StupidPrisonerActor])
  }
}

class StupidPrisonerActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case unhandled =>
      log.error(s"received message [${unhandled.getClass.getSimpleName}] but didn't handle it!")

  }
}
