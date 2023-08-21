package ru.vistunov;

import java.io.*;
import java.util.Scanner;

public class MergerImpl implements Merger {

    private String fstWord;
    private String secWord;

    @Override
    public void mergeSortOfTwoIntFiles(File firstFile, File secondFile, File fileForResult, char paramOfSort) {
        try (Scanner firstScanner = new Scanner(new FileInputStream(firstFile));
             Scanner secondScanner = new Scanner(new FileInputStream(secondFile));
             PrintWriter writer = new PrintWriter(fileForResult)) {

            if (firstFile.length() == 0 && secondFile.length() != 0) {
                while (secondScanner.hasNextInt())
                    writer.println(secondScanner.nextInt());
            } else if (firstFile.length() != 0 && secondFile.length() == 0) {
                while (firstScanner.hasNextInt())
                    writer.println(firstScanner.nextInt());
            } else {

                Integer x = searchCorrectNumbInFile(firstScanner);
                Integer y = searchCorrectNumbInFile(secondScanner);
                int lastValue = paramOfSort == 'd' ? Integer.MAX_VALUE : Integer.MIN_VALUE;

                while (x != null && y != null) {
                    if (paramOfSort == 'd' ? (x <= y && lastValue >= y) : (x >= y && lastValue <= y)) {
                        writer.println(y);
                        lastValue = y;
                        y = searchCorrectNumbInFile(secondScanner);
                        if (y == null) {
                            break;
                        }
                    } else if (paramOfSort == 'd' ? lastValue >= x : lastValue <= x) {
                        writer.println(x);
                        lastValue = x;
                        x = searchCorrectNumbInFile(firstScanner);
                        if (x == null) {
                            break;
                        }
                    } else {
                        break;
                    }
                }

                while (x != null) {
                    if (paramOfSort == 'd' ? x <= lastValue : x >= lastValue) {
                        writer.println(x);
                        lastValue = x;
                    }
                    x = searchCorrectNumbInFile(firstScanner);
                }

                while (y != null) {
                    if (paramOfSort == 'd' ? y <= lastValue : y >= lastValue) {
                        writer.println(y);
                        lastValue = y;
                    }
                    y = searchCorrectNumbInFile(secondScanner);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void mergeSortOfTwoStringFiles(File firstFile, File secondFile, File fileForResult, char paramOfSort) {
        try (Scanner firstScanner = new Scanner(new FileInputStream(firstFile));
             Scanner secondScanner = new Scanner(new FileInputStream(secondFile));
             PrintWriter writer = new PrintWriter(fileForResult)) {

            if (firstFile.length() == 0 && secondFile.length() != 0) {
                while (secondScanner.hasNextLine())
                    writer.println(secondScanner.nextLine());
            } else if (firstFile.length() != 0 && secondFile.length() == 0) {
                while (firstScanner.hasNextLine())
                    writer.println(firstScanner.nextLine());
            } else {

                fstWord = searchCorrectStrInFile(firstScanner);
                secWord = searchCorrectStrInFile(secondScanner);
                String currentString;
                String lastValue = null;

                if (fstWord != null && secWord != null) {
                    lastValue = paramOfSort == 'a' ? returnAlpOrderStr(fstWord, secWord) :
                            returnRevAlpOrderStr(fstWord, secWord);
                }

                while (fstWord != null && secWord != null) {
                    if (paramOfSort == 'a') {
                        currentString = returnAlpOrderStrAndIterate(fstWord, secWord, firstScanner, secondScanner);
                        if (currentString.equals(returnRevAlpOrderStr(lastValue, currentString)))
                            writer.println(currentString);
                    } else {
                        currentString = returnRevAlpOrderStrAndIterate(fstWord, secWord, firstScanner, secondScanner);
                        if (currentString.equals(returnAlpOrderStr(lastValue, currentString)))
                            writer.println(currentString);
                    }
                    lastValue = currentString;
                }

                while (fstWord != null) {
                    if (paramOfSort == 'a') {
                        if (fstWord.equals(returnRevAlpOrderStr(lastValue, fstWord))) {
                            writer.println(fstWord);
                            lastValue = fstWord;
                        }
                    } else {
                        if (fstWord.equals(returnAlpOrderStr(lastValue, fstWord))) {
                            writer.println(fstWord);
                            lastValue = fstWord;
                        }
                    }
                    fstWord = searchCorrectStrInFile(firstScanner);
                }

                while (secWord != null) {
                    if (paramOfSort == 'a') {
                        if (secWord.equals(returnRevAlpOrderStr(lastValue, secWord))) {
                            writer.println(secWord);
                            lastValue = secWord;
                        }
                    } else {
                        if (secWord.equals(returnAlpOrderStr(lastValue, secWord))) {
                            writer.println(secWord);
                            lastValue = secWord;
                        }
                    }
                    secWord = searchCorrectStrInFile(secondScanner);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void copyFile(File copy, File fileToCopy) {
        try (Scanner scanner = new Scanner(new FileInputStream(fileToCopy));
             PrintWriter writer = new PrintWriter(copy)) {
            while (scanner.hasNextLine()) {
                writer.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer searchCorrectNumbInFile(Scanner scanner) {
        Integer numb = null;
        while (scanner.hasNextLine()) {
            try {
                numb = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
            }
        }
        return numb;
    }

    private String searchCorrectStrInFile(Scanner scanner) {
        String line;
        while (true) {
            if (!scanner.hasNextLine()) {
                return null;
            }
            line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
        }
    }

    private String returnRevAlpOrderStrAndIterate(String x, String y, Scanner firstScanner, Scanner secondScanner) {
        String result;
        if (x.compareTo(y) < 0) {
            result = y;
            this.secWord = searchCorrectStrInFile(secondScanner);
        } else {
            result = x;
            this.fstWord = searchCorrectStrInFile(firstScanner);
        }
        return result;
    }

    private String returnAlpOrderStrAndIterate(String x, String y, Scanner firstScanner, Scanner secondScanner) {
        String result;
        if (x.compareTo(y) > 0) {
            result = y;
            this.secWord = searchCorrectStrInFile(secondScanner);
        } else {
            result = x;
            this.fstWord = searchCorrectStrInFile(firstScanner);
        }
        return result;
    }

    private String returnAlpOrderStr(String firstStr, String secondStr) {
        if (firstStr.compareTo(secondStr) > 0) {
            return secondStr;
        } else {
            return firstStr;
        }
    }

    private String returnRevAlpOrderStr(String firstStr, String secondStr) {
        if (firstStr.compareTo(secondStr) < 0) {
            return secondStr;
        } else {
            return firstStr;
        }
    }
}
