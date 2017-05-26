package cn.edu.nju.software.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Business implements Serializable {
	private static final long serialVersionUID = -4166473876462325421L;
    private String wxAppId;
    private String wxSecret;

}
