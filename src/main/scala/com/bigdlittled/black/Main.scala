package com.bigdlittled.black

import cats._
import cats.tagless._
import cats.tagless.implicits._
import cats.implicits._
import util.Try

object Main extends App {

  println("Hello, Black!")

  @finalAlg @autoFunctorK
  trait ExpressionAlg[F[_]] {
    def num(i: String): F[Float]
    def divide(dividend: Float, divisor: Float): F[Float]
  }

  @finalAlg @autoFunctorK
  trait Increment[F[_]] {
    def plusOne(i: Int): F[Int]
  }

  implicit object tryExpression extends ExpressionAlg[Try] {
    def num(i: String) = Try(i.toFloat)
    def divide(dividend: Float, divisor: Float) = Try(dividend / divisor)
  }

  implicit val fk : Try ~> Option = λ[Try ~> Option](_.toOption)

  val optionExpression = tryExpression.mapK(fk)

  println(optionExpression.divide(2, 4))
//println(ExpressionAlg[Option].divide(2,4))

/*
  implicit object incTry extends Increment[Try] {
    def plusOne(i: Int) = Try(i + 1)
  }

  def program[F[_]: Monad: Increment](i: Int): F[Int] = for {
    j <- IntryExpressioncrement[F].plusOne(i)
    z <- if (j < 10000) program[F](j) else Monad[F].pure(j)
  } yield z

  program[Try](0)
*/
}
