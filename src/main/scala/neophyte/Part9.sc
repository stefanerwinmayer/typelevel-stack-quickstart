import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future, Promise}

val f = Future {
  "Hello world"
}

case class TaxCut(reduction: Int)

val taxCut = Promise[TaxCut]()
val taxCut2: Promise[TaxCut] = Promise()
val taxCutF: Future[TaxCut] = taxCut.future
taxCut.success(TaxCut(20))

object Government {
  def redeemCampaignPledge(): Future[TaxCut] = {
    val p = Promise[TaxCut]()
    Future {
      println("Starting the new legislative period.")
      Thread.sleep(2000)
      p.success(TaxCut(20))
      println("We reduced the taxes! You must reelect us!!!!1111")
    }
    p.future
  }
}

val executorService = Executors.newFixedThreadPool(4)
val executionContext = ExecutionContext.fromExecutorService(executorService)
