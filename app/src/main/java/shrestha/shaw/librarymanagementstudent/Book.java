package shrestha.shaw.librarymanagementstudent;


public class Book {
    private String title;
    private String author;
    private String description;
    private int copies;

    public Book() {
    }

    public Book(String title, String author, String description, int copies) {
        this.author = author;
        this.description = description;
        this.title = title;
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getCopies() {
        return copies;
    }
}
