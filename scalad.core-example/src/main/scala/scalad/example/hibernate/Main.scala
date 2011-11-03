package org.cakesolutions.scaladata.example.hibernate

import javax.persistence.Persistence
import scalad.example.User
/**
 * @author janmachacek
 */
object Main {

  import scalaz.IterV._
  import scalaz._
  import Scalaz._
  import scalad._
  import Scalad._

  def main(args: Array[String]) {
    val entityManagerFactory = Persistence.createEntityManagerFactory("org.cakesolutions.scaladata.core.example")
    val entityManager = entityManagerFactory.createEntityManager()
    val jpa = new JPA(entityManager)

    for (i <- 0 to 20) {
      val u = new User()
      u.setUsername("a" + i)
      jpa.persist(u)
    }

    val users = jpa.select(list[User])
    println(users)

    val m1 = head[User] >>= (u => head map (u2 => (u <|*|> u2)))
    val firstTwo = jpa.select2(m1)
    println(firstTwo("username" like "a2%"))


    val usersWhose = jpa.select2(head[User])
    println( usersWhose("username" like "a1%") )
  }

}