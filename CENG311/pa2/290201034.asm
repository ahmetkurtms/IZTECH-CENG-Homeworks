##############################################################
#Array
##############################################################
#   4 Bytes - Address of the Data
#   4 Bytes - Size of array
#   4 Bytes - Size of elements
##############################################################

##############################################################
#Linked List
##############################################################
#   4 Bytes - Address of the First Node
#   4 Bytes - Size of linked list
##############################################################

##############################################################
#Linked List Node
##############################################################
#   4 Bytes - Address of the Data
#   4 Bytes - Address of the Next Node
##############################################################

##############################################################
#Recipe
##############################################################
#   4 Bytes - Name (address of the name)
#	4 Bytes - Ingredients (address of the ingredients array)
#   4 Bytes - Cooking Time
#	4 Bytes - Difficulty
#	4 Bytes - Rating
##############################################################


.data
space: .asciiz " "
newLine: .asciiz "\n"
tab: .asciiz "\t"
lines: .asciiz "------------------------------------------------------------------\n"

listStr: .asciiz "List: \n"
recipeName: .asciiz "Recipe name: "
ingredients: .asciiz "Ingredients: "
cookingTime: .asciiz "Cooking time: "
difficulty: .asciiz "Difficulty: "
rating: .asciiz "Rating: "
listSize: .asciiz "List Size: "
emptyListWarning: .asciiz "List is empty!\n"
indexBoundWarning: .asciiz "Index out of bounds!\n"
recipeNotMatch: .asciiz "Recipe not matched!\n"
recipeMatch: .asciiz "Recipe matched!\n"
recipeAdded: .asciiz "Recipe added.\n"
recipeRemoved: .asciiz "Recipe removed.\n"
noRecipeWarning: .asciiz "No recipe to print!\n"

addressOfRecipeList: .word 0 #the address of the array of recipe list stored here!


# Recipe 1: Pancakes
r1: .asciiz "Pancakes"
r1i1: .asciiz "Flour"
r1i2: .asciiz "Milk"
r1i3: .asciiz "Eggs"
r1i4: .asciiz "Sugar"
r1i5: .asciiz "Baking powder"
r1c: .word 15							# Cooking time in minutes
r1d: .word 2							# Difficulty (scale 1-5)
r1r: .word 4							# Rating (scale 1-5)

# Recipe 2: Spaghetti Bolognese
r2: .asciiz "Spaghetti Bolognese"
r2i1: .asciiz "Spaghetti"
r2i2: .asciiz "Ground beef"
r2i3: .asciiz "Tomato sauce"
r2i4: .asciiz "Garlic"
r2i5: .asciiz "Onion"
r2c: .word 30
r2d: .word 3
r2r: .word 5

# Recipe 3: Chicken Stir-Fry
r3: .asciiz "Chicken Stir-Fry"
r3i1: .asciiz "Chicken breast"
r3i2: .asciiz "Soy sauce"
r3i3: .asciiz "Bell peppers"
r3i4: .asciiz "Broccoli"
r3i5: .asciiz "Garlic"
r3c: .word 20
r3d: .word 3
r3r: .word 4

# Recipe 4: Caesar Salad
r4: .asciiz "Caesar Salad"
r4i1: .asciiz "Romaine lettuce"
r4i2: .asciiz "Caesar dressing"
r4i3: .asciiz "Parmesan cheese"
r4i4: .asciiz "Croutons"
r4i5: .asciiz "Chicken breast (optional)"
r4c: .word 10
r4d: .word 1
r4r: .word 4

# Recipe 5: Chocolate Chip Cookies
r5: .asciiz "Chocolate Chip Cookies"
r5i1: .asciiz "Butter"
r5i2: .asciiz "Sugar"
r5i3: .asciiz "Flour"
r5i4: .asciiz "Eggs"
r5i5: .asciiz "Chocolate chips"
r5c: .word 25
r5d: .word 2
r5r: .word 5


search1: .asciiz "Caesar Salad"
search2: .asciiz "Shepherd's Pie"

