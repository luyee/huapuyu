package builder;

public class Director
{
	private Builder builder;

	public Director(Builder builder)
	{
		this.builder = builder;
	}

	public void construct(String from, String to)
	{
		builder.发件人(from);
		builder.收件人(to);
		builder.主题();
		builder.内容();
	}
}
