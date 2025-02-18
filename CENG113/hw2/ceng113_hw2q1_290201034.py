#Homework-1

import time

def createUsername():
    
    while True:
        
        print("Welcome to registration page!"),
        time.sleep(1)
        username = input("Please enter the username:\n")
        
        if not username.startswith('e'):
            print("There must be 'e' at the begining of the username!")
        
        elif len(username) < 6 or len(username) > 12:
            print("Username must be at least 6 characters and no more than 12 characters long!")
            
        elif username.isalnum() == False:
            print("Username must contain only alphanumeric characters!")
            
        else:
            print("Username registration is successful. Hello "+ username +"!")
            time.sleep(1)
            print("Now you can create a password.")
            break
            
    return username

def createPassword():
    
    while True:
        password = input("Please enter the password:\n")
        
        if len(password) < 8:
            print("Password must be at least 8 characters long.")
        
        else:
            print("Registration successful!")
            break
        
        return password


createUsername()
createPassword()

#%%
