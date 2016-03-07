#!/usr/bin/env groovy
while (!count) {
    countStr = System.console.readLine 'How many variables?'
    try {
        count = Integer.parseInt countStr
    } catch (all) {
        println 'Not a valid variable count!'
    }
}

matrix = new Matrix(count)

static class Matrix {
    
    Matrix(size) {
        this.size = size
        this.rows = new double[][size]
        for (i = 0; i < size; i++)
            this.rows[i] = new double[size]
    }
    
    int at(row, col) {
        return this.rows[row][col]
    }
    
    void multiply(row, mult) {
        for (i = 0; i < size; i++)
            this.rows[row][i] *= mult
    }
    
    void swap(a, b) {
        rA = this.rows[a]
        this.rows[a] = this.rows[b]
        this.rows[b] = rA
    }
    
    void add(a, b, mult) {
        for (i = 0; i < size; i++)
            this.rows[b][i] += this.rows[a][i] * mult
    }
    
    void unify(row, col) {
        multiply(row, Math.pow(this.rows[row][col], -1))
    }
    
    void nullify(a, b, col) {
        add(a, b, this.rows[a][col] / this.rows[b][col])
    }
    
}

