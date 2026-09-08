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

import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Player;
import net.runelite.client.party.PartyService;

@Singleton
class PlayerHighlightService
{
	private final TrueTileSettings settings;
	private final PartyService partyService;

	@Inject
	PlayerHighlightService(TrueTileSettings settings, PartyService partyService)
	{
		this.settings = settings;
		this.partyService = partyService;
	}

	TrueTileStyle getStyle(Player player, Player localPlayer)
	{
		if (player.getName() == null)
		{
			return null;
		}

		Map<PlayerType, TrueTileStyle> styles = settings.getStyles();
		if (player == localPlayer)
		{
			return styles.get(PlayerType.OWN);
		}

		if (partyService.isInParty()
			&& styles.containsKey(PlayerType.PARTY)
			&& partyService.getMemberByDisplayName(player.getName()) != null)
		{
			return styles.get(PlayerType.PARTY);
		}

		if (player.isFriend() && styles.containsKey(PlayerType.FRIENDS))
		{
			return styles.get(PlayerType.FRIENDS);
		}

		if (player.isFriendsChatMember() && styles.containsKey(PlayerType.FRIENDS_CHAT))
		{
			return styles.get(PlayerType.FRIENDS_CHAT);
		}

		if (player.getTeam() > 0
			&& localPlayer != null
			&& localPlayer.getTeam() == player.getTeam()
			&& styles.containsKey(PlayerType.TEAM))
		{
			return styles.get(PlayerType.TEAM);
		}

		if (player.isClanMember() && styles.containsKey(PlayerType.CLAN))
		{
			return styles.get(PlayerType.CLAN);
		}

		if (!player.isFriendsChatMember()
			&& !player.isClanMember()
			&& styles.containsKey(PlayerType.OTHERS))
		{
			return styles.get(PlayerType.OTHERS);
		}

		return null;
	}
}
