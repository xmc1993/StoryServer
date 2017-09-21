package cn.edu.nju.software.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadingPlanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ReadingPlanExample() {
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

        public Criteria andAgegroupIsNull() {
            addCriterion("ageGroup is null");
            return (Criteria) this;
        }

        public Criteria andAgegroupIsNotNull() {
            addCriterion("ageGroup is not null");
            return (Criteria) this;
        }

        public Criteria andAgegroupEqualTo(String value) {
            addCriterion("ageGroup =", value, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupNotEqualTo(String value) {
            addCriterion("ageGroup <>", value, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupGreaterThan(String value) {
            addCriterion("ageGroup >", value, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupGreaterThanOrEqualTo(String value) {
            addCriterion("ageGroup >=", value, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupLessThan(String value) {
            addCriterion("ageGroup <", value, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupLessThanOrEqualTo(String value) {
            addCriterion("ageGroup <=", value, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupLike(String value) {
            addCriterion("ageGroup like", value, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupNotLike(String value) {
            addCriterion("ageGroup not like", value, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupIn(List<String> values) {
            addCriterion("ageGroup in", values, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupNotIn(List<String> values) {
            addCriterion("ageGroup not in", values, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupBetween(String value1, String value2) {
            addCriterion("ageGroup between", value1, value2, "agegroup");
            return (Criteria) this;
        }

        public Criteria andAgegroupNotBetween(String value1, String value2) {
            addCriterion("ageGroup not between", value1, value2, "agegroup");
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

        public Criteria andTimepointIsNull() {
            addCriterion("timePoint is null");
            return (Criteria) this;
        }

        public Criteria andTimepointIsNotNull() {
            addCriterion("timePoint is not null");
            return (Criteria) this;
        }

        public Criteria andTimepointEqualTo(String value) {
            addCriterion("timePoint =", value, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointNotEqualTo(String value) {
            addCriterion("timePoint <>", value, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointGreaterThan(String value) {
            addCriterion("timePoint >", value, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointGreaterThanOrEqualTo(String value) {
            addCriterion("timePoint >=", value, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointLessThan(String value) {
            addCriterion("timePoint <", value, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointLessThanOrEqualTo(String value) {
            addCriterion("timePoint <=", value, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointLike(String value) {
            addCriterion("timePoint like", value, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointNotLike(String value) {
            addCriterion("timePoint not like", value, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointIn(List<String> values) {
            addCriterion("timePoint in", values, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointNotIn(List<String> values) {
            addCriterion("timePoint not in", values, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointBetween(String value1, String value2) {
            addCriterion("timePoint between", value1, value2, "timepoint");
            return (Criteria) this;
        }

        public Criteria andTimepointNotBetween(String value1, String value2) {
            addCriterion("timePoint not between", value1, value2, "timepoint");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updateTime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updateTime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updateTime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updateTime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updateTime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updateTime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updateTime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updateTime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updateTime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updateTime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andCoverurlIsNull() {
            addCriterion("coverUrl is null");
            return (Criteria) this;
        }

        public Criteria andCoverurlIsNotNull() {
            addCriterion("coverUrl is not null");
            return (Criteria) this;
        }

        public Criteria andCoverurlEqualTo(String value) {
            addCriterion("coverUrl =", value, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlNotEqualTo(String value) {
            addCriterion("coverUrl <>", value, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlGreaterThan(String value) {
            addCriterion("coverUrl >", value, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlGreaterThanOrEqualTo(String value) {
            addCriterion("coverUrl >=", value, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlLessThan(String value) {
            addCriterion("coverUrl <", value, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlLessThanOrEqualTo(String value) {
            addCriterion("coverUrl <=", value, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlLike(String value) {
            addCriterion("coverUrl like", value, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlNotLike(String value) {
            addCriterion("coverUrl not like", value, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlIn(List<String> values) {
            addCriterion("coverUrl in", values, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlNotIn(List<String> values) {
            addCriterion("coverUrl not in", values, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlBetween(String value1, String value2) {
            addCriterion("coverUrl between", value1, value2, "coverurl");
            return (Criteria) this;
        }

        public Criteria andCoverurlNotBetween(String value1, String value2) {
            addCriterion("coverUrl not between", value1, value2, "coverurl");
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