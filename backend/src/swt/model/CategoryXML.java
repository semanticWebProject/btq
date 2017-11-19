package swt.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Arrays;


@JacksonXmlRootElement(localName = "category")
public final class CategoryXML {

	@JacksonXmlElementWrapper(localName = "question", useWrapping = false)
	private QuestionXML[] question;

	@JacksonXmlProperty(localName = "id", isAttribute = true)
	private int id;
	@JacksonXmlProperty(localName = "name", isAttribute = true)
	private String name;
	@JacksonXmlProperty(localName = "image", isAttribute = true)
	private String imageURL;

	public CategoryXML() {
	}

	public CategoryXML(QuestionXML[] question) {
		this.question = question;
	}

	public QuestionXML[] getQuestion() {
		return question;
	}

	public void setQuestion(QuestionXML[] question) {
		this.question = question;
	}

	@Override public String toString() {
		return "Questions{" +
				"questions=" + Arrays.toString(question) +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
}
