# Models of Code    

Add to this folder the files that contain the package relationship diagram
and call graphs that you were asked to extract from the code.


# Checkstyle Problems

Use the space below to record the changes that you made to class, method and variable names that passed checkstyle but were poorly chosen.  We've identified three such changes below but there are others that you will need to add...

- `Ball` class, `doSomething` method was renamed to `collided`
- `Puddle` class was renamed to `Paddle`
- `PBG` class, `DIMENSION1` variable was renamed to `WIDTH`
- `PBG` class, `DIMENSION2` variable was renamed to `HEIGHT`
- `PBG` class, `RND` variable was renamed to `RANDOM`
- `PBG` class was renamed to `PaddleBallGame`
- `Ball` class, `deal_with_it` method was renamed to `ballBoundaryCollision`
- `Paddle` class, `WhichWay` variable renamed to `direction`
- `PBG` class, `checkstyle` method renamed to `isGameOver`
- `PBG` class, `paddleControl` method renamed to `paddleDirection`
- `GamePanel` class, `gameOver` method renamed to `drawGameOver`