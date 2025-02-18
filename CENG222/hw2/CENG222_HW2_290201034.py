# %% Imports
import random
import numpy as np
from matplotlib import pyplot as plt

# %% Functions

# Function to generate a population with given parameter and size using the
# inverse transformation method.
def gen_inverse(k, M):
    examp = np.zeros(M)
    for i in range(M):
        U = random.random()
        examp[i] = U ** (1 / (k+1))
    return examp


# Function to generate a population with given parameter and size using the
# rejection method.
def gen_rejection(k, M):
    a = 0
    b = 1
    c = k+1
    count = 0
    examp = np.zeros(M)
    
    while count < M:
        U = random.random()
        V = random.random()
        X = a + (b - a) * U
        Y = c * V
        if Y <= (k+1) * X ** k:
            examp[count] = X
            count += 1
            
    return examp


# Function to calculate the population mean using k.
def calc_population_mean(k):
    return (k+1) / (k+2) # E[X] = (k+1)/(k+2)


# Function to calculate the population variance using k.
def calc_population_variance(k):
    # Var(X) = E[X^2] - E[X]^2 = (k+1)/(k+3) - (k+1)^2/(k+2)^2
    # E(X)   = (k+1)/(k+2)
    # E(X^2) = (k+1)/(k+3)
    return ((k+1)/(k+3)) - ((k+1) / (k+2))**2 # Var(X)


# Function to randomly take samples of size N from a population.
def random_sample(population, N):
    examp = np.zeros(N, dtype=float)
    for i in range(N):
        examp[i] = float(random.random() * (len(population)))
    return population[examp.astype(int)]

# Function to calculate the sample mean.
def calc_sample_mean(sample):
    examps_sum = np.sum(sample)
    examps_mean = examps_sum / len(sample)
    return examps_mean


# Function to calculate the sample variance (biased/unbiased).
def calc_sample_variance(sample, unbiased=True):
    examps_mean = calc_sample_mean(sample) # sample mean
    calc = np.sum((sample - examps_mean) ** 2) # sum of (x_i - x_mean)^2
    if unbiased:
        sample_variance = calc / (len(sample) - 1) # unbiased sample variance
    else:
        sample_variance = calc / len(sample) # biased sample variance
    return sample_variance


# Function to estimate the parameter k using method of moments
def estimate_k_mom(sample):
    # mean = sum(sample) / size(sample)
    # k = (1-2*mean)/(mean-1)
    mean = (np.sum(sample))/(np.size(sample))
    
    return ((1-2*mean)/(mean-1))

# Function to estimate the parameter k using maximum likelihood
def estimate_k_mle(sample):
    n = len(sample)
    
    log_sum = np.sum(np.log(sample))
    k_mle = (-n/log_sum) - 1
    
    return k_mle

# Function to calculate the confidence interval for population mean given the
# sample and the required confidence level. If population standard deviation is
# not provided, use sample standard deviation as its estimator. As confidence
# level, it should only accept 95, 96, 97, 98 and 99 for which the z values are
# hard-coded in the function.
def calc_conf_int_mean(sample, confidence_lvl, pop_std=0):
    z_values = {95: 1.960, 96: 2.054, 97: 2.170, 98: 2.326, 99: 2.576}
    if confidence_lvl not in z_values:
        raise ValueError('Confidence level should be 95, 96, 97, 98 or 99.')

    n = len(sample)
    examps_mean = sum(sample) / n

    if pop_std == 0:
        # standard deviation
        variance_hp = 0
        for x in sample:
            variance_hp += (x - examps_mean) ** 2
        std_dev = (variance_hp / (n - 1)) ** 0.5
        std_err = std_dev / (n ** 0.5)
    else:
        std_err = pop_std / (n ** 0.5)


    z = z_values[confidence_lvl]
    
    conf_int = (examps_mean - z * std_err, examps_mean + z * std_err) # confidence interval
    
    return conf_int

# %% Experiments

# Generate the two populations of size 1000000, calculate and print their means
# and variances and plot the population histograms.
M = 1000000
k_1 = 2.1
k_2 = 3.7
conf_lvl = 97

# YOUR CODE HERE
population_1_inv = gen_inverse(k_1, M) # population 1 using inverse transformation
population_2_rej = gen_rejection(k_2, M) # population 2 using rejection method

