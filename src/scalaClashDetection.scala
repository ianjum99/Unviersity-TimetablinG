import scala.collection.mutable.ListBuffer

case class scalaClashDetection(df: DataFactory) {
  def checkForClashes(): Unit = {
    var listOfActivities: ListBuffer[Activity] = ListBuffer()
    df.forEach(i => i.getModules.forEach(j => j.getActivities.forEach(y => listOfActivities +=(y))))
  }
  checkForClashes()
}