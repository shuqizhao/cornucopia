package cornucopia.model;

public class ProcessDiagramViewModel {
	private FileViewModel[] fileId;
	private int processId;
	private FileViewModel[] picFileId;
	private String deployId;
	private String defId;
	private String defKey;
	private String name;

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public String getDefKey() {
		return defKey;
	}

	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileViewModel[] getFileId() {
		return fileId;
	}

	public void setFileId(FileViewModel[] fileId) {
		this.fileId = fileId;
	}

	public FileViewModel[] getPicFileId() {
		return picFileId;
	}

	public void setPicFileId(FileViewModel[] picFileId) {
		this.picFileId = picFileId;
	}
}
