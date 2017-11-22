package swt.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Arrays;


@JacksonXmlRootElement(localName = "questions")
public final class QuestionsXML {

	@JacksonXmlElementWrapper(localName = "question", useWrapping = false)
	private QuestionXML[] question;

	public QuestionsXML() {
	}

	public QuestionsXML(QuestionXML[] question) {
		this.question = question;
	}

	public QuestionXML[] getQuestion() {
		return question;
	}

	public void setQuestion(QuestionXML[] question) {
		this.question = question;
	}

}
