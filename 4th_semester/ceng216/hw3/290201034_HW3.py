# Importing the libraries
import numpy as np
import matplotlib.pyplot as plt

####################################################################################################
# Coordinates of the city given: (-2,3), (0,1), (3,0), (5,2).                                      #
# a) Use the least squares method to find a straight road that can be built according to           #
# the requirement given above.                                                                     #
#                                                                                                  #
# Let's write the function as y(t) = c1 + c2*t                                                     #
# And place the coordinates in the function:                                                       #
#       c1 - 2c2 = 3 for (-2,3)                                                                    #
#       c1 + 0c2 = 1 for (0,1)                                                                     #
#       c1 + 3c2 = 0 for (3,0)                                                                     #
#       c1 + 5c2 = 2 for (5,2)                                                                     #
####################################################################################################

print("#"*25, " PART A ", "#"*25, "\n")
data_points = [(-2, 3), (0, 1), (3, 0), (5, 2)]
t_values = np.array([point[0] for point in data_points])
y_values = np.array([point[1] for point in data_points])

# Creating the matrix A
A_straight_road = np.array([
    [1, -2],
    [1, 0],
    [1, 3],
    [1, 5]
])
print("Matrix A for the straight road: \n", A_straight_road, "\n")

b_all = np.array([3, 1, 0, 2])
print("Vector b for the straight road: \n", b_all, "\n")

# A^T * A * x = A^T * b
# A^T means the transpose of A

ATA_for_straight_road = np.dot(A_straight_road.T, A_straight_road)  # A^T * A
ATb_for_straight_road = np.dot(A_straight_road.T, b_all)  # A^T * b

print("A transpose * A for the straight road: \n", ATA_for_straight_road, "\n")
print("A transpose * b for the straight road: \n", ATb_for_straight_road, "\n")

# Solving the equation

c_for_straight_road = np.linalg.solve(
    ATA_for_straight_road, ATb_for_straight_road)  # x = (A^T * A)^-1 * A^T * b

# Equation of the straight road


def equation_of_straight_road(t):
    return c_for_straight_road[0] + c_for_straight_road[1] * t

# Printing the values


print("c1 and c2 values for the straight road \n")
print("c1: ", c_for_straight_road[0])
print("c2: ", c_for_straight_road[1], "\n")
print("Equation of the straight road: y(t) = ",
      c_for_straight_road[0], " + ", c_for_straight_road[1], "t \n")
print("#" * 60)

# Plotting the graph

t1_q1 = np.linspace(-4, 7, 400)
line1 = equation_of_straight_road(t1_q1)
plt.figure(figsize=(10, 6))
plt.plot(t1_q1, line1, color='green',
         label=f'y = {c_for_straight_road[0]:.2f} + {c_for_straight_road[1]:.2f}t')
plt.scatter(t_values, y_values, color='red', label="Data Values")
plt.title("Plot of y = c1 + c2t")
plt.xlabel("t")
plt.ylabel("y")
plt.legend()
plt.grid()
plt.show()

####################################################################################################
# b) Use the least squares method to find a quadratic road that can be built according to          #
# the requirement given above.                                                                     #
#                                                                                                  #
# Since the road is quadratic, the function will be y(t) = c1 + c2*t + c3*t^2                      #
# And place the coordinates in the function:                                                       #
#       c1 - 2c2 + 4c3 = 3 for (-2,3)                                                              #
#       c1 + 0c2 + 0c3 = 1 for (0,1)                                                               #
#       c1 + 3c2 + 9c3 = 0 for (3,0)                                                               #
#       c1 + 5c2 + 25c3 = 2 for (5,2)                                                              #
####################################################################################################

print("\n")
print("#"*25, " PART B ", "#"*25, "\n")

A_single_curve = np.array([
    [1, -2, 4],
    [1, 0, 0],
    [1, 3, 9],
    [1, 5, 25]
])

print("Matrix A for the quadratic road: \n",
      A_single_curve, "\n")  # A^T A C = A^T b

ATA_for_curve_road = np.dot(A_single_curve.T, A_single_curve)  # A^T * A
ATb_for_curve_road = np.dot(A_single_curve.T, b_all)  # A^T * b
print("A transpose * A for the quadratic road: \n", ATA_for_curve_road, "\n")
print("A transpose * b for the quadratic road: \n", ATb_for_curve_road, "\n")

# Solving the equation

c_for_curve_road = np.linalg.solve(
    ATA_for_curve_road, ATb_for_curve_road)  # x = (A^T * A)^-1 * A^T * b

# Equation of the quadratic road


def equation_of_curve_road(t):
    return c_for_curve_road[0] + c_for_curve_road[1] * t + c_for_curve_road[2] * t ** 2

# Printing the values


