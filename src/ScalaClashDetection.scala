import kotlin.Pair

class ScalaClashDetection(activities: Seq[Activity]) {

  def findClashes(activity: Activity) = {
    for (duration <- 0 to activity.getDuration;
         clashes <- activities.filter(act => act.getDay == activity.getDay && act.getTime == activity.getTime+duration && act != activity)
    if (clashes != null)) yield clashes
  }

  def getClashes() = {
    for (x <- activities; y <- findClashes(x) if (y != null)) yield (Pair(x,y))
  }
}