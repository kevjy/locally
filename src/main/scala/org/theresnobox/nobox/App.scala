package org.theresnobox.nobox

import cats.data.EitherT
import cats.effect.IO
import java.io.File
import java.security.MessageDigest
import scala.io.Source

object App {

  def main(args: Array[String]): Unit = {
    println("Hello world!")
  }

  def ls(f: File): EitherT[IO, Throwable, Seq[File]] = 
    EitherT(IO {
      f.listFiles.toSeq
    }.attempt)

  def empty: EitherT[IO, Throwable, Map[String, String]] =
    EitherT.rightT[IO, Throwable](Map())

  def read(f: File): EitherT[IO, Throwable, Map[String, String]] = 
    if (f.isFile) EitherT(
      IO {
        Map(f.getName -> Source.fromFile(f).mkString)
      }.attempt)
    else if (f.isDirectory) for {
        contents <- ls(f)
        strContents <- contents.foldLeft(empty) {
          (acc, con) => for {
            a <- acc
            c <- read(con)
          } yield a ++ c
        }
      } yield strContents
    else empty

  def getSha(f: File): EitherT[IO, Throwable, Seq[(String, String)]] = for {
    shas <- read(f)
  } yield shas.toSeq map {
    case (name, sha) =>
      (
        name,
        MessageDigest
          .getInstance("SHA-256")
          .digest(sha.getBytes("UTF-8"))
          .map("%02x".format(_))
          .mkString
      )
  }

}
