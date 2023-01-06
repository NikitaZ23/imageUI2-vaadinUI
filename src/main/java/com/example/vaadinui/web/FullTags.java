package com.example.vaadinui.web;

import com.example.vaadinui.dto.ImageDto;
import com.example.vaadinui.dto.TagDto;
import com.example.vaadinui.service.WebService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Route("tags")
public class FullTags extends AppLayout {
    Grid<TagDto> grid;
    Label label;
    ImageDto image;
    Button buttonEnd;
    Button buttonSet;
    Grid<TagDto> grid2;

    WebService service;


    public FullTags() {
        VerticalLayout layoutMain = new VerticalLayout();
        VerticalLayout layoutL = new VerticalLayout();
        VerticalLayout layoutR = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        service = new WebService();

        grid = new Grid<>();
        grid2 = new Grid<>();
        grid2.setSelectionMode(Grid.SelectionMode.MULTI);

        label = new Label();
        buttonEnd = new Button("Назад");
        buttonEnd.addClickListener((event -> UI.getCurrent().navigate(MainWindow.class)));

        buttonSet = new Button("Привязать");
        buttonSet.addClickListener((buttonClickEvent -> {
            GridSelectionModel<TagDto> selectionModel = grid2.getSelectionModel();

            Set<String> tags = new HashSet<>();
            selectionModel.getSelectedItems().forEach(tag -> tags.add(tag.getName()));

            service.getIwtTagsName(image.getId()).forEach((tag -> tags.add(tag.getName())));

            service.createIWT(image.getId(), new ArrayList<>(tags));

            refreshAll();
        }));

        layoutMain.add(label);

        layoutL.add(grid);
        layoutL.add(buttonEnd);
        layoutL.setMinWidth("600px");

        layoutR.add(grid2);
        layoutR.add(buttonSet);
        layoutR.setMinWidth("600px");

        horizontalLayout.add(layoutL, layoutR);

        layoutMain.add(horizontalLayout);

        setContent(layoutMain);
    }

    public void setImage(ImageDto image) {
        this.image = image;
    }

    public void refreshAll() {
        grid.setItems();

        label.setText(image.getName());

        grid.setItems(service.getIwtTagsName(image.getId()));
        grid2.setItems();

        List<TagDto> tags = new ArrayList<>();
        tags.addAll(service.getTags());

        grid2.setItems(tags);
    }

    @PostConstruct
    public void fillGrid() {
        grid.addColumn(TagDto::getName).setHeader("Name");
        grid.addColumn(new NativeButtonRenderer<>("Удалить", contact -> {
            service.deleteTag(image.getId(), contact.getId());

            refreshAll();
        }));

        grid2.addColumn(TagDto::getName).setHeader("Name");
    }
}
