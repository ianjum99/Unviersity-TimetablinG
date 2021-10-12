import nQueens
var count = 0
var c = IntArray(0)
var f = ""

fun main(args: Array<String>) {
    for (n in 1..8)
    {
        count = 0
        c = IntArray(n + 1)
        f = ""
         val nQueens= nQueens()
        nQueens.nQueensAlgo(1, n)
        println("For a $n x $n board:")
        println("  Solutions = $count")
        if (count > 0) println("  First is $f")
        println()
    }
}
