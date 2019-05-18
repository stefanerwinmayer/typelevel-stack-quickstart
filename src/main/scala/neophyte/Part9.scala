package neophyte

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

object Part9 {

  def main(args: Array[String]): Unit = {
    val f = Future {
      "Hello world!"
    }
    val taxCut = Promise[TaxCut]()
    val taxCut2: Promise[TaxCut] = Promise()
    val taxCutF: Future[TaxCut] = taxCut.future
    taxCut.success(TaxCut(20))

    val taxCutF2 = Government.redeemCampaignPledge()
    println("Now that they're elected, let's see if they remember their promises...")
    taxCutF2.onComplete {
      case Success(TaxCut(reduction)) =>
        println(s"A miracle! They really cut our taxes by $reduction percentage points!")
      case Failure(ex) =>
        println(s"They broke their promises! Becaus of a ${ex.getMessage}")
    }

    Thread.sleep(3000)
  }
}

case class TaxCut(reduction: Int)

case class LameExcuse(str: String) extends Exception(str)

object Government {
  def redeemCampaignPledge(): Future[TaxCut] = {
    val p = Promise[TaxCut]()
    Future {
      println("Starting the new legislative period.")
      Thread.sleep(2000)
      //      p.success(TaxCut(20))
      p.failure(LameExcuse("global economic crisis"))
      //      println("We reduced the taxes! You must reelect us!!!!1111")
      println("We didn't fulfill our promises, but surely they'll understand.")
    }
    p.future
  }
}
