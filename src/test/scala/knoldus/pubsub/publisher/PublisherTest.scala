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

package knoldus.pubsub.publisher

import com.google.api.core.ApiFuture
import com.google.cloud.pubsub.v1
import com.google.pubsub.v1.PubsubMessage
import knoldus.pubsub.CommonSpec
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.when

import java.util.concurrent.{ Executor, TimeUnit }

class PublisherTest extends CommonSpec {

  behavior.of("PublisherTest")

  val mocked: v1.Publisher = mock[v1.Publisher]

  it should "publish a message" in {
    val publisher     = new Publisher(mocked)
    val pubSubMessage = getPubSubMessage("id", "message")

    when(mocked.publish(any[PubsubMessage])).thenReturn(new ApiFuture[String] {
      override def addListener(listener: Runnable, executor: Executor): Unit = ()

      override def cancel(mayInterruptIfRunning: Boolean): Boolean = false

      override def isCancelled: Boolean = false

      override def isDone: Boolean = true

      override def get(): String = "message"

      override def get(timeout: Long, unit: TimeUnit): String = "message"
    })

    val publish = publisher.publish(pubSubMessage).futureValue
    publish should be("message")
  }

  it should "shutdown publisher" in {
    val publisher = new Publisher(mocked)
    publisher.shutdown().futureValue should be(())
  }

}
