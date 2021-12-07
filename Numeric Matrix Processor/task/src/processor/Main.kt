package processor

import java.math.BigDecimal
enum class TranspositionType {
    AlongMainDiagonal, AlongSideDiagonal, AlongVerticalLine, AlongHorizontalLine
}



fun main() {
    menu()
}

fun menu() {
    while (true) {
        println("1. Add matrices")
        println("2. Multiply matrix by a constant")
        println("3. Multiply matrices")
        println("4. Transpose matrix")
        println("0. Exit")
        when (readLine()!!) {
            "1" -> addMatrices()
            "2" -> multiplyByConst()
            "3" -> multiplyMatrices()
            "4" -> transposeMatrix()
            "0" -> return
        }
    }

}

fun transposeMatrix() {
    println("1. Main diagonal")
    println("2. Side diagonal")
    println("3. Vertical line")
    println("4. Horizontal line")
    when (readLine()!!) {
        "1" -> transposeMatrix(TranspositionType.AlongMainDiagonal)
        "2" -> transposeMatrix(TranspositionType.AlongSideDiagonal)
        "3" -> transposeMatrix(TranspositionType.AlongVerticalLine)
        "4" -> transposeMatrix(TranspositionType.AlongHorizontalLine)
    }
}

fun transposeMatrix(type: TranspositionType) {
    println("Enter matrix size:")
    val size1 = readLine()!!.split(" ")
    val matrix1 = Matrix(size1[0].toInt(), size1[1].toInt())
    println("Enter matrix:")
    matrix1.fill()
    val matrix3 = when(type) {
        TranspositionType.AlongMainDiagonal -> matrix1.mainDiagonalTransposition()
        TranspositionType.AlongSideDiagonal -> matrix1.sideDiagonalTransposition()
        TranspositionType.AlongVerticalLine -> matrix1.verticalLineTransposition()
        TranspositionType.AlongHorizontalLine -> matrix1.horizontalLineTransposition()
        else -> throw Exception("Not Implemented type $type")
    }
    println("The result is:")
    matrix3.print()
}

fun multiplyMatrices() {
    println("Enter size of first matrix:")
    val size1 = readLine()!!.split(" ")
    val matrix1 = Matrix(size1[0].toInt(), size1[1].toInt())
    println("Enter first matrix:")
    matrix1.fill()
    println("Enter size of second matrix:")
    val size2 = readLine()!!.split(" ")
    val matrix2 = Matrix(size2[0].toInt(), size2[1].toInt())
    println("Enter second matrix:")
    matrix2.fill()
    try {
        val matrix3 = matrix1 * matrix2
        println("The result is:")
        matrix3.print()
    } catch(e: Exception) {
        println("The operation cannot be performed.")
    }
}

fun multiplyByConst() {
    println("Enter size of first matrix:")
    val size1 = readLine()!!.split(" ")
    val matrix1 = Matrix(size1[0].toInt(), size1[1].toInt())
    println("Enter first matrix:")
    matrix1.fill()
    println("Enter constant:")
    val mult = readLine()!!.toBigDecimal()
    val result = matrix1 * mult
    println("The result is:")
    result.print()
}

fun addMatrices() {
    println("Enter size of first matrix:")
    val size1 = readLine()!!.split(" ")
    val matrix1 = Matrix(size1[0].toInt(), size1[1].toInt())
    println("Enter first matrix:")
    matrix1.fill()
    println("Enter size of second matrix:")
    val size2 = readLine()!!.split(" ")
    val matrix2 = Matrix(size2[0].toInt(), size2[1].toInt())
    println("Enter second matrix:")
    matrix2.fill()
    try {
        val matrix3 = matrix1 + matrix2
        println("The result is:")
        matrix3.print()
    } catch(e: Exception) {
        println("The operation cannot be performed.")
    }
}

class Matrix(_rows: Int, _cols: Int ) {
    val rows = _rows
    val cols = _cols
    val array: MutableList<MutableList<BigDecimal>> = mutableListOf<MutableList<BigDecimal>>()
    init {
        for (i in 0 until rows) {
            this.array.add(MutableList<BigDecimal>(cols){ 0.toBigDecimal() })
        }
    }
    fun fill() {
        for (i in 0 until rows) {
            val rowArray = readLine()!!.split(" ")
            for (j in 0 until cols) {
                array[i][j] = rowArray[j].toBigDecimal()
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

    operator fun times(matrix: Matrix): Matrix {
        if (this.cols == matrix.rows) {
            val result = Matrix(this.rows, matrix.cols)
            for (i in 0 until result.rows) {
                for (j in 0 until result.cols) {
                    result.array[i][j] = this.array[i].foldIndexed(0.toBigDecimal()) { index, acc, v -> acc + v * matrix.array[index][j] }
                }
            }
            return result
        } else {
            throw Exception("IncorrectSize")
        }
    }

    operator fun times(mult: BigDecimal): Matrix {
        val result = Matrix(this.rows, this.cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.array[i][j] = this.array[i][j] * mult
            }
        }
        return result
    }

    fun mainDiagonalTransposition(): Matrix {
        if (this.rows == this.cols) {
            val result = Matrix(this.rows, this.cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result.array[i][j] = this.array[j][i]
                }
            }
            return result
        } else {
            throw Exception("IncorrectSize")
        }
    }

    fun sideDiagonalTransposition(): Matrix {
        if (this.rows == this.cols) {
            val result = Matrix(this.rows, this.cols)
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    result.array[i][j] = this.array[this.cols - j - 1][this.rows - i - 1]
                }
            }
            return result
        } else {
            throw Exception("IncorrectSize")
        }
    }

    fun verticalLineTransposition(): Matrix {
        val result = Matrix(this.rows, this.cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.array[i][j] = this.array[i][this.cols - j - 1]
            }
        }
        return result
    }

    fun horizontalLineTransposition(): Matrix {
        val result = Matrix(this.rows, this.cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.array[i][j] = this.array[this.rows - i - 1][j]
            }
        }
        return result
    }

    fun print() {
        this.array.forEach { i -> println(i.joinToString(" ")) }
    }
}
