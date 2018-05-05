package cornucopia.model;

import java.util.List;

public class TransferViewModel {
	private List<IdNameModel> left;
	private List<IdNameModel> right;

	public List<IdNameModel> getLeft() {
		return left;
	}

	public void setLeft(List<IdNameModel> left) {
		this.left = left;
	}

	public List<IdNameModel> getRight() {
		return right;
	}

	public void setRight(List<IdNameModel> right) {
		this.right = right;
	}
}
