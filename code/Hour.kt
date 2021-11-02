package code

class Hour (var hours: HashMap<Int, Activity?> = HashMap()){
    init {
        for (i in 9..20) {
            hours[i] = null
        }
    }
}