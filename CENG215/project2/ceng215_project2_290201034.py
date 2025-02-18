#############################################
#                                           #
#           Ahmet Kurt 290201034            #
#             Lab Project[2]                #
#                                           #
#############################################

#imports (numpy, matplotlib, etc.)
import numpy as np
import matplotlib.pyplot as plt
import math


#plt.style and plt.figure (I used ggplot style.)
plt.style.use('ggplot')
plt.figure(figsize=(20,20))


#Now we can define N and T as number of steps and time in seconds.
N=50000 #Number of steps. We can change it to another value for the graph. It doesn't matter.
T=0.5 #Time in seconds. In question says that "Let the simulation time be 0.5 sec."


#Components of the circuit. (Given in the question.)
c = 2200*10**(-6) #Farad 
r = 80 #Ohm   


#Now we can define the arrays. We will need 5 arrays.
t = np.empty(N)
Vg = np.empty(N)
V = np.empty(N)
I = np.empty(N)
Vi = np.empty(N)


#Now we can define the time step.
dt=T/N;


#Now we can define the initial values.
Vg[0]=0
V[0] = 0
I[0]=0
Vi[0]=0
t[0] = 0


Is = 10 ** (-12) #Ampere (Given in the question)


#Now we can define the function that will be used to calculate the voltage. Also I convert the formulas on the paper to the code.
#We can use "for" loop here.
for k in range(0, N - 1):
  Vi[k + 1] = 0.75*(1+np.sin(30*t[k])) #V_i(t) = 0.75(1+sin(30t)) (Given in the question.)
  Vg[k + 1] = Vg[k] + (Is * (np.exp((Vi[k]- Vg[k]) / 0.025) - 1) * dt / c)-((Vg[k]*dt)/(r*c)) 
  
  V[k+1] = 5-20*((0.5)*((Vg[k+1]-1)**2)) 
  t[k + 1] = t[k] + dt #Time step.
  


#Now we can plot the graph.
plt.plot(t,V,label='Output Voltage (V_o(t))', color='green')
plt.plot(t,Vi,label='Input Voltage (V_i(t))', color='black')


#Now we can define the labels and the title.
plt.xlabel('Time (second)')
plt.title('Lab Project [2]')
plt.legend()
plt.show()  

#End of the project.