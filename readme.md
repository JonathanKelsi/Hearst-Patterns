# Hearst Patterns

This project provides two main features:
1. Given a corpus, where each noun phrase is tagged with '<np></np>', the program creates a database of hypernym relations in the format:
```txt
hypernym: hyponym1 (x), hyponym2 (x) ...
hypernym: hyponym1 (x), hyponym2 (x) ...
...
```
2. Given a corpus as mentioned above and a lemma, the program will search all the possible hypernyms of the input lemma and print them to the console as follows:
```txt
hypernym1: (x)
hypernym2: (x)
...
```

## Installation

Clone the repository:
```bash
git clone 'https://github.com/JonathanKelsi/Hearst-Patterns.git'
```

## Running the program

1. Compile with ant:
```bash
ant compile
```
2. Create the hypernym database:
```bash
ant run1 -Dargs="<corpus path> <output path>"
```
3. Discover hypernyms of a lemma:
```bash
ant run2 -Dargs="<corpus path> <lemma>"
```
