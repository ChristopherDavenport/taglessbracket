package io.christopherdavenport.taglessbracket

import cats.implicits._
import cats.effect.Sync
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl

object GetService {

  def getServiceHttp[F[_]: GetAndWrite: Sync]: HttpService[F] = {
    object dsl extends Http4sDsl[F]
    import dsl._

    HttpService[F]{
      case GET -> Root / "get" => Ok(GetAndWrite[F].get.map(_.show))
    }

  }

}
