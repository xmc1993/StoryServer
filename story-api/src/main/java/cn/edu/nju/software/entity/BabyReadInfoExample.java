package cn.edu.nju.software.entity;

import java.util.ArrayList;
import java.util.List;

public class BabyReadInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BabyReadInfoExample() {
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

        public Criteria andStoryidIsNull() {
            addCriterion("storyId is null");
            return (Criteria) this;
        }

        public Criteria andStoryidIsNotNull() {
            addCriterion("storyId is not null");
            return (Criteria) this;
        }

        public Criteria andStoryidEqualTo(Integer value) {
            addCriterion("storyId =", value, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidNotEqualTo(Integer value) {
            addCriterion("storyId <>", value, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidGreaterThan(Integer value) {
            addCriterion("storyId >", value, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidGreaterThanOrEqualTo(Integer value) {
            addCriterion("storyId >=", value, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidLessThan(Integer value) {
            addCriterion("storyId <", value, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidLessThanOrEqualTo(Integer value) {
            addCriterion("storyId <=", value, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidIn(List<Integer> values) {
            addCriterion("storyId in", values, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidNotIn(List<Integer> values) {
            addCriterion("storyId not in", values, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidBetween(Integer value1, Integer value2) {
            addCriterion("storyId between", value1, value2, "storyid");
            return (Criteria) this;
        }

        public Criteria andStoryidNotBetween(Integer value1, Integer value2) {
            addCriterion("storyId not between", value1, value2, "storyid");
            return (Criteria) this;
        }

        public Criteria andIocnurlIsNull() {
            addCriterion("iocnUrl is null");
            return (Criteria) this;
        }

        public Criteria andIocnurlIsNotNull() {
            addCriterion("iocnUrl is not null");
            return (Criteria) this;
        }

        public Criteria andIocnurlEqualTo(String value) {
            addCriterion("iocnUrl =", value, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlNotEqualTo(String value) {
            addCriterion("iocnUrl <>", value, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlGreaterThan(String value) {
            addCriterion("iocnUrl >", value, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlGreaterThanOrEqualTo(String value) {
            addCriterion("iocnUrl >=", value, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlLessThan(String value) {
            addCriterion("iocnUrl <", value, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlLessThanOrEqualTo(String value) {
            addCriterion("iocnUrl <=", value, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlLike(String value) {
            addCriterion("iocnUrl like", value, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlNotLike(String value) {
            addCriterion("iocnUrl not like", value, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlIn(List<String> values) {
            addCriterion("iocnUrl in", values, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlNotIn(List<String> values) {
            addCriterion("iocnUrl not in", values, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlBetween(String value1, String value2) {
            addCriterion("iocnUrl between", value1, value2, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andIocnurlNotBetween(String value1, String value2) {
            addCriterion("iocnUrl not between", value1, value2, "iocnurl");
            return (Criteria) this;
        }

        public Criteria andValidIsNull() {
            addCriterion("valid is null");
            return (Criteria) this;
        }

        public Criteria andValidIsNotNull() {
            addCriterion("valid is not null");
            return (Criteria) this;
        }

        public Criteria andValidEqualTo(Integer value) {
            addCriterion("valid =", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotEqualTo(Integer value) {
            addCriterion("valid <>", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThan(Integer value) {
            addCriterion("valid >", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid >=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThan(Integer value) {
            addCriterion("valid <", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThanOrEqualTo(Integer value) {
            addCriterion("valid <=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidIn(List<Integer> values) {
            addCriterion("valid in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotIn(List<Integer> values) {
            addCriterion("valid not in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidBetween(Integer value1, Integer value2) {
            addCriterion("valid between", value1, value2, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotBetween(Integer value1, Integer value2) {
            addCriterion("valid not between", value1, value2, "valid");
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