.text
main:
    # Create linked list
    jal createLinkedList
    sw $v0, addressOfRecipeList

    # Create Array for Pancakes ingredients
    li $a0, 5          # Size of the array
    li $a1, 4          # Size of the elements
    jal createArray
    move $s0, $v0

    # Add Pancakes ingredients
    move $a0, $s0
    la $a2, r1i1       # Flour
    li $a1, 0
    jal putElementToArray

    move $a0, $s0
    la $a2, r1i2       # Milk
    li $a1, 1
    jal putElementToArray

    move $a0, $s0
    la $a2, r1i3       # Eggs
    li $a1, 2
    jal putElementToArray

    move $a0, $s0
    la $a2, r1i4       # Sugar
    li $a1, 3
    jal putElementToArray

    move $a0, $s0
    la $a2, r1i5       # Baking powder
    li $a1, 4
    jal putElementToArray

    # Create Pancakes recipe
    addi $sp, $sp, -4
    lw $t0, r1r
    sw $t0, 0($sp)
    la $a0, r1         # Name
    move $a1, $s0
    lw $a2, r1c        # Cooking time
    lw $a3, r1d        # Difficulty
    jal createRecipe
    addi $sp, $sp, 4
    move $s1, $v0

    # Enqueue Pancakes recipe
    lw $a0, addressOfRecipeList  # Load list address
    move $a1, $s1               # Recipe address
    jal enqueue                 # Add to list

    # Create and Add Spaghetti Bolognese Recipe
    li $a0, 5          # Size
    li $a1, 4          # Element size
    jal createArray
    move $s0, $v0      # Save array address

    # Add Spaghetti ingredients
    move $a0, $s0
    la $a2, r2i1       # Spaghetti
    li $a1, 0
    jal putElementToArray

    move $a0, $s0
    la $a2, r2i2       # Ground beef
    li $a1, 1
    jal putElementToArray

    move $a0, $s0
    la $a2, r2i3       # Tomato sauce
    li $a1, 2
    jal putElementToArray

    move $a0, $s0
    la $a2, r2i4       # Garlic
    li $a1, 3
    jal putElementToArray

    move $a0, $s0
    la $a2, r2i5       # Onion
    li $a1, 4
    jal putElementToArray

    # Create Spaghetti Bolognese recipe
    addi $sp, $sp, -4
    lw $t0, r2r
    sw $t0, 0($sp)
    la $a0, r2         # Name
    move $a1, $s0      # Ingredients array
    lw $a2, r2c        # Cooking time
    lw $a3, r2d        # Difficulty
    jal createRecipe
    addi $sp, $sp, 4
    move $s1, $v0

    # Enqueue Spaghetti Bolognese recipe
    lw $a0, addressOfRecipeList
    move $a1, $s1
    jal enqueue

    #Print queue size
    lw $a0, addressOfRecipeList
    jal queueSize

    #List:
    la $a0, listStr
    li $v0, 4
    syscall

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #Current Recipes in the list (traverseLinkedList)
    lw $a0, addressOfRecipeList
    la $a1, printRecipe
    jal traverseLinkedList

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #New Line
    la $a0, newLine
    li $v0, 4
    syscall

    #Dequeue and print the first recipe
    lw $a0, addressOfRecipeList
    jal dequeue
    lw $t0, 0($v0)
    move $a0, $t0
    jal printRecipe

    #New Line
    la $a0, newLine
    li $v0, 4
    syscall

    #Print queue size
    lw $a0, addressOfRecipeList
    jal queueSize

    #List:
    la $a0, listStr
    li $v0, 4
    syscall

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #Print remaining recipes in the list (traverseLinkedList)
    lw $a0, addressOfRecipeList
    la $a1, printRecipe
    jal traverseLinkedList

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #New line
    la $a0, newLine
    li $v0, 4
    syscall

    #Dequeue and print the next recipe (Spaghetti Bolognese)
    lw $a0, addressOfRecipeList
    jal dequeue
    lw $t0, 0($v0)
    move $a0, $t0
    jal printRecipe

    #New Line
    la $a0, newLine
    li $v0, 4
    syscall

    # Dequeue and print the next recipe
    lw $a0, addressOfRecipeList
    jal dequeue
    move $a0, $t0
    jal printRecipe

    # New Line
    la $a0, newLine
    li $v0, 4
    syscall

    #List size
    lw $a0, addressOfRecipeList
    jal queueSize

    #List:
    la $a0, listStr
    li $v0, 4
    syscall

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #Print remaining recipes in the list (traverseLinkedList)
    lw $a0, addressOfRecipeList
    la $a1, printRecipe
    jal traverseLinkedList

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #New line
    la $a0, newLine
    li $v0, 4
    syscall

    #Create and Add Chicken Stir-Fry Recipe
    li $a0, 5          # Size
    li $a1, 4          # Element size
    jal createArray
    move $s0, $v0

    # Add Chicken Stir-Fry ingredients
    move $a0, $s0
    la $a2, r3i1       # Chicken breast
    li $a1, 0
    jal putElementToArray

    move $a0, $s0
    la $a2, r3i2       # Soy sauce
    li $a1, 1
    jal putElementToArray

    move $a0, $s0
    la $a2, r3i3       # Bell peppers
    li $a1, 2
    jal putElementToArray

    move $a0, $s0
    la $a2, r3i4       # Broccoli
    li $a1, 3
    jal putElementToArray

    move $a0, $s0
    la $a2, r3i5       # Garlic
    li $a1, 4
    jal putElementToArray

    # Create Chicken Stir-Fry recipe
    addi $sp, $sp, -4
    lw $t0, r3r
    sw $t0, 0($sp)
    la $a0, r3         # Name
    move $a1, $s0      # Ingredients array
    lw $a2, r3c        # Cooking time
    lw $a3, r3d        # Difficulty
    jal createRecipe
    addi $sp, $sp, 4
    move $s1, $v0

    # Enqueue Chicken Stir-Fry recipe
    lw $a0, addressOfRecipeList
    move $a1, $s1
    jal enqueue

    #Print queue size
    lw $a0, addressOfRecipeList
    jal queueSize

    #List:
    la $a0, listStr
    li $v0, 4
    syscall

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #Print current recipes in the list (traverseLinkedList)
    lw $a0, addressOfRecipeList
    la $a1, printRecipe
    jal traverseLinkedList

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #New Line
    la $a0, newLine
    li $v0, 4
    syscall

    #Create and Add Caesar Salad Recipe (array with 4 elements)
    li $a0, 4          # Size
    li $a1, 4          # Element size
    jal createArray
    move $s0, $v0

    # Add Caesar Salad ingredients
    move $a0, $s0
    la $a2, r4i1       # Romaine lettuce
    li $a1, 0
    jal putElementToArray

    move $a0, $s0
    la $a2, r4i2       # Caesar dressing
    li $a1, 1
    jal putElementToArray

    move $a0, $s0
    la $a2, r4i3       # Parmesan cheese
    li $a1, 2
    jal putElementToArray

    move $a0, $s0
    la $a2, r4i4       # Croutons
    li $a1, 3
    jal putElementToArray

	move $a0, $s0
    la $a2, r4i5       # Chicken breast (optional)
    li $a1, 4
    jal putElementToArray

    # Create Caesar Salad recipe
    addi $sp, $sp, -4
    lw $t0, r4r
    sw $t0, 0($sp)
    la $a0, r4         # Name
    move $a1, $s0      # Ingredients array
    lw $a2, r4c        # Cooking time
    lw $a3, r4d        # Difficulty
    jal createRecipe
    addi $sp, $sp, 4
    move $s1, $v0

    # Enqueue Caesar Salad recipe
    lw $a0, addressOfRecipeList
    move $a1, $s1
    jal enqueue

    #Print queue size
    lw $a0, addressOfRecipeList
    jal queueSize

    #List:
    la $a0, listStr
    li $v0, 4
    syscall

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #Print current recipes in the list (traverseLinkedList)
    lw $a0, addressOfRecipeList
    la $a1, printRecipe
    jal traverseLinkedList

    #Lines
    la $a0, lines
    li $v0, 4
    syscall

    #New Line
    la $a0, newLine
    li $v0, 4
    syscall

    #Create and Add Chocolate Chip Cookies Recipe
    li $a0, 5          # Size
    li $a1, 4          # Element size
    jal createArray
    move $s0, $v0      # Save array address

    # Add Chocolate Chip Cookies ingredients
    move $a0, $s0
    la $a2, r5i1       # Butter
    li $a1, 0
    jal putElementToArray

    move $a0, $s0
    la $a2, r5i2       # Sugar
    li $a1, 1
    jal putElementToArray

    move $a0, $s0
    la $a2, r5i3       # Flour
    li $a1, 2
    jal putElementToArray

    move $a0, $s0
    la $a2, r5i4       # Eggs
    li $a1, 3
    jal putElementToArray

    move $a0, $s0
    la $a2, r5i5       # Chocolate chips
    li $a1, 4
    jal putElementToArray

    # Create Chocolate Chip Cookies recipe
    addi $sp, $sp, -4
    lw $t0, r5r
    sw $t0, 0($sp)
    la $a0, r5         # Name
    move $a1, $s0      # Ingredients array
    lw $a2, r5c        # Cooking time
    lw $a3, r5d        # Difficulty
    jal createRecipe
    addi $sp, $sp, 4
    move $s1, $v0

    # Enqueue Chocolate Chip Cookies recipe
    lw $a0, addressOfRecipeList
    move $a1, $s1
    jal enqueue

    # Print queue size
    lw $a0, addressOfRecipeList
    jal queueSize

    # List:
    la $a0, listStr
    li $v0, 4
    syscall

    # Lines
    la $a0, lines
    li $v0, 4
    syscall

    # Print current recipes in the list (traverseLinkedList)
    lw $a0, addressOfRecipeList
    la $a1, printRecipe
    jal traverseLinkedList

    # Lines
    la $a0, lines
    li $v0, 4
    syscall

    # New Line
    la $a0, newLine
    li $v0, 4
    syscall

    la $a0, addressOfRecipeList
    lw $a0, 0($a0)
    la $a1, findRecipe
    la $a2, search1

    jal traverseLinkedList

    la $a0, newLine
    li $v0, 4
    syscall

    la $a0, addressOfRecipeList
    lw $a0, 0($a0)
    la $a1, findRecipe
    la $a2, search2

    jal traverseLinkedList

