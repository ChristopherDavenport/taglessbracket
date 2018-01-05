package io.christopherdavenport.taglessbracket

import cats.Monad
import cats.implicits._

trait GetAndWrite[F[_]] {

  def Write_ : WriteSomething[F]
  def Get_ : GetSomething[F]
  def M_ : Monad[F]

  def get: F[Int] = M_.flatMap(Get_.getNextInt)(i => M_.flatMap(Write_.info(i))(_ => M_.pure(i)))

}

object GetAndWrite {

  def apply[F[_]](implicit ev: GetAndWrite[F]): GetAndWrite[F] = ev

  implicit def withAlges[F[_]](implicit W: WriteSomething[F], G: GetSomething[F], M: Monad[F]): GetAndWrite[F] =
    new GetAndWrite[F] {

      override def Get_ : GetSomething[F] = G

      override def Write_ : WriteSomething[F] = W

      override def M_ : Monad[F] = M
    }

}
