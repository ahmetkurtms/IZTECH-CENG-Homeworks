#include "matrix.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/**
 * This function initializes and returns a dense matrix 
 * as a dynamic two-dimensional array.
 */
DenseMatrix initDenseMatrix(int columnLength, int rowLength) {
    DenseMatrix denseMatrix = (DenseMatrix)malloc(sizeof(DenseMatrix_t)); // Allocating (test when final run) 
    denseMatrix->columnLength = columnLength;
    denseMatrix->rowLength = rowLength;
    denseMatrix->matrix = (int**)malloc(rowLength * sizeof(int*)); // rowLength ? columnLength
    for (int i = 0; i < rowLength; i++) {
        denseMatrix->matrix[i] = (int*)calloc(columnLength, sizeof(int));
    }
    return denseMatrix;
}

/**
 * This function deallocates all the rows and 
 * columns of the given dense matrix.
 */
void freeDenseMatrix(DenseMatrix denseMatrix) {
    for (int i = 0; i < denseMatrix->rowLength; i++) {
        free(denseMatrix->matrix[i]);
    }
    free(denseMatrix->matrix);
    free(denseMatrix);
}


/**
 * This function creates a dense matrix and fills 
 * the elements in the given parse matrix as a set.
 */
DenseMatrix parseMatrixToDenseMatrix(Set parseMatrix, int columnLength, int rowLength) {
    DenseMatrix denseMatrix = initDenseMatrix(columnLength, rowLength);
    for (int i = 0; i < parseMatrix->cardinality; i++) {
        int x = ((int *)parseMatrix->elements[i]->data)[0];
        int y = ((int *)parseMatrix->elements[i]->data)[1];
        int value = ((int *)parseMatrix->elements[i]->data)[2];
        denseMatrix->matrix[x][y] = value;
    }
    return denseMatrix;
}

/**
 * This function creates and returns a parse matrix 
 * as a set depending on the given dense matrix's elements.
 */
Set denseMatrixToParseMatrix(DenseMatrix denseMatrix) {
    Set sparseMatrix = initSet();
    for (int i = 0; i < denseMatrix->rowLength; i++) {
        for (int j = 0; j < denseMatrix->columnLength; j++) {
            if (denseMatrix->matrix[i][j] != 0) {
                Element element = createMatrixPointElement(i, j, denseMatrix->matrix[i][j]);
                insertElement(sparseMatrix, element);
            }
        }
    }
    return sparseMatrix;
}

/**
 * This matrix creates a new dense matrix, and the 
 * matrix is the addition of the given two matrices.
 */
DenseMatrix addDenseMatrices(DenseMatrix dm1, DenseMatrix dm2) {
    if (dm1->rowLength != dm2->rowLength || dm1->columnLength != dm2->columnLength) {
        printf("The given matrices are not the same size.\n");
        return NULL;
    }

    DenseMatrix resultMatrix = initDenseMatrix(dm1->columnLength, dm1->rowLength);

    for (int i = 0; i < dm1->rowLength; i++) {
        for (int j = 0; j < dm1->columnLength; j++) {
            resultMatrix->matrix[i][j] = dm1->matrix[i][j] + dm2->matrix[i][j];
        }
    }
    return resultMatrix;
}

/**
 * This matrix creates a new sparse matrix as a set, 
 * and the matrix is the addition of the given 
 * two sparse matrices.
 */
Set addSparseMatrices(Set sm1, Set sm2, int columnLength, int rowLength) {
    DenseMatrix denseMatrix1 = parseMatrixToDenseMatrix(sm1, columnLength, rowLength);
    DenseMatrix denseMatrix2 = parseMatrixToDenseMatrix(sm2, columnLength, rowLength);
    DenseMatrix resultDenseMatrix = addDenseMatrices(denseMatrix1, denseMatrix2);
    Set resultSparseMatrix = denseMatrixToParseMatrix(resultDenseMatrix);

    freeDenseMatrix(denseMatrix1); // Free the memory allocated for the dense matrices.
    freeDenseMatrix(denseMatrix2);
    freeDenseMatrix(resultDenseMatrix);

    return resultSparseMatrix;
}