package link.botwmcs.samchai.realms.screen.multiplayer;

import com.mojang.logging.LogUtils;
import link.botwmcs.samchai.realms.Realms;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.layouts.SpacerElement;
import net.minecraft.client.gui.navigation.CommonInputs;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.ServerStatusPinger;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class BotwServerScreen extends Screen {
    public static final int BUTTON_ROW_WIDTH = 308;
    public static final int TOP_ROW_BUTTON_WIDTH = 100;
    public static final int LOWER_ROW_BUTTON_WIDTH = 74;
    public static final int FOOTER_HEIGHT = 64;
    public static final int LIST_HEIGHT = 128;
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
    }


    protected void init() {
        if (this.initedOnce) {
            this.serverSelectionList.updateSize(this.width, this.height, LIST_HEIGHT, LIST_HEIGHT+40);
        } else {
            this.initedOnce = true;
            this.servers = new ServerList(this.minecraft);
            this.servers.load();


            this.serverSelectionList = new RealmsServerSelectionList(this, this.minecraft, this.width, this.height, LIST_HEIGHT, LIST_HEIGHT+40, 36);
            this.serverSelectionList.updateOnlineServers(this.servers);
        }

        this.addWidget(this.serverSelectionList);
        Button button3 = (Button)this.addRenderableWidget(Button.builder(Component.translatable("selectServer.refresh"), (buttonx) -> {
            this.refreshServerList();
        }).width(74).build());
        GridLayout gridLayout = new GridLayout();
        GridLayout.RowHelper rowHelper = gridLayout.createRowHelper(1);
        LinearLayout linearLayout2 = (LinearLayout)rowHelper.addChild(new LinearLayout(308, 20, LinearLayout.Orientation.HORIZONTAL));
        linearLayout2.addChild(button3);
        gridLayout.arrangeElements();
        FrameLayout.centerInRectangle(gridLayout, 0, this.height - 64, this.width, 64);
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

    private void refreshServerList() {
        this.minecraft.setScreen(new BotwServerScreen(this.lastScreen));
    }

    private void deleteCallback(boolean bl) {
        RealmsServerSelectionList.Entry entry = (RealmsServerSelectionList.Entry)this.serverSelectionList.getSelected();
        if (bl && entry instanceof RealmsServerSelectionList.OnlineServerEntry) {
            this.servers.remove(((RealmsServerSelectionList.OnlineServerEntry)entry).getServerData());
            this.servers.save();
            this.serverSelectionList.setSelected((RealmsServerSelectionList.Entry)null);
            this.serverSelectionList.updateOnlineServers(this.servers);
        }

        this.minecraft.setScreen(this);
    }

    private void editServerCallback(boolean bl) {
        RealmsServerSelectionList.Entry entry = (RealmsServerSelectionList.Entry)this.serverSelectionList.getSelected();
        if (bl && entry instanceof RealmsServerSelectionList.OnlineServerEntry) {
            ServerData serverData = ((RealmsServerSelectionList.OnlineServerEntry)entry).getServerData();
            serverData.name = this.editingServer.name;
            serverData.ip = this.editingServer.ip;
            serverData.copyFrom(this.editingServer);
            this.servers.save();
            this.serverSelectionList.updateOnlineServers(this.servers);
        }

        this.minecraft.setScreen(this);
    }

    private void addServerCallback(boolean bl) {
        if (bl) {
            ServerData serverData = this.servers.unhide(this.editingServer.ip);
            if (serverData != null) {
                serverData.copyNameIconFrom(this.editingServer);
                this.servers.save();
            } else {
                this.servers.add(this.editingServer, false);
                this.servers.save();
            }

            this.serverSelectionList.setSelected((RealmsServerSelectionList.Entry)null);
            this.serverSelectionList.updateOnlineServers(this.servers);
        }

        this.minecraft.setScreen(this);
    }

    private void directJoinCallback(boolean bl) {
        if (bl) {
            ServerData serverData = this.servers.get(this.editingServer.ip);
            if (serverData == null) {
                this.servers.add(this.editingServer, true);
                this.servers.save();
                this.join(this.editingServer);
            } else {
                this.join(serverData);
            }
        } else {
            this.minecraft.setScreen(this);
        }

    }

    private void renderBackgroundInBlock(GuiGraphics guiGraphics, ResourceLocation resourceLocation) {
        guiGraphics.setColor(0.25F, 0.25F, 0.25F, 1.0F);
        guiGraphics.blit(resourceLocation, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void renderAnnouncementBackground(GuiGraphics guiGraphics) {
        ResourceLocation bg = new ResourceLocation(Realms.MODID, "textures/gui/elements.png");
        guiGraphics.blit(bg, this.width / 2, 0, 0, 0, 0, 147, 166, 256, 256);
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
        this.serverSelectionList.render(guiGraphics, i, j, f);
        this.renderBackgroundInBlock(guiGraphics, new ResourceLocation("textures/block/cut_copper.png"));
        this.renderAnnouncementBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
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
