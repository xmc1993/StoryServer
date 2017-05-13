package cn.edu.nju.software.vo.request;

import java.io.Serializable;

/**
 * Created by fenggang on 9/29/16.
 */
public class StatQueryBean extends QueryBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2981227849911550585L;
	private Integer userId;
    private Integer videoId;
    private Integer audioId;
    private String startTime;
    private String endTime;

	private Integer businessId;
    private Integer activityId;
    private Integer articleId;
    private String title;
    private String type;
    private Integer statId;

    public StatQueryBean() {
        super();
    }
    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatId() {
		return statId;
	}

	public void setStatId(Integer statId) {
		this.statId = statId;
	}
    public Integer getAudioId() {
		return audioId;
	}
	public void setAudioId(Integer audioId) {
		this.audioId = audioId;
	}
}
