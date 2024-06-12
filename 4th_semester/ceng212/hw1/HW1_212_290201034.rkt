#lang scheme
;************************
;* Ahmet Kurt 290201034 *
;************************

;First, I define the lists given in the homework.

;-----------------------------------------------------------------------

(define customers
  '((John_Smith 35 New_York)
    (Alice_Johnson 28 Los_Angeles)
    (Michael_Brown 45 Miami)
    (Emily_Davis 32 Houston)
    (Robert_Wilson 40 Miami)
    (Sophia_Martinez 30 New_York)
    (William_Taylor 38 Houston)
    (Emma_White 25 Los_Angeles)
    (James_Harris 32 Houston)
    (Olivia_Clark 29 Los_Angeles)))

;-----------------------------------------------------------------------

(define items
  '((Apples Fruits 2.3)
    (Coffee Beverages 3.5)
    (Bread Bakery 2.0)
    (Milk Dairy 3.5)
    (Bananas Fruits 1.75)
    (Eggs Dairy 4.75)
    (Orange_Juice Beverages 3.25)
    (Tea Beverages 2.75)
    (Fish Seafood 12.5)
    (Broccoli Vegetables 1.8)
    (Orange Fruits 1.25)
    (Chicken Meat 7.0)
    (Lettuce Vegetables 1.2)
    (Pasta Pantry 3.75)
    (Salmon Seafood 9.5)
    (Yogurt Dairy 2.75)
    (Bacon Meat 6.25)
    (Cheese Dairy 5.5)
    (Beef Meat 8.0)
    (Potatoes Vegetables 2.5)
    (Chicken_Soup Canned-Goods 3.5)
    (Rice Grains 2.25)
    (Carrots Vegetables 1.1)
    (Spinach Vegetables 1.6)
    (Tomatoes Vegetables 1.5)
    (Apple_Juice Beverages 3.4)
    (Onions Vegetables 1.2)))

;-----------------------------------------------------------------------

(define purchases
 '((John_Smith (Apples Coffee Bread) 15.03.2024)
   (John_Smith (Milk Bananas) 22.03.2024)
   (John_Smith (Eggs Orange_Juice) 29.03.2024)
   (John_Smith (Tea Fish Broccoli Orange) 5.04.2024)
   (John_Smith (Chicken Lettuce Pasta Salmon) 12.04.2024)
   (Alice_Johnson (Milk Bananas) 20.03.2024)
   (Michael_Brown (Orange_Juice Yogurt) 24.03.2024)
   (Michael_Brown (Bacon) 28.03.2024)
   (Michael_Brown (Coffee Bread Apples) 2.04.2024)
   (Michael_Brown (Milk Bananas Eggs) 5.04.2024)
   (Michael_Brown (Cheese Beef Potatoes Chicken_Soup) 10.04.2024)
   (Emily_Davis (Chicken Lettuce) 24.03.2024)
   (Emily_Davis (Pasta Salmon Rice Potatoes) 28.03.2024)
   (Emily_Davis (Carrots Spinach) 1.04.2024)
   (Robert_Wilson (Salmon Rice) 21.03.2024)
   (Robert_Wilson (Potatoes Chicken Lettuce Pasta) 25.03.2024)
   (Robert_Wilson (Milk Bananas Eggs Orange_Juice) 29.03.2024)
   (Robert_Wilson (Bacon) 2.04.2024)
   (Robert_Wilson (Fish Broccoli) 6.04.2024)
   (Sophia_Martinez (Carrots Spinach) 26.03.2024)
   (Sophia_Martinez (Tea Fish Broccoli Orange) 30.03.2024)
   (William_Taylor (Beef Potatoes) 19.03.2024)
   (William_Taylor (Chicken_Soup Tomatoes Apple_Juice Bread) 23.03.2024)
   (Emma_White (Tomatoes Chicken_Soup) 23.03.2024)
   (Emma_White (Milk Salmon Rice Potatoes) 27.03.2024)
   (Emma_White (Chicken Lettuce Pasta Salmon) 31.03.2024)
   (James_Harris (Onions Apple_Juice) 25.03.2024)
   (James_Harris (Cheese Beef Potatoes Chicken_Soup) 29.03.2024)
   (Olivia_Clark (Fish Broccoli) 25.03.2024)
   (Olivia_Clark (Orange Chicken Lettuce Pasta) 29.03.2024)))

;-----------------------------------------------------------------------

