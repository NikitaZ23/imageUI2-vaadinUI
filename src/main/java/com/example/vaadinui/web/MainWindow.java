package com.example.vaadinui.web;

import com.example.vaadinui.common.ImageFull;
import com.example.vaadinui.dto.ImageDto;
import com.example.vaadinui.service.WebService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Route("mainWindow")
public class MainWindow extends AppLayout {
    VerticalLayout layout;
    HorizontalLayout horizontalLayout;

    Grid<ImageFull> grid;

    MessageInput input;
    List<ImageFull> fulls;

    Button button;

    Label label;

    WebService service;

    public MainWindow() {
        layout = new VerticalLayout();
        horizontalLayout = new HorizontalLayout();

        button = new Button("Refresh");
        label = new Label("Asd");
        grid = new Grid<>();
        service = new WebService();

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();

        Upload upload = new Upload(buffer);

        upload.addSucceededListener(event -> {
            service.createFile(buffer, event.getFileName());
            refreshAll();
        });

        input = new MessageInput();
        input.setMinWidth("200");
        input.addSubmitListener(submitEvent -> {
            Notification.show("Filter: " + submitEvent.getValue(),
                    3000, Notification.Position.MIDDLE);

            grid.setItems(getList(submitEvent.getValue()));

        });

        button.addClickListener(buttonClickEvent -> refreshAll());

        horizontalLayout.add(upload);
        horizontalLayout.add(input);
        horizontalLayout.add(button);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        layout.add(label);
        layout.add(horizontalLayout);
        layout.add(grid);

        setContent(layout);
    }

    @PostConstruct
    public void fillGrid() {
        grid.addColumn(ImageFull::getName).setHeader("Name");
        grid.addColumn(ImageFull::getTags).setHeader("Tags");

        grid.addColumn(new NativeButtonRenderer<>("Редактировать", contact -> UI.getCurrent().navigate(FullTags.class).ifPresent(fullTags ->
        {
            fullTags.setImage(service.getImageName(contact.getName()));

            fullTags.refreshAll();
        })));

        grid.setItems(getList());
    }

    public void refreshAll() {
        grid.setItems();
        grid.setItems(getList());
    }

    public List<ImageFull> getList() {
        fulls = new ArrayList<>();

        service.getImages().forEach(image -> {
            List<String> tags = new ArrayList<>();

            service.getIwtImages(image.getId()).forEach(imWithTagsDto ->
                    tags.add(service.getTag(imWithTagsDto.getId_tg()).getName()));

            fulls.add(new ImageFull(image.getName(), tags));
        });

        return fulls;
    }

    public List<ImageFull> getList(String nameTag) {
        fulls = new ArrayList<>();

        service.getIwtTags(service.getTag(nameTag).getId()).forEach(imWithTagsDto -> {
            ImageDto imageDto = service.getImage(imWithTagsDto.getId_im());

            List<String> tags = new ArrayList<>();
            service.getIwtImages(imageDto.getId()).forEach(imWithTagsDto2 -> tags.add(service.getTag(imWithTagsDto2.getId_tg()).getName()));
            fulls.add(new ImageFull(imageDto.getName(), tags));
        });

        return fulls;
    }

}
