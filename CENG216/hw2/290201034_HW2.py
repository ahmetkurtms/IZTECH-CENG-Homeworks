############################################
#       Ahmet Kurt - CENG216 HW2           #
############################################

# DIGIT: 1

# Import necessary libraries
import numpy as np
import matplotlib.pyplot as plt

# Defining the functions
def f1(x):
    return 2*x - 3

def f2(y):
    return np.full_like(y, 5)

def f3(x):
    return np.full_like(x, 2)

x1 = np.linspace(4, 5, 100) # interval [4, 5]
y2 = np.linspace(2, 7, 100) # interval [2, 7]
x3 = np.linspace(3, 7, 100) # interval [3, 7]

# Plot the functions
plt.plot(x1, f1(x1), label='S1', color='black', linewidth=2)
plt.plot(f2(y2), y2, label='S2', color='green', linewidth=2)
plt.plot(x3, f3(x3), label='S3', color='blue', linewidth=2)

# Add labels and title
plt.xlabel('x')
plt.ylabel('y')
plt.title('DIGIT: 1')
plt.legend()
plt.grid(True)
plt.show()

############################################

# DIGIT: 2

# Define the functions
def f(x): #S1 and S2
    if 3 <= x <= 4.5: # interval [3, 4.5]
        return -4.4444e-1 * x**3 + 4.0000 * x**2 - 9.0000 * x + 1.0000e1
    elif 4.5 < x <= 6: # interval (4.5, 6]
        return 4.4444e-1 * x**3 - 8.0000 * x**2 + 4.5000e1 * x - 7.1000e1
    else:
        return None # return None for x values outside the intervals

def g(x): #S3 and S4
    if 3 <= x <= 4.5: # interval [3, 4.5]
        return 5/3 * x - 3
    elif 4.5 < x <= 6: # interval (4.5, 6]
        return 5/3 * x - 3
    else:
        return None

x_values = np.linspace(3, 6, 1000)

y_values_1 = np.array([f(x) for x in x_values])

y_values_2 = np.array([g(x) for x in x_values]) #linear

y_values_3 = np.full_like(x_values, 2) #constant

# Plot the functions
plt.plot(x_values, y_values_1, label='S1 AND S2')
plt.plot(x_values, y_values_2, label='S3', color='black', linewidth=3)
plt.plot(x_values, y_values_3, label='S4', color='green', linewidth=3)

# Add labels and title
plt.xlabel('x')
plt.ylabel('y')
plt.title('DIGIT: 2')
plt.grid(True)
plt.legend()
plt.show()

############################################

# DIGIT: 4

# Define the functions
def func_4(x):
    condition1 = (x >= 2) & (x <= 3) # interval [2, 3]
    condition2 = (x > 3) & (x <= 3.5) # interval (3, 3.5]
    return np.piecewise(x, [condition1, condition2,], 
                        [lambda x: 5e-1 * x**3 - 3 * x**2 + 7 * x - 7.196e-61,
                         lambda x: -1 * x**3 + 10.5 * x**2 - 33.5 * x + 40.5])
                        

def f1(x):
    return np.full_like(x, 6) # y = 6 for x in [2, 4]

x_values = np.linspace(2, 3.51, 500) # 3.51 instead of 3.5 for bug fix

# Plot the function
plt.plot(x_values, func_4(x_values), label='S1 and S2', color='black', linewidth=2)

# Plot the lines
x_values = np.linspace(2, 4, 500)
plt.plot(x_values, f1(x_values), label='S3', color='green', linewidth=2)

plt.xlabel('x')
plt.ylabel('y')
plt.title('DIGIT: 4')
plt.legend()
plt.grid(True)
plt.show()

############################################

# DIGIT: 0

# Define functions
def f1(x):
    return np.piecewise(x, [((x >= 1) & (x <= 2)), ((x > 2) & (x <= 4))], # interval [1, 2] and (2, 4]
                        [lambda x: -1.6667e-1 * x**3 + 5.0000e-1 * x**2 + 1.6667 * x + 2.0000,
                         lambda x: 8.3333e-2 * x**3 - 1.0000 * x**2 + 4.6667 * x + 1.3333e-63])

def f2(x):
    return np.piecewise(x, [((x >= 4) & (x <= 6)), ((x > 6) & (x <= 7))], # interval [4, 6] and (6, 7]
                        [lambda x: -8.3333e-2 * x**3 + 1.0000 * x**2 - 4.6667 * x + 1.6000e1,
                         lambda x: 1.6667e-1 * x**3 - 3.5000 * x**2 + 2.2333e1 * x - 3.8000e1])

def f3(x):
    return np.piecewise(x, [((x >= 4) & (x <= 6)), ((x > 6) & (x <= 7))], # interval [4, 6] and (6, 7]
                        [lambda x: 1.2500e-1 * x**3 - 1.5000 * x**2 + 6.0000 * x - 7.0000,
                         lambda x: -2.5000e-1 * x**3 + 5.2500 * x**2 - 3.4500e1 * x + 7.4000e1])

def f4(x):
    return np.piecewise(x, [((x >= 1) & (x <= 2)), ((x > 2) & (x <= 4))], # interval [1, 2] and (2, 4]
                        [lambda x: 2.5000e-1 * x**3 - 7.5000e-1 * x**2 - 1.5000 * x + 6.0000,
                         lambda x: -1.2500e-1 * x**3 + 1.5000 * x**2 - 6.0000 * x + 9.0000])

# Generate x values
x_values = np.linspace(1, 7, 1000)

# Plot the functions
x_values = np.linspace(1, 3.9999, 500)
plt.plot(x_values, f1(x_values), label='S1 and S2', color='black', linewidth=2)
x_values = np.linspace(4, 6.9999, 500)
plt.plot(x_values, f2(x_values), label='S3 and S4', color='green', linewidth=2)
x_values = np.linspace(4, 6.9999, 500)
plt.plot(x_values, f3(x_values), label='S5 and S6', color='blue', linewidth=2)
x_values = np.linspace(1, 4, 500)
plt.plot(x_values, f4(x_values), label='S7 and S8', color='red', linewidth=2)

plt.xlabel('x')
plt.ylabel('y')
plt.title('DIGIT: 0')
plt.legend()
plt.grid(True)
plt.show()
############################################