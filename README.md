# BingoManager
HOUSIE/TAMBOLA/BINGO manager. 

Can generate tickets (as many as you want, can also exclude numbers for which you don't have clues)

Also manage the game. Managing is defined as updating the board (numbers which have been played), and verifying claiming. This can be dome with some manual input such as clue numbers and claimers. 

Includes 3 .txt files, 2 of which update every time the game generates new tickets (TICKETS.txt &amp; DISTRIBUTION.TXT) and one (BOARD.txt) that updates every 2 times clues have been entered (can be changed). BOARD.txt contains a 2D Array of 0/1s containing information about which numbers have been played. 

Also includes an excel sheet that can help visualize the board, along with some other administritative work which you may find useful.

Finally, the source code contains numerous classes (all Java), each of which deals with generating tickets, counting the distribution of numbers, managing the board, managing input/output & finally one class that manages the whole game. 

Most importantly, there is a constants file that contains relevant constants used throughout the game. Changing that should ensure consistent changes in the rest of the game. Enjoy!
