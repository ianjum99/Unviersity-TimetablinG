import kotlin.Pair

class scalaClashDetection(activities: Seq[Activity]) {

  def clash(activity: Activity): Seq[Activity] = {
    if (activity.getDuration == 1) {
      activities.filterNot(currentActivity => currentActivity == activity).filter(act => act.getDay == activity.getDay && act.getTime == activity.getTime)
    } else {
      activities.filterNot(currentActivity => currentActivity == activity).filter(act => act.getDay == activity.getDay && act.getTime == activity.getTime).++ (activities.filterNot(currentActivity => currentActivity == activity).filter(act => act.getDay == activity.getDay && act.getTime == activity.getTime+1)
      )
    }
  }

  def findClashes() = {
    for (x <- activities; y <- clash(x) if (y != null)) yield (Pair(x,y))
  }
}