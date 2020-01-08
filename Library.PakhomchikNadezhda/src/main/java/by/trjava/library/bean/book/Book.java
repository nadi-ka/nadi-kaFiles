package by.trjava.library.bean.book;

import java.io.Serializable;

public class Book implements Serializable {
    private long id;
    private BookCategory category;
    private String author;
    private String title;
    private int yearOfEdition;
    private double prise;
    private boolean isPopular;
    private String description;

    public Book() {
    }

    public Book(BookCategory category, String author, String title, int yearOfEdition,
                double prise, boolean isPopular, String description) {
        IdGeneratorBook instance = IdGeneratorBook.getInstance();
        this.id = instance.getNextId();
        this.category = category;
        this.author = author;
        this.title = title;
        this.yearOfEdition = yearOfEdition;
        this.prise = prise;
        this.isPopular = isPopular;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId() {
        IdGeneratorBook instance = IdGeneratorBook.getInstance();
        this.id = instance.getNextId();
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearOfEdition() {
        return yearOfEdition;
    }

    public void setYearOfEdition(int yearOfEdition) {
        this.yearOfEdition = yearOfEdition;
    }

    public double getPrise() {
        return prise;
    }

    public void setPrise(double prise) {
        this.prise = prise;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public void setPopular(boolean popular) {
        isPopular = popular;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Book book = (Book) object;
        return (id == book.id) &&
                (yearOfEdition == book.yearOfEdition) &&
                (isPopular == book.isPopular) &&
                (prise == book.prise) &&
                (category == book.category || category != null && category.equals(book.getCategory())) &&
                (author == book.author || author != null && author.equals(book.getAuthor())) &&
                (title == book.title || title != null && title.equals(book.getTitle())) &&
                (description == book.description || description != null && description.equals(book.getDescription()));
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        long priseToLong = Double.doubleToLongBits(prise);
        result = prime * result + (int)(id - (id>>>32));
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + yearOfEdition;
        result = prime * result + (int)(priseToLong - (priseToLong>>>32));
        result = prime * result + (isPopular ? 1 : 0);
        result = prime * result + ((description == null ? 0 : description.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        return "Book" + id + "[category: " + category + ", author: " + author + ", title: " + title +
        ", yearOfEdition: " + yearOfEdition + ", prise: " + prise + ", isPopular: " + isPopular +
                ", description: " + description + "]";
    }
}
