/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_project_3_part_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Part 2 of Project 3
 *
 *
 * @author Germex w/ Brandon Baraja's Training Set
 */
public class AI_Project_3_Part_2 {

    //Probability distribution for #1 and #2
    static private double PROB_DIST_SUITE_1_AND_2[] = {.20, .20, .20, .20, .20};
    //Probability distribution for #3
    static private double PROB_DIST_SUITE_3[] = {.10, .10, .10, .10, .10, .10, .10, .10, .10, .10};
    //Probability distribution for #4 and #5
    static private double PROB_DIST_SUITE_5[] = {.5, .5, .20, .20, .50};
    //Probability distribution for #6
    static private double PROB_DIST_SUITE_6[] = {.5, .5, .5, .5, .5, .5, .15, .15, .15, .25};

    //Training Configuration #1 Categories
    private static final String[] TEST_ONE_CONFIG_CATEGORIES = {"", "Atheism", "Graphics", "Windows", "IBM Hardware", "MAC Hardware"};
    //Training Configuration #2 Categories
    private static final String[] TEST_TWO_CONFIG_CATEGORIES = {"", "Atheism", "Graphics", "Windows", "IBM Hardware", "MAC Hardware"};
    //Training Configuration #3 Categories
    private static final String[] TEST_THREE_CONFIG_CATEGORIES = {"", "Atheism", "Graphics", "Windows",
        "IBM Hardware", "MAC Hardware", "Windows X", "For Sale", "Autos", "Motorcycles", "Baseball"};

    //Source for Training Set #1
    private static File trainingDataOne = new File("C:/Users/Germex/Documents/NetBeansProjects/AI_Project_3_Part_2/files/testConfigOne.txt");
    //Source for Training Set #2
    private static File trainingDataTwo = new File("C:/Users/Germex/Documents/NetBeansProjects/AI_Project_3_Part_2/files/testConfigTwo.txt");
    //Source for Training Set #3
    private static File trainingDataThree = new File("C:/Users/Germex/Documents/NetBeansProjects/AI_Project_3_Part_2/files/testConfigThree.txt");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        
        // I created a 2D Matrix to store the chart from the Training Data
        // That Branden has allowed us to use. It's stored in such a way:
        /**
         *
         * word1 0.0 0.0 .01 .20 .40 
         * word2 .01 0.0 .01 .20 .10 
         * word3 0.0 0.0 0.0 0.0 0.0 
         * word4 .80 0.0 .02 .10 .50
         *
         * This will facilitate calculations when trying to find the category
         *
         */

        //Training Data #1 the matrix is hardcoded to accept only training set #1
        String[][] trainingSetOneMatrix = new String[119][7];
        //Training Data #2 the matris is hardcoded to accept only training set #2
        String[][] trainingSetTwoMatrix = new String[111][7];
        //Training Data #3 the matrix is hardcoded to accept only training set #3
        String[][] trainingSetThreeMatrix = new String[441][12];

        /**
         * These methods will fix the matrix with the data provided by Brandon 
         * 
         *  word 0.0 0.0 0.0 0.0
         *  word 0.0 0.0 0.0 0.0
         *    ...
         *   n   n   n   n   n
         */
        fillUpMatrixWithTrainingData(trainingSetOneMatrix, trainingDataOne);
        fillUpMatrixWithTrainingData(trainingSetTwoMatrix, trainingDataTwo);
        fillUpMatrixWithTrainingData(trainingSetThreeMatrix, trainingDataThree);

        //Hashmap to count all the words from the input text
        HashMap<String, Integer> inputTextWordCount = new HashMap<>();

        //Put path of the file that you want to test
        File testFile = new File("C:/Users/Germex/Documents/NetBeansProjects/AI_Project_3_Part_2/files/motorcycles.txt");

        //Create a BufferReader from testFile
        BufferedReader br = new BufferedReader(new FileReader(testFile));

        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        //If there's a line, continue to loop throught the file appending. 
        while (line != null) {
            //Append line to String Builder
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }

        //Seperate Stringbuilder into seperate words
        String[] words = sb.toString().split("\\W+");

        //Loop through all the words putting them into a hashmap
        //with a integer as a value to keep track of the words
        for (String word : words) {
            String fixedWord = word.replace("\\d+", "").toLowerCase();
            //Is the word in the ignore list, if yes, ignore it
            //Is the word already in the hash set of saved words, if yes, increment it
            if (inputTextWordCount.containsKey(fixedWord)) {
                //Get the count for the word and increment it
                int x = inputTextWordCount.get(fixedWord);
                x++;
                inputTextWordCount.replace(fixedWord, x);

            } else {
                inputTextWordCount.put(fixedWord, 1);
            }

        }

        
        //Go through 5 different Benchmarking Suites
        long startTime;
        long stopTime;
        long elapsedTime;
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    //Benchmarking Suite #1 with Training Config #1
                    
