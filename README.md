# Skat Bowling Test

This project is an implementation of the Skat 'Programmerings opgave'

Read purpose [here](https://github.com/skat/bowling-opgave)

Comments about the design
--------
* Get call that returns 11 strikes, would better be encoded as elleven [10] instead of [10, 0], 
since the second ball (0) is never thrown.
Get with response: {"points":[[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0],[10,0]],"token":"iWSE3W88VG2aV66Syy241NhO3nc9B8sj"}
Sould better be;
{"points":[[10],[10],[10],[10],[10],[10],[10],[10],[10],[10],[10]],"token":"iWSE3W88VG2aV66Syy241NhO3nc9B8sj"}


* I can not get the POST call to work and I have a comment about that too;It returns http status 200 = OK,

    but in the payload it says;
    {"success":false,"input":[]}

    If a web service can't parse the input it should return a http status of 400. You can then remove the status code in payload.
Maybe it could be replaces with a 'reason' or other debug message or trace.


    
    
Comments about my solution
----
* Description
    
    The code was intended to be pure scala, but since it is hard to make a POST call using pure scala I added dependencies to Playframework.
    Usually you code web applications using some sort of web framework anyhow, like Playframework, Spray or Lift.
    The [Game](src/main/scala/com/skat/bowling/caseclasses/Game.scala) and [Frame](src/main/scala/com/skat/bowling/caseclasses/Frame.scala) are coded in a functional style.
    
 * [Main](src/main/scala/com/skat/bowling/main/Main.scala)   
    The main class is in Main. It just makes one call to the GET method, calculates the result array and returns it to the POSTt method.
    There is no test implementation since the POST method validates the result.
    

How to run
----

* [sbt](http://www.scala-sbt.org/)
    
    Download sbt and run command 'sbt run' in project folder.
    