mainTerminate:
li $v0, 10
syscall

#########################################################################################

createArray:
    # Create an array
    # Inputs: $a0 - max number of elements (size), $a1 - size of elements
    # Outputs: $v0 - address of array

    # Save $ra and $sp
    addi $sp, $sp, -4
    sw $ra, 0($sp)
	move $t5, $a0

    # Calculate total data size
    mul $t0, $a0, $a1

    move $a0, $t0
    li $v0, 9
    syscall
    move $t1, $v0

    li $a0, 12
    li $v0, 9
    syscall
    move $v0, $v0

    sw $t1, 0($v0)
    sw $t5, 4($v0)
    sw $a1, 8($v0)

    # Restoring $ra and return
    lw $ra, 0($sp)
    addi $sp, $sp, 4
    jr $ra

##########################################################################################

putElementToArray:
	# Store an element (recipe) in an array.
	# Inputs: $a0 - address of array, $a1 - element address, $a2 - index


	addi $sp, $sp, -4
	sw $ra, 0($sp)

    lw $t0, 0($a0)
    lw $t1, 4($a0)
    lw $t2, 8($a0)

    # Check bounds
    bge $a1, $t1, indexOutOfBounds # if index >= size
    bltz $a1, indexOutOfBounds

    mul $t3, $a1, $t2  # offset = index * element size
    add $t4, $t0, $t3  # target address = data address + offset

    sw $a2, 0($t4)
    jr $ra

    indexOutOfBounds:

    la $a0, indexBoundWarning
    li $v0, 4
    syscall

	lw $ra, 0($sp)
	addi $sp, $sp, 4
    jr $ra

