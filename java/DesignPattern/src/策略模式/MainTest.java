package 策略模式;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 策略模式
 * 
 * @author Anders
 * 
 */
public class MainTest
{
	public static void main(String[] args)
	{
		new Context(new ConcreteStrategy1()).doSomething();
		new Context(new ConcreteStrategy2()).doSomething();

		// Spring中的策略模式
		// ValidationUtils : Context
		// Validator : Strategy
		// UserValidator : ConcreteStrategy
		// ValidationUtils.invokeValidator(nesll, null);
	}
}

class UserValidator implements Validator
{
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz)
	{
		return false;
	}

	@Override
	public void validate(Object target, Errors errors)
	{
	}
}
