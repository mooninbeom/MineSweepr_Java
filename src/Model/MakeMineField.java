package Model;

import java.util.Arrays;
import java.util.Random;


// 지뢰 랜덤 생성 클래스
public class MakeMineField {

    final int width, height, mineNum;
    private final Boolean[][] mineList;

    // 가로, 세로, 지뢰 갯수를 받아 랜덤으로 지뢰 위치 생성
    public MakeMineField(int height, int width, int mineNum) {
        this.width = width;
        this.height = height;
        this.mineNum = mineNum;
        this.mineList = new Boolean[width][height];

        var random = new Random();
        for( Boolean[] arr: mineList) {
            Arrays.fill(arr, false);
        }

        var loop = 0;

        while(loop < mineNum) {
            var x = random.nextInt(height);
            var y = random.nextInt(width);

            if (!mineList[x][y]) {
                mineList[x][y] = true;
                loop++;
            }
        }


    }

    // 지뢰 위치가 담긴 Boolean 배열 반환
    public Boolean[][] getMineList() {
        return this.mineList;
    }

}



