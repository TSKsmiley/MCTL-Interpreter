package net.abaaja.mctl;

import cpw.mods.modlauncher.api.ITransformationService;
import net.abaaja.mctl.block.ModBlocks;
import net.abaaja.mctl.command.ModCommands;
import net.abaaja.mctl.entity.ModEntityTypes;
import net.abaaja.mctl.item.ModItems;
import net.abaaja.mctl.entity.client.TurtleRenderer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.checkerframework.common.reflection.qual.GetClass;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.io.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MCTL.MOD_ID)
public class MCTL {

    public static final String FileExtension = ".mctl";
    public static final String FileLocation = System.getProperty("user.dir") + "/mctl-scripts/";
    public static final String MOD_ID = "mctl";
    private static final Logger LOGGER = LogUtils.getLogger();
  
    public MCTL() {
        try{
            fsSetup();
        } catch (IOException ignored) {}

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModEntityTypes.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void fsSetup() throws IOException {
        File folder = new File(FileLocation);
        if (!folder.exists()) {
            folder.mkdir();
            FileWriter ExampleFile = new FileWriter(FileLocation + "example" + FileExtension);
            ExampleFile.write(getExampleFile());
            ExampleFile.close();
        }
    }

    private String getExampleFile() {
        InputStream is = getClass().getResourceAsStream("/assets/mctl/example" + FileExtension);
        if (is == null) return "resource not found";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            StringBuilder lines = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                lines.append(line);
            }
            br.close();
            return lines.toString();
        } catch (IOException ignored) {}
        return "";
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            EntityRenderers.register(ModEntityTypes.TURTLE.get(), TurtleRenderer::new);
        }
    }
}
