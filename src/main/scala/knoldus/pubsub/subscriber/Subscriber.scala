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

package knoldus.pubsub.subscriber

import com.google.cloud.pubsub.v1

import scala.concurrent.{ ExecutionContext, Future }

class Subscriber(subscriber: v1.Subscriber)(implicit ec: ExecutionContext) {

  def startAsync(): Future[Unit] = Future(this.subscriber.startAsync).map(_ => ())

  def awaitTerminated(): Future[Unit] = Future(this.subscriber.awaitTerminated()).map(_ => ())

  def stopAsync(): Future[Unit] = Future(this.subscriber.stopAsync).map(_ => ())

}
