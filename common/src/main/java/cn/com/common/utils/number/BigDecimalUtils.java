package cn.com.common.utils.number;

import cn.com.common.handle.exception.BusinessException;
import cn.com.common.model.enums.NumberFormatEnum;
import cn.com.common.utils.nulls.ObjectUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @program: cloud-terrace
 * @description: BigDecimal工具类
 * @author: gm.Zhang
 * @create: 2020-06-28 23:24
 **/
@Slf4j
public class BigDecimalUtils {


    /**
     * 用于高精确处理常用的数学运算
     */
    public static class ArithmeticUtils {
        //默认除法运算精度
        private static final int DEF_DIV_SCALE = 10;

        /**
         * 提供精确的加法运算
         *
         * @param v1 被加数
         * @param v2 加数
         * @return 两个参数的和
         */

        public static double add(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.add(b2).doubleValue();
        }

        /**
         * 提供精确的加法运算
         *
         * @param v1 被加数
         * @param v2 加数
         * @return 两个参数的和
         */
        public static BigDecimal add(String v1, String v2) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2);
        }

        /**
         * 提供精确的加法运算
         *
         * @param v1    被加数
         * @param v2    加数
         * @param scale 保留scale 位小数
         * @return 两个参数的和
         */
        public static String add(String v1, String v2, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 提供精确的减法运算
         *
         * @param v1 被减数
         * @param v2 减数
         * @return 两个参数的差
         */
        public static double sub(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.subtract(b2).doubleValue();
        }

        /**
         * 提供精确的减法运算。
         *
         * @param v1 被减数
         * @param v2 减数
         * @return 两个参数的差
         */
        public static BigDecimal sub(String v1, String v2) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2);
        }

        /**
         * 提供精确的减法运算
         *
         * @param v1    被减数
         * @param v2    减数
         * @param scale 保留scale 位小数
         * @return 两个参数的差
         */
        public static String sub(String v1, String v2, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 提供精确的乘法运算
         *
         * @param v1 被乘数
         * @param v2 乘数
         * @return 两个参数的积
         */
        public static double mul(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.multiply(b2).doubleValue();
        }

        /**
         * 提供精确的乘法运算
         *
         * @param v1 被乘数
         * @param v2 乘数
         * @return 两个参数的积
         */
        public static BigDecimal mul(String v1, String v2) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2);
        }

        /**
         * 提供精确的乘法运算
         *
         * @param v1    被乘数
         * @param v2    乘数
         * @param scale 保留scale 位小数
         * @return 两个参数的积
         */
        public static double mul(double v1, double v2, int scale) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return round(b1.multiply(b2).doubleValue(), scale);
        }

        /**
         * 提供精确的乘法运算
         *
         * @param v1    被乘数
         * @param v2    乘数
         * @param scale 保留scale 位小数
         * @return 两个参数的积
         */
        public static String mul(String v1, String v2, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
         * 小数点以后10位，以后的数字四舍五入
         *
         * @param v1 被除数
         * @param v2 除数
         * @return 两个参数的商
         */

        public static double div(double v1, double v2) {
            return div(v1, v2, DEF_DIV_SCALE);
        }

        /**
         * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
         * 定精度，以后的数字四舍五入
         *
         * @param v1    被除数
         * @param v2    除数
         * @param scale 表示表示需要精确到小数点以后几位。
         * @return 两个参数的商
         */
        public static double div(double v1, double v2, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        /**
         * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
         * 定精度，以后的数字四舍五入
         *
         * @param v1    被除数
         * @param v2    除数
         * @param scale 表示需要精确到小数点以后几位
         * @return 两个参数的商
         */
        public static String div(String v1, String v2, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v1);
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 提供精确的小数位四舍五入处理
         *
         * @param v     需要四舍五入的数字
         * @param scale 小数点后保留几位
         * @return 四舍五入后的结果
         */
        public static double round(double v, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            BigDecimal b = new BigDecimal(Double.toString(v));
            return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        /**
         * 提供精确的小数位四舍五入处理
         *
         * @param v     需要四舍五入的数字
         * @param scale 小数点后保留几位
         * @return 四舍五入后的结果
         */
        public static String round(String v, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            BigDecimal b = new BigDecimal(v);
            return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 取余数
         *
         * @param v1    被除数
         * @param v2    除数
         * @param scale 小数点后保留几位
         * @return 余数
         */
        public static String remainder(String v1, String v2, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.remainder(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
        }

        /**
         * 取余数  BigDecimal
         *
         * @param v1    被除数
         * @param v2    除数
         * @param scale 小数点后保留几位
         * @return 余数
         */
        public static BigDecimal remainder(BigDecimal v1, BigDecimal v2, int scale) {
            if (scale < 0) {
                throw new BusinessException("刻度必须是正整数或零");
            }
            return v1.remainder(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
        }

        /**
         * 比较大小
         *
         * @param v1 被比较数
         * @param v2 比较数
         * @return 如果v1 大于v2 则 返回true 否则false
         */
        public static boolean compare(String v1, String v2) {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            int bj = b1.compareTo(b2);
            boolean res;
            if (bj > 0) {
                res = true;
            } else {
                res = false;
            }
            return res;
        }
    }


    /**
     * 格式转换
     */
    public static class FormatConversion {

        /**
         * 转为货币格式
         * <p>
         * 示例:	￥15,000.48
         *
         * @param loanAmount
         */
        public static String currencyFormat(BigDecimal loanAmount, Locale locale) {
            //建立货币格式化引用
            return NumberFormat.getCurrencyInstance(locale).format(loanAmount);
        }

        /**
         * 百分比格式
         * <p>
         * 示例:	12.12%
         *
         * @param interestRate 需要处理的数据
         * @param maxDec       最大小数位数(如果位数不够,就不显示) ,会被四舍五入
         * @param minDec       最小小数位数(不够的位数用 0 补齐),会被四舍五入,
         *                     如果合在最大小数位同时存在,会以最小小数位数为准
         * @param maxInt       最大整数位数
         * @param minInt       最小整数位数,位数不够用0补齐
         * @return
         */
        public static String percentFormat(BigDecimal interestRate, Integer maxDec, Integer minDec, Integer maxInt, Integer minInt) {
            if (null != interestRate && !"".equals(interestRate)) {
                //建立百分比格式化引用
                NumberFormat percent = NumberFormat.getPercentInstance();
                if (!ObjectUtils.isEmpty(maxDec)) {
                    //最大小数位数(默认3位)
                    percent.setMaximumFractionDigits(maxDec);
                }
                if (!ObjectUtils.isEmpty(minDec)) {
                    //最小小数位数
                    percent.setMinimumFractionDigits(minDec);
                }
                if (!ObjectUtils.isEmpty(maxInt)) {
                    //最大整数位数
                    percent.setMaximumIntegerDigits(maxInt);
                }
                if (!ObjectUtils.isEmpty(minInt)) {
                    //最小整数位数
                    percent.setMinimumIntegerDigits(minInt);
                }
                return percent.format(interestRate);
            } else {
                throw new BusinessException("需要处理的数据不能为空!!");
            }
        }


        /**
         * 1: 0~1之间的BigDecimal小数，格式化后失去前面的0,则前面直接加上0。
         * 2: 传入的参数等于0，则直接返回字符串"0.00"
         * 3: 大于1的小数，直接格式化返回字符串
         *
         * @param obj 传入的小数
         * @return
         */
        public static String formatToNumber(BigDecimal obj) {
            DecimalFormat df = new DecimalFormat("#.00");
            if (obj.compareTo(BigDecimal.ZERO) == 0) {
                return "0.00";
            } else if (obj.compareTo(BigDecimal.ZERO) > 0 && obj.compareTo(new BigDecimal(1)) < 0) {
                return "0" + df.format(obj).toString();
            } else {
                return df.format(obj).toString();
            }
        }


        /**
         * 数据自定义格式化
         *
         * @param num 传入的小数
         * @return
         */
        public static String format(BigDecimal num, NumberFormatEnum numberFormatEnum) {
            DecimalFormat format1 = new DecimalFormat(numberFormatEnum.getFormat());
            return format1.format(num);
        }


    }


}
