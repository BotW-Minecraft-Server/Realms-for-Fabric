package link.botwmcs.samchai.realms.screen.multiplayer;

import com.mojang.logging.LogUtils;
import link.botwmcs.samchai.realms.Realms;
import link.botwmcs.samchai.realms.config.RealmsCommonConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.ServerStatusPinger;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class BotwServerScreen extends Screen {
    public static final int BUTTON_ROW_WIDTH = 308;
    public static final int TOP_ROW_BUTTON_WIDTH = 100;
    public static final int LOWER_ROW_BUTTON_WIDTH = 74;
    public static final int FOOTER_HEIGHT = 64;
    public static final int LIST_HEIGHT = 128;
    public static final List<String> announcementLines = new ArrayList<>();
    private static final Logger LOGGER = LogUtils.getLogger();
    private final ServerStatusPinger pinger = new ServerStatusPinger();
    private final Screen lastScreen;

    protected RealmsServerSelectionList serverSelectionList;
    private ServerList servers;
    private Button editButton;
    private Button selectButton;
    private Button deleteButton;
    @Nullable
    private List<Component> toolTip;
    private ServerData editingServer;
    private boolean initedOnce;

    public BotwServerScreen(Screen screen) {
        super(Component.translatable("menu.botwmcs.realms"));
        this.lastScreen = screen;
        this.fetchAnnouncementText();
    }


    protected void init() {
        Button refreshButton = (Button) this.addRenderableWidget(new ImageButton(this.width / 2 - 150, 0 + 40, 20, 20, 0, 166, 20, new ResourceLocation(Realms.MODID, "textures/gui/elements.png"), 256, 256, (button) -> {
            this.refreshServerList();
        }));
        Button joinButton = (Button) this.addRenderableWidget(new ImageButton(this.width / 2 - 200, 0 + 10, 20, 20, 0, 206, 20, new ResourceLocation(Realms.MODID, "textures/gui/elements.png"), 256, 256, (button) -> {
            this.joinSelectedServer();
        }));
        GridLayout gridLayout = new GridLayout();
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(1);
        LinearLayout linearLayout2 = (LinearLayout) rowHelper.addChild(new LinearLayout(308, 20, LinearLayout.Orientation.HORIZONTAL));
        linearLayout2.addChild(refreshButton);
        gridLayout.arrangeElements();
        FrameLayout.centerInRectangle(gridLayout, 0, 20, this.width - 50, 0);
        LinearLayout linearLayout = (LinearLayout) rowHelper.addChild(new LinearLayout(308, 20, LinearLayout.Orientation.HORIZONTAL));
        linearLayout.addChild(joinButton);

        if (this.initedOnce) {
            this.serverSelectionList.updateSize(this.width, 0 + 40, 0, 0+40);
        } else {
            this.initedOnce = true;
            this.servers = new ServerList(this.minecraft);
            this.servers.load();


            this.serverSelectionList = new RealmsServerSelectionList(this, this.minecraft, this.width, 0 + 40, 0, 0+40, 36);
            this.serverSelectionList.updateOnlineServers(this.servers);
        }
        this.addWidget(this.serverSelectionList);

        this.onSelectedChange();
    }

    public void tick() {
        super.tick();
        this.pinger.tick();
    }

    public void removed() {

        this.pinger.removeAll();
        this.serverSelectionList.removed();
    }

    private void fetchAnnouncementText() {
        RealmsCommonConfig config = AutoConfig.getConfigHolder(RealmsCommonConfig.class).getConfig();
        String announcementUrl = config.announcement_url;
        if (!announcementLines.isEmpty()) {
            announcementLines.clear();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(announcementUrl).openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                announcementLines.add(line);
            }
        } catch (IOException e) {
            Realms.LOGGER.warn("Failed to fetch announcement text from {}", announcementUrl, e);
        }
        Realms.LOGGER.debug("Fetched announcement text from {}", announcementUrl);
    }

    private void refreshServerList() {
        this.minecraft.setScreen(new BotwServerScreen(this.lastScreen));
    }

    private void renderBackgroundInBlock(GuiGraphics guiGraphics, ResourceLocation resourceLocation) {
        guiGraphics.setColor(0.25F, 0.25F, 0.25F, 1.0F);
        guiGraphics.blit(resourceLocation, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderAnnouncementBackground(GuiGraphics guiGraphics) {
        ResourceLocation bg = new ResourceLocation(Realms.MODID, "textures/gui/elements.png");
        guiGraphics.blit(bg, this.width / 2 - 180, 50, 0, 0, 0, 147 / 2, 166, 256, 256);
        guiGraphics.blit(bg, this.width / 2 - 147 / 2 - 100 / 2, 50, 0, 8, 0, 130, 166, 256, 256);
        guiGraphics.blit(bg, this.width / 2 - 147 / 2 + 60, 50, 0, 8, 0, 130, 166, 256, 256);
        guiGraphics.blit(bg, this.width / 2 + 147 / 2 + 35, 50, 0, 147 / 2, 0, 147 / 2, 166, 256, 256);
    }

    private void renderAnnouncement(GuiGraphics guiGraphics) {
        renderAnnouncementBackground(guiGraphics);
        // Total: 147x166, 14 lines
        int y = 50 + 15;
        for (String lines : announcementLines) {
            guiGraphics.drawString(font, lines, this.width / 2 - 147 / 2 - 131 / 2 - 30, y, 0xFFFFFF);
            y += 10;
        }
    }

    public boolean keyPressed(int i, int j, int k) {
        if (super.keyPressed(i, j, k)) {
            return true;
        } else if (i == 294) {
            this.refreshServerList();
            return true;
        } else if (this.serverSelectionList.getSelected() != null) {
            if (CommonInputs.selected(i)) {
                this.joinSelectedServer();
                return true;
            } else {
                return this.serverSelectionList.keyPressed(i, j, k);
            }
        } else {
            return false;
        }
    }

    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        this.toolTip = null;
        this.renderBackgroundInBlock(guiGraphics, new ResourceLocation("textures/block/deepslate_bricks.png"));
        this.renderAnnouncement(guiGraphics);
        this.serverSelectionList.render(guiGraphics, i, j, f);
        String copyright = "MIT SOFTWARE, BOTW MINECRAFT SERVER";
        guiGraphics.drawCenteredString(this.font, copyright, this.width / 2, 225, 16777215);
//        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 180, 16777215);
        super.render(guiGraphics, i, j, f);
        if (this.toolTip != null) {
            guiGraphics.renderComponentTooltip(this.font, this.toolTip, i, j);
        }

    }

    public void joinSelectedServer() {
        RealmsServerSelectionList.Entry entry = (RealmsServerSelectionList.Entry)this.serverSelectionList.getSelected();
        if (entry instanceof RealmsServerSelectionList.OnlineServerEntry) {
            this.join(((RealmsServerSelectionList.OnlineServerEntry)entry).getServerData());
        }
    }

    private void join(ServerData serverData) {
        ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(serverData.ip), serverData, false);
    }

    public void setSelected(RealmsServerSelectionList.Entry entry) {
        this.serverSelectionList.setSelected(entry);
        this.onSelectedChange();
    }

    protected void onSelectedChange() {
        RealmsServerSelectionList.Entry entry = (RealmsServerSelectionList.Entry)this.serverSelectionList.getSelected();

    }

    public ServerStatusPinger getPinger() {
        return this.pinger;
    }

    public void setToolTip(List<Component> list) {
        this.toolTip = list;
    }

    public ServerList getServers() {
        return this.servers;
    }
}
