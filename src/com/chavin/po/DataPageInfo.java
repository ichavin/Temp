package com.chavin.po;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DataPageInfo<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	// 总记录数
	private long total;
	// 结果集
	private List<T> rows;

	public DataPageInfo() {

	}

	/**
	 * 包装Page对象
	 *
	 * @param list
	 *            page结果
	 * @param navigatePages
	 *            页码数量
	 */
	public DataPageInfo(List<T> list) {
		if (list instanceof Page) {
			Page page = (Page) list;
			this.total = page.getTotal();
			this.rows = page;
		}
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "DataPageInfo [total=" + total + ", rows=" + rows + "]";
	}

}
