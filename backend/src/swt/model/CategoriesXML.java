package swt.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "categories")
public final class CategoriesXML {

	@JacksonXmlElementWrapper(localName = "category", useWrapping = false)
	private CategoryXML[] category;

	public CategoriesXML() {
	}

	public CategoriesXML(CategoryXML[] category) {
		this.category = category;
	}

	public CategoryXML[] getCategory() {
		return category;
	}

	public void setCategory(CategoryXML[] category) {
		this.category = category;
	}
}
