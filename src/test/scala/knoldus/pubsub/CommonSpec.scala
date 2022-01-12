/*
 * Copyright (c) 2022 knoldus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package knoldus.pubsub

import knoldus.pubsub.message.PubSubMessageBuilder
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

import java.util.concurrent.{ ExecutorService, Executors }
import scala.concurrent.{ ExecutionContext, ExecutionContextExecutor }

trait CommonSpec extends AnyFlatSpec with ScalaFutures with MockitoSugar with Matchers {

  val singleThreadPool: ExecutorService     = Executors.newSingleThreadExecutor()
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(singleThreadPool)

  protected def getPubSubMessage(messageId: String, message: String) = {
    val pubSubMessageBuilder = PubSubMessageBuilder.make
    val pubSubMessage        = pubSubMessageBuilder
      .withMessage(message)
      .withMessageId(messageId)
      .build
    pubSubMessage
  }

}
