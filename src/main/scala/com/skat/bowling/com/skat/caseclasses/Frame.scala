package com.skat.bowling.com.skat.caseclasses

/**
 * @author Gøran Schumacher (GS) / Schumacher Consulting Aps
 * @version $Revision$ 13/10/2016
 */
case class Frame(var throw1: Int,var throw2: Int,var  throw3: Int, nextFrame : Option[Frame]=None) {

  def pointsForFrame(running: Int): Int = {
    val runningPlusThis=running+throw1+throw2+throw3
    val next = if(throw1==10) {
      if(nextFrame.isDefined)
        nextFrame.get.throw1+nextFrame.get.throw2
      else
        0
    } else if((throw1+throw2)==10) {
      if(nextFrame.isDefined)
        nextFrame.get.throw1+nextFrame.get.throw2
      else
        0
    } else
      0
    runningPlusThis+next
  }
}
