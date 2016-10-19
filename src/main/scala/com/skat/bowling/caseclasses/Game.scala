package com.skat.bowling.caseclasses

/**
 * @author Gøran Schumacher (GS) / Schumacher Consulting Aps
 * @version $Revision$ 13/10/2016
 */
class Game {

  // Start with the 10th frame, the seed to the foldRight method
  // Add frame 1 to 9 BEFORE the seed.
  // Seed is special in that it has no nextFrame
  // the other iterations adds a new frame in front of the list with reference to the next frame.
  // In essence creating a linked list
  //
  // LinkedList and DoubleLinked list are deprecated in Scala.
  val frames = (1 to 10).foldLeft(List(Frame())) ((b: List[Frame], a: Int) => b.+:(Frame(None,None, Some(b.head))))

  /**
   * Calculates total for all frames
   * @return The total
   */
  def gameResultTotal = gameResultUptoFrame(11)

  /**
   * Calculates the total up to frame frameNo
   * @param frameNo
   * @return The total
   */
  def gameResultUptoFrame(frameNo: Int): List[Int] = frames.slice(0, frameNo).
    foldLeft(List.empty[Int])((a: List[Int], b: Frame)=> {
    if(b.throw1.isDefined) {
      a ::: List(b.pointsForFrame(if(a.isEmpty)0 else a.last))
    } else {
      a
    }
  })

}