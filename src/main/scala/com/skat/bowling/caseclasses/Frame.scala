package com.skat.bowling.caseclasses

/**
 * @author Gøran Schumacher (GS) / Schumacher Consulting Aps
 * @version $Revision$ 13/10/2016
 */
case class Frame(var throw1: Option[Int]=None,var throw2: Option[Int]=None, nextFrame : Option[Frame]=None) {

  implicit def implicitToOption[A](any: A): Option[A] = Some(any)

  /**
   *  Adds throws to a Frame
   * @param throws The throws to be registered
   */
  def setThrows(throws: Array[Int]) = {

    if(throws.size>0)
      throw1=throws(0)
    if(throws.size>1)
      throw2=throws(1)
  }

  /**
   * Calculates a running total for the frames
   * @param running Total from previous frames
   * @return The total
   */
  def pointsForFrame(running: Int): Int = {

    def calculateStrike: Int = {
      if (nextFrame.isDefined) {
        nextFrame.get.throw1.getOrElse(0) + //First extra point
          (if (nextFrame.get.throw2.isDefined && nextFrame.get.throw2.get != 0) {
            nextFrame.get.throw2.get // Second extra point
          } else {
            if (nextFrame.get.nextFrame.isDefined && nextFrame.get.nextFrame.get.throw1.isDefined) {
              nextFrame.get.nextFrame.get.throw1.get
            } else {
              0 // Error or ball not thrown yet
            }
          }
            )
      } else {
        0 // Error or ball not thrown yet
      }
    }

    def calculateSpare: Int = {
      if (nextFrame.isDefined)
        nextFrame.get.throw1.getOrElse(0) //First extra point
      else
        0 // Error or ball not thrown yet
    }

    val nextFrameExtraPoints: Int = {
      if(throw1.getOrElse(0)==10) {                             // Strike
        calculateStrike
    } else if((throw1.getOrElse(0)+throw2.getOrElse(0))==10) {  // Spare
      calculateSpare
    } else
      0           // Error or ball not thrown yet
    }
    running+throw1.getOrElse(0)+throw2.getOrElse(0)+nextFrameExtraPoints

  }
}
