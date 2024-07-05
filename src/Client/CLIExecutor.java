package Client;

import Client.Controler.Client;
/**
 * 
 */
public class CLIExecutor {
    /**
     * Main
     */
    public static Client cli;
    public static void main(String[] args) {
        if(args.length!=1){
            System.out.println("Using default localhost server");
            cli=new Client("127.0.0.1");
        }else cli=new Client(args[0]);
    }

}