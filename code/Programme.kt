package code

class Programme (val name: String, val type: Char){ //,var terms : HashMap<String, Array<Module>> : HashMap<String, Array<Module>>) {

    init{
        val terms : HashMap<Int, Array<Module>> = HashMap<Int, Array<Module>>()
        var t1 = arrayOf<Module>()
        var t2= arrayOf<Module>()
        terms.put(1, t1)
        terms.put(2,t2)
       }
}