                    for (int x = 0; x < trainingSetOneMatrix.length; x++) {
                        for (int j = 0; j < trainingSetOneMatrix[0].length; j++) {
                            System.out.print(trainingSetOneMatrix[x][j] + "  ");
                        }
                        
                        System.out.println("");
                    }

                    //Memroy and Time Benchmarking
//                    startTime = System.currentTimeMillis();
                    calculateCategory("Benchmark Suite #1", PROB_DIST_SUITE_1_AND_2, TEST_ONE_CONFIG_CATEGORIES, inputTextWordCount, trainingSetOneMatrix);
//                    stopTime = System.currentTimeMillis();
//                    elapsedTime = stopTime - startTime;
//                    System.out.println(elapsedTime);

                    break;
                case 1:
                    //Benchmarking Suite #2 with Training Config #2

                    //Memroy and Time Benchmarking
//                    startTime = System.currentTimeMillis();
                    calculateCategory("Benchmark Suite #2", PROB_DIST_SUITE_1_AND_2, TEST_TWO_CONFIG_CATEGORIES, inputTextWordCount, trainingSetTwoMatrix);
//                    stopTime = System.currentTimeMillis();
//                    elapsedTime = stopTime - startTime;
//                    System.out.println(elapsedTime);

                    break;
                case 2:
                    //Benchmarking Suite #3 with Training Config #3

                     //Memroy and Time Benchmarking
//                    startTime = System.currentTimeMillis();
                    calculateCategory("Benchmark Suite #3", PROB_DIST_SUITE_3, TEST_THREE_CONFIG_CATEGORIES, inputTextWordCount, trainingSetThreeMatrix);
//                    stopTime = System.currentTimeMillis();
//                    elapsedTime = stopTime - startTime;
//                    System.out.println(elapsedTime);
                    
                    
                    break;
                case 3:
                    //Benchmarking Suite #4 with Training Config #1

                    //Memroy and Time Benchmarking
//                    startTime = System.currentTimeMillis();
                    calculateCategory("Benchmark Suite #4", PROB_DIST_SUITE_5, TEST_ONE_CONFIG_CATEGORIES, inputTextWordCount, trainingSetOneMatrix);
//                    stopTime = System.currentTimeMillis();
//                    elapsedTime = stopTime - startTime;
//                    System.out.println(elapsedTime);
                    
                    break;
                case 4:
                    //Benchmarking Suite #5 with Training Config #2

                    //Memroy and Time Benchmarking
//                    startTime = System.currentTimeMillis();
                    calculateCategory("Benchmark Suite #5", PROB_DIST_SUITE_5, TEST_TWO_CONFIG_CATEGORIES, inputTextWordCount, trainingSetTwoMatrix);
//                    stopTime = System.currentTimeMillis();
//                    elapsedTime = stopTime - startTime;
//                    System.out.println(elapsedTime);
                    
                    break;
                case 5:
                    //Benchmarking Suite #6 with Training Config #3

                    //Memroy and Time Benchmarking
//                    startTime = System.currentTimeMillis();
                    calculateCategory("Benchmark Suite #6", PROB_DIST_SUITE_6, TEST_THREE_CONFIG_CATEGORIES, inputTextWordCount, trainingSetThreeMatrix);
//                    stopTime = System.currentTimeMillis();
//                    elapsedTime = stopTime - startTime;
//                    System.out.println(elapsedTime);
                    
