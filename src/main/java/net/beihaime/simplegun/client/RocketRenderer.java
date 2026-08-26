package net.beihaime.simplegun.client;

import net.beihaime.simplegun.entity.Rocket;
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

public class RocketRenderer extends EntityRenderer<Rocket> {

    private final ItemRenderer itemRenderer;

    public RocketRenderer(EntityRendererProvider.Context context) {
        super(context);

        this.itemRenderer = context.getItemRenderer();
    }
    @Override
    public ResourceLocation getTextureLocation(Rocket entity) {
        return new ResourceLocation(
                "simplegun",
                "textures/entity/rocket/rocket_head.png"
        );
    }
    
    @Override
    public void render(
            Rocket entity,
            float entityYaw,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {
        ItemStack stack = new ItemStack(
                ModItem.ROCKET.get()
        );

        Vec3 velocity = entity.getDeltaMovement();

        if (velocity.lengthSqr() > 0.0001D) {
            velocity = velocity.normalize();
        }

        float yaw = (float) (
                Math.atan2(velocity.x, velocity.z)
                        * 180.0D / Math.PI
        );

        float pitch = (float) (
                Math.atan2(
                        -velocity.y,
                        Math.sqrt(
                                velocity.x * velocity.x
                                        + velocity.z * velocity.z
                        )
                )
                        * 180.0D / Math.PI
        );

        poseStack.pushPose();

        poseStack.mulPose(
                Axis.YP.rotationDegrees(yaw)
        );

        poseStack.mulPose(
                Axis.XP.rotationDegrees(pitch)
        );

        itemRenderer.renderStatic(
                stack,
                ItemDisplayContext.FIXED,
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