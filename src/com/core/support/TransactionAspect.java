package com.core.support;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

/*@Aspect*/
public class TransactionAspect {

	// 声明前置通知
	public void doBefore() {
		System.out.println("前置通知");
	}

	// 声明后置通知
	public void doAfterReturning(String result) {
		System.out.println("后置通知");
		System.out.println("---" + result + "---");
	}

	// 声明例外通知
	public void doAfterThrowing(Exception ex) {
		System.out.println("例外通知");
		System.out.println(ex.getMessage());
	}

	// 声明最终通知
	public void doAfter() {
		System.out.println("最终通知");
	}

	// 声明环绕通知
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("进入方法---环绕通知");
		Object o = pjp.proceed();
		System.out.println("退出方法---环绕通知");
		return o;
	}

}
