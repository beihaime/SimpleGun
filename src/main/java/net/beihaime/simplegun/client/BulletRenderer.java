package net.beihaime.simplegun.client;

import net.beihaime.simplegun.entity.Bullet;
import net.beihaime.simplegun.registry.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.beihaime.simplegun.registry.ModItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;

public class BulletRenderer extends EntityRenderer<Bullet> {

    private final ItemRenderer itemRenderer;

    public BulletRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.itemRenderer = context.getItemRenderer();
    }
    @Override
    public ResourceLocation getTextureLocation(Bullet entity) {
        return new ResourceLocation(
                "simplegun",
                "textures/item/bullet.png"
        );
    }

    @Override
    public void render(
            Bullet entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        ItemStack stack = new ItemStack(ModItem.BULLET.get());

        Vec3 velocity = entity.getDeltaMovement();
        if (velocity.lengthSqr() < 1.0E-6) {
            poseStack.pushPose();
            itemRenderer.renderStatic(
                    stack,
                    ItemDisplayContext.NONE,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack,
                    buffer,
                    entity.level(),
                    entity.getId()
            );
            poseStack.popPose();
            return;
        }

        velocity = velocity.normalize();
        float yaw = (float) (Math.atan2(velocity.x, velocity.z) * (180.0 / Math.PI));
        float pitch = (float) (Math.asin(-velocity.y) * (180.0 / Math.PI));

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
        itemRenderer.renderStatic(
                stack,
                ItemDisplayContext.NONE,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
                entity.getId()
        );
        poseStack.popPose();
    }
}