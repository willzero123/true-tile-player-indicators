package com.playerindicatorstruetile;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PlayerIndicatorsTrueTilePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PlayerIndicatorsTrueTilePlugin.class);
		RuneLite.main(args);
	}
}