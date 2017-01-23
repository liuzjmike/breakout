# Breakout
Created by Mike Liu (zl99)

Jan 16 - Jan 22, 2017

Total time: ~35 hours


## Resources
Oracle Help Center (JavaFX 8)  
StackOverflow

## Roles
Designed and finished single-handedly by Mike Liu

## Running, Testing and Dependencies
The project is started by running Main.java. There was no unit tests written for the project. Debugging was mostly done by
printing to command line and playing the game to look for abnormal behavior of the objects.  
All images in images directory, except for brick1.gif, brick7.gif, brick8.gif, brick9.gif, are necessary for the project
to run.

## How to Play
#### Welcome screen
Hit H to go to help page and hit SPACE to start the game.
#### Help page
Hit Q to go back to welcome screen.
#### End screens (win and lose)
Hit SPACE to go back to welcome screen.
#### In game
Press number key 1-4 to go to corresponding levels.  
Move paddle by pressing LEFT and RIGHT. When there are bouncers on the paddle, press SPACE to launch the bouncers.  
Press H within 0.1s before a bouncer hit the paddle to hold the bouncer on the paddle.  
Press L to lengthen paddle by a factor of 2 and press S to shorten paddle by a factor of 2.  
Press A to add a life and press C to clear all blocks in the current level and get their points.  

## Functional Problem
1. When the bouncer hit the blocks on the side at some certain spots and angles, it could bounce off to some unnatural angle
(not following physical laws). This may be the result of some flaws in the calculation of out angle of bouncer-block
collision. This can be especially obvious in Level 4 where the block is moving and the bouncer is sometimes even caught
temporarily in the block.  
2. When the bouncer hit the paddle right at the edge of the screen, it could be caught in a straight up and down motion while
vibrating at the same time. More complicated handle when the bouncer hit the edge of the screen may be needed to solve this
problem.

## Extra Features
1. The paddle use a function to change the out angle of bouncers with respect to where the bouncers hit it.  
2. There is a hole at the top of Level 1 to 3, which will lead the player to the next level without collecting all the points
in the current level if a bouncer goes into it. The play may need to watch out for the hole and strategically give up extra
bouncer sometimes if they want to collect all points in the level.  
3. The block in Level 4 is able to move horizontally and drop bombs that will shorted paddle for 5s at contact. It is only
damaged when hit from above and will drop powerups when damaged.

## Impression of Assignment
The assignment is fun in general. It required more thoughts into its design than I anticipated, so a considerable portion of
time was spent reconsidering and changing the design. For the first assignment of the whole course, maybe a project that
has less complicated functionality maybe more appropriate, so that students can put more time and effort into planning and
thinking about their design instead of trying to come up with features. Also, some lectures about the higher-level design
might be helpful before and during the assignment, although I don't know if this is possible before talking about
the low-level designs.