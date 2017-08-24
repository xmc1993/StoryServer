package cn.edu.nju.software.entity;

public class TwoTuple <K,V>{
	private K id;
	private V value;
	public K getId() {
		return id;
	}
	public void setId(K id) {
		this.id = id;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
}
