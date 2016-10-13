package com.skat.bowling.com.skat.caseclasses

/**
 * @author Gøran Schumacher (GS) / Schumacher Consulting Aps
 * @version $Revision$ 13/10/2016
 */
class Game() {

  val frames = (1 to 9).foldLeft(List(Frame(0, 0, 0))) ((b: List[Frame], a: Int) => b.+:(Frame(0,0,0, Some(b.head))))

  //def gameResult = frames.foldRight(0)((a,b)=>a.pointsForFrame(b))
  def gameResultUptoFrame(frameNo: Int) = frames.slice(0, frameNo).foldRight(0)((a,b)=>a.pointsForFrame(b))
  def gameResultTotal = gameResultUptoFrame(9)

}
