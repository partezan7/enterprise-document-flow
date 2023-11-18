package com.github.partezan7.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Вход | Документооборот")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setI18n(createLoginI18n());
        login.setAction("login");

        add(new H1("Документооборот"));
        add(new Span("Username: user, Password: password"));
        add(new Span("Username: admin, Password: password"));
        add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    private LoginI18n createLoginI18n() {
        LoginI18n i18n = LoginI18n.createDefault();

        // define all visible Strings to the values you want
        // this code is copied from above-linked example codes for Login
        // in a truly international application you would use i.e. `getTranslation(USERNAME)` instead of hardcoded
        // string values. Make use of your I18nProvider
        i18n.getForm()
                .setTitle("Вход");
        i18n.getErrorMessage()
                .setTitle("Неверное имя пользователя / пароль");
        i18n.getErrorMessage()
                .setMessage("Проверьте свое имя пользователя и пароль и повторите попытку.");
        i18n.getForm()
                .setUsername("Имя пользователя"); // this is the one you asked for.
        i18n.getErrorMessage()
                .setUsername("Введите имя пользователя");
        i18n.getForm()
                .setPassword("Пароль");
        i18n.getErrorMessage()
                .setPassword("Введите пароль");
        i18n.getForm()
                .setSubmit("Войти");
        i18n.getForm()
                .setForgotPassword("Восстановить");
        i18n.setAdditionalInformation("Если вам нужно предоставить пользователю дополнительную информацию " +
                "(например, учетные данные по умолчанию), это самое подходящее место.");
        return i18n;
    }
}