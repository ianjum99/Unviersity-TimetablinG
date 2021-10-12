class N_Queens {
    fun N_QueensAlgo(row: Int, n: Int) {
        outer@ for (x in 1..n) {
            for (y in 1..row - 1) {
                if (c[y] == x) continue@outer
                if (row - y == Math.abs(x - c[y])) continue@outer
            }
            c[row] = x
            if (row < n) N_QueensAlgo(row + 1, n)
            else if (++count == 1) f = c.drop(1).map { it - 1 }.toString()
        }
    }
}