population_1_mean = calc_population_mean(k_1) # population 1 mean
population_1_variance = calc_population_variance(k_1) # population 1 variance

population_2_mean = calc_population_mean(k_2) # population 2 mean
population_2_variance = calc_population_variance(k_2) # population 2 variance

print("Inverse Transformation Method")
print("Population 1 Mean    : ", population_1_mean)
print("Population 1 Variance: ", population_1_variance,"\n")

print("Rejection Method")
print("Population 2 Mean:     ", population_2_mean)
print("Population 2 Variance: ", population_2_variance)

plt.figure()
# YOUR CODE HERE
plt.subplot(1, 2, 1)
plt.hist(population_1_inv, bins=100, color='green', alpha=0.7, label = f'k={k_1}' ,edgecolor = 'black')
plt.title('Population 1 Histogram')
plt.xlabel('X')
plt.ylabel('Y')
plt.legend()

plt.subplot(1, 2, 2)
plt.hist(population_2_rej, bins=100, color='blue', alpha=0.7, label = f'k={k_2}' ,edgecolor = 'black' )
plt.title('Population 2 Histogram')
plt.xlabel('X')
plt.ylabel('Y')
plt.legend()

plt.show()

# Collect 100000 random samples of size 25 from both populations, calculate
# sample means, biased and unbiased sample variances, MoM and MLE estimates of
# the parameter k and population mean intervals with 97% confidence with and
# without the population standard deviation for each sample of each population.
N = 25
R = 100000

# YOUR CODE HERE

sample_mean_1 = []
biased_sample_variance_1 = []
unbiased_sample_variance_1 = []
mom_estimate_1 = []
mle_estimate_1 = [] 
conf_int_1 = [] # without population standard deviation
conf_int_1_std = [] # with population standard deviation

sample_mean_2 = []
biased_sample_variance_2 = []
unbiased_sample_variance_2 = []
mom_estimate_2 = []
mle_estimate_2 = []
conf_int_2 = [] # without population standard deviation
conf_int_2_std = [] # with population standard deviation

for i in range(R):
    sample_1 = random_sample(population_1_inv, N) # sample from population 1
    sample_2 = random_sample(population_2_rej, N) # sample from population 2
    
    sample_mean_1.append(calc_sample_mean(sample_1)) # sample mean of sample 1
    biased_sample_variance_1.append(calc_sample_variance(sample_1, False)) # biased sample variance of sample 1
    unbiased_sample_variance_1.append(calc_sample_variance(sample_1, True)) # unbiased sample variance of sample 1
    mom_estimate_1.append(estimate_k_mom(sample_1)) # MoM estimate of sample 1
    mle_estimate_1.append(estimate_k_mle(sample_1)) # MLE estimate of sample 1
    conf_int_1.append(calc_conf_int_mean(sample_1,97,pop_std=population_1_variance ** 0.5)) # confidence interval of sample 1
    conf_int_1_std.append(calc_conf_int_mean(sample_1,97,pop_std=0)) # confidence interval of sample 1 with population standard deviation
    
    
    sample_mean_2.append(calc_sample_mean(sample_2)) # sample mean of sample 2
    biased_sample_variance_2.append(calc_sample_variance(sample_2, False)) # biased sample variance of sample 2
    unbiased_sample_variance_2.append(calc_sample_variance(sample_2, True)) # unbiased sample variance of sample 2
    mom_estimate_2.append(estimate_k_mom(sample_2)) # MoM estimate of sample 2
    mle_estimate_2.append(estimate_k_mle(sample_2)) # MLE estimate of sample 2
    conf_int_2.append(calc_conf_int_mean(sample_2,97,pop_std=population_2_variance ** 0.5)) # confidence interval of sample 2
    conf_int_2_std.append(calc_conf_int_mean(sample_2,97,pop_std=0)) # confidence interval of sample 2 with population standard deviation


# Calculate and print means of sample means, biased and unbiased sample
# variances, MoM and MLE estimates of parameter k and plot the histograms of
# sample means, k estimates using MoM and MLE for both populations.