(define (waiter)
  
  (sleep 2)) ;I defined a waiter function for reading the output. If you wish you can set as 0.

;-----------------------------------------------------------------------

(display "------------------------------------------------------------\n
Question 1 - Retrieve all items: Return a list of all unique items.\n\n")

(waiter) ;I used (sleep x) for readable output.

(define (retrieve-all-items items)
  (define (helper items result)
    (if (null? items)
        result 
        (helper (cdr items) (if (not (member (caar items) (map car result))) ;If the item is not aldready in the result list
                                (cons (list (caar items)) result) ;Add the current item
                                result))))
  (helper items '())) ;Start the helper function

(display (retrieve-all-items items))

;------------------------------------------------------------------------

(display "\n\n------------------------------------------------------------\n")
(waiter)
(display "\nQuestion 2 - Retrieve all categories: Return a list of all unique categories.\n\n") ;To make the output look more readable and stylish
(waiter)

(define (retrieve-all-categories items)
  (define (helper items result)
    (if (null? items)
        result
        (helper (cdr items) (if (not (member (cadar items) result))
                                (cons (cadar items) result)
                                result)))) ;Same as question 1, just for items.
  (helper items '()))

(define (display-categories categories)
  (display (string-join (map symbol->string categories) ", "))) ;Converted to string.

(define categories (retrieve-all-categories items))
(display-categories categories)

(display "\n\n------------------------------------------------------------\n") 
(waiter)

;------------------------------------------------------------------------


(display "\nQuestion 3 - Retrieve items by category: Take a category name as input and return a list of items belonging to that category.\n\n")
(waiter)
 
(define (retrieve-items-by-category category)
  (filter
    (lambda (item) (equal? (cadr item) category))
    items))
(retrieve-items-by-category 'Fruits) ;If you want to change the category you can type here where the ' after.

(display "\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Question 4 - Get Customer Information: Retrieve information about a specific customer by providing their name as an argument.\n\n")
(define (get-customer-info name)
  (let ((customer-info (assoc name customers))) ;Customer list searching by the name input.
    (if customer-info
        customer-info
        ;else
        "Customer not found."))) ;For wrong input I want to add not found expectation. You can test with wrong input.

(display "Enter the customer's name: (Please use _ between first and last name of the customer)\n\n")
(define customer-name (read-line))
(display (get-customer-info (string->symbol customer-name))) ;You can test it with John_Smith etc.

(display "\n\n------------------------------------------------------------\n\n")

;------------------------------------------------------------------------

(waiter)
(display "Question 5 - Get Item Information: Retrieve information about a specific item by providing its name as an argument.\n\n")

(define (get-item-info name)
  (let ((item-info (assoc name items))) ;Item list searching by the item input.
    (if item-info
        item-info
        "\nItem not found."))) ;Same as customer info, wrong input.

(display "Enter the item name: (Please use _ betwwen first and last name of the item\n)")
(define item-name (read-line))
(display (get-item-info (string->symbol item-name))) ;You can test it with Carro

(display "\n\n------------------------------------------------------------\n\n")
(waiter)
;------------------------------------------------------------------------

(display "Question 6 - Most expensive item: Find the most expensive item.\n")
(waiter)

(define (most-expensive-item)
  (let loop ((items items)
             (max-price 0) ;Set max price to 0.
             (expensive-item '()))
    (if (null? items)
        expensive-item
        (let ((current-item (car items))
              (current-price (caddr (car items))))
          (if (> current-price max-price) ;If the current item's price is greater than the maximum price found so far.
              (loop (cdr items) current-price current-item)
              (loop (cdr items) max-price expensive-item))))))

(display "\nThe most expensive item is: ")
(waiter)
(display (most-expensive-item)) ;The answer must be Fish Seafood 12.5 by the list.

(display "\n\n------------------------------------------------------------\n\n")
(waiter)
;------------------------------------------------------------------------

(display "Question 7 - Cheapest item: Find the cheapest item.\n")
(waiter)
(define (cheapest-item)
  (let loop ((items items)
             (min-price +inf.0) ;Set min price to inf. (also can run by large number)
             (cheapest-item '()))
    (if (null? items)
        cheapest-item
        (let ((current-item (car items))
              (current-price (caddr (car items))))
          (if (< current-price min-price)
              (loop (cdr items) current-price current-item)
              (loop (cdr items) min-price cheapest-item))))))

(display "\nThe cheapest item is: ")
(waiter)
(display (cheapest-item)) ;The answer must be Carrots Vegetables 1.1

(display "\n\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Question 8 - Total cost of items: Calculate the total cost of all items purchased for a specific customer by providing their name as an argument.\n")
(waiter)
(define (total-cost-for-customer customer-name1) ;I already defined customer-name. Because of that I select customer-name1
  (let ((customer-purchases (filter (lambda (purchase) (equal? (car purchase) customer-name1)) purchases))) ;Filter the purchase by specific customer.
    (if (null? customer-purchases)
        "No purchases found for this customer." ;If no purchases found by the user's input.
        (let loop ((purchases customer-purchases)
                   (total-cost 0)) ;loop begin with total cost $0.
          (if (null? purchases)
              total-cost
              (let ((item-names (cadr (car purchases)))) ;cadr(car)
                (let ((items (filter (lambda (item) (member (car item) item-names)) items)))
                  (let ((purchase-cost (apply + (map caddr items))))
                    (loop (cdr purchases) (+ total-cost purchase-cost)))))))))) ;Update the total cost.

(display "\nEnter the customer's name: \n")
(define customer-name1 (read-line))
(display "Total cost of items purchased for ")
(display customer-name1)
(display " is: ")
(display (total-cost-for-customer (string->symbol customer-name1)))

(display "\n\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Question 9 - Items bought by a specific customer: Retrieve all items bought by a specific customer.\n")
(waiter)

(define (items-bought-by-customer customer-name2)
  (let ((customer-purchases (filter (lambda (purchase) (equal? (car purchase) customer-name2)) purchases))) ;Filter function again same as question 8.
    (if (null? customer-purchases)
        "No purchases found for this customer."
        (let loop ((purchases customer-purchases)
                   (items '()))
          (if (null? purchases)
              items
              (let ((item-names (cadr (car purchases))))
                (loop (cdr purchases) (append items item-names))))))))

(display "Enter the customer's name: ")
(define customer-name2 (read-line))
(display "Items bought by ")
(display customer-name2)
(display ": ")
(display (items-bought-by-customer (string->symbol customer-name2)))

(display "\n\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Total cost of transactions: Calculate the total cost of all transactions.")
(waiter)

(define (total-cost-of-transactions)
  (let loop ((purchases purchases)
             (total-cost 0)) ;Set as 0.
    (if (null? purchases) ;If there are no more,
        total-cost ;return
        (let* ((item-names (cadr (car purchases)))
               (items (filter (lambda (item) (member (car item) item-names)) items))
               (purchase-cost (apply + (map caddr items)))) ;Calculate the total cost current loop.
          (loop (cdr purchases) (+ total-cost purchase-cost)))))) ;Updating.

(display "\nTotal cost of all transactions is: ")
(display (total-cost-of-transactions))

(display "\n\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Question 11 - Items purchased on a specific date: Retrieve all items purchased on a specific date.\n")
(waiter)

(define (items_purchased_on_date specific_date)
  (define (extract_items_by_date current_purchases)
    (cond ((null? current_purchases) '())
          ((equal? (caddr (car current_purchases)) specific_date) ;If the date of the current purchases matches the specific.
           (append (cadr (car current_purchases)) (extract_items_by_date (cdr current_purchases)))) ;Append the items, and continue with the remaining.
          (else (extract_items_by_date (cdr current_purchases))))) ;If doesn't match,
  (extract_items_by_date purchases))

(display (items_purchased_on_date '15.03.2024)) ;You can change the date here for testing. I try it with 15.03.2024

(display "\n\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Question 12 - Revenue by category: Calculate the total revenue generated by each category.\n")
(waiter)

(define (revenue-by-category)
  (define category-revenue (make-hash)) ;I use hash table to store the revenues. https://docs.racket-lang.org/guide/hash-tables.html

  (for-each
    (lambda (purchase)
      (let ((customer-items (cadr purchase)))
        ; Iterate through customer items
        (for-each
          (lambda (item)
            (let* ((item-info (assoc item items)) ;Get info about the current item
                   (item-category (cadr item-info)) ;Category
                   (item-price (caddr item-info))) ;Price
              ; Adding the item price to the category.
              (hash-set! category-revenue item-category
                         (+ (hash-ref category-revenue item-category 0) ;default 0.
                            item-price))))
          customer-items)))
    purchases)

  (display "Revenue by category:\n")

  (let ((categories (hash-keys category-revenue)))
    (for-each
      (lambda (category)
        (let ((revenue (hash-ref category-revenue category))) ;Get all keys.
          (display category)
          (display ": $")
          (display revenue)
          (newline)(waiter))) ;Waiter after one by one category revenue.
      categories)))

(revenue-by-category)

(display "\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Question 13 - Most popular category: Determine the most popular category based on the number of items sold.\n")
(waiter)

(define (most-popular-category purchases items)
  ; Hash table for counting
  (define category-counts (make-hash))

  (define (update-category-count category)
    (hash-set! category-counts category
               (+ (hash-ref category-counts category 0) 1))) ;Increasing the count for the category by 1.

  (for-each (lambda (purchase)
              (let ((item-names (cadr purchase))) ; Extract item names from the purchases.
                ; Updating
                (for-each (lambda (item-name)
                            (let ((item-info (assoc item-name items)))
                              (if item-info ;Check if the item is found in the list.
                                  (update-category-count (cadr item-info)) ;Update count
                                  (begin
                                    (display "Item not found: ")
                                    (display item-name)
                                    (newline)) ;Not found
                                  )))
                            item-names)))
              purchases) ; End of for each (out)

  (define (find-max-category)
    (let loop ((categories (hash-keys category-counts)) ;Get all keys.
               (max-category #f) ;Counting.
               (max-count 0))
      (if (null? categories) ;If there are no more
          max-category ;return
          (let ((current-category (car categories)) ;Get the current
                (current-count (hash-ref category-counts (car categories)))) ;Counting.
            (if (> current-count max-count)
                (loop (cdr categories) current-category current-count) ;If > update the maximum
                (loop (cdr categories) max-category max-count)))))) ;Move to the next category.

  (find-max-category))

(define most-popular (most-popular-category purchases items))
(display "Most popular category: ")
(display most-popular)
(newline)

(display "\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Question 14 - Items purchased by age group: Retrieve all items purchased by customers within a certain age range.")
(waiter)

(define (items-purchased-by-age-range)
  ;prompt.
  (display "Enter minimum age: ")
  (define min-age (string->number (read-line)))
  (display "Enter maximum age: ")
  (define max-age (string->number (read-line)))

  ;I used helper function to get the customer name.
  (define (get-customer-age customer-name)
    (let ((customer-info (assoc customer-name customers)))
      (if customer-info
          (cadr customer-info) ; Extract the age.
          #f))) ;Iif customer is not found.

  ;Filter
  (define filtered-purchases
    (filter
      (lambda (purchase)
        (let ((customer-age (get-customer-age (car purchase))))
          (and customer-age (>= customer-age min-age) (<= customer-age max-age)))) ;Within the specific range.
      purchases))

  (let ((items '())) ;Empty list for store.
    (for-each
      (lambda (purchase)
        (set! items (append items (cadr purchase)))) ;Append items from each purchase
      filtered-purchases)
    items))

(display "Items purchased by customers within a specified age range:\n")
(display "\n")
(display (items-purchased-by-age-range))

(display "\n\n------------------------------------------------------------\n\n")
(waiter)

;------------------------------------------------------------------------

(display "Question 15 - Average spending per transaction by loction: Calculate the average spending per transaction in each location.")
(waiter)

(define (average-spending-per-transaction-by-location)
  ;Hash table for storing again.
  (define location-stats (make-hash))

  ;for each purchases
  (for-each
    (lambda (purchase)
      (let* ((customer-location (caddr (assoc (car purchase) customers)))
             (total-cost (apply + (map (lambda (item) (caddr (assoc item items))) (cadr purchase)))) ; Calculate the total spending.
             (transaction-count 1)) ;Count is 1 for purchase.

        ;Update the location.
        (hash-set! location-stats customer-location
                   (cons (+ (car (hash-ref location-stats customer-location '(0 . 0))) total-cost)
                         (+ (cdr (hash-ref location-stats customer-location '(0 . 0))) transaction-count)))))
    purchases)

  ;Calculating for each location.
  (define average-spending-per-transaction
    (map (lambda (entry)
           (cons (car entry)
                 (/ (car (cdr entry)) (cdr (cdr entry)))))
         (hash->list location-stats)))

  (display "\n\nAverage spending per transaction by location:\n")
  (for-each
    (lambda (entry)
      (display "Location: ")
      (display (car entry))
      (display ", Average Spending per Transaction: ")
      (display (cdr entry))
      (newline))
    average-spending-per-transaction))

(average-spending-per-transaction-by-location)

(display "\n------------------------------------------------------------\n\n")
;------------------------------------------------------------------------