print("c1, c2 and c3 values for the quadratic road \n")
print("c1: ", c_for_curve_road[0])
print("c2: ", c_for_curve_road[1])
print("c3: ", c_for_curve_road[2], "\n")
print("Equation of the quadratic road: y(t) = ",
      c_for_curve_road[0], " + ", c_for_curve_road[1], "t + ", c_for_curve_road[2], "t^2 \n")
print("#" * 60)

# Plotting the graph

t1_q2 = np.linspace(-4, 7, 400)
line2 = equation_of_curve_road(t1_q2)
plt.figure(figsize=(10, 6))
plt.plot(t1_q2, line2, color='green',
         label=f'y = {c_for_curve_road[0]:.2f} + {c_for_curve_road[1]:.2f}t + {c_for_curve_road[2]:.2f}t^2')
plt.scatter(t_values, y_values, color='red', label="Data Values")
plt.title("Plot of y = c1 + c2t + c3t^2")
plt.xlabel("t")
plt.ylabel("y")
plt.legend()
plt.grid()
plt.show()

####################################################################################################
# c) Calculate the root mean square error for both roads you have designed in the previous steps.  #
#                                                                                                  #
# Errors formulas (SE: Squared Error, RMSE: Root Mean Squared Error):                              #
# SE is calculated as SE = (y(t) - y)^2                                                            #
# RMSE is calculated as RMSE = sqrt(SE / n)                                                        #
#                                                                                                  #
####################################################################################################

print("\n")
print("#"*25, " PART C ", "#"*25, "\n")

# Calculating the errors for the straight road

SE1_straight_road = (equation_of_straight_road(-2) - b_all[0]) ** 2
SE2_straight_road = (equation_of_straight_road(0) - b_all[1]) ** 2
SE3_straight_road = (equation_of_straight_road(3) - b_all[2]) ** 2
SE4_straight_road = (equation_of_straight_road(5) - b_all[3]) ** 2

SE_total_straight_road = SE1_straight_road + \
    SE2_straight_road + SE3_straight_road + SE4_straight_road
vector_of_errors_straight_road = np.array(
    [SE1_straight_road, SE2_straight_road, SE3_straight_road, SE4_straight_road])
RMSE_straight_road = np.sqrt(SE_total_straight_road / 4)

# Calculating the errors for the curve road

SE1_curve_road = (equation_of_curve_road(-2) - b_all[0]) ** 2
SE2_curve_road = (equation_of_curve_road(0) - b_all[1]) ** 2
SE3_curve_road = (equation_of_curve_road(3) - b_all[2]) ** 2
SE4_curve_road = (equation_of_curve_road(5) - b_all[3]) ** 2

SE_total_curve_road = SE1_curve_road + \
    SE2_curve_road + SE3_curve_road + SE4_curve_road
vector_of_errors_curve_road = np.array(
    [SE1_curve_road, SE2_curve_road, SE3_curve_road, SE4_curve_road])
RMSE_curve_road = np.sqrt(SE_total_curve_road / 4)

# Printing the values

print("Errors for the straight road: \n")
print("Squared Error for the straight road: ", SE_total_straight_road)
print("Vector of Errors for the straight road: ", vector_of_errors_straight_road)
print("Root Mean Squared Error for the straight road: ", RMSE_straight_road, "\n")

print("Errors for the curve road: \n")
print("Squared Error for the curve road: ", SE_total_curve_road)
print("Vector of Errors for the curve road: ", vector_of_errors_curve_road)
print("Root Mean Squared Error for the curve road: ", RMSE_curve_road, "\n")

print("#" * 60)

####################################################################################################
# d) Use the classical Gram-Schmidt method to find the QR factorization so that we can improve     #
# the design of these roads in such a way that the roads become even closer to the cities.         #
# For the straight road, use reduced QR factorization.                                             #
# For the curve road, use the full QR factorization.                                               #
#                                                                                                  #
# y1 = a1, q1 = y1 / ||y1||                                                                        #
# y2 = a2 - q1 * (a2.q1) * q1, q2 = y2 / ||y2||                                                    #
# y3 = a3 - q1 * (a3.q1) * q1 - q2 * (a3.q2) * q2, q3 = y3 / ||y3||                                #
# if q2Tq3 = 0, then q2 and q3 are orthogonal.                                                     #
#                                                                                                  #
####################################################################################################

print("\n")
print("#"*25, " PART D ", "#"*25, "\n")

y1 = A_straight_road[:, 0]
y1_norm = np.linalg.norm(y1)
q1 = y1 / y1_norm

print("y1: ", y1, "\n")
print("q1: ", q1, "\n")

