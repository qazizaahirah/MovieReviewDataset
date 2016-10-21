/*

 */
package moviereview;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/*
 * Qazi Zaahirah
 */
public class MovieReview {

    private static final String LINE_SEPARATOR = "\r\n";

//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        MovieReview app = new MovieReview();
//        app.getUniqueTerms();
//
//    }

    public List getUniqueTerms() throws FileNotFoundException, IOException {
        System.out.println("this program has worked");
        ArrayList<String> Union = new ArrayList<String>();
        ArrayList<String> result = new ArrayList<String>();

        String path = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Dataset";

        File file2 = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\Union.txt");

        File subDirectory = new File(path);
        String[] subDirectoryNames = subDirectory.list();
        if (new File(path).isDirectory()) {

            for (String name : subDirectoryNames) {
                String SubdirectoryPath = path + "//" + name;
                File dir = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\" + name);

                dir.mkdir();

                if (new File(SubdirectoryPath).isDirectory()) {
                    File f = new File(SubdirectoryPath);
                    String[] fileList = f.list();
                    for (String tmpFile : fileList) {
                        try {
                            String FinalFilePath = SubdirectoryPath + "//" + tmpFile;
                            System.out.println(FinalFilePath);
                            if (new File(FinalFilePath).isFile()) {
                                BufferedReader br = new BufferedReader(new FileReader(FinalFilePath));
                                StringBuilder sb = new StringBuilder();
                                String line = br.readLine();

                                while (line != null) {
                                    sb.append(line);
                                    line = br.readLine();
                                }
                                // this part will remove the stop words
                                String content = sb.toString();
                                String contentWithOutStop = content.replaceAll("\t", " ");
                                contentWithOutStop = contentWithOutStop.replaceAll("[-+.^:,=*'?/;#$()&@!1234567890%<>_\"~`]", "");
                                contentWithOutStop = contentWithOutStop.replace("\n", "").replace("\r", "").replace("\t", "");
                                String[] wordArray = contentWithOutStop.split("[ ]+");
                                Scanner s = new Scanner(new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Assignment3\\StopWords.txt"));
                                ArrayList<String> stopWordsList = new ArrayList<String>();
                                while (s.hasNext()) {
                                    stopWordsList.add(s.next());

                                }
                                s.close();
                                for (String WithoutStop : wordArray) {
                                    if (!stopWordsList.contains(WithoutStop)) {
                                        result.add(WithoutStop);
                                        //  System.out.println("WithoutStop "+WithoutStop);
                                    }
                                }
                                Set<String> Uniques;
                                Uniques = new HashSet<String>();
                                Set<String> UniqueUnion;
                                UniqueUnion = new HashSet<String>();
                                Uniques.addAll(result);
                                result.clear();
                                result.addAll(Uniques);
                                Union.addAll(result);
                                UniqueUnion.addAll(Union);
                                Union.clear();
                                Union.addAll(UniqueUnion);
//                                File file1 = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\" + name + "\\" + tmpFile);
//                                BufferedWriter out1 = new BufferedWriter(new FileWriter(file1, true));
//
//                                for (String str : result) {
//                                    out1.newLine();
//                                    out1.write(str);
//                                }
//                                out1.flush();
//                                out1.close();
                                // this is to clear all the variables for the othre incoming texts
                                result.clear();
                                Uniques.clear();
                                content = null;
                            }// if isFile

                        } catch (Exception e) {
                            throw new IllegalStateException("illegal state issue", e);
                        }
                    }

                }//if for the subdirectory2Path

            }// for name

        } // if subdirectoryPath
        // this is write the union file for each user
        PrintWriter UnionWriter = new PrintWriter(new BufferedWriter(new FileWriter(file2, true)));
        for (String str : Union) {
            UnionWriter.println(str);
        }
        UnionWriter.flush();
        UnionWriter.close();

        MovieReview app = new MovieReview();
        // app.PositiveNegativeFrequency(Union);
        app.FinalMatrix(Union);
        return Union;
    }

    public void PositiveNegativeFrequency(List Union) throws IOException {
        int size = Union.size();
        File output = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\FinalFrequency.txt");
        PrintWriter out1 = new PrintWriter(new FileWriter(output, true));
        int[] FrequencyPos = new int[size];
        int[] FrequencyNeg = new int[size];
        String path1 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\posUnique";
        String path2 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\negUnique";
        if (new File(path1).isDirectory()) {
            File f = new File(path1);
            String[] fileList = f.list();
            for (String tmpFile : fileList) {
                String newPath = path1 + "//" + tmpFile;
                System.out.println(newPath);
                Scanner files = new Scanner(new File(newPath));
                ArrayList<String> fileWords = new ArrayList<String>();
                while (files.hasNext()) {
                    fileWords.add(files.next());
                }

                for (int k = 0; k < fileWords.size(); k++) {
                    for (int j = 0; j < Union.size(); j++) {
                        if (Union.get(j).equals(fileWords.get(k))) {
                            //   System.out.println("this matrix is working");
                            FrequencyPos[j] = FrequencyPos[j] + 1;
                        }
                    }

                }

                fileWords.clear();
            }
        }// for the list of all the reviews
        if (new File(path2).isDirectory()) {
            File f = new File(path2);
            String[] fileList = f.list();
            for (String tmpFile : fileList) {
                String newPath = path2 + "//" + tmpFile;
                System.out.println(newPath);
                Scanner files = new Scanner(new File(newPath));
                ArrayList<String> fileWords = new ArrayList<String>();
                while (files.hasNext()) {
                    fileWords.add(files.next());
                }

                for (int k = 0; k < fileWords.size(); k++) {
                    for (int j = 0; j < Union.size(); j++) {
                        if (Union.get(j).equals(fileWords.get(k))) {
                            //   System.out.println("this matrix is working");
                            FrequencyNeg[j] = FrequencyNeg[j] + 1;
                        }
                    }

                }

                fileWords.clear();
            }
        }// for the list of all the reviews
        for (int i = 0; i < size; i++) {

            out1.write(String.valueOf(Union.get(i)));
            System.out.println(Union.get(i) + "\t" + FrequencyPos[i] + "\t" + FrequencyNeg[i]);
            out1.write("\t");
            out1.write(String.valueOf(FrequencyPos[i]));
            out1.write("\t");
            out1.write(String.valueOf(FrequencyNeg[i]));
            out1.write(LINE_SEPARATOR);

        }
        out1.flush();
        out1.close();

    }// method 

    public void FinalMatrix(List Union) throws FileNotFoundException, IOException {

        int size = Union.size();
        File output = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\FinalMatrix.txt");
        PrintWriter out1 = new PrintWriter(new FileWriter(output, true));
        int[][] FinalInput = new int[2000][size];
                for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < size; j++) {
                FinalInput[i][j] =0;
            }
        }
        int filenumber = 0;
        String DirectoryPath = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\Test";
        File Directory = new File(DirectoryPath);
        String[] subDirectoryNames = Directory.list();
        if (new File(DirectoryPath).isDirectory()) {
            for (String name2 : subDirectoryNames) {
                String SubdirectoryPath = DirectoryPath + "//" + name2;
                if (new File(SubdirectoryPath).isDirectory()) {
                    File f = new File(SubdirectoryPath);
                    String[] fileList = f.list();
                    for (String tmpFile : fileList) {
                        String newPath = SubdirectoryPath + "//" + tmpFile;
                        System.out.println(newPath);
                        Scanner files = new Scanner(new File(newPath));
                        ArrayList<String> fileWords = new ArrayList<String>();
                        while (files.hasNext()) {
                            fileWords.add(files.next());
                        }
                        for (int k = 0; k < fileWords.size(); k++) {
                            for (int j = 0; j < Union.size(); j++) {
                                if (Union.get(j).equals(fileWords.get(k))) {
                                    FinalInput[filenumber][j] = FinalInput[filenumber][j] + 1;
                                }
                            }
                        }
                        filenumber++;
                    }
                }
            }
        }
        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < size; j++) {
                System.out.println(FinalInput[i][j]);
                out1.print(String.valueOf(FinalInput[i][j]));
                out1.print(" ");
            }
            out1.print(LINE_SEPARATOR);
        }

        out1.flush();
        out1.close();

    }// method

}// class
