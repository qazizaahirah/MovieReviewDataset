/*
 This Code has been written for the prject for the course machine learning for Big data. This program takes
 documents as input and outputs the matrix consisting of the frequency of the important words.
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/*
 * Qazi Zaahirah
 */
public class MovieReview2 {

    private static final String LINE_SEPARATOR = "\r\n";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        MovieReview2 app = new MovieReview2();

        app.getUniqueTerms();
        app.RemoveCommonWords();
        app.finalMatrix();
        app.createClassfile();

    }

    // Get the corpus of words from both positive and negative classes
    public List getUniqueTerms() throws FileNotFoundException, IOException {
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
                            // System.out.println(FinalFilePath);
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
                                File file1 = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\" + name + "\\" + tmpFile);
                                BufferedWriter out1 = new BufferedWriter(new FileWriter(file1, true));

                                for (String str : result) {
                                    out1.newLine();
                                    out1.write(str);
                                }
                                out1.flush();
                                out1.close();
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
        System.out.println("the size of actual union:" + Union.size());
        MovieReview2 app = new MovieReview2();
      //  app.PositiveNegativeFrequency();
        //  app.newLogAndUnion(Union);
        return Union;
    }

    // This method removes the common words in the corpus. The common words or the intersection of positive and negative
    // corpus increases the feature space
    public void RemoveCommonWords() throws FileNotFoundException, IOException {

        ArrayList<String> pos = new ArrayList<String>();
        ArrayList<String> neg = new ArrayList<String>();
        File file1 = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\UnionNoCommonWords.txt");
        File file2 = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\NegativeWords.txt");
        String path1 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\pos";
        String path2 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\neg";
        if (new File(path1).isDirectory()) {
            File f = new File(path1);
            String[] fileList = f.list();
            for (String tmpFile : fileList) {
                String newPath = path1 + "//" + tmpFile;
                // System.out.println(newPath);
                Scanner files = new Scanner(new File(newPath));
                while (files.hasNext()) {
                    pos.add(files.next());
                }
                Set<String> UniqueWords;
                UniqueWords = new HashSet<String>();
                UniqueWords.addAll(pos);
                pos.clear();
                pos.addAll(UniqueWords);

            }
        }
        if (new File(path2).isDirectory()) {
            File f = new File(path2);
            String[] fileList = f.list();
            for (String tmpFile : fileList) {
                String newPath = path2 + "//" + tmpFile;
                // System.out.println(newPath);
                Scanner files = new Scanner(new File(newPath));
                while (files.hasNext()) {
                    neg.add(files.next());
                }
                Set<String> UniqueWords;
                UniqueWords = new HashSet<String>();
                UniqueWords.addAll(neg);
                neg.clear();
                neg.addAll(UniqueWords);

            }
        }

        pos.removeAll(neg);
        System.out.println("Size: " + pos.size());
        PrintWriter UnionWriter = new PrintWriter(new BufferedWriter(new FileWriter(file1, true)));
        for (String str : pos) {
            UnionWriter.println(str);
        }
        UnionWriter.flush();
        UnionWriter.close();
        MovieReview2 app = new MovieReview2();
        app.PositiveNegativeFrequency();
        app.newLogAndUnion(pos);
    }
