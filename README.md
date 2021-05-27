# RestaurantService
Open [http://localhost:8040](http://localhost:8040) to view it in the browser.
#### Get All Restaurants
##### `/restaurants/all/{page}`
This endpdoint allows clients to sort and filter through all the restaurants as they would be able to in the search endpoint, however, it's only, limitation is that you cannot specify any keywords to use in the search.

###### Parameters
*sort*
- "default" - returns values sorted by restaurant id
- "rating"  - returns values sorted by greatest to smallest rating 
- "a-to-z"  - returns values sorted alphabetically 
    
*active* 
- 0 - returns both active and inactive restaurants (only administrators can access these)
- 1 - returns only active restaurants
- default value is 1
    
*pageSize*
- Any Number - returns the specified number of matching results
- default value is 10
    
*rating* - default value - 0
- Any no. 1 to 5  - returns only restaurants with a value greater than or equal to specified value
 
*priceCategories*
- 1 -    cheap eats   - if specified returns restaurants marked as cheap eats
- 2 -     mid range   - if specified returns restaurants marked as mid range
- 3 -    fine dining  - if specified returns restaurants marked as fine dating
- 4 - high end luxury - if specified resturns restaurants marked as high end luxury
- default value is "1, 2, 3, 4" or all
