package processor

fun main() {
    val size1 = readLine()!!.split(" ")
    val matrix1 = Matrix(size1[0].toInt(), size1[1].toInt())
    matrix1.fill()
    val mult = readLine()!!.toInt()
    val result = matrix1 * mult
    result.print()
//    val size2 = readLine()!!.split(" ")
//    val matrix2 = Matrix(size2[0].toInt(), size2[1].toInt())
//    matrix2.fill()

//    try {
//        val matrix3 = matrix1 + matrix2
//        matrix3.print()
//    } catch(e: Exception) {
//        println("ERROR")
//    }

}

class Matrix(_rows: Int, _cols: Int ) {
    val rows = _rows
    val cols = _cols
    val array: MutableList<MutableList<Int>> = mutableListOf<MutableList<Int>>()
    init {
        for (i in 0 until rows) {
            this.array.add(MutableList<Int>(cols){ 0 })
        }
    }
    fun fill() {
        for (i in 0 until rows) {
            val rowArray = readLine()!!.split(" ")
            for (j in 0 until cols) {
                array[i][j] = rowArray[j].toInt()
            }
        }
    }
    operator fun plus(matrix: Matrix): Matrix {
        if (this.cols == matrix.cols && this.rows == matrix.rows) {
            val result = Matrix(this.rows, this.cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result.array[i][j] = this.array[i][j] + matrix.array[i][j]
                }
            }
            return result
        } else {
            throw Exception("IncorrectSize")
        }
    }

    operator fun times(mult: Int): Matrix {
        val result = Matrix(this.rows, this.cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.array[i][j] = this.array[i][j] * mult
            }
        }
        return result
    }

    fun print() {
        this.array.forEach { i -> println(i.joinToString(" ")) }
    }
}
