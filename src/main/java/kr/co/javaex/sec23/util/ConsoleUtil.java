package kr.co.javaex.sec23.util;

import java.util.Scanner;

public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);


    /**
     * 문자열 입력
     * @param message
     * @return scanner.nextLine()
     */
    public static String readString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * 숫자 입력 (버퍼 제거)
     * @param message
     * @return input
     */
    public static int readInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("숫자만 입력해주세요.");
            scanner.next();
            System.out.print(message);
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    /**
     * 숫자 입력 (버퍼 제거) - Long 전용
     *  @param message
     *  @return input
     */
    public static long readLong(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("숫자만 입력해주세요.");
            scanner.next();
            System.out.print(message);
        }
        long input = scanner.nextLong();
        scanner.nextLine();
        return input;
    }
}
