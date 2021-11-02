package code

class Timetable (var days: ArrayList<Hour> = ArrayList()){
    init {
        for (i in 0..4) {
            days.add(Hour())
        }
    }
}