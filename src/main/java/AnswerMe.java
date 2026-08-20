// imports
import java.util.Scanner;
import java.util.ArrayList;

public class AnswerMe {
    public static final String BOTNAME = "AnswerMe";
    public static final String HLINE = "____________________________________________________________";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static ArrayList<String> taskList = new ArrayList<>();

    public static void main(String[] args) {
        String banner = """
                      >>                                                        >=>       >=>          \s
                     >>=>                                                       >> >=>   >>=>          \s
                    >> >=>     >==>>==>   >===>  >=>      >=>   >==>    >> >==> >=> >=> > >=>   >==>   \s
                   >=>  >=>     >=>  >=> >=>      >=>  >  >=> >>   >=>   >=>    >=>  >=>  >=> >>   >=> \s
                  >=====>>=>    >=>  >=>   >==>   >=> >>  >=> >>===>>=>  >=>    >=>   >>  >=> >>===>>=>\s
                 >=>      >=>   >=>  >=>     >=>  >=>>  >=>=> >>         >=>    >=>       >=> >>       \s
                >=>        >=> >==>  >=> >=> >=> >==>    >==>  >====>   >==>    >=>       >=>  >====>  \s
                """;
        System.out.println(banner);
        System.out.println("Hello! I'm " + BOTNAME + ", your personal assistant bot.\nWhat can I do for you today?\n");
        System.out.println(AnswerMe.HLINE);
        System.out.println("What can I do for you today?");
        System.out.println(AnswerMe.HLINE);

        boolean exitProgram = false;
        do {
            String userResponse = readUserInput();
            exitProgram = process(userResponse);
        }
        while (!exitProgram);

        printIndentedString("Bye. Hope to see you again soon!");
    }

    public static String readUserInput() {
        return AnswerMe.SCANNER.nextLine();
    }

    public static boolean process(String userResponse) {
        if (userResponse.equalsIgnoreCase("bye")) {
            return true;
        }
        else if (userResponse.equalsIgnoreCase("list")) {
            System.out.println(AnswerMe.HLINE);
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println((i + 1) + ". " + taskList.get(i));
            }
            System.out.println(AnswerMe.HLINE + "\n");
        }
        else {
            taskList.add(userResponse);
            printIndentedString("added: " + userResponse);
        }
        return false;
    }

    public static void printIndentedString(String toPrint) {
        System.out.println(AnswerMe.HLINE);
        System.out.println(toPrint);
        System.out.println(AnswerMe.HLINE + "\n");
    }
}
