package domain

import akka.actor.ActorRef


object PrisonerProtocol {
  trait Message

  case class Captured(prison: ActorRef) extends Message
  case class CheckIn(name: String) extends Message
  case class Imprisoned(message: String) extends Message
  case class KickedOut(reason: String) extends Message

  case class Question(accomplice: String) extends Message
  sealed trait PrisonerDecision
  object PrisonerDecision {
    case object Accuse extends PrisonerDecision     //Accuse / Cooperate
    case object Refuse extends PrisonerDecision     //Stay Silent / Defect
  }
  case class PrisonerResponse(decision: PrisonerDecision) extends Message
  case class QuestionResult(accomplice: String, decision: PrisonerDecision) extends Message
}
