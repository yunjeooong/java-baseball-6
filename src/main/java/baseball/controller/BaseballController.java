package baseball.controller;

import baseball.service.ScoreCalculator;
import baseball.util.Converter;
import baseball.util.RandomNumbersMaker;
import baseball.view.InputView;
import baseball.view.OutputView;
import validator.NumberValidator;
import validator.Validator;

import java.util.List;

public class BaseballController {
    private final OutputView outputView;
    private final InputView inputView;
    private ScoreCalculator scoreCalculator;

    private  NumberValidator validator;



    public BaseballController(InputView inputView, OutputView outputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        outputView.printStartMessage();
        app();
    }

    public void app() {

        List<Integer> computer = RandomNumbersMaker.maker();
        System.out.println("computer = " + computer);  // 디버깅용 출력

        while(true){
            String guessNumber = String.valueOf(inputView.getGuessNumber());
            List<Integer> player = Converter.convertList(guessNumber);
            this.scoreCalculator = new ScoreCalculator(computer, player);
            int strike = scoreCalculator.calculateStrike();
            int ball = scoreCalculator.calculateBall();
            outputView.printResultMessage(strike, ball);
            if (strike == 3) {
                outputView.printGameEndMessage();
                break;
            }

        }

        String gameCommand = inputView.getRestartMessage();
        if (gameCommand.equals("1")) {
            app();
            return;
        }
        if (gameCommand.equals("2")) {
            return;
        }
        throw new IllegalArgumentException("재시작/종료 명령이 잘못되었습니다."); //필수
    }
    private String readGuessNumber() {
        // 입력받은 숫자 유효성 검증 후 반환
        String value = String.valueOf(inputView.getGuessNumber());
        validator.validate(value);  // NumberValidator를 통한 유효성 검증
        return value;
    }


    }

