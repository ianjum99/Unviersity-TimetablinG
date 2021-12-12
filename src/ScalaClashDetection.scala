import kotlin.Pair

class ScalaClashDetection(activities: Seq[Activity]) {

  def findClashes(activity: Activity) = {
    activities.filter(act => act.getDay == activity.getDay
      && (act.getTime == activity.getTime || act.getTime == activity.getTime-1 && act.getDuration>1)
      && act != activity)
  }

  def getClashes() = {
    for (x <- activities; y <- findClashes(x) if (y != null)) yield (Pair(x,y))
  }
}