# RestaurantService

Get All Restaurants
/restaurants/all/{page}

Parameters
sort  - default value - "default"
    - "default" - returns values sorted by restaurant id
    - "rating"  - returns values sorted by greatest to smallest rating 
    - "a-to-z"  - returns values sorted alphabetically 
    
active - default value - 1
    - 0 - returns both active and inactive restaurants (only administrators can access these)
    - 1 - returns only active restaurants
    
pageSize - default value - 10
    - Any Number - returns the specified number of matching results
    
 rating - default value - 0
    - Any no. 1 to 5  - returns only restaurants with a value greater than or equal to specified value
 
 priceCategories - default - "1, 2, 3, 4" (all)
    - 1 -    cheap eats   - if specified returns restaurants marked as cheap eats
    - 2 -     mid range   - if specified returns restaurants marked as mid range
    - 3 -    fine dining  - if specified returns restaurants marked as fine dating
    - 4 - high end luxury - if specified resturns restaurants marked as high end luxury
