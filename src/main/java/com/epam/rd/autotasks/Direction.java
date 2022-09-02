package com.epam.rd.autotasks;

import java.util.Optional;

public enum Direction {
    N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);

    Direction(final int degrees) {
        this.degrees = degrees;
    }

    private int degrees;

    public static Direction ofDegrees(int degrees) {
        for (Direction dir : Direction.values()) {
            if (dir.degrees == transformNumberInDegrees(degrees)) {
                return dir;
            }
        }
        return null;
    }

    public static Direction closestToDegrees(int degrees) {
        //https://www.campbellsci.com/blog/convert-wind-directions

        long index  = transformNumberInDegrees(degrees);


        //Для 16ти напрямків - index = Math.round(index/22.05); //буде знайдено індекс в межах від 0 до 16
        index = Math.round(index/45.0005); //похибка 0.0005

        //System.out.println("Index of Direction - " + index);
        return Direction.values()[(int)index];
    }

    public Direction opposite() {
        /**
         * 1) Взяти індекс обраної сторони та добавити до неї половину від максимальної кількості сторін
         * 2) Повернути залишок від отриманого числа, аби залишитись в межах кількості сторін
         * 3) Опустити мінус у відємних значеннь
         */

        int directionIndex = Math.abs( (this.ordinal()+4) % 8);
        //System.out.println("opposite - " + Direction.values()[directionIndex]);
        return Direction.values()[directionIndex];
    }

    public int differenceDegreesTo(Direction direction) {
    /*    int result;
        if(this.degrees==0) result = transformNumberInDegrees(direction.degrees);
       else result = transformNumberInDegrees(direction.degrees) - this.degrees;
        System.out.println("differenceDegreesTo " + this.degrees + " - " +direction.degrees + " = "+ result);

        return Math.abs(result);*/

        int result;
        if(this.degrees==0) result = transformNumberInDegrees(direction.degrees);
        else result = transformNumberInDegrees(direction.degrees) - this.degrees;

        //якщо значення більше 180, значить воно буде ближче до своєї протилежності
        //тест 9 - 315 буде ближче до 0, тобто між ними різниця лишень 45 градусів а не 315
        if (result>180) result = Math.abs(360-result);

        //System.out.println("differenceDegreesTo " + this.degrees + " - " +direction.degrees + " = "+ result);

        return Math.abs(result);
    }

    private static int transformNumberInDegrees(int degrees){
        /**
         * Відємні значення в межах -360 будуть враховуватись як протилежні
         * Будь яке число більше 360 буде приведене до цього проміжку
         */
        if(degrees <0){
        return Math.abs((360+degrees)%360);}
        else return Math.abs((degrees)%360);
        //System.out.println("Degrees in range 360 - " + index);
    }
}