##########################################################################################

createLinkedList:
    # Create a linked list.
    # Outputs: $v0 - address of linked List

    li $a0, 8
    li $v0, 9
    syscall

    sw $zero, 0($v0)
    sw $zero, 4($v0)
    jr $ra

##########################################################################################

enqueue:
	# Inputs: $a0 - address of the linked list structure, $a1 - address of data to add

    addi $sp, $sp, -8
    sw $ra, 0($sp)
    sw $s0, 4($sp)
    move $s0, $a0

    li $a0, 8
    li $v0, 9
    syscall
    move $t0, $v0      # t0 = new node address

    # Initializing new node
    sw $a1, 0($t0)
    sw $zero, 4($t0)   # next = null ?

    lw $t1, 0($s0)     # t1 = head pointer
    beqz $t1, enqueue_empty

    # Last node
    move $t2, $t1

    enqueue_loop:
    lw $t3, 4($t2)     # t3 = next pointer
    beqz $t3, enqueue_end
    move $t2, $t3
    j enqueue_loop

    enqueue_end:
    sw $t0, 4($t2)     # set next pointer of last node
    j enqueue_update_size

    enqueue_empty:
    sw $t0, 0($s0)

    enqueue_update_size:
    # Increment of list size
    lw $t4, 4($s0)
    addi $t4, $t4, 1
    sw $t4, 4($s0)

    la $a0, recipeAdded
    li $v0, 4
    syscall

    lw $ra, 0($sp)
    lw $s0, 4($sp)
    addi $sp, $sp, 8
    jr $ra

