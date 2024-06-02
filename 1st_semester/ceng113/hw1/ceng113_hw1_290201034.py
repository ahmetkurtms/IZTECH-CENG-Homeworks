#%%
"""

CHARACTER PALETTE
You can copy the necessary characters for drawing cards from here.

Horizontal lines:  │

Vertical lines:  ─

Corners of a card:  ┐  ┌  ┘  └

Intersections of two cards:

    if card1_height == card2_height:  ┬  ┴

    if card1_height < card2_height:  ┤

    if card1_height > card2_height:  ├

"""

print("This program will draw two cards next to each other.")
print("─" * 20)

print("Texts must not be empty.")
card1_text = input("Text of first card: ")
card2_text = input("Text of second card: ")
print("─" * 20)

##############################
# INSERT YOUR CODE HERE
# Assign proper values to card1_min_width and card2_min_width here.
# They are length of the corresponding text + 2.
# For example, if card1_text contains 5 characters, then card1_min_width must be 7.
card1_min_width = len(card1_text) + 2
card2_min_width = len(card2_text) + 2
# DO NOT EDIT THE CODE UNDER THIS LINE.
##############################

print("Width of first card must be at least " + str(card1_min_width) + ".")
card1_width = int(input("Width of first card: "))
print("Width of second card must be at least " + str(card2_min_width) + ".")
card2_width = int(input("Width of second card: "))
print("─" * 20)

print("Heights must be odd and at least 3.")
card1_height = int(input("Height of first card: "))
card2_height = int(input("Height of second card: "))
print("─" * 20)


