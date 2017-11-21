package cn.edu.nju.software.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpinionFeedbackExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OpinionFeedbackExample() {
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

        public Criteria andModelIsNull() {
            addCriterion("model is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("model is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("model =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("model <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("model >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("model >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("model <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("model <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("model like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("model not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("model in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("model not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("model between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("model not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andAppversionIsNull() {
            addCriterion("appVersion is null");
            return (Criteria) this;
        }

        public Criteria andAppversionIsNotNull() {
            addCriterion("appVersion is not null");
            return (Criteria) this;
        }

        public Criteria andAppversionEqualTo(String value) {
            addCriterion("appVersion =", value, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionNotEqualTo(String value) {
            addCriterion("appVersion <>", value, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionGreaterThan(String value) {
            addCriterion("appVersion >", value, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionGreaterThanOrEqualTo(String value) {
            addCriterion("appVersion >=", value, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionLessThan(String value) {
            addCriterion("appVersion <", value, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionLessThanOrEqualTo(String value) {
            addCriterion("appVersion <=", value, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionLike(String value) {
            addCriterion("appVersion like", value, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionNotLike(String value) {
            addCriterion("appVersion not like", value, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionIn(List<String> values) {
            addCriterion("appVersion in", values, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionNotIn(List<String> values) {
            addCriterion("appVersion not in", values, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionBetween(String value1, String value2) {
            addCriterion("appVersion between", value1, value2, "appversion");
            return (Criteria) this;
        }

        public Criteria andAppversionNotBetween(String value1, String value2) {
            addCriterion("appVersion not between", value1, value2, "appversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionIsNull() {
            addCriterion("androidVersion is null");
            return (Criteria) this;
        }

        public Criteria andAndroidversionIsNotNull() {
            addCriterion("androidVersion is not null");
            return (Criteria) this;
        }

        public Criteria andAndroidversionEqualTo(String value) {
            addCriterion("androidVersion =", value, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionNotEqualTo(String value) {
            addCriterion("androidVersion <>", value, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionGreaterThan(String value) {
            addCriterion("androidVersion >", value, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionGreaterThanOrEqualTo(String value) {
            addCriterion("androidVersion >=", value, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionLessThan(String value) {
            addCriterion("androidVersion <", value, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionLessThanOrEqualTo(String value) {
            addCriterion("androidVersion <=", value, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionLike(String value) {
            addCriterion("androidVersion like", value, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionNotLike(String value) {
            addCriterion("androidVersion not like", value, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionIn(List<String> values) {
            addCriterion("androidVersion in", values, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionNotIn(List<String> values) {
            addCriterion("androidVersion not in", values, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionBetween(String value1, String value2) {
            addCriterion("androidVersion between", value1, value2, "androidversion");
            return (Criteria) this;
        }

        public Criteria andAndroidversionNotBetween(String value1, String value2) {
            addCriterion("androidVersion not between", value1, value2, "androidversion");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentIsNull() {
            addCriterion("networkEnvironment is null");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentIsNotNull() {
            addCriterion("networkEnvironment is not null");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentEqualTo(String value) {
            addCriterion("networkEnvironment =", value, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentNotEqualTo(String value) {
            addCriterion("networkEnvironment <>", value, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentGreaterThan(String value) {
            addCriterion("networkEnvironment >", value, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentGreaterThanOrEqualTo(String value) {
            addCriterion("networkEnvironment >=", value, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentLessThan(String value) {
            addCriterion("networkEnvironment <", value, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentLessThanOrEqualTo(String value) {
            addCriterion("networkEnvironment <=", value, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentLike(String value) {
            addCriterion("networkEnvironment like", value, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentNotLike(String value) {
            addCriterion("networkEnvironment not like", value, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentIn(List<String> values) {
            addCriterion("networkEnvironment in", values, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentNotIn(List<String> values) {
            addCriterion("networkEnvironment not in", values, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentBetween(String value1, String value2) {
            addCriterion("networkEnvironment between", value1, value2, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andNetworkenvironmentNotBetween(String value1, String value2) {
            addCriterion("networkEnvironment not between", value1, value2, "networkenvironment");
            return (Criteria) this;
        }

        public Criteria andConnectionIsNull() {
            addCriterion("connection is null");
            return (Criteria) this;
        }

        public Criteria andConnectionIsNotNull() {
            addCriterion("connection is not null");
            return (Criteria) this;
        }

        public Criteria andConnectionEqualTo(String value) {
            addCriterion("connection =", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionNotEqualTo(String value) {
            addCriterion("connection <>", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionGreaterThan(String value) {
            addCriterion("connection >", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionGreaterThanOrEqualTo(String value) {
            addCriterion("connection >=", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionLessThan(String value) {
            addCriterion("connection <", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionLessThanOrEqualTo(String value) {
            addCriterion("connection <=", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionLike(String value) {
            addCriterion("connection like", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionNotLike(String value) {
            addCriterion("connection not like", value, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionIn(List<String> values) {
            addCriterion("connection in", values, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionNotIn(List<String> values) {
            addCriterion("connection not in", values, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionBetween(String value1, String value2) {
            addCriterion("connection between", value1, value2, "connection");
            return (Criteria) this;
        }

        public Criteria andConnectionNotBetween(String value1, String value2) {
            addCriterion("connection not between", value1, value2, "connection");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userId is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userId is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userId =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userId <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userId >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userId >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userId <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userId <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userId in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userId not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userId between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userId not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andPicurlsIsNull() {
            addCriterion("picUrls is null");
            return (Criteria) this;
        }

        public Criteria andPicurlsIsNotNull() {
            addCriterion("picUrls is not null");
            return (Criteria) this;
        }

        public Criteria andPicurlsEqualTo(String value) {
            addCriterion("picUrls =", value, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsNotEqualTo(String value) {
            addCriterion("picUrls <>", value, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsGreaterThan(String value) {
            addCriterion("picUrls >", value, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsGreaterThanOrEqualTo(String value) {
            addCriterion("picUrls >=", value, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsLessThan(String value) {
            addCriterion("picUrls <", value, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsLessThanOrEqualTo(String value) {
            addCriterion("picUrls <=", value, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsLike(String value) {
            addCriterion("picUrls like", value, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsNotLike(String value) {
            addCriterion("picUrls not like", value, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsIn(List<String> values) {
            addCriterion("picUrls in", values, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsNotIn(List<String> values) {
            addCriterion("picUrls not in", values, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsBetween(String value1, String value2) {
            addCriterion("picUrls between", value1, value2, "picurls");
            return (Criteria) this;
        }

        public Criteria andPicurlsNotBetween(String value1, String value2) {
            addCriterion("picUrls not between", value1, value2, "picurls");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeIsNull() {
            addCriterion("opinionType is null");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeIsNotNull() {
            addCriterion("opinionType is not null");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeEqualTo(String value) {
            addCriterion("opinionType =", value, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeNotEqualTo(String value) {
            addCriterion("opinionType <>", value, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeGreaterThan(String value) {
            addCriterion("opinionType >", value, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeGreaterThanOrEqualTo(String value) {
            addCriterion("opinionType >=", value, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeLessThan(String value) {
            addCriterion("opinionType <", value, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeLessThanOrEqualTo(String value) {
            addCriterion("opinionType <=", value, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeLike(String value) {
            addCriterion("opinionType like", value, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeNotLike(String value) {
            addCriterion("opinionType not like", value, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeIn(List<String> values) {
            addCriterion("opinionType in", values, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeNotIn(List<String> values) {
            addCriterion("opinionType not in", values, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeBetween(String value1, String value2) {
            addCriterion("opinionType between", value1, value2, "opiniontype");
            return (Criteria) this;
        }

        public Criteria andOpiniontypeNotBetween(String value1, String value2) {
            addCriterion("opinionType not between", value1, value2, "opiniontype");
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