package swt.model;

public class Answer {

    private int id;
    private String text;
    private String description;
    private String descriptionImage;

    public Answer() {
    }

    public Answer(int id, String text, String description, String descriptionImage) {
        this.id = id;
        this.text = text;
        this.description = description;
        this.descriptionImage = descriptionImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionImage() {
        return descriptionImage;
    }

    public void setDescriptionImage(String descriptionImage) {
        this.descriptionImage = descriptionImage;
    }
}
