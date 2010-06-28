package struts.one.form;

import org.apache.struts.validator.ValidatorForm;

/**
 * ValidatorForm是ActionFrom的子类 方便在于可以直接使用xml来验证表单。
 * 
 * @author Administrator
 * 
 */
public class LearnValidatorForm extends ValidatorForm
{
	private String name;
	private String passWord;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassWord()
	{
		return passWord;
	}

	public void setPassWord(String passWord)
	{
		this.passWord = passWord;
	}

}
