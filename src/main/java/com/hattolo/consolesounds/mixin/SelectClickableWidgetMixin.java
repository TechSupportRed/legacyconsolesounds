package com.hattolo.consolesounds.mixin;

import com.hattolo.consolesounds.ConsoleSoundsClient;
import com.hattolo.consolesounds.ConsoleSoundsConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ClickableWidget.class)
public class SelectClickableWidgetMixin {
	private static float randFloat(float min, float max) {
		Random rand = new Random();
		return rand.nextFloat() * (max - min) + min;
	}

	@Inject(at = @At("RETURN"), method = "isHovered")
	private void isHovered(CallbackInfoReturnable<Boolean> cir) {
		if (cir.getReturnValue()) {
			if (selected == false) {
				selected = true;
				if (AutoConfig.getConfigHolder(ConsoleSoundsConfig.class).getConfig().enableHoverSounds) MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(ConsoleSoundsClient.UI_SELECT_EVENT, randFloat(0.9f, 1.1f)));
			}
		}
		else {
			selected = false;
		}
	}

	Boolean selected = false;
	/*
	@Inject(at = @At("HEAD"), method = "renderTooltip")
	private void renderTooltip(CallbackInfo info) {
		System.out.println("This line is printed by an example mod mixin!");
		MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(ConsoleModClient.UI_SELECT_EVENT, 1.0F));
	}
	*/
}