# YOUR CODE HERE
mean_of_sample_means_1 = np.mean(sample_mean_1) # mean of sample means of sample 1
mean_of_biased_sample_variance_1 = np.mean(biased_sample_variance_1) # mean of biased sample variance of sample 1
mean_of_unbiased_sample_variance_1 = np.mean(unbiased_sample_variance_1) # mean of unbiased sample variance of sample 1
mean_of_mom_estimate_1 = np.mean(mom_estimate_1) # mean of MoM estimate of sample 1
mean_of_mle_estimate_1 = np.mean(mle_estimate_1) # mean of MLE estimate of sample 1


mean_of_sample_means2 = np.mean(sample_mean_2) # mean of sample means of sample 2
mean_of_biased_sample_variance_2 = np.mean(biased_sample_variance_2) # mean of biased sample variance of sample 2
mean_of_unbiased_sample_variance_2 = np.mean(unbiased_sample_variance_2) # mean of unbiased sample variance of sample 2
mean_of_mom_estimate_2 = np.mean(mom_estimate_2) # mean of MoM estimate of sample 2
mean_of_mle_estimate_2 = np.mean(mle_estimate_2) # mean of MLE estimate of sample 2

print("\n")
print("Population 1\n")
print("Mean of Sample Means: ", mean_of_sample_means_1)
print("Mean of Biased Sample Variance: ", mean_of_biased_sample_variance_1)
print("Mean of Unbiased Sample Variance: ", mean_of_unbiased_sample_variance_1)
print("Mean of MoM Estimate: ", mean_of_mom_estimate_1)
print("Mean of MLE Estimate: ", mean_of_mle_estimate_1)

print("\n")
print("Population 2\n")
print("Mean of Sample Means: ", mean_of_sample_means2)
print("Mean of Biased Sample Variance: ", mean_of_biased_sample_variance_2)
print("Mean of Unbiased Sample Variance: ", mean_of_unbiased_sample_variance_2)
print("Mean of MoM Estimate: ", mean_of_mom_estimate_2)
print("Mean of MLE Estimate: ", mean_of_mle_estimate_2)

plt.figure()
# YOUR CODE HERE
plt.subplot(1, 2, 1)
plt.hist(sample_mean_1, bins=100, color='green', alpha=0.7, label = 'Sample Mean', edgecolor = 'black')
plt.title('Sample Mean Histogram of \nPopulation 1')
plt.xlabel('X')
plt.ylabel('Y')

plt.subplot(1, 2, 2)
plt.hist(sample_mean_2, bins=100, color='blue', alpha=0.7, label = 'Sample Mean', edgecolor = 'black')
plt.title('Sample Mean Histogram of \nPopulation 2')
plt.xlabel('X')
plt.ylabel('Y')

plt.show()



plt.figure()
# YOUR CODE HERE
plt.subplot(1, 2, 1)
plt.hist(mom_estimate_1, bins=100, color='green', alpha=0.7, label = 'MoM Estimate', edgecolor = 'black')
plt.title('MoM Estimate Histogram of \nPopulation 1')
plt.xlabel('X')
plt.ylabel('Y')

plt.subplot(1, 2, 2)
plt.hist(mom_estimate_2, bins=100, color='blue', alpha=0.7, label = 'MoM Estimate', edgecolor = 'black')
plt.title('MoM Estimate Histogram of \nPopulation 2')
plt.xlabel('X')
plt.ylabel('Y')

plt.show()



plt.figure()
# YOUR CODE HERE
plt.subplot(1, 2, 1)
plt.hist(mle_estimate_1, bins=100, color='green', alpha=0.7, label = 'MLE Estimate', edgecolor = 'black')
plt.title('MLE Estimate Histogram of \nPopulation 1')
plt.xlabel('X')
plt.ylabel('Y')

plt.subplot(1, 2, 2)
plt.hist(mle_estimate_2, bins=100, color='blue', alpha=0.7, label = 'MLE Estimate', edgecolor = 'black')
plt.title('MLE Estimate Histogram of \nPopulation 2')
plt.xlabel('X')
plt.ylabel('Y')

plt.show()

# Calculate and print the ratio of confidence intervals computed with and
# without using the population standard deviation that contains the population
# mean for both populations.

# YOUR CODE HERE
counter_1 = 0
counter_without_1 = 0
counter_2 = 0
counter_without_2 = 0

