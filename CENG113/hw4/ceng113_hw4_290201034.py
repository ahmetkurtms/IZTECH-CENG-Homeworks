#%%

class Menu:
    def __init__(this, category, name, portion, valueofpr):
        this.category = category
        this.name = name
        this.portion = portion
        this.valueofpr = valueofpr


with open("menu.txt", "r") as menu:
    productslist = [Menu(*line.split("; ")) for line in menu]

menu_ctg = """Product Categories
1. Fries
2. Milkshake
3. Salads
4. Sandwiches or Wrap
5. Sauce
6. Side
Please select product category: """

receipt = []
total = 0

while True:
    try:
        temp = ["Fries", "Milkshake", "Salads", "Sandwiches or Wrap", "Sauce", "Side"]
        choice = int(input(menu_ctg))
        category = temp[choice - 1]

        temp = [p.name for p in productslist if p.category == category]
        temp.sort()

        print("Productslist in", category)
        for ix, name in enumerate(temp, 1):
            print(f"{ix}. {name}")
        name = temp[int(input("Please select product name: ")) - 1]

        temp = [p.portion for p in productslist if p.name == name]
        temp.sort()

        print(name, "Portions")
        for jx, portion in enumerate(temp, 1):
            print(f"{jx}. {portion}")
        portion = temp[int(input("Please select product portion: ")) - 1]

    except (IndexError, ValueError):
        print("\nPlease enter one of the numbers in the options!\n")
    else:
        item = next(p for p in productslist if p.name == name and p.portion == portion)
        total += float(item.valueofpr.lstrip("$"))
        receipt.append(f"{item.name:<40}{item.portion:<20}{item.valueofpr}")

        quit = int(input("1. Add New\n2. Checkout\nPlease select an operation: "))
        if quit == 2:
            break
    print()

for item in receipt:
    print(item)

print("\nTotal: $" + format(total, ".2f"))

#%%