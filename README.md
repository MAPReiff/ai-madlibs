madlibs but funny - twang
---

## Group Members
- [Jacob Gerega](https://github.com/jgerega107)
- [Mitchell Reiff](https://github.com/MAPReiff)
- [Thomas Wang](https://github.com/twang1905)

# AI-Powered Mad Libs
Mad Libs are a popular word game created in the 1950s.  To play the game, an author writes a short story consisting of a few sentences
and then replaces some key words in the story with blanks labeled with a part of speech (noun, verb, adjective, etc.).  Then, players 
are given a list of parts of speech without context and must suggest a word to fill in the blank.  The words suggested by the players
are then filled into the blanks in order and the story is read back to the player, often to hillarious effect.

We like to think that computers don't have a sense of humor, but a mad lib is probably something a computer can do. 

## Minimal Requirements
Create a program that allows you to:
* Given a source text, randomly replace words with blanks and annotate those blanks with a part of speech (you can use open source natural language processingt (NLP) libraries to achieve this - for example [OpenNLP](https://opennlp.apache.org/) is written in Java)
* Present that list of blanks and parts of speech to a player
* Collect the player's responses for each blank and present the original text with those words substituted in
* Save and load particularly amusing results to/from a file

## Additional Feature Ideas
* How does your program decide which words to replace with blanks?  Are there settings you can introduce to tune this behavior?
* Can you turn it into a multi-player game where each player gets to fill in one blank?
* Can it be multi-player over a network?
* Can you also make the computer try to solve mad libs?  How will it choose the word to substitute?
* Can the program validate that the words the players suggest are of the right part of speech?