package cn.edu.nju.software.vo;

import java.util.List;

import com.wordnik.swagger.annotations.Api;

/**
 * Created by xmc1993 on 16/12/21.
 */
@Api("")
public class ExamineRes {
    private ExamineInfo examineInfo;
    private List<QuestionInfo> questions;

    public ExamineInfo getExamineInfo() {
        return examineInfo;
    }

    public void setExamineInfo(ExamineInfo examineInfo) {
        this.examineInfo = examineInfo;
    }

    public List<QuestionInfo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionInfo> questions) {
        this.questions = questions;
    }

    @Api("")
    public static class ExamineInfo{
        private Integer id;
        private String title;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @Api("")
    public static class QuestionInfo{
        private String qType;
        private String content;
        private List<String> qItems;
        private List<Integer> correctAnswer;
        private List<Integer> qUserOption;
        private String qAccuracy;
        private String quesExplain;

        public String getqType() {
            return qType;
        }

        public void setqType(String qType) {
            this.qType = qType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getqItems() {
            return qItems;
        }

        public void setqItems(List<String> qItems) {
            this.qItems = qItems;
        }

        public List<Integer> getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(List<Integer> correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public List<Integer> getqUserOption() {
            return qUserOption;
        }

        public void setqUserOption(List<Integer> qUserOption) {
            this.qUserOption = qUserOption;
        }

        public String getqAccuracy() {
            return qAccuracy;
        }

        public void setqAccuracy(String qAccuracy) {
            this.qAccuracy = qAccuracy;
        }

        public String getQuesExplain() {
            return quesExplain;
        }

        public void setQuesExplain(String quesExplain) {
            this.quesExplain = quesExplain;
        }
    }
}
