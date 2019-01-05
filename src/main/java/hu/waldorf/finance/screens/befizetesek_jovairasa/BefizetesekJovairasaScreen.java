package hu.waldorf.finance.screens.befizetesek_jovairasa;

import com.google.inject.Inject;
import hu.waldorf.finance.model.Befizetes;
import hu.waldorf.finance.repository.BefizetesRepository;
import hu.waldorf.finance.screens.Screen;
import hu.waldorf.finance.screens.ScreenLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.text.SimpleDateFormat;
import java.util.List;

public class BefizetesekJovairasaScreen implements Screen {
    private final ScreenLoader screenLoader;
    private final BefizetesRepository befizetesRepository;
    @FXML
    private TableView tableView;

    @Inject
    public BefizetesekJovairasaScreen(ScreenLoader screenLoader, BefizetesRepository befizetesRepository) {
        this.screenLoader = screenLoader;
        this.befizetesRepository = befizetesRepository;
    }

    @Override
    public void init() {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        List<Befizetes> befizetesek = befizetesRepository.findNemFeldolgozottak();

        ObservableList items = tableView.getItems();
        befizetesek.forEach(befizetes -> {
            String importForras = befizetes.getImportForras();

            String forras;
            if (importForras.toLowerCase().contains("erste")) {
                forras = "E";
            } else if (importForras.toLowerCase().contains("magnet")) {
                forras = "M";
            } else {
                forras = "?";
            }

            String beerkezesIdopontja = DATE_FORMAT.format(befizetes.getKonyvelesiNap());

            items.add(new BefizetesJovairasaRow(
                    forras,
                    beerkezesIdopontja,
                    befizetes.getBefizetoNev() + "\n(" + befizetes.getBefizetoSzamlaszam() + ")\n" + befizetes.getKozlemeny(),
                    "" + befizetes.getOsszeg()
            ));
        });
    }

    @FXML
    public void onRefresh(ActionEvent actionEvent) {
        screenLoader.hide(actionEvent);
        screenLoader.loadScreen(getClass());
    }

}
