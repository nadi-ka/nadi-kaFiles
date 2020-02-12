package by.trjava.library.settings.settingsImpl;

import by.trjava.library.settings.LibrarySettings;

public class LibrarySettingsImpl implements LibrarySettings {
    private final static String BASE_FILE_USER = "D:\\Workspace\\user\\user.json";
    private final static String BASE_FILE_BOOK = "D:\\Workspace\\book\\book.json";

    @Override
    public String getUserSettings() {
        return BASE_FILE_USER;
    }

    @Override
    public String getBookSettings() {
        return BASE_FILE_BOOK;
    }
}