##########################################################################################

dequeue:
    # Inputs: $a0 - address of the linked list structure
    # Outputs: $v0 - removed head node, 0 if empty

    addi $sp, $sp, -8
    sw $ra, 0($sp)
    sw $s0, 4($sp)
    move $s0, $a0

    lw $t0, 0($s0)
    lw $t1, 4($s0)

    # If list is empty
    beqz $t0, dequeue_empty

    lw $t2, 4($t0)  # $t2 = next node

    # Update head and size
    sw $t2, 0($s0)
    addi $t1, $t1, -1  # Decrement size
    sw $t1, 4($s0)  # Store new size

    # Print removed message
    la $a0, recipeRemoved
    li $v0, 4
    syscall

    # Removed node
    move $v0, $t0

    lw $ra, 0($sp)
    lw $s0, 4($sp)
    addi $sp, $sp, 8
    jr $ra

    dequeue_empty:

    la $a0, emptyListWarning
    li $v0, 4
    syscall

    la $a0, recipeRemoved
    li $v0, 4
    syscall

    move $v0, $zero
    jr $ra

##########################################################################################

queueSize:
    # Inputs: $a0 - address of the linked list structure

    # Save the original $a0 to $s0
    move $s0, $a0

    # Print "List Size: "
    la $a0, listSize
    li $v0, 4
    syscall

    # Print size
    lw $t0, 4($s0) # Load size
    move $a0, $t0
    li $v0, 1
    syscall

    # Print newline
    la $a0, newLine
    li $v0, 4
    syscall

    jr $ra

##########################################################################################

traverseArray:
	# Traverse and print recipes from array.
	# Inputs: $a0 - address of array, $a1 - called function

    # Stack space and save registers
    addi $sp, $sp, -12
    sw $ra, 0($sp)
    sw $s0, 4($sp)
    sw $s1, 8($sp)
    move $s0, $a0      # s0 = array address
    move $s1, $a1      # s1 = function address

    # Load array
    lw $t0, 0($s0)
    lw $t1, 4($s0)
    lw $t2, 8($s0)
    li $t3, 0

    traverse_array_loop:

    beq $t3, $t1, traverse_array_end

    mul $t4, $t3, $t2  # offset = index * element size
    add $t5, $t0, $t4  # element address = data address + offset

    # Load element (ingredient string address)
    lw $a0, 0($t5)

    jalr $s1
    addi $t3, $t3, 1   # index += 1
    j traverse_array_loop

    traverse_array_end:
    lw $ra, 0($sp)
    lw $s0, 4($sp)
    lw $s1, 8($sp)
    addi $sp, $sp, 12
    jr $ra

##########################################################################################

traverseLinkedList:
	# Traverse linked list.
	# Inputs: $a0 - head node of linked list, $a1 - called function, $a2 - extra arguments

    addi $sp, $sp, -12
    sw $ra, 0($sp)
    sw $s0, 4($sp)
    sw $s1, 8($sp)

    lw $s0, 0($a0)     # s0 = head node
    beqz $s0, traverse_list_empty
    move $s1, $a1      # s1 = function address

    traverse_list_loop:
    lw $t1, 0($s0)
    move $a0, $t1
    move $a1, $a2
    jalr $s1

    # Move to next node
    lw $s0, 4($s0)     # Load next node
    bnez $s0, traverse_list_loop

    traverse_list_end:
    lw $ra, 0($sp)
    lw $s0, 4($sp)
    lw $s1, 8($sp)
    addi $sp, $sp, 12
    jr $ra

    traverse_list_empty:
    la $a0, emptyListWarning
    li $v0, 4
    syscall
    j traverse_list_end

##########################################################################################

compareString:
	# Compare two strings.
	# Inputs: $a0 - string 1 address, $a1 - string 2 address
	# Outputs: $v0 - 0 found, 1 not found

    compare_loop:
    lb $t0, 0($a0)  # string 1
    lb $t1, 0($a1)  # string 2

    bne $t0, $t1, strings_not_equal
    beqz $t0, strings_equal

    addi $a0, $a0, 1  # next byte in string 1
    addi $a1, $a1, 1  # next byte in string 2
    j compare_loop

    strings_not_equal:
    li $v0, 1
    jr $ra

    strings_equal:
    li $v0, 0
    jr $ra

