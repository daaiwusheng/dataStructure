package com.wy.testCases;

public class Assert {
	public static void test(boolean judge) {
		try {
			if (!judge) {
				throw new Exception("有bug哦");
			}
			else {
				System.out.println("测试通过");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void testAndLogMethodName(String methodName,boolean judge) {
		try {
			if (!judge) {
				throw new Exception("有bug哦");
			}
			else {
				System.out.println(methodName + " : 测试通过");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
