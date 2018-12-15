package cornucopia.model;

public class ProcessSearchViewModel{
    private int processId;
    private String columnName;
    private String columnValue;

    /**
     * @return the processId
     */
    public int getProcessId() {
        return processId;
    }

    /**
     * @return the columnValue
     */
    public String getColumnValue() {
        return columnValue;
    }

    /**
     * @param columnValue the columnValue to set
     */
    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(int processId) {
        this.processId = processId;
    }
}