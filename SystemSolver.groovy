#!/usr/bin/env groovy
def count;
while (!count) {
    countStr = System.console().readLine 'How many variables?\n> '
    try {
        count = Double.parseDouble countStr
    } catch (all) {
        println 'Not a valid variable count!'
    }
}

matrix = new Matrix(count)

for (def i = 0; i < count; i++) {
    def worked = false
    while (!worked) {
        try {
            eq = System.console().readLine "Input coefficients for equation ${i + 1}.\n> "
            coeffs = eq.split(/\s/)
            if (coeffs.length != count + 1) {
                println 'Incorrect number of coefficients!'
                continue
            }
            for (def j = 0; j < coeffs.length; j++)
                matrix.set(i, j, Double.parseDouble(coeffs[j]))
            worked = true
        } catch (all) {
            println 'Incorrectly formatted!'
        }
    }
}

for (def n = 0; n < count; n++) {
    for (def n2 = n + 1; matrix.at(n, n) == 0; n2++) {
        try {
            matrix.swap(n, n2)
        } catch (all) {
            throw new IllegalStateException('this matrix makes no sense')
        }
    }
    matrix.unify(n, n)
    for (def i = 0; i < count; i++) {
        if (i != n)
            matrix.nullify(n, i, n)
    }
}

println 'Solutions:'
for (def i = 0; i < count; i++) {
    System.out.print matrix.at(i, count)
    if (i < count - 1)
        System.out.print ' '
    else
        println()
}

class Matrix {
    
    def size, rows
    
    Matrix(size) {
        this.size = size
        this.rows = new BigInteger[size][]
        for (def i = 0; i < size; i++)
            this.rows[i] = new BigInteger[size + 1]
    }
    
    BigInteger at(row, col) {
        return this.rows[(int)row][(int)col]
    }
    
    void set(row, col, val) {
        this.rows[row][col] = val;
    }
    
    void multiply(row, mult) {
        for (def i = 0; i < size + 1; i++)
            this.rows[row][i] *= mult
        this.print "Multiply R${row + 1} by ${mult}"
    }
    
    void swap(a, b) {
        def rA = this.rows[a]
        this.rows[a] = this.rows[b]
        this.rows[b] = rA
        this.print "Swap R${a + 1} and R${b + 1}"
    }
    
    void add(a, b, mult) {
        for (def i = 0; i < size + 1; i++)
            this.rows[b][i] += this.rows[a][i] * mult
        this.print "Add R${a + 1} * ${mult} to R${b + 1}"
    }
    
    void unify(row, col) {
        multiply(row, Math.pow(this.rows[row][col], -1))
    }
    
    void nullify(a, b, col) {
        add(a, b, -this.rows[b][col] / this.rows[a][col])
    }
    
    void print(msg) {
        println msg
        for (def i = 0; i < this.rows.length; i++) {
            if (i == 0)
                System.out.print '/'
            else if (i == this.rows.length - 1)
                System.out.print '\\'
            else
                System.out.print '|'
            for (def j = 0; j <= this.rows[i].length; j++) {
                System.out.print ' '
                if (j == this.rows[i].length - 1)
                    System.out.print '|'
                else if (j == this.rows[i].length)
                    System.out.print String.format('%+5.2f', this.rows[i][j - 1])
                else
                    System.out.print String.format('%+5.2f', this.rows[i][j])
                System.out.print ' '
            }
            if (i == 0)
                println '\\'
            else if (i == this.rows.length - 1)
                println '/'
            else
                println '|'
        }
        println ''
    }
    
}

