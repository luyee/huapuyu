package com.anders.dp.行为模式.模板;

public class PlayJapan extends PlayPES
{
	@Override
	protected void chooseShirt()
	{
		System.out.println("客场");
	}

	@Override
	protected void chooseTeam()
	{
		System.out.println("日本");
	}
}
