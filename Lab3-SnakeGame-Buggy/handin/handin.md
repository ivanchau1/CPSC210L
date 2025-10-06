# The First Bug

- what is the name of the variable on `line 98` of the `SnakeGame` class whose value is `null`?

Answer: The name of the variable on line 98 with value is null is `food`. 

- what change did you make to fix the bug that caused the `NullPointerException`

Answer: To fix the bug, I moved `line 102` to `line 97` (before the while loop). In addition to another `NullPointerException` error, I initalized the snake on `line 19`

# The Remaining Four Bugs

First provide your answer to each of the following questions:

- when the game first starts, is the colour of the food green, as expected?

Answer: when I initally ran the program, the colour of the food is red.

- every time the game starts, is the food placed at a random location on the board?

Answer: after running the program multiple times, the food is placed at a fixed location on the board.

- when you repeatedly try to rotate the snake's head counter-clockwise by pressing the left arrow key - does it behave as expected?

Answer: after trying to rotate the snake's head counterclockwise by pressing the left arrow key, the snake does behave as expected. 

- when you repeatedly try to rotate the snake's head clockwise by pressing the right arrow key - does it behave as expected?

Answer: after trying to rotate the snake's head clockwise by pressing the right arrow key, the snake does not behave as expected. The movement of the snake appears to be moving to the top left of the screen.

- does the game end when the snake runs off the edge of the board?

Answer: When the snake runs off the edge of the board, the game does end. 



## Bug 1

- Briefly describe the bug. How does it manifest itself when the program runs?: 

Answer: For the first bug, when the game first starts, the colour of the food is red, when it should be green.

- What is your hypothesis / hypotheses for the cause of this bug:

Answer: Since the colour of the food is based on the nutritional value, my hypothesis for the bug is that the nutritional value was wrongly initialized to 0.

- Where did you set a breakpoint when debugging the code?  If you set more than one, list all of them. 
AND/OR 

Answer: Under the Food class, I set a breakpoint at `line 13`. 
- What test method names, if any, assisted you in determining where to find the bug?

- What did you do to fix the bug (include the class name and line number(s))?

Answer: I fixed the bug by using the breakpoint at `line 13` of the `Food` class. Stepping over, I noticed that the `nutritionalValue` was initilized to 0. I also read in the specifications that the `nutritionalValue` was supposed to be initalized to `INITIAL_NUTRITIONAL_VALUE`. To fix this, I changed `line 14` of the `Food` class from `nutritionalValue = 0;` to `nutritionalValue = INITIAL_NUTRITIONAL_VALUE;`


## Bug 2

- Briefly describe the bug. How does it manifest itself when the program runs?: 

Answer: For the second bug, when pressing the right arrow key once, the snake does behave correctly in turning 90 degrees to the right. However, when pressing the key multiple times, the snake does not behave correctly and starts moving towards the top left corner.

- What is your hypothesis / hypotheses for the cause of this bug:

Answer: My hypothesis for the cause of the bug is that in the `SnakeGame` class, the `rotateSnakeRight()` method is not configured correctly when the snake is facing upwards.

- Where did you set a breakpoint when debugging the code?  If you set more than one, list all of them. 
AND/OR

Answer: Under the SnakeGame class, I set a breakpoint at `line 73`.

- What test method names, if any, assisted you in determining where to find the bug?

- What did you do to fix the bug (include the class name and line number(s))?

Answer: Stepping into the method `rotateRight` under the `Snake` class, I noticed that when the snake is facing upwards, the direction of the snake turns left. To fix this, I changed `line 78` in the `Snake` class from `direction = Direction.LEFT;` to `direction = Direction.RIGHT;`


## Bug 3

- Briefly describe the bug. How does it manifest itself when the program runs?: 

Answer: For the third bug, when the snake lands on the cell where the food is located, the food does not automatically get moved to a different location. 

- What is your hypothesis / hypotheses for the cause of this bug:

My hypothesis for the cause of this bug is that there is no program that automatically generates a new location for the food. 

- Where did you set a breakpoint when debugging the code?  If you set more than one, list all of them. 
AND/OR

Answer: I set a breakpoint on `line 97` of the `SnakeGame` class. 
- What test method names, if any, assisted you in determining where to find the bug?

- What did you do to fix the bug (include the class name and line number(s))?

Answer: After setting the breakpoint at `line 97` of the `SnakeGame` class, using the step over functions, I notice that when food was initilized, the cell location is always fixed because it uses fixed constants `BOARD_ROWS` and `BOARD_COLS`. To fix this, I delete `new Cell(BOARD_ROWS / 4, BOARD_COLS / 4)` on `line 97` and replace it with a the variable `position`. The variable would call `randomCell()` which would randomly generate coordinations on the board for the food.


## Bug 4

- Briefly describe the bug. How does it manifest itself when the program runs?: 

Answer: For the fourth bug, it appears that the snake does not grow longer based on the nutrition consumed. Once the snake at reached a certain number of nutriton consumed, it should grow by one more cell.

- What is your hypothesis / hypotheses for the cause of this bug:

Answer: My hypothesis for the cause of this bug is that there is no program that checks if the snake has reached the nutrition consumption to grow an extra cell.

- Where did you set a breakpoint when debugging the code?  If you set more than one, list all of them. 
AND/OR

Answer: Under the `Snake` class, I placed a breakpoint at `line 100`.

- What test method names, if any, assisted you in determining where to find the bug?

- What did you do to fix the bug (include the class name and line number(s))?

Answer: After setting the breakpoint at `line 100` of the `Snake` class and using the step in function, I noticed that the method `canGrow` of the `Snake` class would return `false` even though there was enough nutrition consumed to grow a cell larger. Upon reading the specification of the `canGrow` method, I noticed that the expression was only checking if the nutrition consumed was equal to the `NUTRITION_TO_GROW` threshold. To fix the bug, I changed the expression from `nutritionConsumed == NUTRITION_TO_GROW` to `nutritionConsumed >= NUTRITION_TO_GROW`. This ensured that when there is enough nutrition consumed to grow, the snake will be able to grow a single cell. 


