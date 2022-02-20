import zio._
import zio.random.nextInt


import java.io.Serializable
import scala.language.postfixOps

object Main extends App {

  import zio.console._

  val failed : ZIO[Console,Serializable,Unit]  =
    putStrLn("About to fail ....") *>
    ZIO.fail("Uh oh!") *>
    putStr("This will never be printed")

  def repeat[R,E,A](n: Int)(effect: ZIO[R,E,A]): ZIO[R,E,A] =
    if (n <=1) effect
    else effect *> repeat(n-1)(effect)

  val interactive  = putStrLn("What is your name") *>
    getStrLn.flatMap(name => putStrLn(s"Hello ${name}!") )

  val interactiveFor  = (for {
    _    <- putStrLn("What is your name")
    name <- getStrLn
    _    <- putStrLn(s"Hello $name from a for compreh")
  } yield 0) orElse ZIO.succeed(1)

  def analyzeAnswer(random: Int, guess: String) = if (random.toString == guess.trim) putStr("You guessed correctly")
  else putStr(s"You did not guessed correctly. The correct answer is ${random}")

  val numerGuess = (for {
    random <- nextInt
    _      <- putStrLn("Please guess a number from 0 to 3: ")
    guess  <- getStrLn
    _      <- analyzeAnswer(random,guess)
  } yield  0) orElse ZIO.succeed(1)



  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    //val cat  = (failed as 0) orElse ZIO.succeed(1)

    //failed.fold(_ => 1,  _ => 0).exitCode

    //val other = failed as 0
    //other.catchAllCause(cause => putStrLn(s"${cause.prettyPrint}")).exitCode

    //val times = 3
    //repeat(times)(putStrLn(s"This should repeat ${times} times").exitCode)

    //interactive.exitCode

    //interactiveFor.exitCode

    numerGuess.exitCode

    
    //cat exitCode
    //putStrLn("Welcome to your first ZIO app!").exitCode

    //print-sequence
    //putStrLn("Hello") *> putStrLn("Alex") exitCode

  }


}


