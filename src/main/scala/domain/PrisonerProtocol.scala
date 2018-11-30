package domain

import akka.actor.ActorRef


object PrisonerProtocol {
  // messages to be received by your actor

  //server discovered you and ask for a check in
  case class Captured(prison: ActorRef)

  //confirmation of a successful check in
  case class Imprisoned(message: String)

  //Prison set you free, no more questioning for you
  case class KickedOut(reason: String)

  //Questioning started, you're facing accomplice
  case class Question(accomplice: String)

  //the accomplice answer to the last questioning
  case class QuestionResult(accomplice: String, decision: PrisonerDecision)



  // messages to be send by your actor

  //join the prison und the given name
  case class CheckIn(name: String)

  //answer to the last questioning
  case class PrisonerResponse(decision: PrisonerDecision)

  ////////
  sealed trait PrisonerDecision
  object PrisonerDecision {
    case object Accuse extends PrisonerDecision     //Accuse / Cooperate
    case object Refuse extends PrisonerDecision     //Stay Silent / Defect
  }
}
