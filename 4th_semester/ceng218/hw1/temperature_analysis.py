# Question's pseudo code:
def TEMP(days, temperatures, start, end):
    start_index = start - days[0]
    end_index = end - start + start_index
    temp_list = 0
    for d in range(start_index, end_index + 1):
        temp_list = temp_list + temperatures[d]
    return temp_list

# Solution in Q1-a:
def TARGET_TEMP(days, temperatures):
    max_temp = float('-inf') # negative infinity
    for i in range(len(days)):
        for j in range(i+1,len(days)): 
            temp_list = TEMP(days, temperatures, days[i], days[j]) 
            if temp_list > max_temp: 
                max_temp = temp_list 
                t_start = days[i] #target start
                t_end = days[j] #target end
    return t_start, t_end

#We can use print() now to test the function:
print(TARGET_TEMP([22, 23, 24, 25, 26], [9, 2, 3, 11, 4]))  ## (22, 26)
print(TARGET_TEMP([34, 35, 36, 37, 38], [-1, -3, 7, 4, 2])) ## (36, 38)
print(TARGET_TEMP([1, 2, 3, 4, 5], [5, -2, 3, 8, -4]))      ## (1, 4)


