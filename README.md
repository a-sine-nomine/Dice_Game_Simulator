# Team Dice Game Simulator

## Description
This project is a simulator that generates random dice rolls and calculates points for each team participating in a dice game. To start the simulation, the user must input the number of teams participating in the game. The simulator runs for 35 seconds and generates random dice rolls for each team with 3 players. Once the simulation is complete, the application displays the results and declares the winner. Finally, the application prompts the user to run another round by asking if they want to play again.

## Programming Features
The application makes use of multi-threading to improve performance. Specifically, the application runs separate threads for each player, allowing them to receive random dice rolls independently and simultaneously.

## How to Run
To run the application on your computer, follow these steps:
1. Download the *application.jar* file.
2. Open the terminal in the same folder as the jar file.
3. Type the following command:


      java -jar application.jar <n>

Replace <n> with the number of teams you want to participate in the game.

Make sure that you have Java 17 installed on your computer to run the application.