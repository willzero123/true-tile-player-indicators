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

import com.google.inject.Provides;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.ProfileChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.playerindicators.PlayerIndicatorsConfig;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "True Tile Player Indicators",
	description = "Adds true tile highlights to Player Indicators. Disabled in PvP.",
	tags = {"player indicators", "true tile"},
	enabledByDefault = true
)
public class TrueTilePlayerIndicatorsPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private TrueTilePlayerIndicatorsOverlay overlay;

	@Inject
	private TrueTileSettings settings;

	@Inject
	private ClientThread clientThread;

	private final AtomicBoolean refreshQueued = new AtomicBoolean();
	private volatile boolean active;

	@Provides
	TrueTilePlayerIndicatorsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TrueTilePlayerIndicatorsConfig.class);
	}

	@Override
	protected void startUp()
	{
		settings.loadProfile();
		active = true;
		queueRefresh();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		active = false;
		overlayManager.remove(overlay);
	}

	@Override
	public void resetConfiguration()
	{
		clientThread.invokeLater(() ->
		{
			settings.resetStyles();
			if (active)
			{
				settings.refresh();
			}
		});
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getProfile() != null)
		{
			return;
		}

		if (TrueTilePlayerIndicatorsConfig.GROUP.equals(event.getGroup()))
		{
			settings.onStyleChanged(event);
			queueRefresh();
		}
		else if (PlayerIndicatorsConfig.GROUP.equals(event.getGroup()))
		{
			queueRefresh();
		}
	}

	@Subscribe(priority = -1)
	public void onProfileChanged(ProfileChanged event)
	{
		settings.loadProfile();
		queueRefresh();
	}

	private void queueRefresh()
	{
		if (!active || !refreshQueued.compareAndSet(false, true))
		{
			return;
		}

		clientThread.invokeLater(() ->
		{
			refreshQueued.set(false);
			if (active)
			{
				settings.refresh();
			}
		});
	}
}
