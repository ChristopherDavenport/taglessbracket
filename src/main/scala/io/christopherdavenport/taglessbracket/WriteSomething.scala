package io.christopherdavenport.taglessbracket

import cats._
import cats.effect.Sync
import cats.implicits._
import org.log4s

trait WriteSomething[F[_]] {
  def error[A: Show](a: A): F[Unit]
  def warn[A: Show](a: A): F[Unit]
  def info[A: Show](a: A): F[Unit]
  def trace[A: Show](a: A): F[Unit]
}

object WriteSomething{

  def apply[F[_]](implicit ev: WriteSomething[F]): WriteSomething[F] = ev

  implicit def stdout[F[_]](implicit F: Sync[F]): WriteSomething[F] = new WriteSomething[F] {
    override def error[A: Show](a: A): F[Unit] = Sync[F].delay(log4s.getLogger.error(a.show))
    override def warn[A: Show](a: A): F[Unit] = Sync[F].delay(log4s.getLogger.warn(a.show))
    override def trace[A: Show](a: A): F[Unit] = Sync[F].delay(log4s.getLogger.trace(a.show))
    override def info[A: Show](a: A): F[Unit] = Sync[F].delay(log4s.getLogger.info(a.show))
  }
}
