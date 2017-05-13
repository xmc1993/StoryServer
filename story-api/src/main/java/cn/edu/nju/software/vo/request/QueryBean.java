package cn.edu.nju.software.vo.request;

import java.io.Serializable;

public class QueryBean implements Serializable{

	private static final long serialVersionUID = 3362680102032850181L;
	private Integer offset = 0;//limit 开始数
    private Integer num = 10;// Size
    private Integer pageSize;
    private Integer page;
    
    public QueryBean() {
		super();
	}

	public QueryBean(Integer pageSize, Integer page) {
		super();
		this.pageSize = pageSize;
		this.page = page;
	}


	public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    public void setNum(Object pageSize){
        try{
            if(pageSize==null || pageSize.toString().equals("0")){
                this.num=10;
                this.pageSize=10;
            }else {
                this.num = Integer.valueOf(pageSize.toString());
                this.pageSize = Integer.valueOf(pageSize.toString());
            }
        }catch (Exception e){
            this.num=10;
            this.pageSize=10;
        }

    }

    public void setOffset(Object page){
        try{
            if(page==null || page.toString().toString().equals("0")){
                this.offset=0;
                this.page=0;
            }else{
                this.offset = (Integer.valueOf(page.toString())-1)*this.num;
                this.page = Integer.valueOf(page.toString());
            }

        }catch (Exception e){
            this.offset=0;
            this.page=0;
        }
    }
}
