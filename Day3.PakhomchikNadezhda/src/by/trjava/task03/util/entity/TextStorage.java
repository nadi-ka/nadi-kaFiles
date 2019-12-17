package by.trjava.task03.util.entity;

public class TextStorage {
    private String text;

    public TextStorage() {
        this.text = "";
    }

    public TextStorage(String text) {
        this.setText(text);
    }

    public void setText(String text) {
        if (text != null) {
            this.text = text;
        }
        else {
            text = "";
        }
    }

    public String getText() {
        return text;
    }

    public int getTextLength() {
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
        return (text.length() == textStorage.getTextLength()) &&
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