##########################################################################################

createRecipe:
	# Create a recipe and store in the recipe struct.
	# Inputs: $a0 - recipe name, $a1 - address of ingredients array,
	#         $a2 - cooking time, $a3 - difficulty, 0($sp) - rating
	# Outputs: $v0 - recipe address

    # Allocating memory for recipe struct
    move $t2, $a0
    li $v0, 9
    li $a0, 20          # 20 bytes (4 * 5)
    syscall
    move $t0, $v0

    sw $t2, 0($t0)      # Name
    sw $a1, 4($t0)      # Ingredients
    sw $a2, 8($t0)      # Cooking time
    sw $a3, 12($t0)     # Difficulty
    lw $t1, 0($sp)
    sw $t1, 16($t0)     # Rating

    move $v0, $t0
    jr $ra

##########################################################################################

findRecipe:
    # Arguments:
    # $a0 = Address of the linked list structure
    # $a1 = Searched recipe name

    addi $sp, $sp, -8
    sw $ra, 4($sp)
    sw $s0, 0($sp)

    # Save the original $a0 to $s0
    move $s0, $a0
    lw $t0, 0($s0)

    move $a0, $t0
    jal compareString

    bnez $v0, notMatched

    # Print recipe found message
    la $a0, recipeMatch
    li $v0, 4
    syscall

    # Print recipe details
    move $a0, $s0
    jal printRecipe
    j find_recipe_end

    notMatched:
    la $a0, recipeNotMatch
    li $v0, 4
    syscall

    find_recipe_end:
    lw $s0, 0($sp)
    lw $ra, 4($sp)
    addi $sp, $sp, 8
    jr $ra

##########################################################################################

printRecipe:
	# Print recipe details.
	# Inputs: $a0 - address of recipe struct

    # Checking for null
    beqz $a0, print_recipe_null

    # Stack space and save registers
    addi $sp, $sp, -8
    sw $ra, 0($sp)
    sw $s0, 4($sp)

    move $s0, $a0      # s0 = recipe address

    # Print recipe name
    la $a0, recipeName
    li $v0, 4
    syscall

    lw $t0, 0($s0)     # t0 = name address
    beqz $t0, print_recipe_error # if name is null
    move $a0, $t0
    li $v0, 4
    syscall

    # Print newline
    la $a0, newLine
    li $v0, 4
    syscall

    # Print ingredients
    la $a0, ingredients
    li $v0, 4
    syscall

    # Print newline
    la $a0, newLine
    li $v0, 4
    syscall

    # Traverse and print ingredients
    lw $a0, 4($s0)     # a0 = array address
    la $a1, printIngredient
    jal traverseArray

    # Print cooking time
    la $a0, cookingTime
    li $v0, 4
    syscall

    lw $a0, 8($s0)     # cooking time
    li $v0, 1
    syscall

    # Print newline
    la $a0, newLine
    li $v0, 4
    syscall

    # Print difficulty
    la $a0, difficulty
    li $v0, 4
    syscall

    lw $a0, 12($s0)    # difficulty
    li $v0, 1
    syscall

    # Print newline
    la $a0, newLine
    li $v0, 4
    syscall

    # Print rating
    la $a0, rating
    li $v0, 4
    syscall

    lw $a0, 16($s0)    # rating
    li $v0, 1
    syscall

    # Print newline
    la $a0, newLine
    li $v0, 4
    syscall

    # Restore registers and return
    lw $ra, 0($sp)
    lw $s0, 4($sp)
    addi $sp, $sp, 8
    jr $ra

    print_recipe_error:
        la $a0, noRecipeWarning
        li $v0, 4
        syscall
        j print_recipe_end

    print_recipe_null:
        la $a0, noRecipeWarning
        li $v0, 4
        syscall

    print_recipe_end:
        jr $ra

##########################################################################################

printIngredient:
	# Print ingredient.
	# Inputs: $a0 - address of ingredient

    # For saving $a0 value I use $t6
    move $t6, $a0

    # Print tab
    la $a0, tab
    li $v0, 4
    syscall

    # Saving back $a0 value
    move $a0, $t6

    # Print ingredient string
    li $v0, 4
    syscall

    # Print newline
    la $a0, newLine
    li $v0, 4
    syscall

    jr $ra

##########################################################################################

