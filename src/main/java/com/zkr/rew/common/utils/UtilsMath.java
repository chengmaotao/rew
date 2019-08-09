package com.zkr.rew.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zkr.rew.csres.entity.RewcsResult;

public class UtilsMath {

	/**
	 * @param allNum
	 *            总人数
	 * @param num
	 *            投票人数
	 * @param code
	 *            1：2/3以上； 2：半数以上 都是包含
	 * @return 以上 返回true
	 */
	public static boolean isTrue(int allNum, int num, int code) {

		if (allNum < 0 || num < 0 || (code != 1 && code != 2)) {
			return false;
		}

		double tempNum1 = (double) allNum;

		int tempResult = 0;

		if (code == 1) {
			tempResult = (int) Math.ceil(tempNum1 * 2 / 3);
		} else if (code == 2) {
			tempResult = (int) Math.ceil(tempNum1 / 2);
		}

		if (num >= tempResult) {
			return true;
		}

		return false;
	}

	// 去掉 项目id 重复的
	public static List<RewcsResult> getResult(List<RewcsResult> parametes) {
		Map<String, RewcsResult> res = new HashMap<String, RewcsResult>();
		List<RewcsResult> list = new ArrayList<RewcsResult>();
		RewcsResult t = null;
		for (RewcsResult entitya : parametes) {
			t = res.get(entitya.getProjectid());
			if (t == null) {
				res.put(entitya.getProjectid(), entitya);
				list.add(entitya);
			}

		}
		return list;
	}
}
