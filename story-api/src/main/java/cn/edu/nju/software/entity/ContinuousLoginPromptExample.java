package cn.edu.nju.software.entity;

import java.util.ArrayList;
import java.util.List;

public class ContinuousLoginPromptExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ContinuousLoginPromptExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPromptIsNull() {
            addCriterion("prompt is null");
            return (Criteria) this;
        }

        public Criteria andPromptIsNotNull() {
            addCriterion("prompt is not null");
            return (Criteria) this;
        }

        public Criteria andPromptEqualTo(String value) {
            addCriterion("prompt =", value, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptNotEqualTo(String value) {
            addCriterion("prompt <>", value, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptGreaterThan(String value) {
            addCriterion("prompt >", value, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptGreaterThanOrEqualTo(String value) {
            addCriterion("prompt >=", value, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptLessThan(String value) {
            addCriterion("prompt <", value, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptLessThanOrEqualTo(String value) {
            addCriterion("prompt <=", value, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptLike(String value) {
            addCriterion("prompt like", value, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptNotLike(String value) {
            addCriterion("prompt not like", value, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptIn(List<String> values) {
            addCriterion("prompt in", values, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptNotIn(List<String> values) {
            addCriterion("prompt not in", values, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptBetween(String value1, String value2) {
            addCriterion("prompt between", value1, value2, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptNotBetween(String value1, String value2) {
            addCriterion("prompt not between", value1, value2, "prompt");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeIsNull() {
            addCriterion("prompt_start_time is null");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeIsNotNull() {
            addCriterion("prompt_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeEqualTo(Integer value) {
            addCriterion("prompt_start_time =", value, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeNotEqualTo(Integer value) {
            addCriterion("prompt_start_time <>", value, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeGreaterThan(Integer value) {
            addCriterion("prompt_start_time >", value, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("prompt_start_time >=", value, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeLessThan(Integer value) {
            addCriterion("prompt_start_time <", value, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeLessThanOrEqualTo(Integer value) {
            addCriterion("prompt_start_time <=", value, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeIn(List<Integer> values) {
            addCriterion("prompt_start_time in", values, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeNotIn(List<Integer> values) {
            addCriterion("prompt_start_time not in", values, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeBetween(Integer value1, Integer value2) {
            addCriterion("prompt_start_time between", value1, value2, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptStartTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("prompt_start_time not between", value1, value2, "promptStartTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeIsNull() {
            addCriterion("prompt_end_time is null");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeIsNotNull() {
            addCriterion("prompt_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeEqualTo(Integer value) {
            addCriterion("prompt_end_time =", value, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeNotEqualTo(Integer value) {
            addCriterion("prompt_end_time <>", value, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeGreaterThan(Integer value) {
            addCriterion("prompt_end_time >", value, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("prompt_end_time >=", value, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeLessThan(Integer value) {
            addCriterion("prompt_end_time <", value, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeLessThanOrEqualTo(Integer value) {
            addCriterion("prompt_end_time <=", value, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeIn(List<Integer> values) {
            addCriterion("prompt_end_time in", values, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeNotIn(List<Integer> values) {
            addCriterion("prompt_end_time not in", values, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeBetween(Integer value1, Integer value2) {
            addCriterion("prompt_end_time between", value1, value2, "promptEndTime");
            return (Criteria) this;
        }

        public Criteria andPromptEndTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("prompt_end_time not between", value1, value2, "promptEndTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}