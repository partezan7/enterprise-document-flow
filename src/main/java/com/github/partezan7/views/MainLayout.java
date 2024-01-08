package com.github.partezan7.views;

import com.github.partezan7.data.entity.user.Role;
import com.github.partezan7.security.SecurityService;
import com.github.partezan7.views.list.DepartmentView;
import com.github.partezan7.views.list.EmployeeView;
import com.github.partezan7.views.list.StatusView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Документооборот");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        String u = securityService.getAuthenticatedUser().getUsername();
        Button logout = new Button("Выйти из: " + u, e -> securityService.logout());

        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);
    }

    private void createDrawer() {
        VerticalLayout leftPupUpMenu = new VerticalLayout(
                new RouterLink("Сотрудники", EmployeeView.class));

        if (securityService.isUserHaveRights(Role.ADMIN)) {
            leftPupUpMenu.add(
                    new RouterLink("Подразделения", DepartmentView.class),
                    new RouterLink("Статусы", StatusView.class)
            );
        }

        addToDrawer(leftPupUpMenu);
    }
}