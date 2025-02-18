#%%
#Homework-2

def countCharacter(string):
    count = 0
    
    for i in string:
        if i == char:
            count += 1
    
    return count

text = input("Please write your text:\n") 
char = input("Please write your special character:\n")


while len(char) != 1:
    char = input("Please enter a valid character: ")

print("There is a", countCharacter(text), f"special character '{char}'.")