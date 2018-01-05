package io.christopherdavenport.taglessbracket

import cats._
import cats.implicits._
import cats.effect.Sync

trait GetSomething[F[_]] {
  def getNextInt: F[Int]
}

object GetSomething {

  def apply[F[_]](implicit ev: GetSomething[F]) = ev

  def alwaysGet[F[_]: Applicative](i: Int): GetSomething[F] = new GetSomething[F] {
    override def getNextInt: F[Int] = i.pure[F]
  }

  def refGet[F[_]](implicit F: Sync[F]): F[GetSomething[F]] =
    fs2.async.refOf(0).map(getSomethingFromRef[F])

  def getSomethingFromRef[F[_]: Sync](ref: fs2.async.Ref[F, Int]): GetSomething[F] = {
    new GetSomething[F] {
      override def getNextInt: F[Int] = ref.modify(_ + 1).map(_.now)
    }
  }

}
