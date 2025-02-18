############################################
#           AHMET KURT 290201034           #
############################################
import numpy as np
import random
from matplotlib import pyplot as plt

# Experiment 1

ar_A = []
ar_B = []
ar_C = []
ar_X = []

av_A = []
av_B = []
av_C = []
av_X = []
vr_X = []

# Populate the given arrays.
total_a = 0
total_b = 0
total_c = 0
total_x = 0
total_x2 = 0

#For random variables
for i in range(30000): #30000 iterations
    
    r=random.random() #Random variable
    r_a = ((6*r)//1)+1 #Dice A is rolled. (6 faces)
    ar_A.append(r_a) #Storing the values of A in the array.

    r = random.random()
    r_b = ((4*r)//1)+1 #Dice B is rolled. (4 faces)
    ar_B.append(r_b) #Storing the values of B in the array.
    
    r = random.random()
    r_c = (-1)**((2*r)//1) #Coin toss. (2 outcomes)
    ar_C.append(r_c) #Storing the values of C in the array.
    
    r_x = r_a + r_b*r_c #Random variable X is calculated. Given formula in the homework pdf.
    ar_X.append(r_x) #Storing the values of X in the array.
    
    total_a += r_a #Sum of A
    avg_a = total_a / (i+1) #Average of A
    av_A.append(avg_a) #Storing the average of A in the array.
    
    total_b += r_b #Sum of B
    avg_b = total_b / (i+1) #Average of B
    av_B.append(avg_b) #Storing the average of B in the array.
    
    total_c += r_c #Sum of C
    avg_c = total_c / (i+1) #Average of C
    av_C.append(avg_c) #Storing the average of C in the array.
    
    total_x += r_x #Sum of X
    avg_x = total_x / (i+1) #Average of X
    av_X.append(avg_x) #Storing the average of X in the array.
    
    total_x2 += r_x**2 #Sum of X^2
    var_x = (total_x2 / (i+1)) - av_X[i]**2 #Variance of X
    vr_X.append(var_x) #Storing the variance of X in the array.
    
# Inspect the following plots.
plt.figure()
plt.hist(ar_A, 6, range=(1, 7), align='left', density=True, rwidth=0.8)
plt.figure()
plt.hist(ar_B, 4, range=(1, 5), align='left', density=True, rwidth=0.8)
plt.figure()
plt.hist(ar_C, 3, range=(-1, 2), align='left', density=True, rwidth=0.8)
plt.figure()
plt.hist(ar_X, 14, range=(-3, 11), align='left', density=True, rwidth=0.8)

# Plot the average and variance values.
plt.figure()
plt.plot(av_A)
plt.title('Average of A')

plt.figure()
plt.plot(av_B)
plt.title('Average of B')

plt.figure()
plt.plot(av_C)
plt.title('Average of C')

plt.figure()
plt.plot(av_X)
plt.title('Average of X')

plt.figure()
plt.plot(vr_X)
plt.title('Variance of X')

plt.show()

# Experiment 2

# Part a (Inverse Transform Method)
U = []
Xa = []
av_Xa = []
vr_Xa = []

# Populate the given arrays.
total_x = 0
total_x2 = 0

for j in range (30000): #30000 iterations
    r = random.random() #Random variable
    U.append(r) #Storing the values of U in the array.
    x = r
    Xa.append(x) #Storing the values of Xa in the array.
    
    #avgs
    total_x += x #Sum of Xa
    avg_x = total_x / (j+1) #Average of Xa
    av_Xa.append(avg_x) #Storing the average of Xa in the array.
    
    #vars
    total_x2 += x**2 #Sum of Xa^2
    var_x = (total_x2 / (j+1)) - av_Xa[j]**2 #Variance of Xa
    vr_Xa.append(var_x) #Storing the variance of Xa in the array.
    

# Inspect the following plots.
plt.figure()
for i in range(len(Xa)):
    plt.plot([Xa[i],U[i]],[1,1.2])
plt.figure()
hU = plt.hist(U, 100, alpha=0.5, density=True)
hXa = plt.hist(Xa, 100, alpha=0.5, density=True)
plt.figure()
plt.plot(np.cumsum(hU[0]))
plt.plot(np.cumsum(hXa[0]))

# Plot the average and variance values.
plt.figure()
plt.plot(av_Xa)
plt.title("Average of X (Inverse Transform Method)")
plt.xlabel("Number of Samples")
plt.ylabel("Average of Value")

plt.figure()
plt.plot(vr_Xa)
plt.title('Variance of X (Inverse Transform Method)')
plt.xlabel('Number of Samples')
plt.ylabel('Variance')

#Part b
Xb = []
av_Xb = []
vr_Xb = []

# Populate the given arrays.
total_x = 0
total_x2 = 0

for j in range (30000): #30000 iterations
    
    U1 = random.random() #Random variable
    U2 = random.random() #Random variable
    Y = 2 * U2 #Random variable
    X = 0 + (1 - 0) * U1 #Random variable
    if Y < (2*X): #If Y < 2X, then X is stored in the array.
        Xb.append(X)
        
        #avgs
        total_x += X #Sum of X
        avg_x = total_x / (len(av_Xb)+1) #Average of X
        av_Xb.append(avg_x) #Storing the average of X in the array.
        
        #vars
        total_x2 += X**2
        var_x = (total_x2 / (len(av_Xb))) - av_Xb[(len(av_Xb)-1)]**2
        vr_Xb.append(var_x)
        
# Inspect the following plots.
plt.figure()
hXb = plt.hist(Xb,100,density=True)
plt.figure()
plt.plot(np.cumsum(hXb[0]))

# Plot the average and variance values.
plt.figure()
plt.plot(av_Xb)
plt.figure()
plt.plot(vr_Xb)
plt.show()