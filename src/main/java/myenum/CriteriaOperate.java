package myenum;

/**
 * mongodb查询操作符
 */
public enum CriteriaOperate {
    /**
     * 等于
     */
    IS("is", "等于"),
    /**
     * 大于
     */
    GT("gt", "大于"),
    /**
     * 小于
     */
    LT("lt", "小于"),
    /**
     * 大于等于
     */
    GTE("gte", "大于或等于"),
    /**
     * 小于等于
     */
    LTE("lte", "小于或等于"),
    /**
     * 不等于
     */
    NE("ne", "不等于"),
    /**
     * IN
     */
    IN("in", "IN"),
    /**
     * NOT IN
     */
    NIN("nin", "NOT IN"),
    /**
     * 取模或取余数
     */
    MOD("mod", "取模或取余数"),
    /**
     * 与IN相似，但必须是集合匹配
     */
    ALL("all", "与IN相似，但必须是集合匹配"),
    /**
     * 左模糊
     */
    LLIKE("llike", "左模糊"),
    /**
     * 右模糊
     */
    RLIKE("rlike", "右模糊"),
    /**
     * 全模糊
     */
    ALLLIKE("alllike", "全模糊");

    private String code;
    private String message;


    CriteriaOperate(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 得到枚举码
     * @return 枚举码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 得到中文提示信息
     * @return 提示信息
     */
    public String getMessage() {
        return this.message;
    }
}
