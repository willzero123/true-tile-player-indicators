/*
 * Copyright (c) 2026, willzero123 <willzerodev@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.truetileplayerindicators;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup(TrueTilePlayerIndicatorsConfig.GROUP)
public interface TrueTilePlayerIndicatorsConfig extends Config
{
	String GROUP = "trueTilePlayerIndicators";
	Color DEFAULT_FILL = new Color(0, 0, 0, 50);
	int DEFAULT_BORDER_WIDTH = 2;
	int MAX_BORDER_WIDTH = 10;

	@ConfigSection(
		name = "Global Styles",
		description = "Default fill color and border width for all true tiles",
		position = 0
	)
	String styleSection = "style";

	@Alpha
	@ConfigItem(
		keyName = "fillColor",
		name = "Fill color",
		description = "True tile fill color",
		section = styleSection,
		position = 0
	)
	default Color fillColor()
	{
		return DEFAULT_FILL;
	}

	@Range(min = 1, max = MAX_BORDER_WIDTH)
	@ConfigItem(
		keyName = "borderWidth",
		name = "Border width",
		description = "True tile border width",
		section = styleSection,
		position = 1
	)
	default int borderWidth()
	{
		return DEFAULT_BORDER_WIDTH;
	}

	@ConfigSection(
		name = "Own Player True Tile",
		description = "Own player true tile visibility and style",
		position = 1
	)
	String ownSection = "own";

	@ConfigItem(
		keyName = "ownHighlight",
		name = "True Tile highlight",
		description = "Inherit from Player Indicators, always on, or always off",
		section = ownSection,
		position = 0
	)
	default TrueTileHighlightMode ownHighlight()
	{
		return TrueTileHighlightMode.INHERIT;
	}

	@Alpha
	@ConfigItem(
		keyName = "ownHighlightColor",
		name = "Border color",
		description = "True tile border color",
		section = ownSection,
		position = 1
	)
	Color ownHighlightColor();

	@ConfigItem(
		keyName = "ownCustomize",
		name = "Customize styles",
		description = "If enabled, uses the fill color and border width below. When off, uses Global Styles",
		section = ownSection,
		position = 2
	)
	default boolean ownCustomize()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		keyName = "ownFillColor",
		name = "Fill color",
		description = "True tile fill color",
		section = ownSection,
		position = 3
	)
	default Color ownFillColor()
	{
		return DEFAULT_FILL;
	}

	@Range(min = 1, max = MAX_BORDER_WIDTH)
	@ConfigItem(
		keyName = "ownBorderWidth",
		name = "Border width",
		description = "True tile border width",
		section = ownSection,
		position = 4
	)
	default int ownBorderWidth()
	{
		return DEFAULT_BORDER_WIDTH;
	}

	@ConfigItem(
		keyName = "ownCustomized",
		name = "Own player style customized",
		description = "Tracks if this style has been edited",
		hidden = true
	)
	default boolean ownCustomized()
	{
		return false;
	}

	@ConfigSection(
		name = "Party True Tiles",
		description = "Party true tile visibility and style",
		position = 2
	)
	String partySection = "party";

	@ConfigItem(
		keyName = "partyHighlight",
		name = "True Tile highlight",
		description = "Inherit from Player Indicators, always on, or always off",
		section = partySection,
		position = 0
	)
	default TrueTileHighlightMode partyHighlight()
	{
		return TrueTileHighlightMode.INHERIT;
	}

	@Alpha
	@ConfigItem(
		keyName = "partyHighlightColor",
		name = "Border color",
		description = "True tile border color",
		section = partySection,
		position = 1
	)
	Color partyHighlightColor();

	@ConfigItem(
		keyName = "partyCustomize",
		name = "Customize styles",
		description = "If enabled, uses the fill color and border width below. When off, uses Global Styles",
		section = partySection,
		position = 2
	)
	default boolean partyCustomize()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		keyName = "partyFillColor",
		name = "Fill color",
		description = "True tile fill color",
		section = partySection,
		position = 3
	)
	default Color partyFillColor()
	{
		return DEFAULT_FILL;
	}

	@Range(min = 1, max = MAX_BORDER_WIDTH)
	@ConfigItem(
		keyName = "partyBorderWidth",
		name = "Border width",
		description = "True tile border width",
		section = partySection,
		position = 4
	)
	default int partyBorderWidth()
	{
		return DEFAULT_BORDER_WIDTH;
	}

	@ConfigItem(
		keyName = "partyCustomized",
		name = "Party style customized",
		description = "Tracks if this style has been edited",
		hidden = true
	)
	default boolean partyCustomized()
	{
		return false;
	}

	@ConfigSection(
		name = "Friends True Tiles",
		description = "Friends true tile visibility and style",
		position = 3
	)
	String friendsSection = "friends";

	@ConfigItem(
		keyName = "friendsHighlight",
		name = "True Tile highlight",
		description = "Inherit from Player Indicators, always on, or always off",
		section = friendsSection,
		position = 0
	)
	default TrueTileHighlightMode friendsHighlight()
	{
		return TrueTileHighlightMode.INHERIT;
	}

	@Alpha
	@ConfigItem(
		keyName = "friendsHighlightColor",
		name = "Border color",
		description = "True tile border color",
		section = friendsSection,
		position = 1
	)
	Color friendsHighlightColor();

	@ConfigItem(
		keyName = "friendsCustomize",
		name = "Customize styles",
		description = "If enabled, uses the fill color and border width below. When off, uses Global Styles",
		section = friendsSection,
		position = 2
	)
	default boolean friendsCustomize()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		keyName = "friendsFillColor",
		name = "Fill color",
		description = "True tile fill color",
		section = friendsSection,
		position = 3
	)
	default Color friendsFillColor()
	{
		return DEFAULT_FILL;
	}

	@Range(min = 1, max = MAX_BORDER_WIDTH)
	@ConfigItem(
		keyName = "friendsBorderWidth",
		name = "Border width",
		description = "True tile border width",
		section = friendsSection,
		position = 4
	)
	default int friendsBorderWidth()
	{
		return DEFAULT_BORDER_WIDTH;
	}

	@ConfigItem(
		keyName = "friendsCustomized",
		name = "Friends style customized",
		description = "Tracks if this style has been edited",
		hidden = true
	)
	default boolean friendsCustomized()
	{
		return false;
	}

	@ConfigSection(
		name = "Friends Chat True Tiles",
		description = "Friends chat true tile visibility and style",
		position = 4
	)
	String friendsChatSection = "friendsChat";

	@ConfigItem(
		keyName = "friendsChatHighlight",
		name = "True Tile highlight",
		description = "Inherit from Player Indicators, always on, or always off",
		section = friendsChatSection,
		position = 0
	)
	default TrueTileHighlightMode friendsChatHighlight()
	{
		return TrueTileHighlightMode.INHERIT;
	}

	@Alpha
	@ConfigItem(
		keyName = "friendsChatHighlightColor",
		name = "Border color",
		description = "True tile border color",
		section = friendsChatSection,
		position = 1
	)
	Color friendsChatHighlightColor();

	@ConfigItem(
		keyName = "friendsChatCustomize",
		name = "Customize styles",
		description = "If enabled, uses the fill color and border width below. When off, uses Global Styles",
		section = friendsChatSection,
		position = 2
	)
	default boolean friendsChatCustomize()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		keyName = "friendsChatFillColor",
		name = "Fill color",
		description = "True tile fill color",
		section = friendsChatSection,
		position = 3
	)
	default Color friendsChatFillColor()
	{
		return DEFAULT_FILL;
	}

	@Range(min = 1, max = MAX_BORDER_WIDTH)
	@ConfigItem(
		keyName = "friendsChatBorderWidth",
		name = "Border width",
		description = "True tile border width",
		section = friendsChatSection,
		position = 4
	)
	default int friendsChatBorderWidth()
	{
		return DEFAULT_BORDER_WIDTH;
	}

	@ConfigItem(
		keyName = "friendsChatCustomized",
		name = "Friends chat style customized",
		description = "Tracks if this style has been edited",
		hidden = true
	)
	default boolean friendsChatCustomized()
	{
		return false;
	}

	@ConfigSection(
		name = "Team True Tiles",
		description = "Team true tile visibility and style",
		position = 5
	)
	String teamSection = "team";

	@ConfigItem(
		keyName = "teamHighlight",
		name = "True Tile highlight",
		description = "Inherit from Player Indicators, always on, or always off",
		section = teamSection,
		position = 0
	)
	default TrueTileHighlightMode teamHighlight()
	{
		return TrueTileHighlightMode.INHERIT;
	}

	@Alpha
	@ConfigItem(
		keyName = "teamHighlightColor",
		name = "Border color",
		description = "True tile border color",
		section = teamSection,
		position = 1
	)
	Color teamHighlightColor();

	@ConfigItem(
		keyName = "teamCustomize",
		name = "Customize styles",
		description = "If enabled, uses the fill color and border width below. When off, uses Global Styles",
		section = teamSection,
		position = 2
	)
	default boolean teamCustomize()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		keyName = "teamFillColor",
		name = "Fill color",
		description = "True tile fill color",
		section = teamSection,
		position = 3
	)
	default Color teamFillColor()
	{
		return DEFAULT_FILL;
	}

	@Range(min = 1, max = MAX_BORDER_WIDTH)
	@ConfigItem(
		keyName = "teamBorderWidth",
		name = "Border width",
		description = "True tile border width",
		section = teamSection,
		position = 4
	)
	default int teamBorderWidth()
	{
		return DEFAULT_BORDER_WIDTH;
	}

	@ConfigItem(
		keyName = "teamCustomized",
		name = "Team style customized",
		description = "Tracks if this style has been edited",
		hidden = true
	)
	default boolean teamCustomized()
	{
		return false;
	}

	@ConfigSection(
		name = "Clan True Tiles",
		description = "Clan true tile visibility and style",
		position = 6
	)
	String clanSection = "clan";

	@ConfigItem(
		keyName = "clanHighlight",
		name = "True Tile highlight",
		description = "Inherit from Player Indicators, always on, or always off",
		section = clanSection,
		position = 0
	)
	default TrueTileHighlightMode clanHighlight()
	{
		return TrueTileHighlightMode.INHERIT;
	}

	@Alpha
	@ConfigItem(
		keyName = "clanHighlightColor",
		name = "Border color",
		description = "True tile border color",
		section = clanSection,
		position = 1
	)
	Color clanHighlightColor();

	@ConfigItem(
		keyName = "clanCustomize",
		name = "Customize styles",
		description = "If enabled, uses the fill color and border width below. When off, uses Global Styles",
		section = clanSection,
		position = 2
	)
	default boolean clanCustomize()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		keyName = "clanFillColor",
		name = "Fill color",
		description = "True tile fill color",
		section = clanSection,
		position = 3
	)
	default Color clanFillColor()
	{
		return DEFAULT_FILL;
	}

	@Range(min = 1, max = MAX_BORDER_WIDTH)
	@ConfigItem(
		keyName = "clanBorderWidth",
		name = "Border width",
		description = "True tile border width",
		section = clanSection,
		position = 4
	)
	default int clanBorderWidth()
	{
		return DEFAULT_BORDER_WIDTH;
	}

	@ConfigItem(
		keyName = "clanCustomized",
		name = "Clan style customized",
		description = "Tracks if this style has been edited",
		hidden = true
	)
	default boolean clanCustomized()
	{
		return false;
	}

	@ConfigSection(
		name = "Others True Tiles",
		description = "Others true tile visibility and style",
		position = 7
	)
	String othersSection = "others";

	@ConfigItem(
		keyName = "othersHighlight",
		name = "True Tile highlight",
		description = "Inherit from Player Indicators, always on, or always off",
		section = othersSection,
		position = 0
	)
	default TrueTileHighlightMode othersHighlight()
	{
		return TrueTileHighlightMode.INHERIT;
	}

	@Alpha
	@ConfigItem(
		keyName = "othersHighlightColor",
		name = "Border color",
		description = "True tile border color",
		section = othersSection,
		position = 1
	)
	Color othersHighlightColor();

	@ConfigItem(
		keyName = "othersCustomize",
		name = "Customize styles",
		description = "If enabled, uses the fill color and border width below. When off, uses Global Styles",
		section = othersSection,
		position = 2
	)
	default boolean othersCustomize()
	{
		return false;
	}

	@Alpha
	@ConfigItem(
		keyName = "othersFillColor",
		name = "Fill color",
		description = "True tile fill color",
		section = othersSection,
		position = 3
	)
	default Color othersFillColor()
	{
		return DEFAULT_FILL;
	}

	@Range(min = 1, max = MAX_BORDER_WIDTH)
	@ConfigItem(
		keyName = "othersBorderWidth",
		name = "Border width",
		description = "True tile border width",
		section = othersSection,
		position = 4
	)
	default int othersBorderWidth()
	{
		return DEFAULT_BORDER_WIDTH;
	}

	@ConfigItem(
		keyName = "othersCustomized",
		name = "Others style customized",
		description = "Tracks if this style has been edited",
		hidden = true
	)
	default boolean othersCustomized()
	{
		return false;
	}
}
