/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_project_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Stephen Lightcap AI
 */
public class AI_Project_3 {

    //Scanner to scan user I/O
    static Scanner scan;
    //has map to save words and their respective count. 
    static HashMap<String, Integer> hash = new HashMap<>();

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        scan = new Scanner(System.in);
        //Variable to keep scanning 
        boolean keepScanning = true;
        String category;

        //Hash set of words to ignore
        Set<String> setWordsToIgnore = new HashSet<>();
        //Hardcoded path to a file with a list of words to ignore
        File FileOfWordsToIgnore = new File("C:/mini_newsgroups/ignorelist.txt");

        //Populates all words to ignore in set
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(FileOfWordsToIgnore);
        } catch (FileNotFoundException e) {
        }

        while (sc2.hasNextLine()) {
            Scanner s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()) {
                setWordsToIgnore.add(s2.next());

            }
        }

        while (keepScanning) {

            //Categroy name 
            System.out.println("What category do you want to get data for?");
            category = scan.next();
            //Get the file location from user
            System.out.println("Folder path of files you want to scan - i.e. 'C:/some/where/to/file'");
            String path = scan.next();

            //Grab directory or file
            File folder = new File(path);
            //If folder is a directory, go in it and start scanning each file
            if (folder.isDirectory()) {
                System.out.println("The file is a directory...");
                //Loop through all the files 
                for (File file : folder.listFiles()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();

                        int count = 0;
                        while (line != null) {
                            //Skip the first 9 lines because they're just email headers
                            //i.e FROM, SCHOOLS, foobar@foo.bar etc etc
                            if (count > 9) {
                                sb.append(line);
                                sb.append(System.lineSeparator());
                                line = br.readLine();
                            }
                            count++;
                        }
                        //String everything = sb.toString();
                        saveLineOfWordsToCategory(category, sb.toString(), hash, setWordsToIgnore);
                    }
                }

            } else {
                System.out.println("Please enter a real directory - exclude files...");
            }

            Map<String, Integer> reversedMap = new TreeMap<>(hash);

            Stream<Map.Entry<String, Integer>> sorted
                    = hash.entrySet().stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));

            //Get the top values specified in the .limit(integer)
            //Getting 200 right now but, it does not print correctly, though it still gets the 
            //top values given it's limit
            Map<String, Integer> topTen
                    = hash.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(200)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            //Print out the top 30 words
            try (PrintWriter writer = new PrintWriter(category + "_training_data.txt", "UTF-8")) {
                topTen.entrySet().stream().forEach((entry) -> {
                    writer.println(entry.getKey() + " == " + entry.getValue());
                });
            } catch (IOException e) {
                // do something
            }

            topTen.clear();
            hash.clear();

            System.out.println("Do you want to scan another directory? - (Y)es or (N)");
            char y_n = scan.next().charAt(0);

            if (y_n == 'N' || y_n == 'n') {
                keepScanning = false;
            }

        }

    }

    /**
     * Accepts a string category to make sure all training data sticks to that
     * category and line which will then be split into a string array and
     * processed by each word.
     *
     * @param category
     * @param line
     */
    private static void saveLineOfWordsToCategory(String category, String line, HashMap<String, Integer> hash, Set<String> setWordsToIgnore) {
        //Split the line
        String[] words = line.split("\\W+");

        //Loop through the string array
        for (String word1 : words) {
            String word = word1.replace("\\d+", "").toLowerCase();
            //Is the word in the ignore list, if yes, ignore it
            if (!setWordsToIgnore.contains(word)) {
                //Is the word already in the hash set of saved words, if yes, increment it
                if (hash.containsKey(word)) {
                    //Get the count for the word and increment it
                    int x = hash.get(word);
                    x++;
                    hash.replace(word, x);

                } else {
                    hash.put(word, 1);
                }
            }
        }

    }

}
