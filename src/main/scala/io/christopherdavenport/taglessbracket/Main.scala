package io.christopherdavenport.taglessbracket

import cats.effect.IO
import fs2._
import org.http4s.server.blaze.BlazeBuilder

object Main extends StreamApp[IO] {

  override def stream(args: List[String], requestShutdown: IO[Unit]): Stream[IO, StreamApp.ExitCode] = {
    for {
      getSomething <- Stream.eval(GetSomething.refGet[IO])
      exitCode <- {
        implicit val somethin = getSomething
        implicit val writeSomething = WriteSomething.stdout[IO]
        implicit val getAndWrite = GetAndWrite.withAlges[IO]

        BlazeBuilder[IO]
          .mountService(GetService.getServiceHttp[IO])
          .bindHttp(8080, "0.0.0.0")
          .serve
      }

    } yield exitCode

  }
//
//  def main(args: Array[String]): Unit = {
//    Program.myMain[IO].unsafeRunSync()
//  }

}