package by.trjava.task03.util.entity;

public class TextStorage {
    private String text;

    public TextStorage() {
        this.text = "";
    }

    public TextStorage(String text) {
        this.setText(text);
    }

    // I prefer not to have such Public methods if possible
    private void setText(String text) {
        if (text != null) {
            this.text = text;
        }
        else {
            text = "";
        }
    }

    // Here it's ok, since the strings are immutable
    public String getText() {
        return text;
    }

    public int length() {
        return text.length();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        TextStorage textStorage = (TextStorage) object;
        return (text.length() == textStorage.length()) &&
                ((text == textStorage.text) || ((text != null) && (text.equals(textStorage.getText()))));
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@[" + text + "];";
    }
}
