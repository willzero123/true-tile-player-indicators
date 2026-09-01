/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Player;
import net.runelite.client.party.PartyService;
import net.runelite.client.plugins.playerindicators.PlayerIndicatorsConfig;

@Singleton
class PlayerHighlightService
{
	private final PlayerIndicatorsConfig config;
	private final PartyService partyService;

	@Inject
	PlayerHighlightService(PlayerIndicatorsConfig config, PartyService partyService)
	{
		this.config = config;
		this.partyService = partyService;
	}

	Color getColor(Player player, Player localPlayer, boolean pvpActive)
	{
		if (player.getName() == null)
		{
			return null;
		}

		if (player == localPlayer)
		{
			return isEnabled(config.highlightOwnPlayer(), pvpActive)
				? config.getOwnPlayerColor()
				: null;
		}

		if (partyService.isInParty()
			&& isEnabled(config.highlightPartyMembers(), pvpActive)
			&& partyService.getMemberByDisplayName(player.getName()) != null)
		{
			return config.getPartyMemberColor();
		}

		if (player.isFriend() && isEnabled(config.highlightFriends(), pvpActive))
		{
			return config.getFriendColor();
		}

		if (player.isFriendsChatMember() && isEnabled(config.highlightFriendsChat(), pvpActive))
		{
			return config.getFriendsChatMemberColor();
		}

		if (player.getTeam() > 0
			&& localPlayer != null
			&& localPlayer.getTeam() == player.getTeam()
			&& isEnabled(config.highlightTeamMembers(), pvpActive))
		{
			return config.getTeamMemberColor();
		}

		if (player.isClanMember() && isEnabled(config.highlightClanMembers(), pvpActive))
		{
			return config.getClanMemberColor();
		}

		if (!player.isFriendsChatMember()
			&& !player.isClanMember()
			&& isEnabled(config.highlightOthers(), pvpActive))
		{
			return config.getOthersColor();
		}

		return null;
	}

	private static boolean isEnabled(PlayerIndicatorsConfig.HighlightSetting setting, boolean pvpActive)
	{
		return setting == PlayerIndicatorsConfig.HighlightSetting.ENABLED
			|| setting == PlayerIndicatorsConfig.HighlightSetting.PVP && pvpActive;
	}
}
