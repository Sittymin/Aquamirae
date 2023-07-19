package com.obscuria.aquamirae.mixin;

import com.obscuria.aquamirae.Aquamirae;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    private DamageSource source = null;

    @Inject(method = "applyDamage", at = @At("HEAD"))
    private void saveSource(DamageSource source, float amount, CallbackInfo ci) {
        this.source = source;
        Aquamirae.onEntityDamage((LivingEntity)(Object)this, source);
    }

    @ModifyVariable(method = "applyDamage", at = @At("HEAD"), argsOnly = true)
    private float modifyDamage(float amount) {
        return Aquamirae.modifyDamage((LivingEntity)(Object)this, source, amount);
    }
}
