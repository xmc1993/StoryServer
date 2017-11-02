package cn.edu.nju.software.enums;

public enum ResourceType {
    ICON("icon"),//图标
    AUDIO("audio"),//音频
    VIDEO("video");//视频

    String name;

    ResourceType(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
