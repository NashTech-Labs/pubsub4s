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

package knoldus.pubsub.message

import knoldus.pubsub.CommonSpec

class PubSubMessageBuilderTest extends CommonSpec {

  behavior.of("PubSubMessageBuilderTest")

  it should "make a builder" in {
    val pubSubMessageBuilder = PubSubMessageBuilder.make
    pubSubMessageBuilder shouldBe a[PubSubMessageBuilder]
  }

  it should "build a instance of PubSubMessage" in {
    val pubSubMessageBuilder = PubSubMessageBuilder.make
    val pubSubMessage        = pubSubMessageBuilder.build
    pubSubMessage shouldBe a[PubSubMessage]
  }

  it should "build a instance of PubSubMessage with message and id" in {
    val pubSubMessageBuilder = PubSubMessageBuilder.make
    val pubSubMessage        = pubSubMessageBuilder
      .withMessage("Hello World")
      .withMessageId("id")
      .build
    pubSubMessage.getDataString should be("Hello World")
    pubSubMessage.getMessageId should be("id")
  }

}
