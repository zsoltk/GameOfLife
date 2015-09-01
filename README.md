# GameOfLife
An Android implementation of Conway's Game of Life, focusing on clean design and implementation

## About Game of Life
* [Stephen Hawkings The Meaning of Life (John Conway's Game of Life segment)](https://www.youtube.com/watch?v=CgOcEZinQ2I)
* [Video showing some epic creations](https://www.youtube.com/watch?v=C2vgICfQawE)
* [Wikipedia article] (http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

## About the app
Changeable automaton rules, presets, and interactive board. Some interesting outcomes:

![](http://i.imgur.com/jZPfMlv.png)
![](http://i.imgur.com/sDMRCJW.png)
![](http://i.imgur.com/FOdxb8i.png)
![](http://i.imgur.com/zGZSbBK.png)

![](http://i.imgur.com/wmSrTRt.png)
![](http://i.imgur.com/cwAe79y.png)
![](http://i.imgur.com/8bi2kLT.png)
![](http://i.imgur.com/siG7kWd.png)

## About the code
#### Used technologies
* [AndroidAnnotations](https://github.com/excilys/androidannotations) 
* [Otto](square.github.io/otto/) 
* [Hugo](https://github.com/JakeWharton/hugo)
* [android-gradle-template](https://github.com/nenick/android-gradle-template)

#### Running tests
Type ```./scripts/run-tests-fast.sh```. Results can be checked in the generated HTML.

Note: you might need to run ```./gradlew clean``` first

#### Tampering around
The code is intentionally implemented in a way to make it easy to create other types of cellular automata. Feel free to fork it and play around. For a starter:

1. Add your own implementation for Rule and CellularAutomata, and for CellColors if your automaton needs more than a basic dead / alive coloring:
   * [Rule](https://github.com/zsoltk/GameOfLife/tree/master/app/src/main/java/hu/supercluster/gameoflife/game/rule)
   * [CellullarAutomaton](https://github.com/zsoltk/GameOfLife/tree/master/app/src/main/java/hu/supercluster/gameoflife/game/cellularautomaton)
   * [CellColors](https://github.com/zsoltk/GameOfLife/tree/master/app/src/main/java/hu/supercluster/gameoflife/game/visualization/cell)

2. Use them in:
   * [MainPresenter](https://github.com/zsoltk/GameOfLife/blob/master/app/src/main/java/hu/supercluster/gameoflife/app/activity/main/MainPresenter.java)

3. If you're interested how the transformation is done, check:
   * [Transformer](https://github.com/zsoltk/GameOfLife/tree/master/app/src/main/java/hu/supercluster/gameoflife/game/transformer)

4. Have fun and message me if you create something cool!

#### Known limitations
The approach used in the transformation logic is that only cell state changes are observed, and a counter of dead / alive neighbors is updated in every surrounding cell. While there are other, faster algorithms, this is a good general approach for general, neighbor count based automata, and is still a whole lot faster than iterating through *every* cell and counting their alive neighbors in every frame.

By using the [ThreadedGridTransformer](https://github.com/zsoltk/GameOfLife/blob/master/app/src/main/java/hu/supercluster/gameoflife/game/transformer/ThreadedGridTransformer.java) it's fast enough to be smooth for Game of Life and most other rules. However, rules resulting in lots of changes around the screen might slow the game down.


