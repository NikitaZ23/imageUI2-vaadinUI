package com.example.vaadinui.web;

import com.example.vaadinui.dto.ImageDto;
import com.example.vaadinui.dto.TagDto;
import com.example.vaadinui.service.imp.IWTServiceImp;
import com.example.vaadinui.service.imp.TagServiceImp;
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
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Route("tags")
public class FullTags extends AppLayout {
    Grid<TagDto> grid;
    Label label;
    ImageDto image;
    Button buttonEnd;
    Button buttonSet;
    Grid<TagDto> grid2;

    @Autowired
    TagServiceImp tagService;

    @Autowired
    IWTServiceImp iwtService;

    public FullTags() {
        VerticalLayout layoutMain = new VerticalLayout();
        VerticalLayout layoutL = new VerticalLayout();
        VerticalLayout layoutR = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        grid = new Grid<>();
        grid2 = new Grid<>();
        grid2.setSelectionMode(Grid.SelectionMode.MULTI);

        label = new Label();
        buttonEnd = new Button("Назад");
        buttonEnd.addClickListener((event -> UI.getCurrent().navigate(MainWindow.class)));

        buttonSet = new Button("Привязать");
        buttonSet.addClickListener((buttonClickEvent -> {
            GridSelectionModel<TagDto> selectionModel = grid2.getSelectionModel();

            List<String> tags = new ArrayList<>();
            selectionModel.getSelectedItems().forEach(tag -> tags.add(tag.getName()));

            iwtService.updateIWT(image.getId(), tags);

            grid.setItems(iwtService.getIwtTagsName(image.getId()));
            (grid2.getSelectionModel()).deselectAll();
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

        grid.setItems(iwtService.getIwtTagsName(image.getId()));
        grid2.setItems();

        List<TagDto> tags = new ArrayList<>(tagService.getTags());

        grid2.setItems(tags);
    }

    @PostConstruct
    public void fillGrid() {
        grid.addColumn(TagDto::getName).setHeader("Name");
        grid.addColumn(new NativeButtonRenderer<>("Удалить", contact -> {
            iwtService.deleteTag(image.getId(), contact.getId());

            refreshAll();
        }));

        grid2.addColumn(TagDto::getName).setHeader("Name");
    }
}
