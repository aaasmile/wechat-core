package com.d1m.wechat.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

public class CouponUtil {
	/**
	 * 活动编号 去掉前2位(例如20160621-006)去除为160621006
	 * 
	 * @param gnno
	 * @return
	 */
	public static String generateCode(String gnno) {
		String code = null;
		if (StringUtils.isNotBlank(gnno)) {
			gnno = StringUtils.substring(gnno, 2);
			gnno = gnno.replace("-", "");
			code = gnno + RandomStringUtils.randomNumeric(7);
			int checkCode = getCheckCode(code);
			if (checkCode < 0) {
				code = null;
			} else {
				code = code + checkCode;
			}
		}
		return code;
	}

	private static int getCheckCode(String code) {
		int checkCode = -1;
		if (StringUtils.isBlank(code)) {
			return -2;
		} else if (!Pattern.compile("^[0-9]*$").matcher(code).matches()) {
			checkCode = -1;
		} else {
			try {
				int evensum = 0; // 偶数位的和
				int oddsum = 0; // 奇数位的和
				evensum += Integer.parseInt(code.substring(15, 16));
				evensum += Integer.parseInt(code.substring(13, 14));
				evensum += Integer.parseInt(code.substring(11, 12));
				evensum += Integer.parseInt(code.substring(9, 10));
				evensum += Integer.parseInt(code.substring(7, 8));
				evensum += Integer.parseInt(code.substring(5, 6));
				evensum += Integer.parseInt(code.substring(3, 4));
				evensum += Integer.parseInt(code.substring(1, 2));
				evensum *= 3;
				oddsum += Integer.parseInt(code.substring(14, 15));
				oddsum += Integer.parseInt(code.substring(12, 13));
				oddsum += Integer.parseInt(code.substring(10, 11));
				oddsum += Integer.parseInt(code.substring(8, 9));
				oddsum += Integer.parseInt(code.substring(6, 7));
				oddsum += Integer.parseInt(code.substring(4, 5));
				oddsum += Integer.parseInt(code.substring(2, 3));
				oddsum += Integer.parseInt(code.substring(0, 1));
				int sum = evensum + oddsum;
				int ck = 0;
				if (sum % 10 == 0) {
					ck = sum;
				} else {
					ck = (sum / 10 + 1) * 10;
				}
				checkCode = ck - sum;
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return checkCode;
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int k = 0;
		for (int i = 0; i < 10000000; i++) {
			String code = CouponUtil.generateCode("160621006");
			System.out.println("CODE:" + code);
			if (!list.contains(code)) {
				list.add(code);
			} else {
				k++;
				System.out.println("重复CODE:" + code);
			}
		}
		System.out.println("生成完成,重复次数：" + k);
	}
}
