package com.example.vaadinui.web;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Route("tags")
public class FullTags extends AppLayout {
  //  Grid<Tag> grid;
    Label label;

   // Image image;

    Button buttonEnd;
    Button buttonAdd;
    Button buttonSet;
 //   Grid<Tag> grid2;

//    @Autowired
//    ImWithTagsServiceImp imWithTagsServiceImp;
//
//    @Autowired
//    TagServiceImp tagServiceImp;

    public FullTags() {
        VerticalLayout layoutMain = new VerticalLayout();
        VerticalLayout layoutL = new VerticalLayout();
        VerticalLayout layoutR = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();

//        grid = new Grid<>();
//        grid2 = new Grid<>();
//        grid2.setSelectionMode(Grid.SelectionMode.MULTI);

        label = new Label();
        buttonEnd = new Button("Назад");
        buttonEnd.addClickListener((event -> UI.getCurrent().navigate(MainWindow.class)));

        buttonSet = new Button("Привязать");
//        buttonSet.addClickListener((buttonClickEvent -> {
//            GridSelectionModel<Tag> selectionModel = grid2.getSelectionModel();
//
//            Set<String> tags = new HashSet<>();
//            selectionModel.getSelectedItems().forEach(tag ->
//            {
//                tags.add(tag.getName());
//                System.out.println(tag.getName() + " OOOOOOOOOOOO");
//            });
//
//            imWithTagsServiceImp.getTags(image.getId()).forEach((tag -> tags.add(tag.getName())));
//
//            tags.forEach(s -> System.out.println(s + " LLLLLLLLLLLLLL"));
//
//            imWithTagsServiceImp.createIWT(image.getId(), new ArrayList<>(tags));
//            refreshAll();
//        }));

        layoutMain.add(label);

        //layoutL.add(grid);
        layoutL.add(buttonEnd);
        layoutL.setMinWidth("600px");

        //layoutR.add(grid2);
        layoutR.add(buttonSet);
        layoutR.setMinWidth("600px");

        horizontalLayout.add(layoutL, layoutR);

        layoutMain.add(horizontalLayout);

        setContent(layoutMain);
    }

//    public void setImage(Image image) {
//        this.image = image;
//    }
//
//    public void refreshAll() {
//        grid.setItems();
//
//        label.setText(image.getName());
//
//        grid.setItems(imWithTagsServiceImp.getTags(image.getId()));
//
//        grid2.setItems();
//
//        List<Tag> tags = new ArrayList<>();
//        tagServiceImp.findAll().forEach(tags::add);
//
//        grid2.setItems(tags);
//    }
//
//    @PostConstruct
//    public void fillGrid() {
//        grid.addColumn(Tag::getName).setHeader("Name");
//        grid.addColumn(new NativeButtonRenderer<>("Удалить", contact -> {
//            System.out.println(contact.getName() + contact.getId());
//            imWithTagsServiceImp.delete(imWithTagsServiceImp.findByOneObject(image.getId(), contact.getId()).get().getUuid());
//            refreshAll();
//        }));
//
//        grid2.addColumn(Tag::getName).setHeader("Name");
//    }
}
