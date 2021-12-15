import kotlin.Pair

class ScalaClashDetection(activities: Seq[Activity]) {
  //This class takes a Sequence of activities as a parameter, which will be used by the methods below
  //to find and return clashes.


  def findClashes(activity: Activity) = {
    activities.filter(act => act.getDay == activity.getDay
      && (act.getTime == activity.getTime || act.getTime == activity.getTime-1 && act.getDuration>1)
      && act != activity)
  }
  //The function above takes an activity as a parameters and returns a "Seq" containing
  //all the activities that clash with that activity.

  def getClashes() = {
    for (x <- activities; y <- findClashes(x) if (y != null)) yield (Pair(x,y))
  }

  //The function getClashes() will find the clashes of every activity in the sequence passed and it will
  //yield a list of Kotlin pairs containing the two activities that clash. We decided to use a Kotlin data
  //structure to make it simpler to use the data once it goes back to the other languages.
}