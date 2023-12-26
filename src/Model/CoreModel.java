package Model;

import Views.GameOverView;
import Views.MainView;

import java.util.*;

public class CoreModel {
    private static final int[] dervX = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] dervY = {-1, 0, 1, -1, 1, -1, 0, 1};
    private final MakeMineField mineField;
    private final Boolean[][] isVisited;
    private final Boolean[][] isSelected;
    private final int[][] mineSolution;
    private final MainView mainView;
    private GameTimer gameTimer;
    private  Thread timerThread;




    // Views.StartView 에서 가로 세로, 지뢰 갯수를 받아 모델 생성
    public CoreModel(int height, int width, int mineNum, MainView view) {
        this.mainView = view;
        // 지뢰 객체 생성
        this.mineField = new MakeMineField(height, width, mineNum);
        mineSolution = new int[height][width];
        setTimer();


        /*
        for(int i=0; i<test1.length; i++) {
            for(int j=0; j<test1.length; j++) {
                if (test1[i][j]) System.out.print("* ");
                else System.out.print("0 ");
            }
            System.out.println();
        }
        System.out.println("------------------------------------");

         */


        // 답지 0으로 초기화
        for(int[] test: mineSolution) {
            Arrays.fill(test,0);
        }

        // BFS 위한 방문 flag 표시
        isVisited = new Boolean[height][width];
        for(Boolean[] test: isVisited) {
            Arrays.fill(test, false);
        }

        // 답 판별을 위한 배열
        isSelected = new Boolean[height][width];
        for(Boolean[] test: isSelected) {
            Arrays.fill(test, false);
        }


        this.mineSolution();
    }

    // 지뢰 위치 getter
    public Boolean getIsMine(int x, int y) {
        return this.mineField.getMineList()[x][y];
    }

    // 지뢰 찾기 답지 getter
    public int[][] getMineSolution() {
        return this.mineSolution;
    }



    // 매설된 지뢰를 바탕으로 화면에 보여줄 지뢰찾기 답지
    // BFS 사용
    private void mineSolution() {
        var mine = this.mineField.getMineList();
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(0, 0));
        isVisited[0][0] = true;

        while (!queue.isEmpty()) {
            var now = queue.poll();
            var nowX = now.x;
            var nowY = now.y;

            for (int i = 0; i < 8; i++) {
                var tempX = nowX + dervX[i];
                var tempY = nowY + dervY[i];

                if (tempX < 0 || tempX > mine.length - 1) continue;
                if (tempY < 0 || tempY > mine.length - 1) continue;
                if (mine[tempX][tempY]) {
                    mineSolution[nowX][nowY]++;
                    mineSolution[tempX][tempY] = -1;
                }
                if (isVisited[tempX][tempY]) continue;

                isVisited[tempX][tempY] = true;

                queue.add(new Point(tempX, tempY));
            }
        }
    }

    // 버튼을 클릭했을 시 필요한 화면들을 바꾸는 메소드
    public void cellClick(int x, int y) {
        var selected = new Point(x,y);
        Queue<Point> queue = new LinkedList<>();
        queue.add(selected);

        while (!queue.isEmpty()) {
            var now = queue.poll();
            var nowCell = mineSolution[now.x][now.y];
            isSelected[now.x][now.y] = true;

            // if clicked over 0 cell
            // 현재 셀에 숫자를 보여줌
            if ( nowCell > 0 ) {
                mainView.changeButtonTitle(now.x, now.y);
                continue;
            } else if ( nowCell == 0 ) { // this cell is zero
                mainView.changeBackgroundColor(now.x, now.y);
            } else { // 지뢰를 클릭함, 게임오버
                mainView.mineButton(now.x, now.y);

                var time = this.gameTimer.getSecondAndStop();

                new GameOverView(this, mainView, false, time);
                break;
            }

            for(int i=0; i<8; i++) {
                var tempX = now.x + dervX[i];
                var tempY = now.y + dervY[i];

                if (tempX < 0 || tempX > mineSolution.length - 1) continue;
                if (tempY < 0 || tempY > mineSolution.length - 1) continue;
                if (isSelected[tempX][tempY]) continue;


                queue.add(new Point(tempX, tempY));
            }
        }
    }

    // 타이머 초기화
    private void setTimer() {
        this.gameTimer = new GameTimer(this);
        this.timerThread = new Thread(this.gameTimer);
        timerThread.start();
    }

    // 타이머 getter
    public GameTimer getGameTimer() {
        return this.gameTimer;
    }


    // 깃발 세우기 메소드
    public void exeFlagButton(int x, int y) {
        mainView.flagButton(x,y);
    }


    // 알고리즘에 사용되는 내부 클래스
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}

