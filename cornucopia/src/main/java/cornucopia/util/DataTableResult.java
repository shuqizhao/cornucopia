package cornucopia.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataTableResult<T> {
	
	public DataTableResult() {
		
	}
	
	public DataTableResult(int echo, int totalRecords, int totalDisplayRecords, List<T> data) {
		this.echo = echo;
		this.totalRecords = totalRecords;
		this.totalDisplayRecords = totalDisplayRecords;
		this.data = data;
	}

	/// DataTable请求服务器端次数
	@JsonProperty(value = "sEcho")
	private int echo;
	@JsonProperty(value = "iTotalRecords")
	private int totalRecords;
	@JsonProperty(value = "iTotalDisplayRecords")
	private int totalDisplayRecords;
	@JsonProperty(value = "aaData")
	private List<T> data;

	public int getEcho() {
		return echo;
	}

	public void setEcho(int echo) {
		this.echo = echo;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalDisplayRecords() {
		return totalDisplayRecords;
	}

	public void setTotalDisplayRecords(int totalDisplayRecords) {
		this.totalDisplayRecords = totalDisplayRecords;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
