/*
 * Copyright (c) 2018, Kamiel <https://github.com/Kamielvf>
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
package com.playerindicatorstruetile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.WorldView;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class PlayerIndicatorsTrueTileOverlay extends Overlay
{
	private final Client client;
	private final PlayerHighlightService playerHighlightService;

	@Inject
	private PlayerIndicatorsTrueTileOverlay(Client client, PlayerHighlightService playerHighlightService)
	{
		this.client = client;
		this.playerHighlightService = playerHighlightService;
		setLayer(OverlayLayer.ABOVE_SCENE);
		setPosition(OverlayPosition.DYNAMIC);
		setPriority(PRIORITY_MED);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		WorldView topLevelWorldView = client.getTopLevelWorldView();
		if (topLevelWorldView == null)
		{
			return null;
		}

		Player localPlayer = client.getLocalPlayer();
		boolean pvpActive = client.getVarbitValue(VarbitID.INSIDE_WILDERNESS) == 1
			|| client.getVarbitValue(VarbitID.PVP_AREA_CLIENT) == 1;

		renderWorldView(graphics, topLevelWorldView, localPlayer, pvpActive);
		for (WorldView worldView : topLevelWorldView.worldViews())
		{
			renderWorldView(graphics, worldView, localPlayer, pvpActive);
		}

		return null;
	}

	private void renderWorldView(Graphics2D graphics, WorldView worldView, Player localPlayer, boolean pvpActive)
	{
		for (Player player : worldView.players())
		{
			LocalPoint renderedLocation = player.getLocalLocation();
			if (renderedLocation == null || !worldView.contains(renderedLocation))
			{
				continue;
			}

			Color color = playerHighlightService.getColor(player, localPlayer, pvpActive);
			if (color == null)
			{
				continue;
			}

			LocalPoint trueTileLocation = getTrueTileLocation(player);
			if (trueTileLocation == null)
			{
				continue;
			}

			Polygon polygon = Perspective.getCanvasTilePoly(client, trueTileLocation);
			if (polygon != null)
			{
				OverlayUtil.renderPolygon(graphics, polygon, color);
			}
		}
	}

	static LocalPoint getTrueTileLocation(Player player)
	{
		WorldView worldView = player.getWorldView();
		WorldPoint worldLocation = player.getWorldLocation();
		return worldView == null || worldLocation == null
			? null
			: LocalPoint.fromWorld(worldView, worldLocation);
	}
}
