package hu.waldorf.finance.screens;

import com.google.inject.Inject;

public class BefizetesekJovairasaScreen implements Screen {
    private final ScreenLoader screenLoader;

    @Inject
    public BefizetesekJovairasaScreen(ScreenLoader screenLoader) {
        this.screenLoader = screenLoader;
    }
}
