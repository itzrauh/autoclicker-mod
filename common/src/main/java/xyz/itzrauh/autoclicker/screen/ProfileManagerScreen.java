package xyz.itzrauh.autoclicker.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import xyz.itzrauh.autoclicker.manager.ProfileManager;
import xyz.itzrauh.autoclicker.model.ClickButton;
import xyz.itzrauh.autoclicker.model.ConfigProfile;

import java.util.ArrayList;
import java.util.List;

public class ProfileManagerScreen extends Screen {

    private static final int TAB_HEIGHT = 20;
    private static final int TAB_WIDTH = 70;
    private static final int FIELD_W = 120;
    private static final int PADDING = 8;

    private static final int LABEL_X = 40;
    private static final int WIDGET_X = 160;

    private static final int ROW_NAME = 58;
    private static final int ROW_DELAY = 84;
    private static final int ROW_BUTTON = 110;
    private static final int ROW_DOUBLECLICK = 136;

    private final Screen parent;
    private final List<ButtonWidget> tabButtons = new ArrayList<>();

    private TextFieldWidget nameField;
    private TextFieldWidget delayField;

    public ProfileManagerScreen(Screen parent) {
        super(Text.translatable("autoclicker.screen.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
        rebuildWidgets();
    }

    private void rebuildWidgets() {
        clearChildren();
        tabButtons.clear();
        buildTabs();
        buildProfileEditor();
        buildActionButtons();
    }

    private void buildTabs() {
        List<String> names = ProfileManager.getProfileNames();
        int y = 24;

        for (int i = 0; i < names.size(); i++) {
            final int idx = i;
            boolean isActive = (idx == ProfileManager.getActiveIndex());

            ButtonWidget tab = ButtonWidget.builder(
                            Text.literal(names.get(i)).formatted(isActive ? Formatting.YELLOW : Formatting.WHITE),
                            btn -> { ProfileManager.setActiveIndex(idx); rebuildWidgets(); })
                    .dimensions(PADDING + idx * (TAB_WIDTH + 2), y, TAB_WIDTH, TAB_HEIGHT)
                    .build();

            tabButtons.add(tab);
            addDrawableChild(tab);
        }

        if (names.size() < 10) {
            int plusX = PADDING + names.size() * (TAB_WIDTH + 2);
            addDrawableChild(ButtonWidget.builder(
                            Text.translatable("autoclicker.screen.btn.new"),
                            btn -> { ProfileManager.createProfile(
                                    Text.translatable("autoclicker.profile.default_name").getString());
                                rebuildWidgets(); })
                    .dimensions(plusX, y, 20, TAB_HEIGHT)
                    .build());
        }
    }

    private void buildProfileEditor() {
        ConfigProfile active = ProfileManager.getActive();

        nameField = new TextFieldWidget(textRenderer, WIDGET_X, ROW_NAME, FIELD_W, 16,
                Text.translatable("autoclicker.screen.field.name"));
        nameField.setMaxLength(24);
        nameField.setText(active.getName());
        nameField.setChangedListener(value -> ProfileManager.getActive().setName(value));
        addDrawableChild(nameField);

        delayField = new TextFieldWidget(textRenderer, WIDGET_X, ROW_DELAY, 60, 16,
                Text.translatable("autoclicker.screen.field.delay"));
        delayField.setMaxLength(6);
        delayField.setText(String.valueOf(active.getDelayMs()));
        delayField.setChangedListener(value -> {
            try { ProfileManager.getActive().setDelayMs(Integer.parseInt(value)); }
            catch (NumberFormatException ignored) {}
        });
        addDrawableChild(delayField);

        boolean isLeft = active.getClickButton() == ClickButton.LEFT;

        addDrawableChild(ButtonWidget.builder(
                        Text.translatable("autoclicker.screen.btn.left")
                                .formatted(isLeft ? Formatting.GREEN : Formatting.WHITE),
                        btn -> { ProfileManager.setActiveClickButton(ClickButton.LEFT); rebuildWidgets(); })
                .dimensions(WIDGET_X, ROW_BUTTON, 58, 16).build());

        addDrawableChild(ButtonWidget.builder(
                        Text.translatable("autoclicker.screen.btn.right")
                                .formatted(!isLeft ? Formatting.GREEN : Formatting.WHITE),
                        btn -> { ProfileManager.setActiveClickButton(ClickButton.RIGHT); rebuildWidgets(); })
                .dimensions(WIDGET_X + 62, ROW_BUTTON, 58, 16).build());

        boolean dc = active.isDoubleClick();
        addDrawableChild(ButtonWidget.builder(
                        Text.translatable(dc
                                        ? "autoclicker.screen.btn.doubleclick.on"
                                        : "autoclicker.screen.btn.doubleclick.off")
                                .formatted(dc ? Formatting.GREEN : Formatting.RED),
                        btn -> { ProfileManager.setActiveDoubleClick(!ProfileManager.getActive().isDoubleClick()); rebuildWidgets(); })
                .dimensions(WIDGET_X, ROW_DOUBLECLICK, FIELD_W, 16).build());
    }

    private void buildActionButtons() {
        int y       = height - 30;
        int centerX = width / 2;

        addDrawableChild(ButtonWidget.builder(
                        Text.translatable("autoclicker.screen.btn.duplicate"),
                        btn -> { if (ProfileManager.duplicateActive() != -1) rebuildWidgets(); })
                .dimensions(centerX - 118, y, 74, 20).build());

        ButtonWidget btnDelete = ButtonWidget.builder(
                        Text.translatable("autoclicker.screen.btn.delete").formatted(Formatting.RED),
                        btn -> { if (ProfileManager.deleteActive()) rebuildWidgets(); })
                .dimensions(centerX - 38, y, 74, 20).build();
        btnDelete.active = ProfileManager.getProfiles().size() > 1;
        addDrawableChild(btnDelete);

        addDrawableChild(ButtonWidget.builder(
                        Text.translatable("autoclicker.screen.btn.close"),
                        btn -> close())
                .dimensions(centerX + 42, y, 74, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("autoclicker.screen.title").formatted(Formatting.GOLD),
                width / 2, 8, 0xFFFFFFFF);

        Text counter = Text.translatable("autoclicker.screen.profile_counter",
                        ProfileManager.getActiveIndex() + 1,
                        ProfileManager.getProfiles().size())
                .formatted(Formatting.GRAY);
        context.drawTextWithShadow(textRenderer, counter,
                width - textRenderer.getWidth(counter) - PADDING, 8, 0xFFFFFFFF);

        context.fill(PADDING, 46, width - PADDING, 47, 0x88AAAAAA);
        context.fill(PADDING, height - 38, width - PADDING, height - 37, 0x88AAAAAA);

        int textOffsetY = 4;
        context.drawTextWithShadow(textRenderer,
                Text.translatable("autoclicker.screen.field.name"),
                LABEL_X, ROW_NAME + textOffsetY, 0xFFAAAAFF);

        context.drawTextWithShadow(textRenderer,
                Text.translatable("autoclicker.screen.field.delay"),
                LABEL_X, ROW_DELAY + textOffsetY, 0xFFAAAAFF);

        context.drawTextWithShadow(textRenderer,
                Text.translatable("autoclicker.screen.field.button"),
                LABEL_X, ROW_BUTTON + textOffsetY, 0xFFAAAAFF);

        context.drawTextWithShadow(textRenderer,
                Text.translatable("autoclicker.screen.field.doubleclick"),
                LABEL_X, ROW_DOUBLECLICK + textOffsetY, 0xFFAAAAFF);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        ProfileManager.save();
        assert client != null;
        client.setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}