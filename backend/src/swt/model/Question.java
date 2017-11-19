package swt.model;


import java.util.ArrayList;

public class Question {
	
	private String question = "";
	private String image = "";
	private ArrayList<Answer> answers;
	private int correct;
	
	public Question() {
		
	}

    public Question(String question, String image, ArrayList<Answer> answers, int correct) {
        this.question = question;
        this.image = image;
        this.answers = answers;
        this.correct = correct;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}
}
