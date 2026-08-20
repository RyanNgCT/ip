// imports
import java.util.Scanner;

public class AnswerMe {
    public static final String BOTNAME = "AnswerMe";
    public static final String HLINE = "____________________________________________________________";
    private static final Scanner SCANNER = new Scanner(System.in);

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
            exitProgram = echo(userResponse);
        }
        while (!exitProgram);

        System.out.println("\nBye. Hope to see you again soon!");
        System.out.println(AnswerMe.HLINE);
    }

    public static String readUserInput() {
        return SCANNER.nextLine();
    }

    public static boolean echo(String userResponse) {
        if (userResponse.equalsIgnoreCase("bye")) {
            return true;
        }
        else {
            System.out.println("\t" + AnswerMe.HLINE);
            System.out.println("\t" + userResponse);
            System.out.println("\t" + AnswerMe.HLINE);
            return false;
        }
    }
}