q1_transpose_and_a2 = np.dot(q1.T, A_straight_road[:, 1])
y2 = A_straight_road[:, 1] - q1 * q1_transpose_and_a2
y2_norm = np.linalg.norm(y2)
q2 = y2 / y2_norm

print("y2: ", y2, "\n")
print("q2: ", q2, "\n")

QR = np.column_stack((q1, q2))
print("Reduced QR factorization for the straight road: \n", QR, "\n")

####################################################################################################
# R Matrix equals                                                                                  #
# [ ||y1||      q1T * a2 ]                                                                         #
# [ 0,           ||y2||]                                                                           #
#                                                                                                  #
####################################################################################################

R = np.array([
    [y1_norm, q1_transpose_and_a2],
    [0, y2_norm]
])

print("R Matrix for the straight road: \n", R, "\n")

vector_d = np.dot(QR.T, b_all)
print("Vector d for the straight road: \n", vector_d, "\n")

# Solving the equation
values = np.linalg.solve(R, vector_d)
print("Values for the straight road: \n")
print("x1: ", values[0])
print("x2: ", values[1], "\n")

t2 = np.linspace(-4, 7, 400)
line3 = equation_of_straight_road(t2)

# Plotting the graph
plt.figure(figsize=(10, 6))
plt.plot(t2, line3, color='blue',
         label=f'y = {values[0]:.2f} + {values[1]:.2f}t')
plt.scatter(t_values, y_values, color='red', label="Data Values")
plt.title("Plot of y = c1 + c2t using QR factorization")
plt.xlabel("t")
plt.ylabel("y")
plt.legend()
plt.grid()
plt.show()

print("#" * 60)

# Full QR factorization for the curve road
q1_transpose_and_a3 = np.dot(q1.T, A_single_curve[:, 2])
q2_transpose_and_a3 = np.dot(q2.T, A_single_curve[:, 2])
y3 = A_single_curve[:, 2] - q1 * q1_transpose_and_a3 - q2 * q2_transpose_and_a3
y3_norm = np.linalg.norm(y3)
q3 = y3 / y3_norm

print()
print("y3: ", y3, "\n")
print("q3: ", q3, "\n")

a4 = np.array([1, 0, 0, 0])
q1_transpose_and_a4 = np.dot(q1.T, a4)
q2_transpose_and_a4 = np.dot(q2.T, a4)
q3_transpose_and_a4 = np.dot(q3.T, a4)

y4 = a4 - q1 * q1_transpose_and_a4 - q2 * \
    q2_transpose_and_a4 - q3 * q3_transpose_and_a4
y4_norm = np.linalg.norm(y4)
q4 = y4 / y4_norm

print("y4: ", y4, "\n")
print("q4: ", q4, "\n")

QR_full = np.column_stack((q1, q2, q3, q4))
print("Full QR factorization for the curve road: \n", QR_full, "\n")

####################################################################################################
# Full QR R is equals                                                                              #
# [ ||y1||      q1T * a2     q1T * a3]                                                             #
# [ 0,           ||y2||      q2T * a3]                                                             #
# [ 0,            0,           ||y3||]                                                             #
# [ 0,            0,             0]                                                                #
#                                                                                                  #
####################################################################################################

R_full = np.array([
    [y1_norm, q1_transpose_and_a2, q1_transpose_and_a3],
    [0, y2_norm, q2_transpose_and_a3],
    [0, 0, y3_norm],
    [0, 0, 0]
])

print("R Matrix for the curve road: \n", R_full, "\n")

RR_full = np.array([
    [y1_norm, q1_transpose_and_a2, q1_transpose_and_a3],
    [0, y2_norm, q2_transpose_and_a3],
    [0, 0, y3_norm]
])

print("R Matrix for the curve road (Reduced): \n", RR_full, "\n")

vector_d_full = np.delete(np.dot(QR_full.T, b_all), -1)
print("Vector d for the curve road: \n", vector_d_full, "\n")
print(vector_d_full.reshape(3, 1))

values1 = np.linalg.solve(RR_full, vector_d_full)

print("Values for the curve road: \n")
print("x1: ", values1[0])
print("x2: ", values1[1])
print("x3: ", values1[2], "\n")
print("#" * 60)

# Plotting the graph

t2_q2 = np.linspace(-4, 7, 400)
line4 = equation_of_curve_road(t2_q2)

plt.figure(figsize=(10, 6))
plt.plot(t2_q2, line4, color='green',
         label=f'y = {values1[0]:.2f} + {values1[1]:.2f}t + {values1[2]:.2f}t^2')
plt.scatter(t_values, y_values, color='red', label="Data Values")
plt.title("Plot of y = c1 + c2t + c3t^2 using QR factorization")
plt.xlabel("t")
plt.ylabel("y")
plt.legend()
plt.grid()
plt.show()
