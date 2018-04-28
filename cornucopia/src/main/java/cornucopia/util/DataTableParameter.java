package cornucopia.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataTableParameter {
	/// DataTable请求服务器端次数
	@JsonProperty(value = "sEcho")
	private int sEcho;
	/// 过滤文本
	@JsonProperty(value = "sSearch")
	private String sSearch;
	/// 每页显示的数量
	@JsonProperty(value = "iDisplayLength")
	private int iDisplayLength;
	/// 分页时每页跨度数量
	@JsonProperty(value = "iDisplayStart")
	private int iDisplayStart;
	/// 列数
	@JsonProperty(value = "iColumns")
	private int iColumns;
	/// 排序列的数量
	@JsonProperty(value = "iSortingCols")
	private int iSortingCols;
	/// 逗号分割所有的列
	@JsonProperty(value = "sColumns")
	private String sColumns;
	/// 排序的列
	@JsonProperty(value = "iSortCol_0")
	private String iSortCol_0;
	/// 排序的状态
	@JsonProperty(value = "sSortDir_0")
	private String sSortDir_0;

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getiColumns() {
		return iColumns;
	}

	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}

	public int getiSortingCols() {
		return iSortingCols;
	}

	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	public String getsColumns() {
		return sColumns;
	}

	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

	public String getiSortCol_0() {
		return iSortCol_0;
	}

	public void setiSortCol_0(String iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	public String getsSortDir_0() {
		return sSortDir_0;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}
}