for interval in conf_int_1:
    if interval[0] <= population_1_mean <= interval[1]:
        counter_1 += 1 # confidence interval contains population mean

for interval in conf_int_1_std:
    if interval[0] <= population_1_mean <= interval[1]:
        counter_without_1 += 1

for interval in conf_int_2:
    if interval[0] <= population_2_mean <= interval[1]:
        counter_2 += 1

for interval in conf_int_2_std:
    if interval[0] <= population_2_mean <= interval[1]:
        counter_without_2 += 1

ratio_1 = counter_1 / R # ratio of confidence intervals of population 1
ratio_without_1 = counter_without_1 / R # ratio of confidence intervals of population 1 without population standard deviation
ratio_2 = counter_2 / R # ratio of confidence intervals of population 2
ratio_without_2 = counter_without_2 / R # ratio of confidence intervals of population 2 without population standard deviation

print("\n")
print("Population 1\n")
print("Ratio of Confidence Intervals: ", ratio_1)
print("Ratio of Confidence Intervals without Population Standard Deviation: ", ratio_without_1)

print("\n")
print("Population 2\n")
print("Ratio of Confidence Intervals: ", ratio_2)
print("Ratio of Confidence Intervals without Population Standard Deviation: ", ratio_without_2)

print("\n")
print('*'*50)
# Collect a sample of length 100000*25 from both populations, calculate and
# print their sample means, biased and unbiased sample variances, MoM and MLE
# estimates of parameter k and confidence intervals with and without using the
# population standard deviation.

# YOUR CODE HERE

sample_3 = random_sample(population_1_inv, 100000*25) # sample from population 1
sample_4 = random_sample(population_2_rej, 100000*25) # sample from population 2

sample_mean_3 = calc_sample_mean(sample_3) # sample mean of sample 3
biased_sample_variance_3 = calc_sample_variance(sample_3, False) # biased sample variance of sample 3
unbiased_sample_variance_3 = calc_sample_variance(sample_3, True) # unbiased sample variance of sample 3
mom_estimate_3 = estimate_k_mom(sample_3) # MoM estimate of sample 3
mle_estimate_3 = estimate_k_mle(sample_3) # MLE estimate of sample 3
conf_int_3 = calc_conf_int_mean(sample_3,97,pop_std=population_1_variance ** 0.5) # confidence interval of sample 3
conf_int_3_std = calc_conf_int_mean(sample_3,97,pop_std=0) # confidence interval of sample 3 with population standard deviation

sample_mean_4 = calc_sample_mean(sample_4) # sample mean of sample 4
biased_sample_variance_4 = calc_sample_variance(sample_4, False) # biased sample variance of sample 4
unbiased_sample_variance_4 = calc_sample_variance(sample_4, True) # unbiased sample variance of sample 4
mom_estimate_4 = estimate_k_mom(sample_4) # MoM estimate of sample 4
mle_estimate_4 = estimate_k_mle(sample_4) # MLE estimate of sample 4
conf_int_4 = calc_conf_int_mean(sample_4,97,pop_std=population_2_variance ** 0.5) # confidence interval of sample 4
conf_int_4_std = calc_conf_int_mean(sample_4,97,pop_std=0) # confidence interval of sample 4 with population standard deviation

print("\n")
print("Population 1 (100000x25)\n")
print("Sample Mean: ", sample_mean_3)
print("Biased Sample Variance: ", biased_sample_variance_3)
print("Unbiased Sample Variance: ", unbiased_sample_variance_3)
print("MoM Estimate: ", mom_estimate_3)
print("MLE Estimate: ", mle_estimate_3)
print("Confidence Interval: ", conf_int_3)
print("Confidence Interval without Population Standard Deviation: ", conf_int_3_std)

print("\n")
print("Population 2 (100000x25)\n")
print("Sample Mean: ", sample_mean_4)
print("Biased Sample Variance: ", biased_sample_variance_4)
print("Unbiased Sample Variance: ", unbiased_sample_variance_4)
print("MoM Estimate: ", mom_estimate_4)
print("MLE Estimate: ", mle_estimate_4)
print("Confidence Interval: ", conf_int_4)
print("Confidence Interval without Population Standard Deviation: ", conf_int_4_std)

print("\n")
print("*"*50, "\n")
# %%
# Ahmet Kurt 290201034