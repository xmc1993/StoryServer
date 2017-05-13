package cn.edu.nju.software.vo.response;

import java.io.Serializable;
import java.util.List;


public class PageResponseBean<T> implements Serializable {

  private static final long serialVersionUID = 6887389993060457824L;
  /**
   * 返回数据内容
   */
  private List<T> content;

  public Object getContentObj() {
    return contentObj;
  }

  public void setContentObj(Object contentObj) {
    this.contentObj = contentObj;
  }

  /**
   * 其他数据
   */
  private Object contentObj;
  /**
   * 总条目数
   */
  private int totalElements;
  /**
   * 总页数
   */
  private int totalPages;
  /**
   * 是否是最后一页
   */
  private boolean last;
  /**
   * 当前页
   */
  private int number;
  /**
   * 当前页的条目数
   */
  private int size;
  private int numberOfElements;
  /**
   * 是否是第一页
   */
  private boolean first;

  public PageResponseBean() {
  }
  
  

  public PageResponseBean(PageResponseBean<?> pageEntity) {
	super();
	this.contentObj = pageEntity.getContentObj();
	this.totalElements = pageEntity.getTotalElements();
	this.totalPages = pageEntity.getTotalPages();
	this.last = pageEntity.isLast();
	this.number = pageEntity.getNumber();
	this.size = pageEntity.getSize();
	this.numberOfElements = pageEntity.getNumberOfElements();
	this.first = pageEntity.isFirst();
}

public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(int totalElements) {
    this.totalElements = totalElements;
  }

  public boolean isLast() {
    return last;
  }

  public void setLast(boolean last) {
    this.last = last;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getNumberOfElements() {
    return numberOfElements;
  }

  public void setNumberOfElements(int numberOfElements) {
    this.numberOfElements = numberOfElements;
  }

  public boolean isFirst() {
    return first;
  }

  public void setFirst(boolean first) {
    this.first = first;
  }
}
