package com.truetileplayerindicators;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TrueTilePlayerIndicatorsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(TrueTilePlayerIndicatorsPlugin.class);
		RuneLite.main(args);
	}
}