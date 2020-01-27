package by.trjava.library.settings.settingsImpl;

import by.trjava.library.settings.LibrarySettings;

public class LibrarySettingsImpl implements LibrarySettings {
    private final static String baseFileUser = "D:\\Workspace\\user\\user.json";
    private final static String baseFileBook = "D:\\Workspace\\book\\book.json";

    @Override
    public String getUserSettings() {
        return baseFileUser;
    }

    @Override
    public String getBookSettings() {
        return baseFileBook;
    }
}