##############################
# INSERT YOUR CODE HERE
vertical_1 = card1_height - 3
upper_1 = vertical_1 // 2
lower_1 = vertical_1 - upper_1
horizontal_1 = card1_width - 2 - len(card1_text) 
left_1 = horizontal_1 // 2
right_1  = horizontal_1 - left_1
space_1 = card1_width - 2
vertical_2 = card2_height - 3
upper_2 = vertical_2 // 2
lower_2 = vertical_2 - upper_2
horizontal_2 = card2_width - 2 - len(card2_text) 
left_2 = horizontal_2 // 2
right_2  = horizontal_2 - left_2
space_2 = card2_width - 2
connecting_space = ((card1_height - card2_height) // 2) - 1
connecting_space2 = ((card2_height - card1_height) // 2) -1
# Assign the proper value to is_invalid.
# Check if there is at least one problem in the inputs.
# I added two conditions, add more to the line below.
is_invalid1 = (card1_width < card1_min_width) or (card2_width < card2_min_width) 
is_invalid2 = card1_height % 2 == 0 or card2_height % 2 == 0
is_invalid3 = card1_height < 3 or card2_height < 3 
is_invalid = len(card1_text) == 0 or len(card2_text) == 0 or is_invalid1 or is_invalid2 or is_invalid3
# DO NOT EDIT THE CODE UNDER THIS LINE.
##############################

if is_invalid:
    print("ERROR: Invalid inputs.")
else:

    if card1_height == card2_height:
        ##############################
        
        # INSERT YOUR CODE HERE
        top_line2 = (card2_width) * "─" + "┐"
        upper_lines2 = (space_2+2)*" " + "│"
        center_lines2 = (left_2+1)*" " + card2_text + (right_2+1)*" " + "│"
        lower_lines2 = (space_2+2)*" " + "│"
        bottom_line2 = (card2_width) * "─" + "┘"
        top_line1 = "┌" + (card1_width) * "─" + "┬" + top_line2 + "\n"
        upper_lines1 = ("│" + (space_1+2)*" " + "│" + upper_lines2 + "\n") * upper_1
        center_lines1 = "│" + (left_1+1)*" " + card1_text + (right_1+1)*" " + "│" + center_lines2 + "\n"
        lower_lines1 = ("│" + (space_1+2)*" " + "│" + lower_lines2 +"\n") * lower_1
        bottom_line1 = "└" + (card1_width) * "─" + "┴" + bottom_line2 + "\n"

        print(top_line1 + upper_lines1 + center_lines1 + lower_lines1 + bottom_line1)        
        # Case 1
        # You can add as many new lines as you need.
        pass


        # DO NOT EDIT THE CODE UNDER THIS LINE.
        ##############################


    elif card1_height > card2_height:

        ##############################
        # INSERT YOUR CODE HERE
        
        top_line2 = (card2_width) * "─" +"┐" +"\n"
        upper_lines2 = (space_2+2)*" " + "│"
        center_lines2 = (left_2+1)*" " + card2_text + (right_2+1)*" " + "│"
        lower_lines2 = (space_2+2)*" " + "│"
        bottom_line2 = (card2_width) * "─" + "┘" + "\n"
        
        top_line1 = "┌" + (card1_width) * "─" + "┐" + "\n"
        upper_lines1 = ("│" + (space_1+2)*" " + "│" + "\n")*connecting_space 
        connect_lines1 = ("│" + (space_1+2)*" " + "├" + top_line2)
        upper_lines3 = ("│" + (space_1+2)*" " + "│" + upper_lines2 + "\n")*upper_2
        center_lines1 = "│" + (left_1+1)*" " + card1_text + (right_1+1)*" " + "│" +center_lines2 + "\n"      
        lower_lines3 = ("│" + (space_1+2)*" " + "│" + lower_lines2 + "\n")*lower_2
        connect_lines = ("│" + (space_1+2)*" " + "├" + bottom_line2)
        lower_lines1 = ("│" + (space_1+2)*" " + "│" + "\n")*connecting_space
        bottom_line1 = "└" + (card1_width) * "─" + "┘" + "\n"
        
        print(top_line1+ upper_lines1 +connect_lines1 + upper_lines3 + center_lines1 
        +lower_lines3 +connect_lines +lower_lines1+ bottom_line1)
        
        # Case 2
        # You can add as many new lines as you need.
        pass



        # DO NOT EDIT THE CODE UNDER THIS LINE.
        ##############################


    else:

        ##############################
        # INSERT YOUR CODE HERE
        top_line2 = (card2_width) * "─" +"┐"
        upper_lines2 = (space_2 + 2)*" " + "│"
        center_lines2 = (left_2 +1)*" " + card2_text + (right_2 +1)*" " + "│"
        lower_lines2 = (space_2 + 2)*" " + "│"
        bottom_line2 = (card2_width) * "─" + "┘" 
        
        top_line1 = (card1_width + 1) * " " + "┌" + top_line2 + "\n"
        upper_lines1 = ((space_1 + 3)*" " + "│" + upper_lines2 + "\n")*connecting_space2 
        connect_lines1 = ("┌" + (space_1 +2 )*"─" + "┤" + upper_lines2 + "\n")
        upper_lines3 = ("│" + (space_1+2)*" " + "│" + upper_lines2 + "\n")*upper_1 
        center_lines1 = "│" + (left_1+1)*" " + card1_text + (right_1+1)*" " + "│" + center_lines2 + "\n"      
        lower_lines3 = ("│" + (space_1+2)*" " + "│" + lower_lines2 + "\n")*lower_1
        connect_lines = ("└" + (space_1+2)*"─" + "┤" + lower_lines2 + "\n")
        lower_lines1 = ((space_1 + 3)*" " + "│" + lower_lines2 + "\n")*connecting_space2
        bottom_line1 = (card1_width +1) * " " + "└" + bottom_line2 + "\n"
        
        print(top_line1+ upper_lines1 +connect_lines1 + upper_lines3 + center_lines1 
        +lower_lines3 +connect_lines +lower_lines1+ bottom_line1)


        # Case 3
        # You can add as many new lines as you need.
        pass



        # DO NOT EDIT THE CODE UNDER THIS LINE.
        ##############################
#%%
