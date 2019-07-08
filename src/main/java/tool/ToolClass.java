package tool;

import model.CriterialFilter;
import myenum.CriteriaOperate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.regex.Pattern;

/**
 * Mongodb所涉及到的工具类
 *
 * @author 梁昊
 * @version 1.0
 * @Date:2019-07-04
 */
public class ToolClass {
    /**
     * 指定属性列表，在类中，是否全部存在
     *
     * @param fieldNameList 属性列表
     * @param tClass        类名
     * @param <T>           泛型
     * @return 是否全部存在
     */
    public static <T> boolean fieldIsHave(List<String> fieldNameList, Class<T> tClass) {
        boolean finder = true;
        if (fieldNameList == null || fieldNameList.isEmpty()) {
            return false;
        }
        for (String fieldName :
                fieldNameList) {
            finder = finder && fieldIsHave(fieldName, tClass);
        }
        return finder;
    }

    /**
     * 指定属性，在类中，是否存在
     *
     * @param fieldName 属性名乐
     * @param tClass    类名
     * @param <T>       泛型
     * @return 是否存在
     */
    public static <T> boolean fieldIsHave(String fieldName, Class<T> tClass) {
        boolean finder = false;
        Field[] declaredFields = tClass.getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            int index = 0;
            while (!finder) {
                if (declaredFields[index].getName().equals(fieldName)) {
                    finder = true;
                }
                index++;
            }
        }
        return finder;
    }

    /**
     * 得到解析条件构造器
     *
     * @param criterialFilterList 条件构造器
     * @return Mongodb指定的Criteria对象
     * @throws ParseException ISODATE日期转换异常
     */
    public static Criteria getCriteria(List<CriterialFilter> criterialFilterList) throws ParseException {
        Criteria criteria = null;
        if (criterialFilterList != null && criterialFilterList.size() > 0) {
            int indexCount = 0;
            for (CriterialFilter criterialFilter : criterialFilterList
                    ) {
                CriteriaOperate criteriaOperate = criterialFilter.getCriteriaOperate();
                String fieldName = criterialFilter.getFieldName();
                Object fieldValue = criterialFilter.getFieldValue();
                Object fieldValueEnd = criterialFilter.getFieldValueEnd();
                String javaType = criterialFilter.getJavaType();
                if (javaType != null) {
                    if (javaType.equals("class java.util.Date")) {
                        if (fieldValue != null) {
                            fieldValue = ToolClass.dateToISODate(fieldValue.toString());
                        }
                        if (fieldValueEnd != null) {
                            fieldValueEnd = ToolClass.dateToISODate(fieldValueEnd.toString());
                        }
                    }
                }
                boolean firstSign = indexCount > 0 ? false : true;
                if (criteriaOperate == CriteriaOperate.MOD) {
                    if (fieldValue != null && fieldValueEnd != null) {
                        if (Math.abs((Integer) fieldValue) > 0) {
                            if (firstSign) {
                                criteria = criteria.where(fieldName);
                            } else
                                criteria = criteria.and(fieldName);
                        }
                    }
                } else {
                    if (firstSign) {
                        criteria = criteria.where(fieldName);
                    } else
                        criteria = criteria.and(fieldName);
                }
                switch (criteriaOperate) {
                    case IS:
                        criteria = criteria.is(fieldValue);
                        break;
                    case GT:
                        criteria = criteria.gt(fieldValue);
                        break;
                    case LT:
                        criteria = criteria.lt(fieldValue);
                        break;
                    case GTE:
                        criteria = criteria.gte(fieldValue);
                        break;
                    case LTE:
                        criteria = criteria.lte(fieldValue);
                        break;
                    case NE:
                        criteria = criteria.ne(fieldValue);
                        break;
                    case IN:
                        criteria = criteria.in(fieldValue);
                        break;
                    case NIN:
                        criteria = criteria.nin(fieldValue);
                        break;
                    case MOD:
                        if (fieldValue != null && fieldValueEnd != null) {
                            if (Math.abs((Integer) fieldValue) > 0) {
                                Number value = Integer.valueOf(fieldValue.toString());
                                Number remainder = Integer.valueOf(fieldValueEnd.toString());
                                criteria = criteria.mod(value, remainder);
                            }
                        }
                        break;
                    case ALL:
                        criteria = criteria.all(fieldValue);
                        break;
                    case LLIKE:
//                      左模糊
                        criteria = criteria.regex(
                                Pattern.compile(String.format("^.*%s$", fieldValue)
                                        , Pattern.CASE_INSENSITIVE));
                        break;
                    case RLIKE:
//                      右模糊
                        criteria = criteria.regex(
                                Pattern.compile(String.format("^%s.*$", fieldValue)
                                        , Pattern.CASE_INSENSITIVE));
                        break;
                    case ALLLIKE:
//                      全模糊匹配
                        criteria = criteria.regex(
                                Pattern.compile(String.format("^.*%s.*$", fieldValue)
                                        , Pattern.CASE_INSENSITIVE));
                        break;
                    default:
                        break;
                }
                indexCount++;
            }
        }
        return criteria;
    }

    /**
     * 将java的Date类型转成mongodb中的ISODate类型
     *
     * @param dateStr 字符串类型的日期，格式：yyyy-MM-dd HH:mm:ss
     * @return mongodb中的ISODate类型
     * @throws ParseException
     */
    public static Date dateToISODate(String dateStr) throws ParseException {
        //T代表后面跟着时间，Z代表UTC统一时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = simpleDateFormat.parse(dateStr);
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
        String isoDate = format.format(date);
        try {
            return format.parse(isoDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
