import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String[] strings = {"arr", "car", "mar", "ou", "roma", "vorona", "dom", "carto", "marka"};

        Scanner scanner = new Scanner(System.in);
        String necessary = scanner.nextLine();
        int index;

        index = Arrays.binarySearch(strings, necessary);

        if (index < 0) {
            System.out.println("Данной строки нет!");
        } else {
            for (int i = strings[index].length() - 1; i >= 0; i--) {
                System.out.print(strings[index].charAt(i));
            }
        }
    }
}
