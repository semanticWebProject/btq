package swt.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;



public final class CategoryXML {

	@JacksonXmlProperty(localName = "id", isAttribute = true)
	private int id;
	@JacksonXmlProperty(localName = "name", isAttribute = true)
	private String name;
	@JacksonXmlProperty(localName = "image", isAttribute = true)
	private String imageURL;
	@JacksonXmlProperty(localName = "fileName", isAttribute = true)
	private String fileName;

	public CategoryXML() {
	}

	public CategoryXML(int id, String name, String imageURL, String fileName) {
		this.id = id;
		this.name = name;
		this.imageURL = imageURL;
		this.fileName = fileName;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
