package hu.waldorf.finance;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Route
@Component
public class MainView extends VerticalLayout {
    private final FamilyService familyService;

    @Autowired
    public MainView(FamilyService familyService) {
        this.familyService = familyService;

        add(new Button("Click me", e -> showNotification()));
    }

    private void showNotification() {
        Notification.show("Hello Spring+Vaadin user!");
        familyService.addFamily();
    }
}