// this method shows the frequency of the respective words in the corpus in the documents (positive and negative review)

    public void PositiveNegativeFrequency() throws IOException {
        System.out.println("I am in PositiveNegativeFrequency ");
        String path = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\UnionNoCommonWords.txt";
        Scanner file = new Scanner(new File(path));
        ArrayList<String> Union = new ArrayList<String>();
        ArrayList<Integer> index = new ArrayList<Integer>();
        while (file.hasNext()) {
            Union.add(file.next());
        }
        int size = Union.size();

        File output = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\LogValue.txt");
        PrintWriter out1 = new PrintWriter(new FileWriter(output, true));
        File indexList = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\index.txt");
        PrintWriter out2 = new PrintWriter(new FileWriter(indexList, true));
        int[] FrequencyPos = new int[size];
        int[] FrequencyNeg = new int[size];
        String path1 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\pos";
        String path2 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\neg";
        if (new File(path1).isDirectory()) {
            File f = new File(path1);
            String[] fileList = f.list();
            for (String tmpFile : fileList) {
                String newPath = path1 + "//" + tmpFile;
                // System.out.println(newPath);
                Scanner files = new Scanner(new File(newPath));
                ArrayList<String> fileWords = new ArrayList<String>();
                while (files.hasNext()) {
                    fileWords.add(files.next());
                }

                for (int k = 0; k < fileWords.size(); k++) {
                    for (int j = 0; j < Union.size(); j++) {
                        if (Union.get(j).equals(fileWords.get(k))) {
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
                //  System.out.println(newPath);
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
        double[] logValue = new double[size];
        for (int i = 0; i < size; i++) {
            double mul;
            if (FrequencyNeg[i] == 0 || FrequencyPos[i] == 0) {
                logValue[i] = 5;
            } else {
                mul = Math.abs(FrequencyNeg[i] / FrequencyPos[i]);
                if (mul < 0.1) {
                    logValue[i] = 0;
                } else {
                    logValue[i] = Math.round((Math.log(mul) / Math.log(2)) * 100.0) / 100.0;
                }
            }
        }
        // Form the index list
        for (int i = 0; i < logValue.length; i++) {
            if (logValue[i] != 0) {
                index.add(i);
            }
        }
        for (int i = 0; i < size; i++) {
            out1.write(String.valueOf(logValue[i]));
            out1.write(LINE_SEPARATOR);

        }
        out1.flush();
        out1.close();

        for (int i = 0; i < index.size(); i++) {
            out2.write(String.valueOf(index.get(i)));
            out2.write(LINE_SEPARATOR);
        }
        out2.flush();
        out2.close();
//        Arrays.sort(logValue);
//        System.out.println("the largest log value is: " + logValue[size - 1]);
    }// method

    // this is to make the new corpus list for the words which have highest weight
    public void newLogAndUnion(List Union) throws IOException {
        System.out.println("I am in newLogAndUnion");
        ArrayList<String> newLogValue = new ArrayList<String>();
        ArrayList<String> newUnion = new ArrayList<String>();
        ArrayList<String> logValue = new ArrayList<String>();
        ArrayList<Integer> index = new ArrayList<Integer>();
        String path = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\LogValue.txt";
        Scanner file = new Scanner(new File(path));
        while (file.hasNext()) {
            logValue.add(file.next());
        }
        String path2 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\index.txt";
        Scanner file2 = new Scanner(new File(path2));
        while (file2.hasNext()) {
            int temp = Integer.parseInt(file2.next());
            index.add(temp);
        }
        File newUnionList = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\NewUnion.txt");
        PrintWriter out2 = new PrintWriter(new FileWriter(newUnionList, true));
        File output = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\NewLogValue.txt");
        PrintWriter out1 = new PrintWriter(new FileWriter(output, true));

        for (int j = 0; j < index.size(); j++) {
            for (int i = j; i < logValue.size(); i++) {
                int temp = (int) index.get(j);
                if (i == temp) {
                    //  System.out.println("for index value: "+ index.get(j)+" we add "+ logValue.get(i)+"at index: "+i);
                    newLogValue.add((String) logValue.get(i));
                }
            }
        }
        System.out.println("LogValue: " + logValue.size() + " NewLogValue: " + newLogValue.size());
        for (int i = 0; i < Union.size(); i++) {
            for (int j = 0; j < index.size(); j++) {
                int temp = (int) index.get(j);
                if (i == temp) {
                    newUnion.add((String) Union.get(i));
                }
            }
        }
        System.out.println("Union: " + Union.size() + " NewUnion: " + newUnion.size());
        for (int i = 0; i < newLogValue.size(); i++) {
            out1.write(String.valueOf(newLogValue.get(i)));
            out1.write(LINE_SEPARATOR);

        }
        out1.flush();
        out1.close();
        for (int i = 0; i < newUnion.size(); i++) {
            out2.write(String.valueOf(newUnion.get(i)));
            out2.write(LINE_SEPARATOR);
        }
        out2.flush();
        out2.close();
        MovieReview2 app = new MovieReview2();
        app.CountTerms(newUnion);
    }
// This method gets the number of times the terms in the corpus occur in the document

    public void CountTerms(List Union) throws FileNotFoundException, IOException {
        System.out.println("I am in count terms");
        int size = Union.size();

        int[] FinalInput = new int[size];
        for (int j = 0; j < size; j++) {
            FinalInput[j] = 0;
        }
        String DirectoryPath = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\Test";
        File Directory = new File(DirectoryPath);
        String[] subDirectoryNames = Directory.list();
        if (new File(DirectoryPath).isDirectory()) {
            for (String name2 : subDirectoryNames) {
                String SubdirectoryPath = DirectoryPath + "//" + name2;
                File dir = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\" + name2 + "CountTerms");
                dir.mkdir();
                if (new File(SubdirectoryPath).isDirectory()) {
                    File f = new File(SubdirectoryPath);
                    String[] fileList = f.list();
                    for (String tmpFile : fileList) {
                        for (int j = 0; j < size; j++) {
                            FinalInput[j] = 0;
                        }
                        String newPath = SubdirectoryPath + "//" + tmpFile;
                        //  System.out.println(newPath);
                        Scanner files = new Scanner(new File(newPath));
                        ArrayList<String> fileWords = new ArrayList<String>();
                        while (files.hasNext()) {
                            fileWords.add(files.next());
                        }
                        for (int j = 0; j < Union.size(); j++) {
                            for (int k = 0; k < fileWords.size(); k++) {
                                if (Union.get(j).equals(fileWords.get(k))) {
                                    FinalInput[j] = FinalInput[j] + 1;
                                }
                            }
                        }
                        File output = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\" + name2 + "CountTerms\\" + tmpFile);
                        PrintWriter out1 = new PrintWriter(new FileWriter(output, true));
                        for (int j = 0; j < size; j++) {
                            if (FinalInput[j] > 1) {
                                // System.out.println(FinalInput[j]);
                            }
                            out1.print(String.valueOf(FinalInput[j]));
                            out1.print(LINE_SEPARATOR);
                        }

                        out1.flush();
                        out1.close();

                    }
                }
            }
        }

    }// method
// this method gives the final matrix

    public void finalMatrix() throws FileNotFoundException, IOException {
        String path = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\newLogValue.txt";
        Scanner file = new Scanner(new File(path));
        double[][] finalMatrix = new double[2000][14611];

        int[] summation = new int[14611];
        ArrayList<String> logValues = new ArrayList<String>();
        ArrayList<Integer> newFeatures = new ArrayList<Integer>();
        while (file.hasNext()) {
            logValues.add(file.next());
        }
        int rows = 0;
        System.out.println("the size of the logVlaues is:" + logValues.size());
        File output = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\FinalMatrixsmall.txt");

        String DirectoryPath = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\Count Terms";
        File Directory = new File(DirectoryPath);
        String[] subDirectoryNames = Directory.list();
        if (new File(DirectoryPath).isDirectory()) {
            System.out.println("this part is working");
            for (String name2 : subDirectoryNames) {
                String SubdirectoryPath = DirectoryPath + "//" + name2;
                if (new File(SubdirectoryPath).isDirectory()) {
                    File f = new File(SubdirectoryPath);
                    String[] fileList = f.list();
                    for (String tmpFile : fileList) {
                        String newPath = SubdirectoryPath + "//" + tmpFile;
                        System.out.println(newPath);
                        Scanner files = new Scanner(new File(newPath));
                        ArrayList<String> fileValues = new ArrayList<String>();
                        ArrayList<Double> FinalValues = new ArrayList<Double>();
                        while (files.hasNext()) {
                            fileValues.add(files.next());
                        }
                        //  System.out.println("the size of the file is: "+fileValues.size());
                        for (int j = 0; j < fileValues.size(); j++) {
                            double mul;
                            double fileValue = Double.parseDouble(fileValues.get(j));
                            double logValue = Double.parseDouble(logValues.get(j));
                            mul = fileValue * logValue;
//                            if(mul!=0.0){
//                            System.out.println(mul);
//                            }
                            FinalValues.add(mul);
                            // System.out.println("size of the final value is: "+FinalValues.size());
                        }
                        for (int i = 0; i < 14611; i++) {
                            finalMatrix[rows][i] = FinalValues.get(i);
                            summation[i] = (int) (summation[i] + FinalValues.get(i));
                        }
                        rows++;
                        // System.out.println("size of the final value is: "+FinalValues.size());

                        FinalValues.clear();
                        fileValues.clear();
                    }
                }
            }
        }
        for (int i = 0; i < 14611; i++) {
            if (summation[i] > 30) {
                newFeatures.add(i);
            }
        }
        int noOfFeatures = newFeatures.size();
        double[][] newFeatureSpace;
        newFeatureSpace = new double[2000][noOfFeatures];
        for (int i = 0; i < 2000; i++) {
            for (int j = 0; j < noOfFeatures; j++) {
                int newcol = newFeatures.get(j);
                newFeatureSpace[i][j] = finalMatrix[i][newcol];
            }
        }
        PrintWriter out1 = new PrintWriter(new FileWriter(output, true));
        for (int i = 0; i < 2000; i++) {
            out1.append(String.valueOf(newFeatureSpace[i][0]));
            for (int j = 1; j < noOfFeatures; j++) {
                out1.append("\t");
                out1.append(String.valueOf(newFeatureSpace[i][j]));
            }
            out1.append(LINE_SEPARATOR);
        }

        out1.flush();
        out1.close();
        String path1 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\FinalMatrixsmall.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path1));
        int lines = 0;
        while (reader.readLine() != null) {
            lines++;
        }
        reader.close();
        System.out.println("Lines: " + lines);
        System.out.println("New Feature Space is: " + noOfFeatures);
    }// method

    public void createClassfile() throws IOException {
        File output = new File("C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\Classfile.txt");
        PrintWriter out1 = new PrintWriter(new FileWriter(output, true));
        String value1 = "0";
        String value2 = "1";
        for (int i = 0; i < 1000; i++) {
            out1.write(value1);
            out1.write(LINE_SEPARATOR);
        }
        out1.write(LINE_SEPARATOR);
        for (int i = 0; i < 1000; i++) {
            out1.write(value2);
            out1.write(LINE_SEPARATOR);
        }
        out1.flush();
        out1.close();

        String path1 = "C:\\Users\\dell\\Desktop\\DalDocuments\\MachineLearningBigData\\Project\\Results\\ClassFile.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path1));
        int lines = 0;
        while (reader.readLine() != null) {
            lines++;
        }
        reader.close();
        System.out.println("Lines: " + lines);
    }
}// class

