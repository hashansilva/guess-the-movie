package com.udacity.project;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GuessTheMovie {
    private static final String filename = "movies.txt";

    public static void main(String[] args) {
        try {
            List<String> movieList = getRandomMovie(filename);
            List<String> wrongGuesses = new ArrayList<>();
            List<String> correctGuesses = new ArrayList<>();
            boolean isGuessed = false;
            Scanner scanner = new Scanner(System.in);
            int randomIndex = (int) ((Math.random() * (movieList.size() - 1)) + 1);
            String randomMovie = movieList.get(randomIndex);
            isGuessed = validateGuess(wrongGuesses, correctGuesses, isGuessed, scanner, randomMovie);

            if (isGuessed) {
                System.out.println("Congratulations you have guessed the movie correctly");
            } else {
                System.out.println(String.format("Correct answer is %s", randomMovie));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean validateGuess(List<String> wrongGuesses, List<String> correctGuesses, boolean isGuessed, Scanner scanner, String randomMovie) {
        while (wrongGuesses.size() < 10) {
            int mishit = 0;
            System.out.print("You are guessing : ");
            for (int i = 0; i < randomMovie.length(); i++) {
                char[] movieChars = randomMovie.toCharArray();
                if (Character.isAlphabetic(movieChars[i]) && !correctGuesses.contains(String.valueOf(movieChars[i]).toLowerCase())) {
                    System.out.print("_");
                    mishit++;
                } else {
                    System.out.print(movieChars[i]);
                }

            }
            System.out.print("\n");
            if (mishit == 0) {
                isGuessed = true;
                break;
            }
            System.out.println(String.format("You have guesses (%d) wrong letters: %s", wrongGuesses.size(), Arrays.toString(wrongGuesses.toArray())));
            System.out.println("Guess a letter: ");
            String guess = scanner.nextLine();
            if (randomMovie.toLowerCase().contains(guess.toLowerCase())) {
                correctGuesses.add(guess.toLowerCase());
            } else {
                wrongGuesses.add(guess);
            }
        }
        return isGuessed;
    }

    private static List<String> getRandomMovie(String filename) throws IOException {
        List<String> movies = new ArrayList<>();
        InputStream inputStream = GuessTheMovie.class.getClassLoader().getResourceAsStream(filename);
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            movies.add(line);
        }
        return movies;
    }
}
