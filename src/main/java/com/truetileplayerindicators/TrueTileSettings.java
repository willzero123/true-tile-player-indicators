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
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.playerindicators.PlayerIndicatorsConfig;

@Singleton
class TrueTileSettings
{
	private final TrueTilePlayerIndicatorsConfig config;
	private final PlayerIndicatorsConfig playerIndicatorsConfig;
	private final ConfigManager configManager;
	private volatile Map<PlayerType, TrueTileStyle> styles = Collections.emptyMap();
	private long profileId;
	private boolean updatingInheritedColor;

	@Inject
	TrueTileSettings(ConfigManager configManager)
	{
		this.config = configManager.getConfig(TrueTilePlayerIndicatorsConfig.class);
		this.playerIndicatorsConfig = configManager.getConfig(PlayerIndicatorsConfig.class);
		this.configManager = configManager;
	}

	Map<PlayerType, TrueTileStyle> getStyles()
	{
		return styles;
	}

	synchronized void loadProfile()
	{
		profileId = configManager.getProfile().getId();
	}

	synchronized void onStyleChanged(ConfigChanged event)
	{
		if (updatingInheritedColor || profileId != configManager.getProfile().getId())
		{
			return;
		}

		for (PlayerType type : PlayerType.values())
		{
			if (!event.getKey().equals(type.getKey() + "HighlightColor"))
			{
				continue;
			}

			setCustomized(type, event.getNewValue() != null);
			if (event.getNewValue() == null)
			{
				refresh();
			}
			return;
		}
	}

	synchronized void resetStyles()
	{
		for (PlayerType type : PlayerType.values())
		{
			configManager.unsetConfiguration(TrueTilePlayerIndicatorsConfig.GROUP, type.getKey() + "HighlightColor");
			setCustomized(type, false);
		}
	}

	synchronized void refresh()
	{
		if (profileId != configManager.getProfile().getId())
		{
			return;
		}

		Map<PlayerType, TrueTileStyle> updated = new EnumMap<>(PlayerType.class);
		updateStyle(updated, PlayerType.OWN,
			playerIndicatorsConfig.highlightOwnPlayer(), playerIndicatorsConfig.getOwnPlayerColor());
		updateStyle(updated, PlayerType.PARTY,
			playerIndicatorsConfig.highlightPartyMembers(), playerIndicatorsConfig.getPartyMemberColor());
		updateStyle(updated, PlayerType.FRIENDS,
			playerIndicatorsConfig.highlightFriends(), playerIndicatorsConfig.getFriendColor());
		updateStyle(updated, PlayerType.FRIENDS_CHAT,
			playerIndicatorsConfig.highlightFriendsChat(), playerIndicatorsConfig.getFriendsChatMemberColor());
		updateStyle(updated, PlayerType.TEAM,
			playerIndicatorsConfig.highlightTeamMembers(), playerIndicatorsConfig.getTeamMemberColor());
		updateStyle(updated, PlayerType.CLAN,
			playerIndicatorsConfig.highlightClanMembers(), playerIndicatorsConfig.getClanMemberColor());
		updateStyle(updated, PlayerType.OTHERS,
			playerIndicatorsConfig.highlightOthers(), playerIndicatorsConfig.getOthersColor());
		styles = Collections.unmodifiableMap(updated);
	}

	private void updateStyle(Map<PlayerType, TrueTileStyle> updated, PlayerType type,
		PlayerIndicatorsConfig.HighlightSetting inheritedHighlight, Color inheritedColor)
	{
		synchronizeStyle(type, inheritedColor);
		TrueTileHighlightMode highlight = getConfig(type, "Highlight", TrueTileHighlightMode.class, TrueTileHighlightMode.INHERIT);
		if (!highlight.isEnabled(inheritedHighlight))
		{
			return;
		}

		boolean customized = getConfig(type, "Customized", Boolean.class, false);
		Color color = customized ? getConfig(type, "HighlightColor", Color.class, inheritedColor) : inheritedColor;
		boolean customize = getConfig(type, "Customize", Boolean.class, false);
		Color fill = customize ? getConfig(type, "FillColor", Color.class, config.fillColor()) : config.fillColor();
		int width = customize ? getConfig(type, "BorderWidth", Integer.class, config.borderWidth()) : config.borderWidth();
		updated.put(type, new TrueTileStyle(color, fill, width));
	}

	private void synchronizeStyle(PlayerType type, Color inheritedColor)
	{
		Color color = getConfig(type, "HighlightColor", Color.class, null);
		if (!getConfig(type, "Customized", Boolean.class, false) || color == null)
		{
			setColor(type.getKey() + "HighlightColor", color, inheritedColor);
		}
	}

	private void setCustomized(PlayerType type, boolean customized)
	{
		configManager.setConfiguration(TrueTilePlayerIndicatorsConfig.GROUP, type.getKey() + "Customized", customized);
	}

	private <T> T getConfig(PlayerType type, String suffix, Class<T> valueType, T defaultValue)
	{
		T value = configManager.getConfiguration(TrueTilePlayerIndicatorsConfig.GROUP, type.getKey() + suffix, valueType);
		return value == null ? defaultValue : value;
	}

	private void setColor(String key, Color current, Color value)
	{
		if (!value.equals(current))
		{
			updatingInheritedColor = true;
			try
			{
				configManager.setConfiguration(TrueTilePlayerIndicatorsConfig.GROUP, key, value);
			}
			finally
			{
				updatingInheritedColor = false;
			}
		}
	}
}
