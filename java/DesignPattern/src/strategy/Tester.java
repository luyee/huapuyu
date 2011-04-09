package strategy;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class Tester
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		new Context(new ConcreteStrategy1()).doSomething();
		new Context(new ConcreteStrategy2()).doSomething();

		// Spring中的策略模式
		// ValidationUtils相当于Context
		// Validator相当于Strategy
		// UserValidator相当于ConcreteStrategy
		ValidationUtils.invokeValidator(new UserValidator(), null, new UserErrors());
	}
}

class UserValidator implements Validator
{

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

class UserErrors implements Errors
{

	@Override
	public String getObjectName()
	{
		return null;
	}

	@Override
	public void setNestedPath(String nestedPath)
	{
	}

	@Override
	public String getNestedPath()
	{
		return null;
	}

	@Override
	public void pushNestedPath(String subPath)
	{
	}

	@Override
	public void popNestedPath() throws IllegalStateException
	{
	}

	@Override
	public void reject(String errorCode)
	{
	}

	@Override
	public void reject(String errorCode, String defaultMessage)
	{
	}

	@Override
	public void reject(String errorCode, Object[] errorArgs, String defaultMessage)
	{
	}

	@Override
	public void rejectValue(String field, String errorCode)
	{
	}

	@Override
	public void rejectValue(String field, String errorCode, String defaultMessage)
	{
	}

	@Override
	public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage)
	{
	}

	@Override
	public void addAllErrors(Errors errors)
	{
	}

	@Override
	public boolean hasErrors()
	{
		return false;
	}

	@Override
	public int getErrorCount()
	{
		return 0;
	}

	@Override
	public List getAllErrors()
	{
		return null;
	}

	@Override
	public boolean hasGlobalErrors()
	{
		return false;
	}

	@Override
	public int getGlobalErrorCount()
	{
		return 0;
	}

	@Override
	public List getGlobalErrors()
	{
		return null;
	}

	@Override
	public ObjectError getGlobalError()
	{
		return null;
	}

	@Override
	public boolean hasFieldErrors()
	{
		return false;
	}

	@Override
	public int getFieldErrorCount()
	{
		return 0;
	}

	@Override
	public List getFieldErrors()
	{
		return null;
	}

	@Override
	public FieldError getFieldError()
	{
		return null;
	}

	@Override
	public boolean hasFieldErrors(String field)
	{
		return false;
	}

	@Override
	public int getFieldErrorCount(String field)
	{
		return 0;
	}

	@Override
	public List getFieldErrors(String field)
	{
		return null;
	}

	@Override
	public FieldError getFieldError(String field)
	{
		return null;
	}

	@Override
	public Object getFieldValue(String field)
	{
		return null;
	}

	@Override
	public Class getFieldType(String field)
	{
		return null;
	}

}
