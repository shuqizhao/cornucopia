package cornucopia.entity;

public class ProcessDiagramEntity extends BaseEntity {
	private String fileId;
	private int processId;
	private String picFileId;
	private String deployId;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public String getPicFileId() {
		return picFileId;
	}

	public void setPicFileId(String picFileId) {
		this.picFileId = picFileId;
	}

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}
}
