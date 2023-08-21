package ru.vistunov;

import java.io.File;

public interface Merger {

    void mergeSortOfTwoIntFiles(File firstFile, File secondFile, File fileForResult, char paramOfSort);

    void mergeSortOfTwoStringFiles(File firstFile, File secondFile, File fileForResult, char paramOfSort);

    void copyFile(File copy, File fileToCopy);
}
