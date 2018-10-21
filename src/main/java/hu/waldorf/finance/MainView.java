package hu.waldorf.finance;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.EmailValidator;
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

        TextField tamogato = new TextField("Támogató (Szerződő)");
        TextField emailCim = new TextField("Email-cím");

        Binder<FamilyDTO> binder = new Binder<>();
        binder
                .forField(tamogato)
                .withValidator((Validator<String>) (value, context) -> {
                    if (value == null || value.trim().isEmpty()) {
                        return ValidationResult.create("Töltse ki a mezőt.", ErrorLevel.ERROR);
                    }

                    return ValidationResult.ok();
                })
                .bind(FamilyDTO::getSupporter, FamilyDTO::setSupporter);

        binder
                .forField(emailCim)
                .withValidator(new EmailValidator("Valódi e-mail címet adjon meg."))
                .bind(FamilyDTO::getSupporterEmail, FamilyDTO::setSupporterEmail);

        add(tamogato,
                emailCim,
                new Button("Click me", e -> {
                    FamilyDTO newFamily = new FamilyDTO();
                    try {
                        binder.writeBean(newFamily);
                    } catch (ValidationException ex) {
                        throw new RuntimeException(ex);
                    }
                    showNotification(newFamily);
                }
                ));
    }

    private void showNotification(FamilyDTO newFamily) {
        Notification.show("Hello Spring+Vaadin user! " + newFamily.getSupporter() + " - " + newFamily.getSupporterEmail());
        familyService.addFamily();
    }
}