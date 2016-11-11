package org.vaadin.crudui.impl.form;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.vaadin.crudui.AbstractAutoGeneratedCrudFormFactory;
import org.vaadin.crudui.CrudFormConfiguration;

/**
 * @author Alejandro Duarte
 */
public class GridLayoutCrudFormFactory<T> extends AbstractAutoGeneratedCrudFormFactory<T> {

    private int columns;
    private int rows;

    public GridLayoutCrudFormFactory(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }

    @Override
    public Component buildNewForm(T domainObject, CrudFormConfiguration configuration) {
        GridLayout gridLayout = new GridLayout(columns, rows);
        gridLayout.setWidth("100%");
        gridLayout.setSpacing(true);
        gridLayout.setSizeUndefined();

        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.setSizeUndefined();

        VerticalLayout mainLayout = new VerticalLayout(gridLayout, footerLayout);
        mainLayout.setComponentAlignment(footerLayout, Alignment.BOTTOM_RIGHT);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        FieldGroup fieldGroup = new FieldGroup(new BeanItem<T>(domainObject));
        fieldGroup.setFieldFactory(fieldFactory);

        addFields(configuration, fieldGroup, gridLayout);

        Button button = new Button(configuration.getButtonCaption(), configuration.getButtonIcon());
        button.setStyleName(configuration.getButtonStyle());
        footerLayout.addComponent(button);

        button.addClickListener(e -> {
            try {
                fieldGroup.commit();
                configuration.getButtonClickListener().buttonClick(e);

            } catch (CommitException e1) {
                Notification.show(configuration.getErrorMessage());
            }
        });

        return mainLayout;
    }

}
