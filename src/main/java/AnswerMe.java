/**
 * Entry point for the AnswerMe chatbot.
 */
public class AnswerMe {
    public static void main(String[] args) {
        String botName = "AnswerMe";
        String hLine = "____________________________________________________________";
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
        System.out.println("Hello! I'm " + botName + ", your personal assistant bot.\nWhat can I do for you today?\n");
        System.out.println(hLine);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(hLine);
    }
}
