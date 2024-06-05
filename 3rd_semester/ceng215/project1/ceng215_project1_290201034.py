#############################################
#                                           #
#           Ahmet Kurt 290201034            #
#             Lab Project[1]                #
#                                           #
#############################################

#imports (numpy, matplotlib, etc.)
import numpy as np  
import matplotlib.pyplot as plt
import math

#plt.style and plt.figure
plt.style.use("ggplot")
plt.figure(figsize=(20,20))


#Now we can define NS and TS as number of steps and time in seconds.
NS = 50000  #Number of steps. We can change it to 100000 to get a better graph. It doesn't matter.
TS = 10     #Time in seconds. We can change it to 20 to get a better graph. It doesn't matter.


#Components of the circuit
r=1   #Ohm
c1=1  #Farad
c2=1  #Farad


#Now we can define the arrays. We will need 6 arrays. 
t=np.empty(NS)
V_i=np.empty(NS)
V_d=np.empty(NS)
I=np.empty(NS)
Vc1=np.empty(NS)
Vc2=np.empty(NS)

#dt is the time step. We can calculate it by dividing TS by NS.
dt=TS/NS


#Initial values. We can define them as 0.
V_i[0]=0
Vc1[0]=0
Vc2[0]=0
I[0]=0
t[0]=0
Is=10**(-12)

#Now we can define the function that will be used to calculate the voltage at each step.
#We can use "for" loop here. Also I convert the formulas on the paper to the code.

for k in range(0, NS-1):
    V_i[k+1] = 5*np.sin(t[k])
    #V_i[k+1] = 0.8
    Vc1[k + 1] = Vc1[k] + (V_i[k] - Vc1[k]) * dt / (c1 * r) - (Is * (np.exp((Vc1[k]- Vc2[k]) / 0.025) - 1) * dt / c1)
    Vc2[k + 1] = Vc2[k] + (Is * dt * (np.exp((Vc1[k]- Vc2[k]) / 0.025) - 1) / c2)
    t[k + 1] = t[k] + dt



# Now we can plot the graph.
plt.plot(t, Vc1, label="Voltage of First Capacitor")
plt.plot(t, Vc2, label="Voltage of Second Capacitor")

# Now we can add the labels and the legend.
plt.xlabel("Time (second)")
plt.title("Lab Project [1]")
plt.legend()
plt.show()

# End of the project.