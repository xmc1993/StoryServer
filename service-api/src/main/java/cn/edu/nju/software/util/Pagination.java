package cn.edu.nju.software.util;

/**
 * 分页信息
 * 
 * @author cuiyan
 * 
 */
public class Pagination {

  public static final Integer DEFAULT_SIZE = 10; // 默认显示10条

  private Long totalCount; // 总数
  private Integer totalPage; // 总页数
  private Integer pageSize; // 每页大小
  private Integer currPage; // 当前页
  private String sort; // 排序字段
  private Boolean next; // 是否有下一页

  /**
   * 默认构造器
   */
  public Pagination() {}


  public Pagination(Long totalCount, Integer currPage) {
    this(totalCount, currPage, null);
  }

  public Pagination(Long totalCount, Integer currPage, String sort) {
    this(totalCount, currPage, DEFAULT_SIZE, sort);
  }

  public Pagination(Long totalCount, Integer currPage,Integer pageSize, String sort) {
    this.totalCount = totalCount;
    this.currPage = currPage;
    this.sort = sort;
    this.pageSize = pageSize;
  }

  /**
   * 全值构造函数.
   */
  public Pagination(Long totalCount, Integer totalPage, Integer pageSize, Integer currPage,
      String sort, boolean next) {
    super();
    this.totalCount = totalCount;
    this.totalPage = totalPage;
    this.pageSize = pageSize;
    this.currPage = currPage;
    this.sort = sort;
    this.next = next;
  }

  public Long getTotalCount() {
    if (totalCount == null) {
      throw new RuntimeException("分页的记录总数不能为null");
    }
    return totalCount;
  }

  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getPageSize() {
    if (pageSize == null) {
      return DEFAULT_SIZE;
    }
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCurrPage() {
    if (currPage == null) {
      return 1;
    }
    return currPage;
  }

  public void setCurrPage(Integer currPage) {
    this.currPage = currPage;
  }

  public String getSort() {
    return sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public Integer getTotalPage() {
    if (totalPage == null){
      Integer pageCount = (int) (getTotalCount() / getPageSize());
      Integer mod = (int) (getTotalCount() % getPageSize());
      if (pageCount <= 0) {
        totalPage = 1;
      } else {
        if (mod > 0) {
          totalPage = pageCount + 1;
        }else{
          totalPage = pageCount;
        }
      }
    }
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public boolean isNext() {
    if (next == null){
      if (getCurrPage() * getPageSize() < getTotalCount()) {
        next = true;
      } else {
        next = false;
      }
    }
    return next;
  }

  public void setNext(boolean next) {
    this.next = next;
  }
}
