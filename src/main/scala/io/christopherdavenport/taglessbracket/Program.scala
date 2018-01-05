package io.christopherdavenport.taglessbracket

import cats.effect.Sync
import cats.implicits._

object Program {

  def myMain[F[_]: Sync] : F[Unit] = {
//    val getAlgebra: F[GetSomething[F]] = GetSomething.refGet[F]
    val getAlgebra: F[GetSomething[F]] = GetSomething.alwaysGet[F](5).pure[F]

   getAlgebra.flatMap { gs =>
      implicit val gS = gs
      implicit  val write = WriteSomething.stdout[F]
      implicit val gW = GetAndWrite.withAlges[F]

      gW.get >> gW.get >> gW.get >> gW.get.void
    }

  }

}
