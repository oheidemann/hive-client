package domain

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object Main extends App {

  override def main(args: Array[String]): Unit = {

    val config = ConfigFactory.load("configuration")
    val akkaConf = config.getConfig("adp-client")
    val system = ActorSystem("adpSystem", akkaConf)

    system.actorOf(Props[RandomPrisonerActor])
  }
}

class RandomPrisonerActor extends Actor with ActorLogging {
  import domain.PrisonerProtocol._

  val name = UUID.randomUUID().toString

  val rnd = new scala.util.Random(System.currentTimeMillis())

  def receive = {
    case Captured(prison) =>
      log.info(s"being captured by Prison[$prison]")
      prison ! CheckIn(name)

    case Imprisoned(message) =>
      log.info(s"got imprisoned: [$message]")

    case KickedOut(reason) =>
      log.info(s"got kicked out: [$reason]")
      context.system.stop(self)

    case Question(accomplice) =>
      log.debug(s"questioning starts against Accomplice[$accomplice]")
      sender() ! PrisonerResponse(if (rnd.nextBoolean()) PrisonerDecision.Accuse else PrisonerDecision.Refuse)

    case QuestionResult(accomplice, decision) =>
      log.info(s"questioning ends against Accomplice[$accomplice] and Decision[$decision]")

    case unknown =>
      log.debug(s"received unknown message [${unknown.getClass.getSimpleName}]")
  }
}