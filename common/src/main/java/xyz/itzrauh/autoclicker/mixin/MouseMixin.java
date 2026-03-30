package xyz.itzrauh.autoclicker.mixin;

import net.minecraft.client.Mouse;
import net.minecraft.client.input.MouseInput;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

	@Inject(method = "onMouseButton", at = @At("HEAD"))
	private void onMouseButton(long window, MouseInput input, int action, CallbackInfo ci) {}
}