                    break;
            }
        }
            Runtime rt = Runtime.getRuntime();
            long total_mem = rt.totalMemory();
            long free_mem = rt.freeMemory();
            long used_mem = total_mem - free_mem;
            
            System.out.println(used_mem + "bytes");
    }

    /**
     * Takes in a matrix that will hold the values with their respective word
     * for each category and the training data that will fill up the matrix
     *
     * @param trainingSetOneMatrix
     * @param trainingDataOne
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void fillUpMatrixWithTrainingData(String[][] trainingSetOneMatrix, File trainingDataOne) throws FileNotFoundException, IOException {
        BufferedReader bufRead = new BufferedReader(new FileReader(trainingDataOne));
        StringBuilder strBul = new StringBuilder();

        String textLine = bufRead.readLine();

        //Loop through the matrix inserting values
        for (String[] trainingSetOneMatrix1 : trainingSetOneMatrix) {
            if (textLine != null) {

                //Get the line and divide it among a String Array by whitespaces
                String[] words = textLine.split("\\s+");

                //Add the word and the following values into a table (matrix)
                for (int j = 0; j < words.length; j++) {
                    trainingSetOneMatrix1[j] = words[j].toLowerCase();
                }
                textLine = bufRead.readLine();
            }

        }
    }

    /**
     * This method takes in the hash map with the count of how many words appear
     * in a Document - D, It then puts it into Bayes Theorem.
     *
     */
    private static void calculateCategory(String testSuite, double[] PROB_DIST, String[] CATEGORIES, HashMap<String, Integer> inputTextWordCount, String[][] matrix) throws IOException {

        /**
         * Example:
         *
         * Input D to guess C:
         *
         *       D
         * Word  0 
         * Word  1 
         * Word  2 
         * Word  0
         *
         * Training Matrix:
         *
         *       C(20%)
         * Word 0.1 
         * Word 0.0 
         * Word 0.2 
         * Word 0.0
         *
         **/

        //Hashmap will carry the category as the key and the current and final 
        //Computation of all words in a given Doc D / P(D)
        HashMap<String, Double> finalComputations = new HashMap<>();

        //Loop through all the possible Categories
        //I don't start at 0 because the word is in the first column
        for (int i = 1; i < CATEGORIES.length; i++) {

            //New category as key
            finalComputations.put(CATEGORIES[i], 1.0);

            //Loop through the matrix of which Brandon provided
            //So we can find any words that match, then get corresponding value 
            for (int x = 0; x < matrix.length; x++) {
                if (inputTextWordCount.containsKey(matrix[x][1].toLowerCase())) {
                    
         
                    //If it doesn't equal a 0, begin to calculate
                    if (Double.parseDouble(matrix[x][i + 1]) != 0.0) {
                        
                        //Get the previous computation 
                        double value = finalComputations.get(CATEGORIES[i]);
                        //The word frequency count is the exponent W = (0.2)^(how many times was W in the Doc)
                        int exponent = inputTextWordCount.get(matrix[x][1]);
                        /**
                         * EX P[Dn|Cn] = (.02) x (.3)^4 x (.1)^5 ≈1.62e-9
                         *
                         * This is what's basically happening here in this code
                         * block value = is the previous computation, say (.02)
                         * x (.3)^4 exponent = is the word frequency so like
                         * (.1)^5 newValue = is value X (.1)^5
                         */
                        double newValue = (value * Math.pow(Double.parseDouble(matrix[x][i + 1]), exponent));

                        //Replace the old value with the new multiplication 
                        finalComputations.replace(CATEGORIES[i], newValue);

                    }

                }

            }

        }

        //Multiply the multiplication values with their respective Pr(Cn)
        for (int i = 1; i < CATEGORIES.length; i++) {
            double value = finalComputations.get(CATEGORIES[i]);

            /**
             * P[Cn|Dn] = P[Dn|Cn] x P[Cn] / P[Dn] ≈1.62e-9 x .4 / P[Dn] =
             * 6.48e-9 / P[Dn]
             *
             * This stage would be the same as the formula above. I'm
             * multiplying them by Pr(Cn) The reason why I put PROB_DIST into an
             * array was mainly for modularity as other test suites will require
             * different values - i.e .10, .10, .30, .30, and etc. So each
             * category will align with their given PROB_DIST at index 'i'
             */
            finalComputations.replace(CATEGORIES[i], (value * PROB_DIST[i - 1]));

        }

        //Finish up and print
        printDataComputationsToFile(testSuite, finalComputations, CATEGORIES);

    }

    /**
     * Prints findings to table in text file
     *
     * @param finalComputations
     */
    private static void printDataComputationsToFile(String testSuite, HashMap<String, Double> finalComputations, String[] CATEGORIES) throws IOException {

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(testSuite + ".txt"), "utf-8"))) {
            writer.write("Results for " + testSuite + "\n\n");
            writer.write(System.getProperty("line.separator"));
            writer.write(System.getProperty("line.separator"));
            writer.write(System.getProperty("line.separator"));
            for (int i = 1; i < CATEGORIES.length; i++) {
                writer.write(CATEGORIES[i] + " = " + finalComputations.get(CATEGORIES[i]));
                writer.write(System.getProperty("line.separator"));
            }

            
            //Get the top result from the finalComputations hashmap
            Map<String, Double> top
                    = finalComputations.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(1)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            top.entrySet().stream().forEach((entry) -> {
                try {
                    writer.write("It's most likely " + entry.getKey() + " with a value of " + entry.getValue());
                } catch (IOException ex) {
                    Logger.getLogger(AI_Project_3_Part_2.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            writer.flush();
            writer.close();
        }
    }

}
