package ru.vistunov;

import java.io.File;
import java.util.ArrayList;

public class Controller {

    private ArrayList<File> listOfFiles;
    private String paramOfSort;
    private String typeOfData;
    private Merger merger;

    public Controller() {
        listOfFiles = new ArrayList<>();
        paramOfSort = "-a";
        typeOfData = null;
        merger = new MergerImpl();
    }

    private void mergerFiles(File firstFile, File secondFile, File resultFile) {
        if (typeOfData.equals("-i")) {
            merger.mergeSortOfTwoIntFiles(firstFile, secondFile, resultFile, paramOfSort.charAt(1));
        } else if (typeOfData.equals("-s")) {
            merger.mergeSortOfTwoStringFiles(firstFile, secondFile, resultFile, paramOfSort.charAt(1));
        }
    }

    public void insertParamAndRun(String[] args) {
        for (String arg : args) {
            if (arg.contains(".txt")) {
                listOfFiles.add(new File(arg));
            }
            switch (arg.trim()) {
                case "-d":
                    paramOfSort = "-d";
                    break;
                case "-i":
                    typeOfData = "-i";
                    break;
                case "-s":
                    typeOfData = "-s";
                    break;
            }
        }

        if (typeOfData == null) {
            System.out.println("Укажите тип данных входных файлов");
        } else {
            switch (listOfFiles.size()) {
                case 0:
                    System.out.println("Добавьте входные файлы, а так же файл для записи результата слияния");
                    break;
                case 1:
                    System.out.println("Недостаточно файлов для слияния");
                    break;
                case 2:
                    System.out.println("Слияние не прозошло - входной файл один." +
                            " Информация из входного файла перенесена в файл с результатом");
                    File tempFile = new File("tempFile.txt");
                    mergerFiles(tempFile, listOfFiles.get(1), listOfFiles.get(0));
                    tempFile.delete();
                    break;
                case 3:
                    mergerFiles(listOfFiles.get(1), listOfFiles.get(2), listOfFiles.get(0));
                    break;
                default:
                    mergerFiles(listOfFiles.get(1), listOfFiles.get(2), listOfFiles.get(0));
                    for (int i = 3; i < listOfFiles.size(); i++) {
                        File tmpFile = new File("tmpFile.txt");
                        merger.copyFile(tmpFile, listOfFiles.get(0));
                        mergerFiles(tmpFile, listOfFiles.get(i), listOfFiles.get(0));
                        tmpFile.delete();
                    }
            }
        }
    }
}
