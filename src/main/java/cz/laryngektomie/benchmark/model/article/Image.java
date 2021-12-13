package cz.laryngektomie.benchmark.model.article;

import cz.laryngektomie.benchmark.model.EntityBase;

public class Image extends EntityBase {

	private String fileName;

	private String fileType;

	private byte[] data;

	public Image(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
