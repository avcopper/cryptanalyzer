package com.javarush.cryptanalyzer.cooper;

import com.javarush.cryptanalyzer.cooper.view.View;
import com.javarush.cryptanalyzer.cooper.view.GUIView;
import com.javarush.cryptanalyzer.cooper.entity.Result;
import com.javarush.cryptanalyzer.cooper.app.Application;
import com.javarush.cryptanalyzer.cooper.utils.ResultCode;
import com.javarush.cryptanalyzer.cooper.controller.Controller;

public class Main {
    public static void main(String[] args) {
        View view = new GUIView();
        Controller controller = new Controller(view);
        Application application = new Application(controller);

        try {
            application.run();
        } catch (Exception ex) {
            view.showResult(new Result(ResultCode.ERROR, ex));
        }
    }
}
